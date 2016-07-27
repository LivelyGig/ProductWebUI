package client.handlers

import diode.data.PotState.PotPending
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import diode.data.{Empty, Pot, PotAction, Ready}
import shared.models.ConnectionsModel
import shared.RootModels.ConnectionsRootModel
import client.services.CoreApi
import client.utils.ConnectionsUtils
import org.scalajs.dom._
import shared.dtos.{ApiResponse, ConnectNotification, ConnectionProfileResponse, IntroConfirmReq}
import shared.sessionitems.SessionItems

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// scalastyle:off
// Actions
case class RefreshConnections(potResult: Pot[ConnectionsRootModel] = Empty) extends PotAction[ConnectionsRootModel, RefreshConnections] {
  override def next(value: Pot[ConnectionsRootModel]) = RefreshConnections(value)
}

case class AddConnection(connectNotification: ConnectNotification)

object ConnectionModelHandler {
  def getConnectionsModel(response: String): ConnectionsRootModel = {

    try {
      val connections = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](response)
      var model = Seq[ConnectionsModel]()

      connections.foreach {
        connection =>
          val json = JSON.parse(connection.content.jsonBlob)
          val name = json.name.asInstanceOf[String]
          val imgSrc = if (connection.content.jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
          model :+= new ConnectionsModel(connection.content.sessionURI, connection.content.connection,
            name, imgSrc)
      }
      ConnectionsRootModel(model.sortBy(_.name))
    } catch {
      case e: Exception  =>
        println("into exception for connection handler ")
        ConnectionsRootModel(Nil)
    }
  }

}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle : PartialFunction[Any, ActionResult[M]]= {

    case AddConnection(connectNotification: ConnectNotification) =>
      val connectionSessionUri = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
      val introProfile = JSON.parse(connectNotification.introProfile)
      val tempname = introProfile.name.asInstanceOf[String]
      val img = if (connectNotification.introProfile.contains("imgSrc")) introProfile.imgSrc.asInstanceOf[String] else ""
      val newConnection = ConnectionsModel(connectionSessionUri, connection = connectNotification.connection, name = tempname, img)
      //      val newConneSeq = Pot[Seq[ConnectionsModel]](newConnection)
      //      val newValue = value.++((newConneSeq))
      //      updated(newValue)
      noChange


    case action: RefreshConnections =>
      ConnectionsUtils.checkIntroductionNotification()
      val updateF = action.effect(CoreApi.getConnections())(connections => ConnectionModelHandler.getConnectionsModel(connections))
      action.handleWith(this, updateF)(PotAction.handler())

  }
}

