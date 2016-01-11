package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.MessagesCSS
import livelygig.client.css.MessagesCSS
import livelygig.client.css.ProjectCSS
import livelygig.client.css.{DashBoardCSS, LftcontainerCSS,ProjectCSS}


import scalacss.ScalaCssReact._

object TalentSearch {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Talent")
    .render_P(ctl =>
      // todo: Need to parameterize on type (e.g. Talent, Project) and preset (e.g. Recommended Mathces)
      "talentPreset1" match {
        case "talentPreset1" =>
          <.div(^.id:="slctScrollContainer", DashBoardCSS.Style.slctContainer)(
            <.div(LftcontainerCSS.Style.fontsize12em,LftcontainerCSS.Style.slctsearchpanelabelposition)(

              <.div(DashBoardCSS.Style.slctHeaders)("Search Criteria"),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Available From Date")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Available To Date")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("From Time")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Capabilities")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Posted By"),
              <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
              )))
      })
    .componentDidMount(scope => Callback {

    })
    .build
}
