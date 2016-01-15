package livelygig.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.shared.dtos.{JsonBlobModel, ConnectionProfileResponse, ApiResponse}
import upickle.default._

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/12/2016.
  */
object ConnectionList {
  case class ConnectionListProps(
    connections: Seq[ApiResponse[ConnectionProfileResponse]]
  )

  private val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionList")
    .render_P(p => {
      def renderConnections(connection: ApiResponse[ConnectionProfileResponse]) = {
        <.li(
//         <.span( connection.content.jsonBlobModel.get.name)
          <.span( connection.content.jsonBlob)
        )
      }
            <.ul(^.className:="media-list")(p.connections map renderConnections)

    })
    .build
  def apply(connections: Seq[ApiResponse[ConnectionProfileResponse]]) =
    ConnectionList(ConnectionListProps(connections))
}
