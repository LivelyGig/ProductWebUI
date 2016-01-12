package livelygig.client.models

import livelygig.shared.dtos.{ApiResponse, ConnectionProfileResponse}

/**
  * Created by shubham.k on 1/12/2016.
  */
case class ConnectionsModel(connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]]) {
      def updated (newConnectionResponse: ApiResponse[ConnectionProfileResponse]) = {
        println(newConnectionResponse)
        connectionsResponse.indexWhere(_.content.connection.target == newConnectionResponse.content.connection.target)
        match {
          case -1 =>
            ConnectionsModel(connectionsResponse:+newConnectionResponse)
          case target =>
            ConnectionsModel(connectionsResponse.updated(target, newConnectionResponse))
        }
      }
}
