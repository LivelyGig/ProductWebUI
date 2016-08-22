package synereo.client.handlers

import org.widok.moment.Moment
import shared.dtos._
import shared.models.{ConnectionsModel, MessagePost, Post}
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import diode.AnyAction._
import synereo.client.rootmodels.SessionRootModel
import synereo.client.utils.{AppUtils, ConnectionsUtils}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by mandar.k on 5/24/2016.
  */
//scalastyle:off
object ContentHandler {
  def filterContent(messages: ApiResponse[ResponseContent]): Option[Post] = {
    try {
      Some(upickle.default.read[MessagePost](messages.content.pageOfPosts(0)))
    } catch {
      case e: Exception =>

        logger.log.debug(s"Exception in content filtering: ${e.getMessage}")
        None
    }
  }

  def processIntroductionNotification(response: String = ""): Unit = {
    //    toDo: Think of some better logic to identify different responses from session ping
    try {
      if (response.contains("sessionPong")) {
        val sessionPong = upickle.default.read[Seq[ApiResponse[SessionPong]]](response)
      } else if (response.contains("introductionNotification")) {
        val intro = upickle.default.read[Seq[ApiResponse[Introduction]]](response)
        SYNEREOCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        SYNEREOCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
      } else if (response.contains("connectNotification")) {
        var isNew: Boolean = true
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        val content = connectNotification(0).content
        SYNEREOCircuit.dispatch(AcceptConnectNotification(content))
        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(content.introProfile)
        val connections = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
        connections.foreach {
          connection => if (connection.name.equals(name)) {
            isNew = false
          }
        }
        if (isNew) {
          SYNEREOCircuit.dispatch(AddConnection(ConnectionsModel("", content.connection, name, imgSrc)))
        }
      } else if (response.contains("beginIntroductionResponse")) {
        val beginIntroductionRes = upickle.default.read[Seq[ApiResponse[BeginIntroductionRes]]](response)
        SYNEREOCircuit.dispatch(PostIntroSuccess(beginIntroductionRes(0).content))
      }
    } catch {
      case e: Exception => /*println("exception for upickle read session ping response")*/
    }
  }
  //  def processIntroductionNotification(response: String = ""): Unit = {
  //    val responseTypes = Map("sessionPong" -> Seq[ApiResponse[SessionPong]],
  //      "introductionNotification" -> Seq[ApiResponse[Introduction]],
  //      "introductionConfirmationResponse" -> Seq[ApiResponse[IntroductionConfirmationResponse]],
  //      "connectNotification" -> Seq[ApiResponse[ConnectNotification]],
  //      "beginIntroductionResponse" -> Seq[ApiResponse[BeginIntroductionRes]]
  //    )
  //    val responseMapping = responseTypes collectFirst { case (k, v) if response contains k => v }
  //    println(responseMapping)
  //    //    responseMapping match {
  //    //      case Some() =>
  //    //      case None =>
  //    //    }
  //
  //    try {
  //      if (response.contains("sessionPong")) {
  //        val sessionPong = upickle.default.read[Seq[ApiResponse[SessionPong]]](response)
  //      } else if (response.contains("introductionNotification")) {
  //        val intro = upickle.default.read[Seq[ApiResponse[Introduction]]](response)
  //        SYNEREOCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
  //      } else if (response.contains("introductionConfirmationResponse")) {
  //        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
  //        SYNEREOCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
  //      } else if (response.contains("connectNotification")) {
  //        var isNew: Boolean = true
  //        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
  //        val content = connectNotification(0).content
  //        SYNEREOCircuit.dispatch(AcceptConnectNotification(content))
  //        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(content.introProfile)
  //        val connections = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
  //        connections.foreach {
  //          connection => if (connection.name.equals(name)) {
  //            isNew = false
  //          }
  //        }
  //        if (isNew) {
  //          SYNEREOCircuit.dispatch(AddConnection(ConnectionsModel("", content.connection, name, imgSrc)))
  //        }
  //      } else if (response.contains("beginIntroductionResponse")) {
  //        val beginIntroductionRes = upickle.default.read[Seq[ApiResponse[BeginIntroductionRes]]](response)
  //        SYNEREOCircuit.dispatch(PostIntroSuccess(beginIntroductionRes(0).content))
  //      }
  //    } catch {
  //      case e: Exception => /*println("exception for upickle read session ping response")*/
  //    }
  //  }

  val responseType = Seq("sessionPong", "introductionNotification", "introductionConfirmationResponse", "connectNotification", "beginIntroductionResponse")

  def getCurrMsgModel(): Seq[Post] = {

    try {
      SYNEREOCircuit.zoom(_.messages).value.get.messagesModelList
    } catch {
      case e: Exception =>
        Nil
    }
  }

  def getContentModel(response: String): Seq[Post] = {
    SYNEREOCircuit.dispatch(TogglePinger())
    if (responseType.exists(response.contains(_))) {
      processIntroductionNotification(response)
      getCurrMsgModel()
    } else {
      val msg = getCurrMsgModel() ++
        upickle.default.read[Seq[ApiResponse[ResponseContent]]](response)
          .filterNot(_.content.pageOfPosts.isEmpty)
          .flatMap(content => filterContent(content))
      msg.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
    }
  }

