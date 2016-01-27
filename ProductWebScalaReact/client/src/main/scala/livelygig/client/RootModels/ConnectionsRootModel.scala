package livelygig.client.RootModels

import livelygig.client.models.ConnectionsModel

/**
  * Created by shubham.k on 1/25/2016.
  */
case class ConnectionsRootModel(connectionsResponse: Seq[ConnectionsModel]) {
  def updated (newConnectionResponse: ConnectionsModel) = {
    println(newConnectionResponse)
    connectionsResponse.indexWhere(_.connection.target == newConnectionResponse.connection.target)
    match {
      case -1 =>
        ConnectionsRootModel(connectionsResponse:+newConnectionResponse)
      case target =>
        ConnectionsRootModel(connectionsResponse.updated(target, newConnectionResponse))
    }
  }
}
