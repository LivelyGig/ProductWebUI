package synereo.client

import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, DashBoardCSS, HeaderCSS}
import synereo.client.modules._
import synereo.client.services.SYNEREOCircuit
import synereo.client.logger._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry

@JSExport("SYNEREOMain")
object SYNEREOMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object DashboardLoc extends Loc

  case object SynereoUserProfileViewLOC extends Loc

  case object SynereoLoc extends Loc

  case object SynereoDashboardLoc extends Loc

  case object SynereoTimelineViewLOC extends Loc

  case object SynereoBlogPostFullLOC extends Loc

  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, DashboardLoc) ~> renderR(ctl => SynereoLogin(ctl))
      | staticRoute("#synereologin", SynereoLoc) ~> renderR(ctl => SynereoLogin(ctl))
      | staticRoute("#synereodashboard", SynereoDashboardLoc) ~> renderR(ctl => SynereoDashboard(ctl))
      | staticRoute("#synereofullblogpost", SynereoBlogPostFullLOC) ~> renderR(ctl => SynereoBlogPostFull(ctl))
      | staticRoute("#userprofileview", SynereoUserProfileViewLOC) ~> renderR(ctl => SynereoUserProfileView(ctl))
      | staticRoute("#usertimeline", SynereoTimelineViewLOC) ~> renderR(ctl => SynereoTimelineView(ctl))
      ).notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div()(
      <.img(^.id := "loginLoader", DashBoardCSS.Style.loading, ^.className := "hidden", ^.src := "./assets/images/processing.gif"),
      <.nav(^.id := "naviContainer", HeaderCSS.Style.naviContainer, ^.className := "navbar navbar-fixed-top")(
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10")(
          <.div(^.className := "navbar-header")(
            <.button(^.className := "navbar-toggle", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#navi-collapse")(
              <.span(^.color := "white")(Icon.thList)
            ),
            c.link(DashboardLoc)(^.className := "navbar-header", <.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/Synereo-logo-name.png"))
          ),
          <.div(^.id := "navi-collapse", ^.className := "collapse navbar-collapse")(
            SYNEREOCircuit.connect(_.user)(proxy => MainMenu(MainMenu.Props(c, r.page, proxy)))
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
    // tell React to render the router in the document body
    //ReactDOM.render(router(), dom.document.getElementById("root"))
    ReactDOM.render(router(), dom.document.getElementById("root"))
  }
}