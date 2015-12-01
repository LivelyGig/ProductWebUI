package livelygig.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{TodoLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.LftcontainerCSS
import scalacss.ScalaCssReact._


object Dashboard {
	
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P(ctl => {

    <.div(^.id:="slct-scroll-container")(
      <.div(LftcontainerCSS.Style.fontsize12em,LftcontainerCSS.Style.slctsearchpanelabelposition)(
      	<.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
      		<.select (^.id:="tokenize")(

      			)
      		)
      	)
    )
  }).build
}
