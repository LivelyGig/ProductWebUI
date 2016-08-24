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
case class AddConnection(newConnectionModel: ConnectionsModel)

case class UpdateConnectionSeq(connections: Seq[Connection])

case class UpdateConnectionModelSeq(listOfConnectionModel: Seq[ConnectionsModel])


class ConnectionHandler[M](modelRW: ModelRW[M, ConnectionsRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case UpdateConnectionSeq(connections: Seq[Connection]) =>
      updated(ConnectionsRootModel(value.connectionsResponse, connections))

    case UpdateConnectionModelSeq(listOfConnectionModel: Seq[ConnectionsModel]) =>
      updated(ConnectionsRootModel(listOfConnectionModel, value.connections))

    case AddConnection(newConnectionModel: ConnectionsModel) =>
      updated(ConnectionsRootModel(Seq(newConnectionModel) ++ value.connectionsResponse, value.connections))
  }
}