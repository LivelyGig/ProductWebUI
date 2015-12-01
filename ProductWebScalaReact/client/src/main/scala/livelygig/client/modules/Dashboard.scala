package livelygig.client.modules

import scalacss.ScalaCssReact._
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{TodoLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS


object Dashboard {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl => {
    <.div(
      /*<.h2("Lively gig")*/
    /**/
     <.div(DashBoardCSS.Style.mainContainerDiv)(
     <.h2("LivelyGig")

     )


    /**/
    )
  }).build
}
