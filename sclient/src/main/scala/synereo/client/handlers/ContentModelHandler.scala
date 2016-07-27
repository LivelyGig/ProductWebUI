package synereo.client.handlers

import org.widok.moment.Moment
import shared.dtos._
import shared.models.{MessagePost, Post, ProjectsPost}
import synereo.client.logger
import synereo.client.modules.AppModule
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import shared.RootModels.SessionRootModel

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
        println(s"IntroductionConfirmationResponse: $introductionConfirmationResponse")
      } else if (response.contains("connectNotification")) {
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        SYNEREOCircuit.dispatch(AcceptConnectNotification(connectNotification(0).content))
        //        SYNEREOCircuit.dispatch(AddConnection(connectNotification(0).content))
        println(s"connectNotification: $connectNotification")
      }
    } catch {
      case e: Exception => /*println("into exception for upickle read session ping response")*/
    }
  }

  val responseType = Seq("sessionPong", "introductionNotification", "introductionConfirmationResponse")

  def getCurrMsgModel(): Seq[Post] = {

      try {
        SYNEREOCircuit.zoom(_.messages).value.get.messagesModelList
      } catch {
        case e:Exception =>
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
      /*try {
        val test = upickle.default.read[Seq[ApiResponse[ResponseContent]]](response)
          .filterNot(_.content.pageOfPosts.isEmpty)
          .flatMap(content => filterContent(content))
          .sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
        println(s"actual value =$test")
      } catch {
        case e: Exception => println("nope not supposrted")
      }*/
      val msg = getCurrMsgModel() ++
        upickle.default.read[Seq[ApiResponse[ResponseContent]]](response)
          .filterNot(_.content.pageOfPosts.isEmpty)
          .flatMap(content => filterContent(content))
      msg.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))

//        .flatten
    }
  }

  /*def getContentModel(response: String):  Seq[Post] = {
//      SYNEREOCircuit.dispatch(HandleSessionPing())
    val value = SYNEREOCircuit.zoom(_.sessionPing).value
    if (!value.stopPing) {
      SessionRootModel(!value.toggleToPing, value.stopPing)
    } else {
      SessionRootModel(value.toggleToPing, value.stopPing)
    }
  }*/
}
