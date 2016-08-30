package client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ Callback, ReactComponentB }
import client.LGMain.{ Loc }
import client.css.{ DashBoardCSS }
import scalacss.ScalaCssReact._

/**
 * Created by bhagyashree.b on 3/29/2016.
 */
object LandingLocation {
  val component = ReactComponentB[RouterCtl[Loc]]("LandingLocation")
    .render_P(ctl =>
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        // <.iframe(^.src := "http://www.livelygig.com", ^.width := "100%", ^.height := "100%")
      ))

    .build
}
