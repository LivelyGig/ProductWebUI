package client.handlers

import diode.data.PotState.PotPending
import diode.{ Effect, ActionHandler, ModelRW }
import diode.data.{ Empty, PotAction, Ready, Pot }
import shared.models.ConnectionsModel
import shared.RootModels.ConnectionsRootModel
import client.services.CoreApi
import shared.dtos.{ ConnectionProfileResponse, ApiResponse }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// Actions
case class RefreshConnections(potResult: Pot[ConnectionsRootModel] = Empty) extends PotAction[ConnectionsRootModel, RefreshConnections] {
  override def next(value: Pot[ConnectionsRootModel]) = RefreshConnections(value)
}

object ConnectionModelHandler {
  def GetConnectionsModel(response: String): ConnectionsRootModel = {

    val connections = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](response)
    //    println("connections = "+ connections)
    var model = Seq[ConnectionsModel]()

    connections.foreach {
      connection =>
        //        println(connection.content.jsonBlob)
        val json = JSON.parse(connection.content.jsonBlob)
        val name = json.name.asInstanceOf[String]
        val source = connection.content.connection.source
        val target = connection.content.connection.target
        val label = connection.content.connection.label
        val imgSrc = if (connection.content.jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
        model :+= new ConnectionsModel(connection.content.sessionURI, connection.content.connection,
          name, imgSrc)
    }
    //    model.foreach(temp => println(temp.name))
    ConnectionsRootModel(model)
  }

}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action: RefreshConnections =>
      val updateF = action.effect(CoreApi.getConnections())(connections => ConnectionModelHandler.GetConnectionsModel(connections))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}