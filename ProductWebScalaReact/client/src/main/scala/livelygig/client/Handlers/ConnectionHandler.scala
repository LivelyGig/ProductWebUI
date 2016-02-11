package livelygig.client.Handlers

import diode.data.PotState.PotPending
import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Empty, PotAction, Ready, Pot}
import livelygig.client.RootModels.ConnectionsRootModel
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.CoreApi
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import rx.ops.Timer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
// Actions
case class RefreshConnections(value: Pot[ConnectionsRootModel] = Empty) extends PotAction[ConnectionsRootModel, RefreshConnections]{
  override def next(value: Pot[ConnectionsRootModel]) = RefreshConnections(value)
}

object ModelHandler{
  def GetConnectionsModel(connections: Seq[ApiResponse[ConnectionProfileResponse]]): ConnectionsRootModel = {
    var model = Seq[ConnectionsModel]()
    connections.map(connection =>
      connection.content.name -> Option(
        JSON.parse(connection.content.jsonBlob).name)
    )
    connections.foreach {
      connection =>
        val json = JSON.parse(connection.content.jsonBlob)
        val name = json.name.asInstanceOf[String]
        val source = connection.content.connection.source
        val target = connection.content.connection.target
        val label = connection.content.connection.label
        val imgSrc = if (connection.content.jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
        model :+= new ConnectionsModel(connection.content.sessionURI, connection.content.connection,
          name, imgSrc)
    }
    model.foreach(temp => println(temp.name))
    ConnectionsRootModel(model)
  }

}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action : RefreshConnections =>
      val updateF = action.effect(CoreApi.sessionPing())(connections=>ModelHandler.GetConnectionsModel(connections))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}