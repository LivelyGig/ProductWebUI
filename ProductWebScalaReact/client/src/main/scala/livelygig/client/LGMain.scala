package livelygig.client


import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._
//import livelygig.client.modals.AddNewAgent
import livelygig.client.components.{GlobalStyles, Icon}
import livelygig.client.css.{AppCSS, FooterCSS, HeaderCSS}
import livelygig.client.logger._
import livelygig.client.models.ConnectionsModel
import livelygig.client.modals._
import livelygig.client.modules._
import org.scalajs.dom
import livelygig.client.services.{LGCircuit}
import livelygig.client.models.ConnectionsModel

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry
import japgolly.scalajs.react.{ReactDOM, React}

@JSExport("LGMain")
object LGMain extends js.JSApp {
  // Define the locations (pages) used in this application
  sealed trait Loc
  case object DashboardLoc extends Loc
  case object TodoLoc extends Loc
  case object CreateAgentLoc extends  Loc
  case object EmailValidationLoc extends Loc
  case object AgentLoginLoc extends Loc
  case object MessagesLoc extends Loc
  case object ProjectsLoc extends Loc
  case object ContractsLoc extends Loc
  case object ContestsLoc extends Loc
  case object EmployersLoc extends Loc
  case object OfferingsLoc extends Loc
  case object TalentLoc extends Loc
  case object ConnectionsLoc extends Loc
  case object LegalLoc extends Loc
  case object BiddingScreenLoc extends Loc
  case object AppModuleLoc extends Loc

  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    (staticRoute(root, DashboardLoc) ~> renderR(ctl => Dashboard.component(ctl))
      |staticRoute("#addnewagent", CreateAgentLoc) ~> render(CreateAgent.component(Unit))
      |staticRoute("#emailvalidation", EmailValidationLoc) ~> renderR(ctl => EmailValidation.component(Unit))
      |staticRoute("#agentlogin", AgentLoginLoc) ~> renderR(ctl => AgentLogin.component(Unit))
      |staticRoute("#messages", MessagesLoc) ~> renderR(ctl =>  AppModule(AppModule.Props(ctl , "messages"))   )
      |staticRoute("#projects", ProjectsLoc) ~> renderR(ctl =>  AppModule(AppModule.Props(ctl , "projects"))   )
      |staticRoute("#contract", ContractsLoc) ~> renderR(ctl => AppModule(AppModule.Props(ctl , "contract")) )
      |staticRoute("#contests", ContestsLoc) ~> renderR(ctl => <.div(^.id:="mainContainer", ^.className:="DashBoardCSS_Style-mainContainerDiv")(""))
      |staticRoute("#talent", TalentLoc) ~> renderR(ctl => AppModule(AppModule.Props(ctl , "talent")))
      //|staticRoute("#apploc", AppModuleLoc) ~> renderR(ctl => AppModule(AppModule.Props(ctl , "")))
      |staticRoute("#offerings", OfferingsLoc) ~> renderR(ctl => <.div(^.id:="mainContainer", ^.className:="DashBoardCSS_Style-mainContainerDiv")(""))
    //  |staticRoute("#contracts", ContractsLoc) ~> renderR(ctl => <.div(^.id:="mainContainer", ^.className:="DashBoardCSS_Style-mainContainerDiv")(""))
      |staticRoute("#employers", EmployersLoc) ~> renderR(ctl => <.div(^.id:="mainContainer", ^.className:="DashBoardCSS_Style-mainContainerDiv")(""))
      |staticRoute("#connections", ConnectionsLoc) ~> renderR(ctl => LGCircuit.connect(_.connections)(Connections(_)))
      ).notFound(redirectToPage(DashboardLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.nav(^.id:="naviContainer" , HeaderCSS.Style.naviContainer ,^.className := "navbar navbar-fixed-top")(
        <.div()(
          <.div (^.className:="navbar-header")(
            <.button(^.className:="navbar-toggle","data-toggle".reactAttr := "collapse" , "data-target".reactAttr:="#navi-collapse")(
              <.span(Icon.thList)
            ),
            c.link(DashboardLoc)(HeaderCSS.Style.logoContainer,^.className := "navbar-header",<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/logo-symbol.png"))
          ),
          <.div(^.id:="navi-collapse", ^.className := "collapse navbar-collapse")(
            LGCircuit.connect(_.user)(proxy => MainMenu(MainMenu.Props(c, r.page, proxy)))
//            MainMenu(MainMenu.Props(c, r.page)),
//            <.div(HeaderCSS.Style.LoginInMenuItem)(
//              AddNewAgent(AddNewAgent.Props(c))
//            )
//         if ()
//        <.div(HeaderCSS.Style.LoginInMenuItem)(
//          <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
//          <.div(HeaderCSS.Style.displayInline) ("Dale Steyn"),
          )
        )
      ),

      // the following div wraps the dashboard
      <.div(^.id:="ed-tmp")(r.render()),

      <.nav(^.id:="footerContainer", FooterCSS.Style.footerContainer)(
        <.div(^.className:="row")(
          <.div(^.className:="col-md-4 col-sm-4 col-xs-3")(
            <.div(FooterCSS.Style.footGlyphContainer)(
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://github.com/LivelyGig", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="GitHub" )(<.span()(Icon.github))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://twitter.com/LivelyGig", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="Twitter" )(
                  <.span()(Icon.twitter))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://www.facebook.com/LivelyGig-835593343168571/", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="Facebook" )(
                  <.span()(Icon.facebook))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://plus.google.com/+LivelygigCommunity", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="Google Plus" )(
                  <.span()(Icon.googlePlus))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://www.youtube.com/channel/UCBM73EEC5disDCDnvUXMe4w", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="YouTube Channel" )(
                  <.span()(Icon.youtube))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://www.linkedin.com/company/10280853", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="LinkedIn" )(
                  <.span()(Icon.linkedin))),
              <.div(FooterCSS.Style.displayInline)(
                <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://livelygig.slack.com", ^.target:="_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="Slack" )(
                  <.span()(Icon.slack)))
            )
          ),
          <.div(^.className:="col-md-8 col-sm-8 col-xs-9")(
            Footer(Footer.Props(c, r.page))
          )
        )
      )

        //<.div(^.className:="col-md-8 col-sm-8 col-xs-9")(
//      CreateNewAgent(CreateNewAgent.Props(c, r.page))
    //)

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