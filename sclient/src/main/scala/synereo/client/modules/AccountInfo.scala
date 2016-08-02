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


object AccountInfo {

  val searchContainer: js.Object = "#searchContainer"
  case class Props(proxy: ModelProxy[UserModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  val response = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST)
  val agentUID = ConnectionsUtils.getSelfConnnection().source
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
      <.div()(
//        <.div(^.className := "row")(
//          //Left Sidebar
//          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
//            ^.onMouseEnter --> Callback{$(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")},
//            ^.onMouseLeave --> Callback{$(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")}
//          )(
//            //            Footer(Footer.Props(c, r.page))
//            Sidebar(Sidebar.Props())
//          )
//        ),
        <.div(UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
          <.div(UserProfileViewCSS.Style.agentUID)(s"Agent UID : ${output.head}"),
          <.div(UserProfileViewCSS.Style.agentUID)("Build Number : "),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("DSLCommLinkClient : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button")
              )
            )),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("DSLEvaluator : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button")
              )
            )),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("DSLEvaluatorPreferredSupplier : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button")
              )
            )),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("BFactoryCommLinkServer : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button")
              )
            )),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("BFactoryCommLinkClient : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button")
              )
            )),
          <.div(UserProfileViewCSS.Style.agentUID)(<.label(UserProfileViewCSS.Style.label)("BFactoryEvaluator : "))
          (<.div(UserProfileViewCSS.Style.buttonDiv)(<.button(UserProfileViewCSS.Style.sectionButtons)
          (^.className := "btn btn-default", ^.`type` := "button")("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button")("Add")),
            <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
              <.input(^.`type` := "text"),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button")
              ),
              <.span(^.className := "input-group-btn",
                <.button(^.className := "btn btn-default", Icon.times, ^.`type` := "button")
              )
            ))
        ))   //connectionsContainerMain
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[UserModel]) = component(Props(proxy))
}


