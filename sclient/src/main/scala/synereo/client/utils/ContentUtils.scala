package synereo.client.utils

import diode.AnyAction._
import org.widok.moment.Moment
import shared.dtos._
import shared.models.{ConnectionsModel, MessagePost, Post}
import synereo.client.handlers._
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import upickle.Js

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}


//scalastyle:off
object ContentUtils {
  /**
    * This function get the notification response from the the api and process
    * the notification to add a new connection to the connection root model
    * @param connectNotification
    */
  def processConnectNotification(connectNotification: Seq[ApiResponse[ConnectNotification]]) = {
    // get the new connections from the new notification response
    val newConnections = connectNotification.map {
      e =>
        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(e.content.introProfile)
        ConnectionsModel("", e.content.connection, name, imgSrc)
    }
    SYNEREOCircuit.dispatch(UpdateConnections(newConnections, newConnections.map(_.connection)))

  }
  def processConnectResponse(connectionRes: Seq[ApiResponse[ConnectionProfileResponse]]) = {
    // get the new connections from the new notification response
    val newConnections = connectionRes.map {
      e =>
        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(e.content.jsonBlob)
        ConnectionsModel("", e.content.connection, name, imgSrc)
    }
    SYNEREOCircuit.dispatch(UpdateConnections(newConnections, newConnections.map(_.connection)))

  }

  /**
    * This function returns the current message model from the MessagesRootModel
    * @return
    */
  def getCurrMsgModel(): Seq[Post] = {
    // #todo Is this method even required. My guess it is not
    if (SYNEREOCircuit.zoom(_.messages).value.nonEmpty) SYNEREOCircuit.zoom(_.messages).value.get.messagesModelList else Nil
  }

  // Note : DO NOT REMOVE FOLLOWING THREE METHODS
  // The next three methods IMO is a better implementation of processing responses
  // These methods basically sort the response based on the Try of pickling by according to their types
  // However upickle for some reason is not able to parse the generics, which makes sense since it doesn't
  // know what is the type it has to parse the data into.
  // it in that case throws the following errors:
  //    [error]  found   : upickle.default.Reader[(shared.dtos.ApiResponse[T], shared.dtos.ApiResponse[T])]
  //    [error]  required: upickle.default.Aliases.R[(String, T)]
  //    [error]     (which expands to)  upickle.default.Reader[(String, T)]
  // #todo My intution is that this should work. Will deal with it later



//  def processRes(response: String): Seq[Post] = {
//    // process response
//    val responseArray = upickle.json.read(response)
//    val (msg, afterMsg) = getParsedAndRemainingObj[ResponseContent](responseArray.arr)
//    val (intro, afterMsgIntro) = getParsedAndRemainingObj[Introduction](afterMsg)
//    val (cnctNot, remObj) = getParsedAndRemainingObj[ConnectNotification](afterMsgIntro)
//    // three more responses session pong, begin introduction and introduction confirmation which are not processed because tney do nothing
//    if (intro.nonEmpty) SYNEREOCircuit.dispatch(AddNotification(Seq(intro(0).content)))
//    if (cnctNot.nonEmpty) processConnectNotification(cnctNot)
//    // return the mod messages model if new messages in response otherwise return the old response
//    if (msg.nonEmpty) getMsgModel(msg)
//    else getCurrMsgModel()
//  }

//  /**
//    * This function just bind the jsValue (json) into Try for pickling by uPickle
//    * @param jsVal
//    * @tparam T
//    * @return
//    */
//  def tryPickle[T](jsVal: Js.Value): Try[ApiResponse[T]] = Try(upickle.default.read[ApiResponse[T]](upickle.json.write(jsVal)))
//
//
//  /**
//    * This function takes the array of the json and yields the two data structures
//    * first is the array of the defired object and other is remaining json objects
//    * @param jsArray
//    * @tparam T
//    * @return
//    */
//  def getParsedAndRemainingObj[T](jsArray: Seq[Js.Value]): (Seq[ApiResponse[T]], Seq[Js.Value]) = {
//    var remainingObj: Seq[Js.Value] = Nil
//    var parsedRes: Seq[ApiResponse[T]] = Nil
//    jsArray.foreach {
//      e =>
//      tryPickle[T](e) match {
//        case r:ResponseContent =>
//        case Success(a) => parsedRes :+= a.asInstanceOf[ApiResponse[Content]]
//        case Failure(b) => r
  // logger.log.error(b.getMessage)emainingObj :+= e
//      }
//
//    }
//    println(s"parsedRes: ${parsedRes}, remainingObj: ${remainingObj}")
//    (parsedRes, remainingObj)
//  }


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
  def processRes(response: String): Seq[Post] = {
    // process response
    val responseArray = upickle.json.read(response).arr.map(e=> upickle.json.write(e)).filterNot(_.contains("sessionPong"))
    val (cnxn, afterCnxn) = getCnxnAndRemObj(responseArray)
    val (msg, afterCnxnMsg) = getMsgAndRemObj(afterCnxn)
    val (intro, afterCnxnMsgIntro) = getIntroAndRemObj(afterCnxnMsg)
    val (cnctNot, afterCnxnMsgIntroCnctNot) = getCnctNotAndRemObj(afterCnxnMsgIntro)
    // three more responses session pong, begin introduction and introduction confirmation which are not processed because tney do nothing
    if (intro.nonEmpty) SYNEREOCircuit.dispatch(AddNotification(intro.map(_.content)))
    if (cnctNot.nonEmpty) processConnectNotification(cnctNot)
    if (cnxn.nonEmpty) processConnectResponse(cnxn)
    // return the mod messages model if new messages in response otherwise return the old response
    if (msg.nonEmpty) getMsgModel(msg)
    else getCurrMsgModel()
  }


