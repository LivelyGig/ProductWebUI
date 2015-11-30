package livelygig.client

import japgolly.scalajs.react.React
import japgolly.scalajs.react.extra.router2._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import livelygig.client.components.{Icon, GlobalStyles}
import livelygig.client.css.AppCSS
import livelygig.client.logger._
import livelygig.client.modules._
import livelygig.client.css.HeaderCSS
import livelygig.client.css.FooterCSS

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
        <.div()(
          c.link(DashboardLoc)(HeaderCSS.Style.logoContainer,^.className := "navbar-header",<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/logo-symbol.png")),
          <.div(^.className := "collapse navbar-collapse")(
            MainMenu(MainMenu.Props(c, r.page)),
           <.div(HeaderCSS.Style.LoginInMenuItem)(
            <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
            <.div(HeaderCSS.Style.displayInline) ("Dale Steyn"),
            <.div(HeaderCSS.Style.displayInline)( c.link(DashboardLoc)(HeaderCSS.Style.logoContainer,<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg")))
           )
          )
//           <.div(^.className:="navbar-right")(
//             <.ul(^.className:="navbar")(
//               <.li()("hello"),
//               <.li()("scala")
//
//             )/*ul*/
//           )/*div*/
        )
      ),
      // currently active module is shown in this container
      //added
      <.div(HeaderCSS.Style.middelNaviContainer)(
        <.div(^.className :="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
             <.div(^.className:="btn-group")(
               <.button(HeaderCSS.Style.projectCreateBtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
               <.span(^.className:="caret")
               ),
                 <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className:="dropdown-menu")(
                    <.li()(<.a(^.href:="#")("Suggested Matches")),
                    <.li()(<.a(^.href:="#")("Available")),
                    <.li()(<.a(^.href:="#")("Active Unavailable")),
                    <.li()(<.a(^.href:="#")("Inactive")),
                    <.li()(<.a(^.href:="#")("Suppressed")),
                    <.li(^.className:="divider")(),
                    <.li()(<.a(^.href:="#")("Customized View 1")),
                    <.li()(<.a(^.href:="#")("Customize..."))
                 )
             ),
            <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("Create New Project")()
          )
        )
      ),   //recommended matches
      <.div(HeaderCSS.Style.ContainerHeight, ^.className := "container-fluid")(r.render()),
      <.nav(FooterCSS.Style.footerContainer)(
        <.div(^.className:="row")(
        <.div(^.className:="col-md-4 col-sm-4 col-xs-3")(
          <.div(FooterCSS.Style.footGlyphContainer)(
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://github.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(<.span()(Icon.github))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://twitter.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.twitter))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://facebook.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.facebook))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://google.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.googlePlus))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://youtube.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.youtube))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://linkedin.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.linkedin))),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href:="https://slack.com/LivelyGig", "data-toggle".reactAttr := "tooltip", "title".reactAttr :="View our GitHub repositories" )(
              <.span()(Icon.slack)))

          )
        ),
        <.div(^.className:="col-md-8 col-sm-8 col-xs-9")(
          Footer(Footer.Props(c, r.page))
        )
      )
      )
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
    React.render(router(), dom.document.getElementById("root"))
  }
}
