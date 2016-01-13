package livelygig.client.services

import autowire._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import livelygig.client.models.ConnectionsModel
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

/**
  * Created by shubham.k on 1/11/2016.
  */

// Actions
case object RefreshConnections

case class UpdateAllConnections( connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]] )

case class UpdateConnection( connection: ConnectionProfileResponse)

case class RootModel(connections: Pot[ConnectionsModel])

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case RefreshConnections =>
      effectOnly(Effect(CoreApi.sessionPing().map(UpdateAllConnections)))
    case UpdateAllConnections(connections) =>
      // got new todos, update model
      //println(connections)
      updated(Ready(ConnectionsModel(connections)))
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
