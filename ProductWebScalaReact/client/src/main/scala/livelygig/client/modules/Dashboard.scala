package livelygig.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{TodoLoc, Loc}
import livelygig.client.components._

object Dashboard {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl => {
    <.div(
      <.h2("Lively gig")
    )
  }).build
}
