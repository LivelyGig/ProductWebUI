package synereo.client.modules


import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.UserModel
import synereo.client.SYNEREOMain
import synereo.client.SYNEREOMain.Loc
import synereo.client.css.{ConnectionsCSS, DashboardCSS, SynereoCommanStylesCSS, UserProfileViewCSS}
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.{ConnectionsModel, MessagePost}
import japgolly.scalajs.react
import org.scalajs.dom._
import shared.RootModels.ConnectionsRootModel
import synereo.client.sessionitems.SessionItems
import synereo.client.components.{Icon, MIcon}
import synereo.client.utils.ConnectionsUtils

import scalacss.ScalaCssReact._


/**
  * Created by bhagyashree.b on 2016-07-01.
  */


object Info {

  case class Props(proxy: ModelProxy[UserModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)
  val agentUID = ConnectionsUtils.getSelfConnnection().source
  val newAgentUID = agentUID.substring(8)
  val output = newAgentUID.split("\"")


  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props): Callback = Callback {
    }
  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      <.div(^.id := "connectionsContainerMain", ConnectionsCSS.Style.connectionsContainerMain,UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
//        <.div(^.className := "row")(
//          //Left Sidebar
//          <.div(^.id := "searchContainer", ^.className := "col-md-2  sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
//            //            Footer(Footer.Props(c, r.page))
//            Sidebar(Sidebar.Props())
//          )
//        ),
        <.div(UserProfileViewCSS.Style.agentUID)(s"Agent UID : ${output.head}"),
        <.div(UserProfileViewCSS.Style.agentUID)("Build Number : ")
      ) //connectionsContainerMain
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[UserModel]) = component(Props(proxy))
}


