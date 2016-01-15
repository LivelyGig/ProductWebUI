package livelygig.client.services

import autowire._
import upickle.default._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import livelygig.client.models.ConnectionsModel
import livelygig.shared.dtos.{JsonBlobModel, ConnectionProfileResponse, ApiResponse}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/11/2016.
  */

// Actions
case object RefreshConnections

case class UpdateAllConnections( connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]] )

case class UpdateConnection( connection: ConnectionProfileResponse)

case class RootModel(connections: Pot[ConnectionsRootModel])

case class ConnectionsRootModel(connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]]) {
  def updated (newConnectionResponse: ApiResponse[ConnectionProfileResponse]) = {
    println(newConnectionResponse)
    connectionsResponse.indexWhere(_.content.connection.target == newConnectionResponse.content.connection.target)
    match {
      case -1 =>
        ConnectionsRootModel(connectionsResponse:+newConnectionResponse)
      case target =>
        ConnectionsRootModel(connectionsResponse.updated(target, newConnectionResponse))
    }
  }
}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case RefreshConnections =>
      effectOnly(Effect(CoreApi.sessionPing().map(UpdateAllConnections)))
    case UpdateAllConnections(connections) =>
      connections.foreach(connection=> connection.content.jsonBlobModel->Option(JsonBlobModel(JSON.parse(connection.content.jsonBlob).name.toString,JSON.parse(connection.content.jsonBlob).imgSrc.toString)))
      //connections.foreach(connection=> println(connection.content.jsonBlobModel.get.name))
      println(connections)
      updated(Ready(ConnectionsRootModel(connections)))
  }
}

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected var model = RootModel(Empty)
  // combine all handlers into one
  override protected val actionHandler = combineHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v)))

  )
}
