package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.{DashBoardCSS, HeaderCSS, BiddingScreenCSS}

import scalacss.ScalaCssReact._

object BiddingScreenPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("BiddingScreen")
    .render_P(ctl =>
      // todo: Need to parameterize.
      // This example is for Talent
      <.div(^.id:="middelNaviContainer" , BiddingScreenCSS.Style.biddingPreset)(
        <.div(^.className :="row")(
          <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(
           <.div()("Stage :")
          ),
          <.div(^.className:="col-md-11 col-sm-11 col-xs-11")(
            <.div()(<.a()("Initial proposal"), ">>" , <.a()("Negotiating") , ">>" , <.a()("Funding") )
          )
        ),
        <.div(^.className :="row")(
          <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(
            <.div()("Project : ")
          ),
          <.div(^.className:="col-md-11 col-sm-11 col-xs-11")(
            <.div()("ID 256...8 Videographer Needed ... >")
          )
        ),    <.div(^.className :="row")(
          <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(
            <.div()("Employer : ")
          ),
          <.div(^.className:="col-md-11 col-sm-11 col-xs-11")(
            <.div()("Pam")
          )
        ),
        <.div(^.className :="row")(
          <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(
            <.div()("Talent : ")
          ),
          <.div(^.className:="col-md-11 col-sm-11 col-xs-11")(
            <.div()("Abed")
          )
        )
      )
    )
    .componentDidMount(scope => Callback {
    })
    .build
}


