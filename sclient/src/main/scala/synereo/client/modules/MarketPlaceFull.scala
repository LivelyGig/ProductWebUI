package synereo.client.modules


import japgolly.scalajs.react.extra.router.RouterCtl
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.css.{MarketPlaceFullCSS}
import scala.scalajs.js
import org.querki.jquery._
import synereo.client.css.{SynereoCommanStylesCSS, UserProfileViewCSS}
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.{Icon, MIcon}
import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 4/1/2016.
  */
object MarketPlaceFull {

  val searchContainer: js.Object = "#searchContainer"

  case class Props()

  case class State()

  class Backend(t: BackendScope[Props, State]) {

    def render() = {
      <.div()(

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
    }
  }

  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)

}
