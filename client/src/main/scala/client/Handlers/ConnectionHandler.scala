package client.handlers

import diode.data.PotState.PotPending
import diode.{ActionHandler, Effect, ModelRW}
import diode.data.{Empty, Pot, PotAction, Ready}
import shared.models.ConnectionsModel
import shared.RootModels.ConnectionsRootModel
import client.services.CoreApi
import org.scalajs.dom._
import shared.dtos.{ApiResponse, ConnectionProfileResponse}
import shared.sessionitems.SessionItems

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// scalastyle:off
// Actions
case class RefreshConnections(potResult: Pot[ConnectionsRootModel] = Empty) extends PotAction[ConnectionsRootModel, RefreshConnections] {
  override def next(value: Pot[ConnectionsRootModel]) = RefreshConnections(value)
}

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
        ConnectionsRootModel(Nil)
    }
  }

}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action: RefreshConnections =>
      val updateF = action.effect(CoreApi.getConnections())(connections => ConnectionModelHandler.getConnectionsModel(connections))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}