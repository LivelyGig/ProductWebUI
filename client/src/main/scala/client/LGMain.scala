package client

import japgolly.scalajs.react.extra.router._
import client.components.{GlobalStyles, Icon}
import client.css._
import client.logger._
import client.modules._
import org.scalajs.dom
import client.services.LGCircuit
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react.{React, ReactDOM}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import scala.scalajs.js
import scalacss.internal.mutable.GlobalRegistry

// scalastyle:off
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

  case object NotificationsLoc extends Loc

  val userProxy = LGCircuit.connect(_.user)
  val appProxy = LGCircuit.connect(_.appRootModel)
  val introProxy = LGCircuit.connect(_.introduction)

  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, LandingLoc) ~> renderR(ctl => LandingLocation.component(ctl))
      | staticRoute(s"#${AppModule.DASHBOARD_VIEW}", DashboardLoc) ~> renderR(ctl => Dashboard.component(ctl))
      | staticRoute(s"#${AppModule.NOTIFICATIONS_VIEW}", NotificationsLoc) ~> renderR(ctl => introProxy(proxy => NotificationResults(NotificationResults.Props(proxy))))
      | staticRoute(s"#${AppModule.MESSAGES_VIEW}", MessagesLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.MESSAGES_VIEW, proxy))))
      // ToDo: the following should be renamed from projects to jobs ?
      | staticRoute(s"#${AppModule.PROJECTS_VIEW}", JobPostsLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.PROJECTS_VIEW, proxy))))
      // ToDo: the following should be contracts not contract
      | staticRoute(s"#${AppModule.CONTRACTS_VIEW}", ContractsLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.CONTRACTS_VIEW, proxy))))
      // ToDo: following route should be called Profiles not Talent.
      | staticRoute(s"#${AppModule.PROFILES_VIEW}", ProfilesLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.PROFILES_VIEW, proxy))))
      | staticRoute(s"#${AppModule.OFFERINGS_VIEW}", OfferingsLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.OFFERINGS_VIEW, proxy))))
      //      | staticRoute(s"#${AppModule.NOTIFICATIONS_VIEW}", NotificationsLoc) ~> renderR(ctl =>appProxy(proxy => AppModule(AppModule.Props(AppModule.NOTIFICATIONS_VIEW, proxy))))
      | staticRoute(s"#${AppModule.CONNECTIONS_VIEW}", ConnectionsLoc) ~> renderR(ctl => appProxy(proxy => AppModule(AppModule.Props(AppModule.CONNECTIONS_VIEW, proxy)))))
      .notFound(redirectToPage(LandingLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.backgroundImage := "url(./assets/images/LG_Background3E.svg)")(
      <.img(^.id := "loginLoader", DashBoardCSS.Style.loading, ^.className := "hidden", ^.src := "./assets/images/processing.gif"),
      <.nav(^.id := "naviContainer", HeaderCSS.Style.naviContainer, HeaderCSS.Style.navbarFixedTop, ^.className := "navbar")
      (
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-10")(
          <.div(/*^.className := "navbar-header"*/)(
            <.div(^.className := "col-md-8 col-sm-8 col-xs-6", DashBoardCSS.Style.padding0px, DashBoardCSS.Style.DisplayFlex)(
              c.link(LandingLoc)(^.className := "navbar-header", <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")),
              <.button(^.className := "navbar-toggle", "data-toggle".reactAttr := "collapse", HeaderCSS.Style.navbarToggle, "data-target".reactAttr := "#navi-collapse")(
                r.page match {
                  case JobPostsLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Jobs")
                  case DashboardLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Dashboard")
                  case MessagesLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Messages")
                  case OfferingsLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Offerings")
                  case ProfilesLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Profiles")
                  case ContractsLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Contracts")
                  case ConnectionsLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Connections")
                  case NotificationsLoc => <.span(^.color := "white", ^.float := "right")(Icon.navicon, "  ", " Notifications")
                  case _ => <.span()
                }
              ),
              <.div(^.className := " loggedInUser")(
                userProxy(userProxy => MainMenu(MainMenu.Props(c, r.page, userProxy)))
              )
            ),
            <.div(^.className := "col-md-4 col-sm-4 col-xs-6 ", DashBoardCSS.Style.padding0px)(userProxy(userProxy => LoggedInUser(LoggedInUser.Props(c, r.page, userProxy))))
          ),
          <.div(^.className := "loggedInUserNav")(
            <.div(^.id := "navi-collapse", ^.className := "collapse navbar-collapse")(
              userProxy(userProxy => MainMenu(MainMenu.Props(c, r.page, userProxy)))
            )
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
    log.warn("LGMain - Application starting")
    // send log messages also to the server
    log.enableServerLogging("/logging")
    log.info("LGMain - This message goes to server as well")
    // create stylesheet
    GlobalStyles.addToDocument()
    AppCSS.load
    window.sessionStorage.removeItem("sessionPingTriggered")
    //    standaloneCSS.render[HTMLStyleElement].outerHTML
    GlobalRegistry.addToDocumentOnRegistration()
    // create the router
    val router = Router(BaseUrl(dom.window.location.href.takeWhile(_ != '#')), routerConfig)
    // tell React to render the router in the document body
    //ReactDOM.render(router(), dom.document.getElementById("root"))
    ReactDOM.render(router(), dom.document.getElementById("root"))
  }
}