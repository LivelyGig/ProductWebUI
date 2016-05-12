package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.css.UserProfileViewCSS

import scalacss.ScalaCssReact._

/**
 * Created by Mandar on 3/28/2016.
 */
object UserProfileView {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard").
    render_P { ctr =>
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row", UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
          <.div("User Profile View", UserProfileViewCSS.Style.heading)
        )
      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)

}

