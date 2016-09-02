package synereo.client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, ReactComponentB}
import synereo.client.rootmodels.ConnectionsRootModel
import diode.react._
import synereo.client.css._
import shared.models.ConnectionsModel
import synereo.client.modalpopups.NewConnection
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 5/18/2016.
  */
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
    .renderPS(($, P, S) => {
      <.div(^.id := "connectionsContainerMain")(
        //        <.div(^.className := "row")(
        //          //Left Sidebar
        //          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
        //            Sidebar(Sidebar.Props())
        //          )
        //        ),
        <.div(^.className := "row",
          <.div(^.className := "col-md-12",
            NewConnection(NewConnection.Props("", Seq(DashboardCSS.Style.inviteFrndBtn), "", "Invite Connections"))
          )
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col -sm -12")(
            ConnectionList(P.proxy().connectionsResponse)
            /*P.proxy().render(connectionsRootModel =>
              ConnectionList(connectionsRootModel.connectionsResponse)),
            P.proxy().renderFailed(ex => <.div("NO CONNECTIONS FOUND", SynereoCommanStylesCSS.Style.renderFailedMessage)),
            if (P.proxy().isEmpty) {
              if (!P.proxy().isFailed) {
                <.div(
                  <.div(<.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "", Icon.spinnerIconPulse))
                  //                  <.div("Loading")
                )
              } else {
                <.div()
              }
            } else {
              <.div(/*"data loaded"*/)
            }*/
          )
        )
      ) //connectionsContainerMain
    })
    //    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[ConnectionsRootModel]) = component(Props(proxy))
}

object ConnectionList {

  case class ConnectionListProps(connections: Seq[ConnectionsModel])

  val ConnectionList = ReactComponentB[ConnectionListProps]("ConnectionList")
    .render_P(p => {
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
          <.div(^.className := "media-body",
            <.h4(^.className := "media-heading")(
              if (!connection.name.isEmpty) {
                connection.name
              } else {
                <.span()
              }
            )
          )
        )
      }
      <.div(^.className := "col-md-12",
        <.ul(^.className := "media-list", ConnectionsCSS.Style.fullDescUL)(p.connections map renderConnections)
      )
    })
    .build

  def apply(connections: Seq[ConnectionsModel]) =
    ConnectionList(ConnectionListProps(connections))
}
