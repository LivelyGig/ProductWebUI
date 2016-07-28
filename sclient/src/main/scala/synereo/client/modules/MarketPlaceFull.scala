package synereo.client.modules


import synereo.client.SYNEREOMain
import synereo.client.css.{ MarketPlaceFullCSS}
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
      <.div(/*^.className := "container-fluid MainContainer"*/)(
//        <.div(^.className := "row")(
//          //Left Sidebar
//          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
//            ^.onMouseEnter --> Callback{$(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")},
//            ^.onMouseLeave --> Callback{$(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")}
//          )(
//            //            Footer(Footer.Props(c, r.page))
//            Sidebar(Sidebar.Props())
//          )
//        ),
        //        <.div(^.className := "row", UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
        //          <.div("User Profile View", UserProfileViewCSS.Style.heading)
        //        )
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
