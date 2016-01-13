package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.Panel
import livelygig.client.components.{ConnectionList, Icon}
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.{UpdateConnection, RefreshConnections}

import scalacss.ScalaCssReact._

object ConnectionsResults {
  case class Props(proxy: ModelProxy[Pot[ConnectionsModel]])
  case class State(selectedItem: Option[ConnectionsModel] = None)
  class Backend($: BackendScope[Props, State]){
    def mounted(props: Props) =
      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections))
    def render (p:Props, s: State) =
      Panel(Panel.Props("Connections"), <.div(
        p.proxy().renderFailed(ex => "Error loading"),
        p.proxy().renderPending(_ > 500, _ => "Loading..."),
        p.proxy().render(connectionsModel =>
          ConnectionList(connectionsModel.connectionsResponse)
        )
      ))

  }


  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Connection")
    .initialState(State()) 
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[ConnectionsModel]]) = component(Props(proxy))
}
