package synereo.client

import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, SynereoCommanStylesCSS}
import synereo.client.modules._
import synereo.client.services.SYNEREOCircuit
import synereo.client.logger._

import scala.scalajs.js
import japgolly.scalajs.react.extra.router._
import org.querki.jquery._
import org.scalajs.dom

import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import scalacss.mutable.GlobalRegistry
import scala.scalajs.js
import scala.scalajs.js.timers._
import diode.AnyAction._
import synereo.client.handlers.CloseAllPopUp
import synereo.client.sessionitems.SessionItems


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

  /**
    * all diode connect proxy
    * there proxy are used in case when a component wants to connect to a rootmodel of the application
    * so as to listen changes into model and read/write to/from model.
    */
  val userProxy = SYNEREOCircuit.connect(_.user)
  val connectionProxy = SYNEREOCircuit.connect(_.connections)
  val messagesProxy = SYNEREOCircuit.connect(_.messages)
  val appProxy = SYNEREOCircuit.connect(_.appRootModel)
  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, SynereoLoc) ~> renderR(ctl => LoginView(LoginView.Props()))
      | staticRoute("#login", SynereoLoc) ~> renderR(ctl => LoginView(LoginView.Props()))
      | staticRoute("#people", PeopleLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.PEOPLE_VIEW, proxy))))
      | staticRoute("#account", AccountLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.ACCOUNT_VIEW, proxy))))
      | staticRoute("#dashboard", DashboardLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.DASHBOARD_VIEW, proxy))))
      | staticRoute("#fullpost", PostFullViewLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.POSTFULL_VIEW, proxy))))
      | staticRoute("#userprofile", UserProfileViewLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.USERPROFILE_VIEW, proxy))))
      | staticRoute("#timeline", TimelineViewLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.TIMELINE_VIEW, proxy))))
      | staticRoute("#notifications", NotificationsLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.NOTIFICATIONS_VIEW, proxy))))
      | staticRoute("#marketplace", MarketPlaceLOC) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.MARKETPLACE_VIEW, proxy)))))
      .notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
      .onPostRender((prev, cur) => /*Callback.log(s"Page changing from $prev to $cur."*/
        Callback{
          $(".modal-backdrop".asInstanceOf[js.Object]).remove()
          SYNEREOCircuit.dispatch(CloseAllPopUp()
        )}
      )
  }.renderWith(layout)

  // scalastyle:off

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
            if (r.page == DashboardLoc || r.page == SynereoLoc) {
              c.link(DashboardLoc)(
              <.img(if (r.page == SynereoLoc) SynereoCommanStylesCSS.Style.imgLogo else SynereoCommanStylesCSS.Style.imgLogoOtherLoc, ^.src := "./assets/synereo-images/Synereo_Logo_White.png"),
              <.img(SynereoCommanStylesCSS.Style.imgSmallLogo, ^.src := "./assets/synereo-images/synereologo.png"))
            } else {
              c.link(DashboardLoc)(^.onClick --> c.refresh)(^.className := "navbar-header",
                <.img(if (r.page == SynereoLoc) SynereoCommanStylesCSS.Style.imgLogo else SynereoCommanStylesCSS.Style.imgLogoOtherLoc, ^.src := "./assets/synereo-images/Synereo_Logo_White.png"),
                <.img(SynereoCommanStylesCSS.Style.imgSmallLogo, ^.src := "./assets/synereo-images/synereologo.png")
              )
            }
          ),
          <.div(^.id := "navi-collapse",SynereoCommanStylesCSS.Style.naviCollapse, ^.className := "collapse navbar-collapse")(
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
    //    log.disableServerLogging()
    log.info("This message goes to server as well")
    // create stylesheet
    GlobalStyles.addToDocument()
    AppCSS.load
    GlobalRegistry.addToDocumentOnRegistration()
    // create the router
    val router = Router(BaseUrl(dom.window.location.href.takeWhile(_ != '#')), routerConfig)
    // tell React to render the router in the document body
    //ReactDOM.render(router(), dom.document.getElementById("root"))
    ReactDOM.render(router(), dom.document.getElementById("root"))
    /*if (dom.window.sessionStorage.getItem(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI) == null) {
      dom.window.location.href = "/"
    }
*/

  }
}