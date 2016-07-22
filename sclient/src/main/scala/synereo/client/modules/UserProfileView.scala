package synereo.client.modules

import japgolly.scalajs.react.{Callback, ReactComponentB}
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import org.querki.jquery._
import synereo.client.css.UserProfileViewCSS

import scala.scalajs.js
import scalacss.ScalaCssReact._
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{AppCSS, SynereoCommanStylesCSS}
import japgolly.scalajs.react.{React, ReactDOM}
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react.{React, ReactDOM}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import scala.scalajs.js
import js.{Date, UndefOr}

/**
 * Created by Mandar on 3/28/2016.
 */
object UserProfileView {
  val searchContainer: js.Object = "#searchContainer"
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid",UserProfileViewCSS.Style.UserProfileContainerMain)(
        <.div(^.className := "row")(
          //Left Sidebar
          <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
            ^.onMouseEnter --> Callback{$(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")},
          ^.onMouseLeave --> Callback{$(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")}
        )(
            //            Footer(Footer.Props(c, r.page))
            Sidebar(Sidebar.Props())
          )
        ),
        <.div(^.className := "row", UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
          <.div("User Profile View", UserProfileViewCSS.Style.heading)
        )
      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}

