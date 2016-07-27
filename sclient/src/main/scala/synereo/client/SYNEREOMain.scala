package synereo.client

//import japgolly.scalajs.react.{Callback, ReactDOM}
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, SynereoCommanStylesCSS}
import shared.models.UserModel
import synereo.client.modules._
import synereo.client.services.SYNEREOCircuit
import synereo.client.logger._
import japgolly.scalajs.react.{React, ReactDOM}

import scala.scalajs.js
import js.{Date, UndefOr}
import japgolly.scalajs.react.extra.router._
import org.querki.jquery._
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
import japgolly.scalajs.react.{React, ReactDOM}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._

import scala.scalajs.js
import js.{Date, UndefOr}

//import shared.models.MessagesModel
import shared.RootModels.MessagesRootModel

@JSExport("SYNEREOMain")
object SYNEREOMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object UserProfileViewLOC extends Loc

  case object SynereoLoc extends Loc

  case object DashboardLoc extends Loc

  case object TimelineViewLOC extends Loc

  case object PostFullViewLOC extends Loc

  case object MarketPlaceLOC extends Loc

  case object AccountLOC extends Loc

  case object NotificationsLOC extends Loc

  case object PeopleLOC extends Loc

  def sidebar = Callback {
    val searchContainer: js.Object = "#searchContainer"
    $(searchContainer).toggleClass("sidebar-left sidebar-animate sidebar-lg-show")
  }

  val userProxy = SYNEREOCircuit.connect(_.user)
  val connectionProxy = SYNEREOCircuit.connect(_.connections)
  val messagesProxy = SYNEREOCircuit.connect(_.messages)
  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, SynereoLoc) ~> renderR(ctl => Login(Login.Props()))
      | staticRoute("#login", SynereoLoc) ~> renderR(ctl => Login(Login.Props()))
      | staticRoute("#people", PeopleLOC) ~> renderR(ctl => connectionProxy(s => ConnectionsResults(s)))
      | staticRoute("#account", AccountLOC) ~> renderR(ctl => userProxy(s => AccountInfo(s)))
      | staticRoute("#dashboard", DashboardLoc) ~> renderR(ctl => messagesProxy(s => Dashboard(s)))
      | staticRoute("#postfullview", PostFullViewLOC) ~> renderR(ctl => PostFullView(ctl))
      | staticRoute("#userprofileview", UserProfileViewLOC) ~> renderR(ctl => userProxy(proxy => UserProfileView(UserProfileView.Props(proxy))))
      | staticRoute("#timelineview", TimelineViewLOC) ~> renderR(ctl => TimelineView(ctl))
      | staticRoute("#notifications", NotificationsLOC) ~> renderR(ctl => NotificationView(NotificationView.Props()))
      | staticRoute("#marketplacefull", MarketPlaceLOC) ~> renderR(ctl => MarketPlaceFull(ctl)))
      .notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
    //      .onPostRender((prev, cur) => Callback.log(s"Page changing from $prev to $cur."))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div()(
      <.div(^.id := "loadingScreen", ^.className := "hidden", SynereoCommanStylesCSS.Style.loadingScreen)(
        <.span(^.id := "loginLoader", SynereoCommanStylesCSS.Style.loading, ^.className := "hidden", Icon.spinnerIconPulse)
      ),
      <.nav(^.id := "naviContainer", SynereoCommanStylesCSS.Style.naviContainer, ^.className := "navbar navbar-fixed-top")(
        //        <.div(^.className := "col-lg-1 col-md-1 col-sm-1")(
        //          //Adding toggle button for sidebar
        //          if (r.page == SynereoLoc) {
        //            <.span()
        //          } else {
        //            <.button(^.id := "sidebarbtn", ^.`type` := "button", ^.className := "navbar-toggle toggle-left", ^.float := "left", "data-toggle".reactAttr := "sidebar", "data-target".reactAttr := ".sidebar-left",
        //              ^.onClick --> sidebar)(
        //              <.span(Icon.bars)
        //            )
        //          }
        //        ),
        <.div(^.className := "col-lg-12 col-md-12 col-sm-12")(
          <.div(
            if (r.page == SynereoLoc) {
              <.span()
            } else {
              <.button(^.id := "sidebarbtn", ^.`type` := "button", ^.className := "navbar-toggle toggle-left", ^.float := "left", "data-toggle".reactAttr := "sidebar", "data-target".reactAttr := ".sidebar-left",
                ^.onClick --> sidebar)(
                <.span(Icon.bars)
              )
            }
          ),
          <.div(^.className := "navbar-header")(
            <.button(^.className := "navbar-toggle", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#navi-collapse")(
              <.span(^.color := "white")(Icon.thList)
            ),
            if (r.page == DashboardLoc) {
              <.img(if (r.page == SynereoLoc) SynereoCommanStylesCSS.Style.imgLogo else SynereoCommanStylesCSS.Style.imgLogoOtherLoc, ^.src := "./assets/synereo-images/Synereo_Logo_White.png")
            } else {
              c.link(DashboardLoc)(^.onClick --> c.refresh)(^.className := "navbar-header",
                <.img(if (r.page == SynereoLoc) SynereoCommanStylesCSS.Style.imgLogo else SynereoCommanStylesCSS.Style.imgLogoOtherLoc, ^.src := "./assets/synereo-images/Synereo_Logo_White.png")
              )
            }
          ),
          <.div(^.id := "navi-collapse", ^.className := "collapse navbar-collapse")(
            userProxy(proxy => MainMenu(MainMenu.Props(c, r.page, proxy)))
          )
        ),
        <.div()()
      ),
      // the vertically center area
      r.render() /*,
      Footer(Footer.Props(c, r.page))*/
    )
  }

  @JSExport
  def main(): Unit = {
    log.warn("Application starting")
    // send log messages also to the server
    log.enableServerLogging("/logging")
    log.info("This message goes to server as well")
    // create stylesheet
    GlobalStyles.addToDocument()
    AppCSS.load
    GlobalRegistry.addToDocumentOnRegistration()
    // create the router
    val router = Router(BaseUrl(dom.window.location.href.takeWhile(_ != '#')), routerConfig)
    window.sessionStorage.removeItem("sessionPingTriggered")
    // tell React to render the router in the document body
    //ReactDOM.render(router(), dom.document.getElementById("root"))
    ReactDOM.render(router(), dom.document.getElementById("root"))
  }
}