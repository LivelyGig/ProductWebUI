package livelygig.client


import japgolly.scalajs.react.extra.router._
import livelygig.client.components.{GlobalStyles, Icon}
import livelygig.client.css.{AppCSS, FooterCSS, HeaderCSS,DashBoardCSS}
import livelygig.client.logger._
import livelygig.client.modules._
import org.scalajs.dom
import livelygig.client.services.{LGCircuit}
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
import japgolly.scalajs.react.{ReactDOM, React}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import scala.scalajs.js
import js.{Date, UndefOr}

@JSExport("LGMain")
object LGMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object DashboardLoc extends Loc

  case object TodoLoc extends Loc

  case object CreateAgentLoc extends Loc

  case object AgentLoginLoc extends Loc

  case object MessagesLoc extends Loc

  case object JobPostsLoc extends Loc

  case object ContractsLoc extends Loc

  case object ContestsLoc extends Loc

  case object EmployersLoc extends Loc

  case object OfferingsLoc extends Loc

  case object TalentLoc extends Loc

  case object ConnectionsLoc extends Loc

  case object LegalLoc extends Loc

  case object BiddingScreenLoc extends Loc



  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, DashboardLoc) ~> renderR(ctl => Dashboard.component(ctl))
      | staticRoute("#messages", MessagesLoc) ~> renderR(ctl => AppModule(AppModule.Props("messages")))
      | staticRoute("#projects", JobPostsLoc) ~> renderR(ctl => AppModule(AppModule.Props("projects")))
      | staticRoute("#contract", ContractsLoc) ~> renderR(ctl => AppModule(AppModule.Props("contract")))
      | staticRoute("#contests", ContestsLoc) ~> renderR(ctl => <.div(^.id := "mainContainer", ^.className := "DashBoardCSS_Style-mainContainerDiv")(""))
      | staticRoute("#talent", TalentLoc) ~> renderR(ctl => AppModule(AppModule.Props("talent")))
      //|staticRoute("#apploc", AppModuleLoc) ~> renderR(ctl => AppModule(AppModule.Props(ctl , "")))
      | staticRoute("#offerings", OfferingsLoc) ~> renderR(ctl => AppModule(AppModule.Props("offerings")))
      | staticRoute("#employers", EmployersLoc) ~> renderR(ctl => <.div(^.id := "mainContainer", ^.className := "DashBoardCSS_Style-mainContainerDiv")(""))
      | staticRoute("#connections", ConnectionsLoc) ~> renderR(ctl => AppModule(AppModule.Props("connections")))
      ).notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div()(
      <.img(^.id:="loginLoader", DashBoardCSS.Style.loading, ^.className:="hidden", ^.src:="./assets/images/processing.gif"),
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.nav(^.id := "naviContainer", HeaderCSS.Style.naviContainer, ^.className := "navbar navbar-fixed-top")(
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10")(
          <.div(^.className := "navbar-header")(
            <.button(^.className := "navbar-toggle", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#navi-collapse")(
              <.span(^.color := "white")(Icon.thList)
            ),
            c.link(DashboardLoc)(HeaderCSS.Style.logoContainer, ^.className := "navbar-header", <.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/logo-symbol.png"))
          ),
          <.div(^.id := "navi-collapse", ^.className := "collapse navbar-collapse")(
            LGCircuit.connect(_.user)(proxy => MainMenu(MainMenu.Props(c, r.page, proxy)))
          )
        ),
        <.div()()
      ),
      // the vertically center area
      r.render(),
      Footer(Footer.Props(c, r.page))
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