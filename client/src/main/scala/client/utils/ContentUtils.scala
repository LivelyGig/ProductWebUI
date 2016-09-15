package client.utils

import client.handler._
import client.logger
import client.modules.AppModule
import client.services.{CoreApi, LGCircuit}
import diode.AnyAction._
import org.widok.moment.Moment
import shared.dtos._
import shared.models._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util.{Failure, Success, Try}

/**
  * Created by shubham.k on 12-05-2016.
  */
object ContentUtils {
  def filterContent(response: ApiResponse[ResponseContent], viewName: String): Option[Post] = {
    try {
      viewName match {
        case AppModule.MESSAGES_VIEW => Some(upickle.default.read[MessagePost](response.content.pageOfPosts(0)))
        case AppModule.PROFILES_VIEW => Some(upickle.default.read[ProfilesPost](response.content.pageOfPosts(0)))
        case AppModule.PROJECTS_VIEW => Some(upickle.default.read[ProjectsPost](response.content.pageOfPosts(0)))
      }

    } catch {
      case e: Exception =>
        logger.log.debug(s"Exception in content filtering: ${e.getMessage} for view ${viewName}")
        None
    }
  }

  def refreshContent(viewName: String): Unit = {
    viewName match {
      case AppModule.MESSAGES_VIEW => LGCircuit.dispatch(RefreshMessages())
      case AppModule.PROFILES_VIEW => LGCircuit.dispatch(RefreshProfiles())
      case AppModule.PROJECTS_VIEW => LGCircuit.dispatch(RefreshProjects())
    }

  }

  def getDefaultProlog(viewName: String): String = {
    viewName match {
      case AppModule.MESSAGES_VIEW => s"any([${AppUtils.MESSAGE_POST_LABEL}])"
      case AppModule.PROFILES_VIEW => s"any([${AppUtils.PROFILE_POST_LABEL}])"
      case AppModule.PROJECTS_VIEW => s"any([${AppUtils.PROJECT_POST_LABEL}])"
    }
  }

