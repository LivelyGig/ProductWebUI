package livelygig.client

import japgolly.scalajs.react.{ReactDOM, React}
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import livelygig.client.components.GlobalStyles
import livelygig.client.css.AppCSS
import livelygig.client.logger._
import livelygig.client.modules._
import livelygig.client.css.HeaderCSS

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry

@JSExport("LGMain")
object LGMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object DashboardLoc extends Loc

  case object TodoLoc extends Loc

  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._

    (staticRoute(root, DashboardLoc) ~> renderR(ctl => Dashboard.component(ctl))

      ).notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.nav(HeaderCSS.Style.naviContainer ,^.className := "navbar navbar-fixed-top")(
        <.div( ^.className := "container")(
          c.link(DashboardLoc)(HeaderCSS.Style.logoContainer,^.className := "navbar-header",<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/logo-symbol.png")),
          <.div(^.className := "collapse navbar-collapse")(
            MainMenu(MainMenu.Props(c, r.page))
          )
        )
      ),
      // currently active module is shown in this container
      <.div(^.className := "container")(r.render())
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
//  @JSExport
//  def main(): Unit = {
//    log.warn("Application starting")
//    // send log messages also to the server
//    log.enableServerLogging("/logging")
//    log.info("This message goes to server as well")
//
//    // create stylesheet
//    GlobalStyles.addToDocument()
//    AppCSS.load
//    GlobalRegistry.addToDocumentOnRegistration()
//    // create the router
//    val router = Router(BaseUrl(dom.window.location.href.takeWhile(_ != '#')), routerConfig)
//    // tell React to render the router in the document body
//    ReactDOM.render(router(), dom.document.getElementById("root"))
//  }
}
