package livelygig.client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.components.Bootstrap.Panel
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import livelygig.client.css.DashBoardCSS
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.{ConnectionsRootModel, RefreshConnections}


object ConnectionsResults {
  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]])
  case class State(selectedItem: Option[ConnectionsModel] = None)
  class Backend($: BackendScope[Props, State]){
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections))
    /*def render (p:Props, s: State) ={
      <.div(^.id := "mainContainer"/*, DashBoardCSS.Style.mainContainerDiv*/)(
        <.div()(
          ConnectionsPresets.component(Unit)
        ),
        // AddNewAgent(AddNewAgent.Props(ctl)),
        <.div(/*DashBoardCSS.Style.splitContainer*/)(
          <.div(^.className := "split")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                // todo: Need to parameterize the Search area depending on EntityType (e.g. Talent, Project) and preset
                ConnectionsSearch.component(Unit)
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10", ^.id := "dashboardResults2"/*, DashBoardCSS.Style.dashboardResults2*/)(
                // todo: Results will be parameterized depending on EntityType, preset
                Panel(Panel.Props("Connections"), <.div(
                  p.proxy().renderFailed(ex => "Error loading"),
                  p.proxy().renderPending(_ > 500, _ => "Loading..."),
                  p.proxy().render(connectionsRootModel =>
                    ConnectionList(connectionsRootModel.connectionsResponse)
                  )
                ))
              )
            )
          )
        )//row
      ) //mainContainer





    }*/
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Connection")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      <.div(^.id := "mainContainer"/*, DashBoardCSS.Style.mainContainerDiv*/, ^.className:="mainContainerDiv")(
        <.div()(
          ConnectionsPresets.component(Unit)
        ),
        // AddNewAgent(AddNewAgent.Props(ctl)),
        <.div(/*DashBoardCSS.Style.splitContainer*/^.className:="splitContainer")(
          <.div(^.className := "split")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                // todo: Need to parameterize the Search area depending on EntityType (e.g. Talent, Project) and preset
                ConnectionsSearch.component(Unit)
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10", ^.id := "dashboardResults2",^.className:="dashboardResults2"/*, DashBoardCSS.Style.dashboardResults2*/)(
                // todo: Results will be parameterized depending on EntityType, preset
                Panel(Panel.Props("Connections"), <.div(
                  P.proxy().renderFailed(ex => "Error loading"),
                  P.proxy().renderPending(_ > 500, _ => "Loading..."),
                  P.proxy().render(connectionsRootModel =>
                    ConnectionList(connectionsRootModel.connectionsResponse)
                  )
                ))
              )
            )
          )
        )//row
      ) //mainContainer
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[ConnectionsRootModel]]) = component(Props(proxy))
}



object ConnectionList {
  case class ConnectionListProps(connections: Seq[ConnectionsModel])

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