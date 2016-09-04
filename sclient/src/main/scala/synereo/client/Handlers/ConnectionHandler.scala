package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.ConnectionsRootModel
import shared.dtos.{Connection, Content, IntroConfirmReq}
import shared.models.ConnectionsModel
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import diode.AnyAction._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

// Actions
//scalastyle:off
case class UpdateConnections(newConnectionModel: Seq[ConnectionsModel], newConnection: Seq[Connection])

class ConnectionHandler[M](modelRW: ModelRW[M, ConnectionsRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case UpdateConnections(newConnectionsModel, newConnections) =>
      println(s"newConnectionsModel : ${newConnectionsModel}, newConnections: ${newConnections}")
      val (cnxnModelMod, cnxnMod) = if (value.connections.nonEmpty){
        println(s"value.connectionsResponse: ${value.connectionsResponse}")
        (value.connectionsResponse ++ newConnectionsModel.filterNot(e=>
          value.connectionsResponse.exists( p=> e.connection.source == p.connection.target || e.connection.target == p.connection.target)),
          value.connections ++ newConnections.filterNot(e=>
            value.connections.exists(p => p.target == e.source || p.target == e.target))
          )
      } else {
        (newConnectionsModel, newConnections)
      }
      updated(ConnectionsRootModel(cnxnModelMod, cnxnMod))
  }
}