package synereo.client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react._
import synereo.client.rootmodels.ConnectionsRootModel
import diode.react._
import synereo.client.css._
import shared.models.ConnectionsModel
import synereo.client.modalpopups.NewConnection
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 5/18/2016.
  */
//scalastyle:off
object ConnectionsResults {

  case class Props(proxy: ModelProxy[ConnectionsRootModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend($: BackendScope[Props, State]) {
    /*def mounted(props: Props): Callback =
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshConnections()))*/
  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, state) => {
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row",
          <.div(^.className := "col-md-12",
            NewConnection(NewConnection.Props("", Seq(ConnectionsCSS.Style.inviteConnectionsBtn), "", "Invite Connections"))
          )
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col -sm -12")(
            ConnectionsList(props.proxy().connectionsResponse)
          )
        )
      )
    })
    //    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[ConnectionsRootModel]) = component(Props(proxy))
}

object ConnectionsList {

  case class ConnectionListProps(connections: Seq[ConnectionsModel])

  case class State(showAmplifyPostForm : Boolean = false , senderAddress : String  ="")

  class Backend(t: BackendScope[ConnectionListProps, State]) {

    def amplifyPost(senderAddress: String): Callback = {
      t.modState(state => state.copy(showAmplifyPostForm = true, senderAddress = senderAddress))
    }
    /*def mounted(props: Props): Callback =
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshConnections()))*/
  }


  val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionsList")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((b,p,s) => {
      def renderConnections(connection: ConnectionsModel) = {
        <.li(^.className := "media well", ConnectionsCSS.Style.fullUserDescription,
          <.div(^.className := "media-left")(
            if (connection.imgSrc.isEmpty) {
              <.img(^.src := "./assets/images/default_avatar.jpg", ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
            } else {
              <.img(^.src := connection.imgSrc, ConnectionsCSS.Style.connectionAvatar, ^.title := "Connection Source: "
                + connection.connection.source + " Target: " + connection.connection.target + " Label: " + connection.connection.label)
            }
          ),
          <.div(^.className := "media-body", ConnectionsCSS.Style.connectionBody,
            <.h4(^.className := "media-heading", ^.wordBreak := "break-word")(
              if (connection.name.nonEmpty) {
                <.div(ConnectionsCSS.Style.connectionName,
                  connection.name,
                  <.div(ConnectionsCSS.Style.connectionInfoTooltip, ^.className := "infoTooltip",
                    <.span()(s"UID: "),
                    connection.connection.target.substring(8).split("/")(0)
                  )
                )
              } else {
                <.span()
              }
            ),
            <.button(^.className := "btn btn-default pull-right", DashboardCSS.Style.ampTokenBtn,
              "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Amplify Post", "data-placement".reactAttr := "right",
              ^.onClick ==> b.backend.amplifyPost)(
              <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg)
            )
          )
        )
      }
      <.ul(^.className := "media-list", ConnectionsCSS.Style.fullDescUL)(p.connections map renderConnections)
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}
