package client.handlers

import client.logger
import client.modules.AppModule
import client.services.{CoreApi, LGCircuit}
import client.utils.{AppUtils, ConnectionsUtils}
import org.widok.moment.Moment
import shared.dtos._
import shared.models._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import diode.AnyAction._

import scala.util.{Failure, Success}

/**
  * Created by shubham.k on 12-05-2016.
  */
object ContentModelHandler {
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

  def processIntroductionNotification(response: String = ""): Unit = {
    //    toDo: Think of some better logic to identify different responses from session ping
    try {
      if (response.contains("sessionPong")) {
        val sessionPong =
          upickle.default.read[Seq[ApiResponse[SessionPong]]](response)
      } else if (response.contains("introductionNotification")) {
        val intro =
          upickle.default.read[Seq[ApiResponse[Introduction]]](response)
        LGCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default
          .read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        LGCircuit.dispatch(
          AcceptIntroductionConfirmationResponse(
            introductionConfirmationResponse(0).content))
      } else if (response.contains("connectNotification")) {
        var isNew: Boolean = true
        val connectNotification =
          upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        val content = connectNotification(0).content
        LGCircuit.dispatch(AcceptConnectNotification(content))
        val (name, imgSrc) =
          ConnectionsUtils.getNameImgFromJson(content.introProfile)
        val connections =
          LGCircuit.zoom(_.connections.connectionsResponse).value
        connections.foreach { connection =>
          if (connection.name.equals(name)) {
            isNew = false
          }
        }
        if (isNew) {
          LGCircuit.dispatch(
            AddConnection(
              ConnectionsModel("", content.connection, name, imgSrc)))
        }
      } else if (response.contains("beginIntroductionResponse")) {
        val beginIntroductionRes = upickle.default.read[Seq[ApiResponse[BeginIntroductionRes]]](response)
        LGCircuit.dispatch(PostIntroSuccess(beginIntroductionRes(0).content))
      }
    } catch {
      case e: Exception =>
      /*println("exception for upickle read session ping response")*/
    }
  }

  val responseType = Seq("sessionPong",
    "introductionNotification",
    "introductionConfirmationResponse",
    "connectNotification",
    "beginIntroductionResponse")

  def getCurrModelAndPing(viewName: String): Seq[Post] = {

    try {
      LGCircuit.dispatch(TogglePinger(viewName))
      viewName match {
        case AppModule.MESSAGES_VIEW =>
          LGCircuit.zoom(_.messages).value.get.messagesModelList
        case AppModule.PROFILES_VIEW =>
          LGCircuit.zoom(_.profiles).value.get.profilesList
        case AppModule.PROJECTS_VIEW =>
          LGCircuit.zoom(_.jobPosts).value.get.projectsModelList
      }

    } catch {
      case e: Exception =>
        Nil
    }
  }

  def getContentModel(response: String, viewName: String): Seq[Post] = {
    if (responseType.exists(response.contains(_))) {
      processIntroductionNotification(response)
      getCurrModelAndPing(viewName)
    } else {
      val msg = getCurrModelAndPing(viewName) ++
        upickle.default
          .read[Seq[ApiResponse[ResponseContent]]](response)
          .filterNot(_.content.pageOfPosts.isEmpty)
          .flatMap(content => filterContent(content, viewName))
      msg.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
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
        LGCircuit.zoom(_.connections.connections).value ++ Seq(
          ConnectionsUtils.getSelfConnnection(viewName)),
        getDefaultProlog(viewName)))
    val req = SubscribeRequest(AppUtils.getSessionUri(viewName), expr)
//    LGCircuit.dispatch(ClearMessages())
    var count = 1
    subscribe()
    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
//        println(s"message post successful ${res}")
        LGCircuit.dispatch(
          UpdatePrevSearchCnxn(req.expression.content.cnxns, viewName))
        LGCircuit.dispatch(
          UpdatePrevSearchLabel(req.expression.content.label, viewName))
        refreshContent(viewName)

      case Failure(res) =>
        if (count == 3) {
//          println(s"Failure data = ${res.getMessage}")
          //            logger.log.error("Open Error modal Popup")
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

  def clearModel(viewName : String) = {
    viewName match {
      case AppModule.MESSAGES_VIEW => LGCircuit.dispatch(ClearMessages())
      case AppModule.PROFILES_VIEW => LGCircuit.dispatch(ClearProfiles())
      case AppModule.PROJECTS_VIEW => LGCircuit.dispatch(ClearProjects())
    }
  }

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
//        logger.log.debug("content post success")
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
          //            logger.log.debug("server error")
          LGCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          post()
        }
    }

  }

}
