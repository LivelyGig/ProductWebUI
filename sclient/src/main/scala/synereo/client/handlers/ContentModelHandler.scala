package synereo.client.handlers

import org.widok.moment.Moment
import shared.dtos._
import shared.models.{ConnectionsModel, MessagePost, Post}
import synereo.client.logger
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import shared.RootModels.SessionRootModel
import synereo.client.utils.ConnectionsUtils

/**
  * Created by mandar.k on 5/24/2016.
  */
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
      } else if (response.contains("introductionNotification")) {
        val intro = upickle.default.read[Seq[ApiResponse[Introduction]]](response)
        SYNEREOCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        SYNEREOCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
      } else if (response.contains("connectNotification")) {
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        val content = connectNotification(0).content
        SYNEREOCircuit.dispatch(AcceptConnectNotification(content))
        val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(content.introProfile)
        SYNEREOCircuit.dispatch(AddConnection(ConnectionsModel("", content.connection, name, imgSrc)))
      }
    } catch {
      case e: Exception => /*println("exception for upickle read session ping response")*/
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
}
