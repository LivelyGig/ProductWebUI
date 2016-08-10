package client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.models.ConnectionsModel
import client.RootModels.ConnectionsRootModel
import shared.dtos._

// Actions
//scalastyle:off
case class AddConnection(newConnectionModel: ConnectionsModel)

case class UpdateConnection(listOfConnectionModel: Seq[ConnectionsModel], connections: Seq[Connection])


class ConnectionHandler[M](modelRW: ModelRW[M, ConnectionsRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case UpdateConnection(listOfConnectionModel: Seq[ConnectionsModel], connections: Seq[Connection]) =>
      updated(ConnectionsRootModel(listOfConnectionModel, connections))

    case AddConnection(newConnectionModel: ConnectionsModel) =>
      updated(ConnectionsRootModel(Seq(newConnectionModel) ++ value.connectionsResponse, value.connections))
  }
}