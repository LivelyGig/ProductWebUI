package livelygig.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.models.ConnectionsModel
import livelygig.shared.dtos.{ ConnectionProfileResponse, ApiResponse}
import upickle.default._

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/12/2016.
  */
object ConnectionList {
  case class ConnectionListProps(
    connections: Seq[ConnectionsModel]
  )

  private val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionList")
    .render_P(p => {
      def renderConnections(connection: ConnectionsModel) = {
        <.li(
//         <.span( connection.content.jsonBlobModel.get.name)
        if (!connection.name.isEmpty){
          <.div(^.className:="col-md-12")(connection.name)
//            <.div(^.className:="col-md-4")(connection.),
//            <.div(^.className:="col-md-4")(connection.sessionURI)


        }
        else {
          <.span()
        }

        )
      }
            <.ul(^.className:="media-list")(p.connections map renderConnections)

    })
    .build
  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}
