package shared.RootModels

import shared.models.ConnectionsModel

case class ConnectionsRootModel(connectionsResponse: Seq[ConnectionsModel]) {
  def updated (newConnectionResponse: ConnectionsModel) = {
//    println(newConnectionResponse)
    connectionsResponse.indexWhere(_.connection.target == newConnectionResponse.connection.target)
    match {
      case -1 =>
        ConnectionsRootModel(connectionsResponse:+newConnectionResponse)
      case target =>
        ConnectionsRootModel(connectionsResponse.updated(target, newConnectionResponse))
    }
  }
}
