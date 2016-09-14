package client.rootmodel

import shared.dtos.Connection
import shared.models.ConnectionsModel

// scalastyle:off
case class ConnectionsRootModel(connectionsResponse: Seq[ConnectionsModel]) {
  def updated(newConnectionResponse: ConnectionsModel) = {
    connectionsResponse.indexWhere(_.connection.target == newConnectionResponse.connection.target) match {
      case -1 =>
        ConnectionsRootModel(connectionsResponse :+ newConnectionResponse)
      case target =>
        ConnectionsRootModel(connectionsResponse.updated(target, newConnectionResponse))
    }
  }
}
