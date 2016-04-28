package client


import client.LGMain.{ContractsLoc, OfferingsLoc}
import japgolly.scalajs.react.extra.router._
import client.components.{GlobalStyles, Icon}
import client.css.{AppCSS, FooterCSS, HeaderCSS, DashBoardCSS}
import client.logger._
import client.modules._
import org.querki.jquery._
import org.scalajs.dom
import client.services.LGCircuit
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

  case object ProfilesLoc extends Loc

  case object ConnectionsLoc extends Loc

  case object LegalLoc extends Loc

  case object BiddingScreenLoc extends Loc

  case object LandingLoc extends Loc


  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, LandingLoc) ~> renderR(ctl => LandingLocation.component(ctl))
      | staticRoute("#dashboard", DashboardLoc) ~> renderR(ctl => Dashboard.component(ctl))
      | staticRoute("#messages", MessagesLoc) ~> renderR(ctl => AppModule(AppModule.Props("messages")))
      // ToDo: the following should be renamed from projects to jobs ?
      | staticRoute("#projects", JobPostsLoc) ~> renderR(ctl => AppModule(AppModule.Props("projects")))
      // ToDo: the following should be contracts not contract
      | staticRoute("#contract", ContractsLoc) ~> renderR(ctl => AppModule(AppModule.Props("contract")))
      // ToDo: following route should be called Profiles not Talent.
      | staticRoute("#talent", ProfilesLoc) ~> renderR(ctl => AppModule(AppModule.Props("talent")))
      | staticRoute("#offerings", OfferingsLoc) ~> renderR(ctl => AppModule(AppModule.Props("offerings")))
      | staticRoute("#connections", ConnectionsLoc) ~> renderR(ctl => AppModule(AppModule.Props("connections")))
      ).notFound(redirectToPage(LandingLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
      <.div()(
      <.img(^.id := "loginLoader", DashBoardCSS.Style.loading, ^.className := "hidden", ^.src := "./assets/images/processing.gif"),
      <.nav(^.id := "naviContainer", HeaderCSS.Style.naviContainer, ^.className := "navbar navbar-fixed-top")(
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10")(
          <.div(^.className := "navbar-header")(
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8", DashBoardCSS.Style.padding0px, DashBoardCSS.Style.DisplayFlex)(
              c.link(LandingLoc)(^.className := "navbar-header", <.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/logo-symbol.png")),
              <.button(^.className := "navbar-toggle", "data-toggle".reactAttr := "collapse", HeaderCSS.Style.navbarToggle, "data-target".reactAttr := "#navi-collapse")(
                r.page match {
                  case JobPostsLoc      => <.span(^.color := "white",^.float:="right")("Jobs ","  ", Icon.thList)
                  case  DashboardLoc    => <.span(^.color := "white",^.float:="right")("Dashboard ","  ", Icon.thList)
                  case  MessagesLoc     => <.span(^.color := "white",^.float:="right")("Messages ","  ", Icon.thList)
                  case  OfferingsLoc    => <.span(^.color := "white",^.float:="right")("Offerings ","  ", Icon.thList)
                  case  ProfilesLoc     => <.span(^.color := "white",^.float:="right")("Profiles ","  ", Icon.thList)
                  case  ContractsLoc    => <.span(^.color := "white",^.float:="right")("Contracts ","  ", Icon.thList)
                  case  ConnectionsLoc  => <.span(^.color := "white",^.float:="right")("Connections ","  ", Icon.thList)
                  case _                => <.span()
                }
              )
            )
         ,
            <.div(^.className:="col-md-4 col-sm-4 col-xs-4 loggedInUserNav", DashBoardCSS.Style.padding0px)(LGCircuit.connect(_.user)(proxy => LoggedInUser(LoggedInUser.Props(c, r.page, proxy))))
          ),
          <.div(^.id := "navi-collapse", ^.className := "collapse navbar-collapse")(
            LGCircuit.connect(_.user)(proxy => MainMenu(MainMenu.Props(c, r.page, proxy)))
          ),
          <.div(^.className:="loggedInUser")(LGCircuit.connect(_.user)(proxy => LoggedInUser(LoggedInUser.Props(c, r.page, proxy))))
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
    log.warn("LGMain - Application starting")
    // send log messages also to the server
    log.enableServerLogging("/logging")
    log.info("LGMain - This message goes to server as well")
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