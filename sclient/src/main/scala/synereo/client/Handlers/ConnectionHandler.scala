package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.RootModels.ConnectionsRootModel
import shared.models.ConnectionsModel

// Actions
//scalastyle:off
case class AddConnection(newConnection: ConnectionsModel)

case class UpdateConnection(connections: Seq[ConnectionsModel])

class ConnectionHandler[M](modelRW: ModelRW[M, ConnectionsRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case UpdateConnection(connections: Seq[ConnectionsModel]) =>
      updated(ConnectionsRootModel(connections))
    case AddConnection(newConnection: ConnectionsModel) =>
      updated(ConnectionsRootModel(Seq(newConnection) ++ value.connectionsResponse))


  }
}