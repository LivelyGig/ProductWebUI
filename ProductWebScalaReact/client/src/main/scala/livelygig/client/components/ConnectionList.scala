package livelygig.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.models.JsonBlobModel
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import upickle.default._

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
        //println(read[JsonBlobModel](connection.content.jsonBlob).name )
        <.li(
         <.span(/*read[JsonBlobModel](*/connection.content.jsonBlob/*).name*/ )
        )
      }
            <.ul(^.className:="media-list")(p.connections map renderConnections)

    })
    .build
  def apply(connections: Seq[ApiResponse[ConnectionProfileResponse]]) =
    ConnectionList(ConnectionListProps(connections))
}