  // Probably a stupid implementation for the sorting of object on their type of pickling.
  // see above where you may find the reason for it

  def getSessionPongAndRemObj(jsArray: Seq[String]): (Seq[ApiResponse[SessionPong]], Seq[String]) = {
    var remainingObj: Seq[String] = Nil
    var parsedRes: Seq[ApiResponse[SessionPong]] = Nil
    jsArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[SessionPong]](e)) match {
          case Success(a) => parsedRes :+= a
          case Failure(b) => {
            remainingObj :+= e}
        }
    }
    (parsedRes, remainingObj)
  }

  def getCnxnAndRemObj(jsArray: Seq[String]): (Seq[ApiResponse[ConnectionProfileResponse]], Seq[String]) = {
    var remainingObj: Seq[String] = Nil
    var parsedRes: Seq[ApiResponse[ConnectionProfileResponse]] = Nil
    jsArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[ConnectionProfileResponse]](e)) match {
        case Success(a) => parsedRes :+= a
        case Failure(b) => {
          remainingObj :+= e}
      }
    }
    (parsedRes, remainingObj)
  }
  def getMsgAndRemObj(jsArray: Seq[String]): (Seq[ApiResponse[ResponseContent]], Seq[String]) = {
    var remainingObj: Seq[String] = Nil
    var parsedRes: Seq[ApiResponse[ResponseContent]] = Nil
    jsArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[ResponseContent]](e)) match {
        case Success(a) => parsedRes :+= a
        case Failure(b) => {
          remainingObj :+= e}
      }
    }
    (parsedRes, remainingObj)
  }
  def getIntroAndRemObj(jsArray: Seq[String]): (Seq[ApiResponse[Introduction]], Seq[String]) = {
    var remainingObj: Seq[String] = Nil
    var parsedRes: Seq[ApiResponse[Introduction]] = Nil
    jsArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[Introduction]](e)) match {
        case Success(a) => parsedRes :+= a
        case Failure(b) => {
          remainingObj :+= e}
      }
    }
    (parsedRes, remainingObj)
  }
  def getCnctNotAndRemObj(jsArray: Seq[String]): (Seq[ApiResponse[ConnectNotification]], Seq[String]) = {
    var remainingObj: Seq[String] = Nil
    var parsedRes: Seq[ApiResponse[ConnectNotification]] = Nil
    jsArray.foreach {
      e =>
        Try(upickle.default.read[ApiResponse[ConnectNotification]](e)) match {
        case Success(a) => parsedRes :+= a
        case Failure(b) => {
          remainingObj :+= e}
      }
    }
    (parsedRes, remainingObj)
  }



  /**
    * This function yields the message model that needs to be updated
    * @param response
    * @return
    */
  def getMsgModel(response: Seq[ApiResponse[ResponseContent]]): Seq[Post] = {
    val msgModelMod = getCurrMsgModel() ++
      response
        .filterNot(_.content.pageOfPosts.isEmpty)
        .flatMap(content => Try(upickle.default.read[MessagePost](content.content.pageOfPosts(0))).toOption)
    msgModelMod.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
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
    SYNEREOCircuit.dispatch(ClearMessages())
    var count = 1
    cancelPrevious()
    def cancelPrevious(): Unit = CoreApi.cancelSubscriptionRequest(CancelSubscribeRequest(
      SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, SYNEREOCircuit.zoom(_.searches.previousSearchCnxn).value,
      SYNEREOCircuit.zoom(_.searches.previousSearchLabel).value)).onComplete {
      case Success(res) =>
        subsForMsg(req)
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

  //  def leaf(text: String /*, color: String = "#CC5C64"*/) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"\"),image(\"\")))"

  def postLabelsAndMsg(labelPost: LabelPost, subscribeReq: SubscribeRequest) = {
    var count = 1
    post()
    def post(): Unit = CoreApi.postLabel(labelPost).onComplete {
      case Success(res) =>
        postMessage(subscribeReq)
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
        logger.log.debug("user image update request successful")
        SYNEREOCircuit.dispatch(UpdateUserImage(req.jsonBlob.imgSrc))
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
