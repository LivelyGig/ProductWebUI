package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.{MarketPlaceFullCSS, SynereoCommanStylesCSS, LoginCSS}
import synereo.client.modalpopups.RequestInvite
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._
import scalacss.Color
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 4/1/2016.
  */
object MarketPlaceFull {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P { ctr =>
      <.div(^.className := "container-fluid", SynereoCommanStylesCSS.Style.backgroundColorWhite, MarketPlaceFullCSS.Style.mainContainer)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2  sidebar sidebar-left sidebar-animate sidebar-lg-show ")(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-2")(
            <.ul(^.className := "nav nav-pills nav-stacked", MarketPlaceFullCSS.Style.marketplaceActionsUL)(
              <.li(MarketPlaceFullCSS.Style.marketplaceActionsLI, ^.className := "active",
                <.a(^.href := "#")(<.span(MIcon.chatBubble))("Marketplace")),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.settings))("Nodes")
              ),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.face))("Profile")
              ),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.people))("People")
              )
            ),
            <.ul(^.className := "nav nav-pills nav-stacked")(
              <.li(MarketPlaceFullCSS.Style.marketplaceActionsLI, ^.className := "",
                <.a(^.href := "#")(<.span(MIcon.casino))("Learn Synereo")),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.casino))("Host a node")
              ),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.casino))("contracts")
              ),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.casino))("other")
              )
            ),
            <.ul(^.className := "nav nav-pills nav-stacked", MarketPlaceFullCSS.Style.footerUL)(
              <.li(MarketPlaceFullCSS.Style.marketplaceActionsLI, ^.className := "",
                <.a(^.href := "#")(<.span(MIcon.add))("New app")),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.settings))("Settings")
              ),
              <.li(
                MarketPlaceFullCSS.Style.marketplaceActionsLI,
                <.a(^.href := "#")(<.span(MIcon.help))("Help & Feedback")
              )
            )
          ),
          <.div(^.className := "container")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12", MarketPlaceFullCSS.Style.headingImageContainerDiv)(
                <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive")
              ),
              <.div(^.className := "col-md-12", MarketPlaceFullCSS.Style.cardsAndButtonContainerDiv)(
                <.div("Blah Blah", MarketPlaceFullCSS.Style.cardsHeading),
                <.div(^.className := "pull-right")(
                  <.button(^.className := "btn btn-primary", MarketPlaceFullCSS.Style.seeMoreBtn)("See more")
                )
              ),
              <.div(^.className := "col-md-12")(
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard, SynereoCommanStylesCSS.Style.paddingRightZero)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                )
              ),
              <.div(^.className := "col-md-12", MarketPlaceFullCSS.Style.cardsAndButtonContainerDiv)(
                <.div("Blah Blah", MarketPlaceFullCSS.Style.cardsHeading),
                <.div(^.className := "pull-right")(
                  <.button(^.className := "btn btn-primary", MarketPlaceFullCSS.Style.seeMoreBtn)("See more")
                )
              ),
              <.div(^.className := "col-md-12")(
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                ),
                <.div(^.className := "", MarketPlaceFullCSS.Style.appCard, SynereoCommanStylesCSS.Style.paddingRightZero)(
                  <.img(^.src := "./assets/synereo-images/cover-background.jpg", ^.className := "img-responsive"),
                  <.div("Create Meetings"),
                  <.div("Combo Company LLC"),
                  <.div(
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span(MIcon.star),
                    <.span("35")
                  )
                )
              )
            )

          )

        )

      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}
