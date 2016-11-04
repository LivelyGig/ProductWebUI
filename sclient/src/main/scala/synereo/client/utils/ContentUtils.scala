package synereo.client.utils

import diode.AnyAction._
import org.widok.moment.Moment
import shared.dtos._
import shared.models.{ConnectionsModel, MessagePost, Post}
import synereo.client.handlers._
import synereo.client.logger
import synereo.client.services.{ApiTypes, CoreApi, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}


//scalastyle:off
object ContentUtils {

  /**
    * This function returns the current message model from the MessagesRootModel
    *
    * @return
    */
  def getCurrMsgModel(): Seq[Post] = {
    // #todo Is this method even required. My guess it is not
    if (SYNEREOCircuit.zoom(_.messages).value.nonEmpty) SYNEREOCircuit.zoom(_.messages).value.get.messagesModelList else Nil
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
    val balanceChangedResponse: Seq[String] = responseArray.filter(_.contains("balanceChanged"))
    if(balanceChangedResponse.nonEmpty) {
      val content = upickle.default.read[ApiResponse[BalanceChange]](balanceChangedResponse.head).content
      SYNEREOCircuit.dispatch(BalanceChanged(content.amp, content.btc, content.address))
    }
    val (cnxn, postContent, intro, cnctNot) = sortContent(responseArray)
    // three more responses session pong, begin introduction and introduction confirmation which are not processed because tney do nothing
    if (intro.nonEmpty) SYNEREOCircuit.dispatch(AddNotification(intro.map(_.content)))
    if (cnctNot.nonEmpty) {
      val resp = cnctNot.map(e => ConnectionsUtils.getCnxnFromNot(e.content))
      SYNEREOCircuit.dispatch(UpdateConnections(resp))
    }
    if (cnxn.nonEmpty) {
      val res = cnxn.map(e => ConnectionsUtils.getCnxnFromRes(e.content))
      SYNEREOCircuit.dispatch(UpdateConnections(res))
    }
    // return the mod messages model if new messages in response otherwise return the old response
    if (postContent.nonEmpty) postContent.map(_.content)
    else Nil
  }

  /**
    * This function sort content based on their types
    *
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
                case Failure(b) => remainingObj :+= e
              }
            }
          }
        }
    }
    (cnxn, msg, intro, cnctNot)

  }

  /**
    * This function yields the message model that needs to be updated
    *
    * @param response
    * @return
    */
  //  def getMsgModel(response: Seq[ApiResponse[ResponseContent]]): Seq[Post] = {
  //    val msgModelMod = getCurrMsgModel() ++
  //      response
  //        .filterNot(_.content.pageOfPosts.isEmpty)
  //        .flatMap(content => Try(upickle.default.read[MessagePost](content.content.pageOfPosts(0))).toOption)
  //    msgModelMod.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
  //  }

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
    var count = 1
    post()
    def post(): Unit = CoreApi.postIntroduction(introConfirmReq).onComplete {
      case Success(response) => logger.log.debug("Intro confirm request sent successfully")
      case Failure(response) => logger.log.error("Error sending intro confirm request")
        if (count == 3) {
          SYNEREOCircuit.dispatch(ShowServerError(response.getMessage))
        }
        else {
          count = count + 1
          post()
        }
    }
  }

  /**
    * This function issue the default eval subscribe request and then session ping
    * The defualt label is MESSAGE_POST_LABEL and then it begin the session ping cycle with
    * the dispatch of refresh messages
    */
  def subsForMsgAndBeginSessionPing() = {
    val expr = Expression(ApiTypes.requestTypes.FEED_EXPRESSION,
      ExpressionContent(SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.map(cnxnResp => cnxnResp.connection) ++ Seq(ConnectionsUtils.getSelfConnnection()),
        s"any([${AppUtils.MESSAGE_POST_LABEL}])"))
    val req = SubscribeRequest(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, expr)
    // clear the previous messages model. It sets the state to Pot.Empty effectively showing the loader
    // The loader on the dashboard is shown for Pot state empty
    SYNEREOCircuit.dispatch(ClearMessages())
    var count = 1
    subscribe()
    def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
      case Success(res) =>
        logger.log.debug(s"eval subscribe complete :${res}")
        // update the previous labels and the connections. It will be used for the cancelation of request
        // before issuing new subscription
        SYNEREOCircuit.dispatch(UpdatePrevSearchCnxn(req.expression.content.cnxns))
        SYNEREOCircuit.dispatch(UpdatePrevSearchLabel(req.expression.content.label))
        // refresh messages to begin the session ping cycle
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

  /**
    * This message issue an eval subscribe issue for the messages
    *
    * @param req
    */
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

  /**
    * This method cancels the previous request and issue the new one
    *
    * @param req
    */
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
      case Success(response) =>{
        logger.log.debug("user image update request successful")
        SYNEREOCircuit.dispatch(UpdateUserImage(req.jsonBlob.imgSrc))
        println(s"In contentutils ${req}")
      }
      case Failure(response) =>
        if (count == 3) {
                     logger.log.error("user update error")
          SYNEREOCircuit.dispatch(ShowServerError(response.toString))
        } else {
          count = count + 1
          post()
        }
    }
  }


  def closeSessionReq(closeSessionRequest: CloseSessionRequest) = {
    var count = 1
    post()
    def post(): Unit = CoreApi.closeSessionRequest(closeSessionRequest).onComplete {
      case Success(response) => {
        logger.log.debug("Closed session request")
        SYNEREOCircuit.dispatch(LogoutUser())
      }
      case Failure(response) => logger.log.error("Error closing the session")
        if (count == 3) {
          SYNEREOCircuit.dispatch(ShowServerError(response.getMessage))
        }
        else {
          count = count + 1
          post()
        }
    }
  }

}
