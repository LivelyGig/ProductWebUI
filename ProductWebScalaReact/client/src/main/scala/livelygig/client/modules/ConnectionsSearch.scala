package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css._

import scalacss.ScalaCssReact._

object ConnectionsSearch {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Connections")
    .render_P(ctl =>
      // todo: Need to parameterize on type (e.g. Talent, Project) and preset (e.g. Recommended Mathces)
      "talentPreset1" match {
        case "talentPreset1" =>
          <.div(^.id:="slctScrollContainer", DashBoardCSS.Style.slctContainer)(
            <.div(LftcontainerCSS.Style.fontsize12em,LftcontainerCSS.Style.slctsearchpanelabelposition)(
              <.div(DashBoardCSS.Style.slctHeaders)("Connections"),
              <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
                //<input type="text" value="Amsterdam,Washington" data-role="tagsinput"
                //                <.input (^.`type`:="text" , "data-role".reactAttr:="tagsinput")
              ),

              <.div(DashBoardCSS.Style.slctHeaders)("Search for Connection"),
                <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Keywords")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                ))
,

              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Job title")
                  //                        <.span(^.className:="checkbox-lbl")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                ))
,

              <.div(DashBoardCSS.Style.slctHeaders)("Search by Name"),
              <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
              ),

              <.div(DashBoardCSS.Style.slctHeaders)("Search by Rating"),
              <.div (LftcontainerCSS.Style.slctleftcontentdiv ,LftcontainerCSS.Style.resizable,^.id:="resizablecontainerskills")(
              ),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.floatRightbtn,"Search")
              ))
      })
    .componentDidMount(scope => Callback {

    })
    .build
}