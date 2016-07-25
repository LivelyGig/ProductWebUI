package synereo.client.modules



import shared.models.UserModel
import synereo.client.css.{ConnectionsCSS, DashboardCSS, SynereoCommanStylesCSS, UserProfileViewCSS}
import diode.react._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import shared.models.{ConnectionsModel, MessagePost}
import org.scalajs.dom._
import shared.sessionitems.SessionItems
import synereo.client.components.{Icon, MIcon}
import synereo.client.utils.ConnectionsUtils
import japgolly.scalajs.react.{Callback, ReactComponentB}
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, SynereoCommanStylesCSS}
import japgolly.scalajs.react.{React, ReactDOM}
import scala.scalajs.js
import js.{Date, UndefOr}
import japgolly.scalajs.react.{React, ReactDOM}
import scala.scalajs.js
import js.{Date, UndefOr}
import japgolly.scalajs.react.{Callback, ReactComponentB}
import synereo.client.SYNEREOMain
import synereo.client.css.UserProfileViewCSS
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, SynereoCommanStylesCSS}
import japgolly.scalajs.react.{React, ReactDOM}
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react.{React, ReactDOM}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js
import js.{Date, UndefOr}




/**
  * Created by bhagyashree.b on 2016-07-01.
  */


object Info {

  val searchContainer: js.Object = "#searchContainer"
  case class Props(proxy: ModelProxy[UserModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  val response = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST)
  val agentUID = ConnectionsUtils.getSelfConnnection(response).source
  val newAgentUID = agentUID.substring(8)
//  println(s"agentUID ${newAgentUID}")
  val output = newAgentUID.split("\"")
//  println(s"output ${output.head}")
//  for(o <- output)yield {  println(o)}


  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props): Callback = Callback {
    }
  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      <.div(^.id := "connectionsContainerMain", ConnectionsCSS.Style.connectionsContainerMain,UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
            ^.onMouseEnter --> Callback{$(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")},
            ^.onMouseLeave --> Callback{$(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")}
          )(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )
        ),
        <.div(UserProfileViewCSS.Style.agentUID)(s"Agent UID : ${output.head}"),
        <.div(UserProfileViewCSS.Style.agentUID)("Build Number : ")
      ) //connectionsContainerMain
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[UserModel]) = component(Props(proxy))
}


