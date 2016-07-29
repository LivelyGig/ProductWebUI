package synereo.client.handlers

import org.widok.moment.Moment
import shared.dtos._
import shared.models.{ConnectionsModel, MessagePost, Post}
import synereo.client.logger
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import shared.RootModels.SessionRootModel
import synereo.client.sessionitems.SessionItems
import synereo.client.utils.ConnectionsUtils
import org.scalajs.dom.window

/**
  * Created by mandar.k on 5/24/2016.
  */
//scalastyle:off
object ContentModelHandler {
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
        //        println(s"sessionPong: $sessionPong")
      } else if (response.contains("introductionNotification")) {
        val introSeq = upickle.default.read[Seq[ApiResponse[Introduction]]](response)
        //        println(s"introSeq: $introSeq")
        SYNEREOCircuit.dispatch(AcceptNotification(Seq(introSeq(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        //        println(s"introductionConfirmationResponse: $introductionConfirmationResponse")
        //        SYNEREOCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
      } else if (response.contains("connectNotification")) {
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        val content = connectNotification(0).content
        //        println(s"connectNotification: $connectNotification")
        //        SYNEREOCircuit.dispatch(AcceptConnectNotification(content))
        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(content.introProfile)
        val uri = window.sessionStorage.getItem(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
        val newConnection = ConnectionsModel(uri, content.connection, name, imgSrc)
        //        println(s"newConnection: $newConnection")
        val connectionsModel = SYNEREOCircuit.zoom(_.connections).value
        val newConnectionsModel = connectionsModel.connectionsResponse.filterNot(_.name.equals(newConnection.name))
        println(s"newConnectionsModel: $newConnectionsModel")
        SYNEREOCircuit.dispatch(AddConnection(newConnection))
      }
    } catch {
      case e: Exception => println("exception for upickle read session ping response")
    }
  }

  val responseType = Seq("sessionPong", "introductionNotification", "introductionConfirmationResponse", "connectNotification")

  def getCurrMsgModel(): Seq[Post] = {

    try {
      SYNEREOCircuit.zoom(_.messages).value.get.messagesModelList
    } catch {
      case e: Exception =>
        Nil
    }
  }

  def getContentModel(response: String): Seq[Post] = {
    val value = SYNEREOCircuit.zoom(_.sessionPing).value
    if (!value.stopPing) {
      SessionRootModel(!value.toggleToPing, value.stopPing)
    } else {
      SessionRootModel(value.toggleToPing, value.stopPing)
    }
    SYNEREOCircuit.dispatch(HandleSessionPing())
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
}