  def subsForContentAndBeginSessionPing(viewName: String): Unit = {
    val expr = Expression(
      "feedExpr",
      ExpressionContent(
        LGCircuit.zoom(_.connections.connectionsResponse).value.map(_.connection) ++ Seq(
          ConnectionsUtils.getSelfConnnection(viewName)),
        getDefaultProlog(viewName)))
    val req = SubscribeRequest(AppUtils.getSessionUri(viewName), expr)
    var count = 1
    subscribe()
    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        LGCircuit.dispatch(
          UpdatePrevSearchCnxn(req.expression.content.cnxns, viewName))
        LGCircuit.dispatch(
          UpdatePrevSearchLabel(req.expression.content.label, viewName))
        refreshContent(viewName)

      case Failure(res) =>
        if (count == 3) {
          LGCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          subscribe()
          logger.log.error("Error in subscription")
        }
    }
  }

  def subsForContent(req: SubscribeRequest, viewName: String): Unit = {
    var count = 1
    subscribe()

    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        LGCircuit.dispatch(
          UpdatePrevSearchCnxn(req.expression.content.cnxns, viewName))
        LGCircuit.dispatch(
          UpdatePrevSearchLabel(req.expression.content.label, viewName))
      case Failure(res) =>
        if (count == 3) {
          //            logger.log.error("Open Error modal Popup")
          LGCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          subscribe()
          logger.log.error("Error in subscription")
        }
    }
  }

  def getCancelSubscribeRequest(viewName: String): CancelSubscribeRequest = {
    viewName match {
      case AppModule.MESSAGES_VIEW =>
        CancelSubscribeRequest(
          LGCircuit.zoom(_.session.messagesSessionUri).value,
          LGCircuit.zoom(_.searches.previousMsgSearchCnxn).value,
          LGCircuit.zoom(_.searches.previousMsgSearchLabel).value)
      case AppModule.PROFILES_VIEW =>
        CancelSubscribeRequest(
          LGCircuit.zoom(_.session.profileSessionUri).value,
          LGCircuit.zoom(_.searches.previousProfileSearchCnxn).value,
          LGCircuit.zoom(_.searches.previousProfileSearchLabel).value)
      case AppModule.PROJECTS_VIEW =>
        CancelSubscribeRequest(
          LGCircuit.zoom(_.session.projectSessionUri).value,
          LGCircuit.zoom(_.searches.previousProjectSearchCnxn).value,
          LGCircuit.zoom(_.searches.previousProjectSearchLabel).value)

    }
  }

  def clearModel(viewName: String) = {
    viewName match {
      case AppModule.MESSAGES_VIEW => LGCircuit.dispatch(ClearMessages())
      case AppModule.PROFILES_VIEW => LGCircuit.dispatch(ClearProfiles())
      case AppModule.PROJECTS_VIEW => LGCircuit.dispatch(ClearProjects())
    }
  }


  /**
    * This function primarily deals with getting the content for the ui interaction
    * it is connected to the session ping response.
    * Session ping response consists of a number of different types of responses
    * which are filtered here and the ui is updated accordingly
    *
    * @param response This function takes the response from the session ping
    *                 It is called from the message handler refresh messages action
    * @return seq of post
    */
  def processRes(response: String): Seq[ResponseContent] = {
    // process response
    val responseArray = upickle.json.read(response).arr.map(e => upickle.json.write(e)).filterNot(_.contains("sessionPong"))
    val (cnxn, postContent, intro, cnctNot) = sortContent(responseArray)
    // three more responses session pong, begin introduction and introduction confirmation which are not processed because tney do nothing
    if (intro.nonEmpty) LGCircuit.dispatch(AddNotification(intro.map(_.content)))
    if (cnctNot.nonEmpty)  {
      val resp= cnctNot.map(e => ConnectionsUtils.getCnxnFromNot(e.content))
      LGCircuit.dispatch(UpdateConnections(resp))
    }
    if (cnxn.nonEmpty) {
      val res= cnxn.map(e => ConnectionsUtils.getCnxnFromRes(e.content))
      LGCircuit.dispatch(UpdateConnections(res))
    }
    // return the mod messages model if new messages in response otherwise return the old response
    if (postContent.nonEmpty) postContent.map(_.content)
    else Nil
  }

  /**
    * This function sort content based on their types
    * @param responseArray
    * @return
    */

  def sortContent(responseArray: Seq[String]): (Seq[ApiResponse[ConnectionProfileResponse]],
    Seq[ApiResponse[ResponseContent]],
    Seq[ApiResponse[Introduction]],
    Seq[ApiResponse[ConnectNotification]]) = {
    var remainingObj: Seq[String] = Nil
    var cnxn: Seq[ApiResponse[ConnectionProfileResponse]] = Nil
    var msg: Seq[ApiResponse[ResponseContent]] = Nil
    var intro: Seq[ApiResponse[Introduction]] = Nil
    var cnctNot: Seq[ApiResponse[ConnectNotification]] = Nil
    responseArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[ConnectionProfileResponse]](e)) match {
          case Success(a) => cnxn :+= a
          case Failure(b) => Try(upickle.default.read[ApiResponse[ResponseContent]](e)) match {
            case Success(a) => msg :+= a
            case Failure(b) => Try(upickle.default.read[ApiResponse[Introduction]](e)) match {
              case Success(a) => intro :+= a
              case Failure(b) => Try(upickle.default.read[ApiResponse[ConnectNotification]](e)) match {
                case Success(a) => cnctNot :+= a
                case Failure(b) => {
                  remainingObj :+= e
                }
              }
            }
          }
        }
    }
    (cnxn, msg, intro, cnctNot)

  }

  /*/**
    * This function yields the message model that needs to be updated
    *
    * @param response
    * @return
    */
  def getMsgModel(response: Seq[ApiResponse[ResponseContent]]): Seq[Post] = {
    val msgModelMod = getCurrMsgModel() ++
      response
        .filterNot(_.content.pageOfPosts.isEmpty)
        .flatMap(content => Try(upickle.default.read[MessagePost](content.content.pageOfPosts(0))).toOption)
    msgModelMod.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
  }*/

  def cancelPreviousAndSubscribeNew(req: SubscribeRequest, viewName: String): Unit = {
    clearModel(viewName)
    var count = 1
    cancelPrevious()
    def cancelPrevious(): Unit =
      CoreApi
        .cancelSubscriptionRequest(getCancelSubscribeRequest(viewName))
        .onComplete {
          case Success(res) =>
            subsForContent(req, viewName)
          case Failure(res) =>
            if (count == 3) {
              //            logger.log.error("server error")
              LGCircuit.dispatch(ShowServerError(res.getMessage))
            } else {
              count = count + 1
              cancelPrevious()
            }
        }
  }

  def postContent(req: SubscribeRequest): Unit = {
    var count = 1
    postMsg()
    def postMsg(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
      case Failure(fail) =>
        if (count == 3) {
          //            logger.log.error("server error")
          LGCircuit.dispatch(ShowServerError(fail.getMessage))
        } else {
          count = count + 1
          postMsg()
        }
    }
  }

  def postLabelsAndMsg(labelNames: Seq[String],
                       subscribeReq: SubscribeRequest): Unit = {
    val labelPost = LabelPost(AppUtils.getSessionUri(AppModule.MESSAGES_VIEW),
      labelNames.map(SearchesModelHandler.leaf),
      "alias")
    var count = 1
    post()
    def post(): Unit = CoreApi.postLabel(labelPost).onComplete {
      case Success(res) =>
        postContent(subscribeReq)
        LGCircuit.dispatch(
          CreateLabels(labelNames.map(SearchesModelHandler.leaf)))
      case Failure(res) =>
        if (count == 3) {
          //            logger.log.debug("server error")
          LGCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          post()
        }

    }

  }

  def postLabels(labelNames: Seq[String]): Unit = {
    val labelPost = LabelPost(
      AppUtils.getSessionUri(AppModule.MESSAGES_VIEW),
      labelNames.map(SearchesModelHandler.leaf),
      "alias")
    var count = 1
    post()
    def post(): Unit = CoreApi.postLabel(labelPost).onComplete {
      case Success(res) =>
        LGCircuit.dispatch(
          CreateLabels(labelNames.map(SearchesModelHandler.leaf)))
      case Failure(res) =>
        if (count == 3) {
          LGCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          post()
        }
    }

  }

}
