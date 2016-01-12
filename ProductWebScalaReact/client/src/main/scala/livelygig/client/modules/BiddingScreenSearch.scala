package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css._

import scalacss.ScalaCssReact._

object BiddingScreenSearch {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("BiddingScreen")
    .render_P(ctl =>
      // todo: Need to parameterize on type (e.g. Talent, Project) and preset (e.g. Recommended Mathces)
      "talentPreset1" match {
        case "talentPreset1" =>
          <.div(^.id:="slctScrollContainer", DashBoardCSS.Style.slctContainer)(
            <.div(LftcontainerCSS.Style.fontsize12em,LftcontainerCSS.Style.slctsearchpanelabelposition)(
              <.div(DashBoardCSS.Style.slctHeaders)("Contract Template"),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div(DashBoardCSS.Style.marginTop20px)("SOW")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin , DashBoardCSS.Style.marginTop10px)(
                  //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                  <.div(^.className:="btn-group")(
                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One")(
                      <.span(^.className:="caret")
                    ),
                    <.ul(^.className:="dropdown-menu")(
                      <.li()(<.a(^.href:="#")("Item 1")),
                      <.li()(<.a(^.href:="#")("Item 2")),
                      <.li()(<.a(^.href:="#")("Item 3"))
                    )
                  )
                )
                ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Rate")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth, ^.placeholder:="input")
                )
              ),
              <.div(DashBoardCSS.Style.slctHeaders)("Complete Agreement")
              ))
      })
    .componentDidMount(scope => Callback {

    })
    .build
}