  def postNewConnection(content: Content) = {
    var count = 1
    post()
    def post(): Unit = CoreApi.postIntroduction(content).onComplete {
      case Success(res) =>
        logger.log.debug("Connection request sent successfully")
      case Failure(fail) =>
        if (count == 3) {
          //            logger.log.error("Error sending connection request")
          SYNEREOCircuit.dispatch(ShowServerError(fail.getMessage))
        } else {
          count = count + 1
          post()
        }
    }
  }

  def updateIntroductionsModel(introConfirmReq: IntroConfirmReq) = {
    CoreApi.postIntroduction(introConfirmReq).onComplete {
      case Success(response) => logger.log.debug("Intro confirm request sent successfully")
      case Failure(response) => logger.log.error("Error sending intro confirm request")
        SYNEREOCircuit.dispatch(ShowServerError(response.getMessage))
    }
  }

  def subsForMsgAndBeginSessionPing() = {
    val expr = Expression("feedExpr", ExpressionContent(SYNEREOCircuit.zoom(_.connections.connections).value ++ Seq(ConnectionsUtils.getSelfConnnection()),
      s"any([${AppUtils.MESSAGE_POST_LABEL}])"))
    val req = SubscribeRequest(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, expr)
    SYNEREOCircuit.dispatch(ClearMessages())
    var count = 1
    subscribe()
    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        logger.log.debug(s"eval subscribe complete :${res}")
        SYNEREOCircuit.dispatch(UpdatePrevSearchCnxn(req.expression.content.cnxns))
        SYNEREOCircuit.dispatch(UpdatePrevSearchLabel(req.expression.content.label))
        SYNEREOCircuit.dispatch(RefreshMessages())

      case Failure(res) =>
        if (count == 3) {
          println(s"Failure data = ${res.getMessage}")
          //            logger.log.error("Open Error modal Popup")
          SYNEREOCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          subscribe()
          logger.log.error("Error in subscription")
        }
    }
  }

  def subsForMsg(req: SubscribeRequest) = {
    var count = 1
    subscribe()
    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        SYNEREOCircuit.dispatch(UpdatePrevSearchCnxn(req.expression.content.cnxns))
        SYNEREOCircuit.dispatch(UpdatePrevSearchLabel(req.expression.content.label))
      case Failure(res) =>
        if (count == 3) {
          //            logger.log.error("Open Error modal Popup")
          SYNEREOCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          subscribe()
          logger.log.error("Error in subscription")
        }
    }

  }

  def cancelPreviousAndSubscribeNew(req: SubscribeRequest) = {
    var count = 1
    cancelPrevious()
    def cancelPrevious(): Unit = CoreApi.cancelSubscriptionRequest(CancelSubscribeRequest(
      SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, SYNEREOCircuit.zoom(_.searches.previousSearchCnxn).value,
      SYNEREOCircuit.zoom(_.searches.previousSearchLabel).value)).onComplete {
      case Success(res) =>
        SYNEREOCircuit.dispatch(SubsForMsg(req))
      case Failure(res) =>
        if (count == 3) {
          //            logger.log.error("server error")
          SYNEREOCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          cancelPrevious()
        }
    }
  }

  def postMessage(req: SubscribeRequest) = {
    var count = 1
    postMsg()
    def postMsg(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        //  println("messages handler message post success")
        logger.log.debug("message post success")
      case Failure(fail) =>
        if (count == 3) {
          //            logger.log.error("server error")
          //  println("messages handler message post failure ")
          SYNEREOCircuit.dispatch(ShowServerError(fail.getMessage))
        } else {
          count = count + 1
          postMsg()
        }
    }
  }

  def leaf(text: String /*, color: String = "#CC5C64"*/) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"\"),image(\"\")))"

  def postLabelsAndMsg(labelPost: LabelPost, subscribeReq: SubscribeRequest) = {

    var count = 1
    post()
    def post(): Unit = CoreApi.postLabel(labelPost).onComplete {
      case Success(res) =>
        // println("searches handler label post success")
        SYNEREOCircuit.dispatch(PostMessage(subscribeReq))
        SYNEREOCircuit.dispatch(CreateLabels(labelPost.labels))
      case Failure(res) =>
        // println("searces handler label post failure")
        if (count == 3) {
          //            logger.log.debug("server error")
          SYNEREOCircuit.dispatch(ShowServerError(res.getMessage))
        } else {
          count = count + 1
          post()
        }

    }
  }

  def postUserUpdate(req: UpdateUserRequest) = {
    var count = 1

    post()
    def post(): Unit = CoreApi.updateUserRequest(req).onComplete {
      case Success(response) =>
        logger.log.debug("user update request sent successfully")
      case Failure(response) =>
        if (count == 3) {
          //            logger.log.error("user update error")
          SYNEREOCircuit.dispatch(ShowServerError(response.toString))
        } else {
          count = count + 1
          post()
        }
    }
  }


}
