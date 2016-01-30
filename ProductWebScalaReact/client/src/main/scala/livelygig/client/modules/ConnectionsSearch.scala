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
  val component = ReactComponentB[Unit]("Connections")
    .render_P(ctl =>
      <.div()(
        <.div(^.wrap := "pull-right", ^.textAlign := "right", ^.height := "55px")(
          <.button(^.tpe := "button", ^.className := "btn btn-default HeaderCSS_Style-rsltContainerBtn", "Search")
        ),
        <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
          <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition, ^.width := "100%", ^.height := "calc(100vh - 238px)", ^.overflowY := "auto", ^.paddingTop := "0px")(
            <.div(DashBoardCSS.Style.slctHeaders)("Status"),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " Online"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " Recommended by Me"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " Recommended to Me"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " with Talent Profile"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " with Employer Profile"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " with Moderator Profile"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " Favorited"),
            <.br(),
            <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
              <.input(^.`type` := "checkbox"), " Include Hidden"),
            <.div(^.paddingLeft:="15px")(
              <.div(^.className := "row")(
                <.div(ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Added Before")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              ),
              <.div(^.className := "row")(
                <.div(ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Added After")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                )
              )
            )
          )
        )
      )
    )


    .componentDidMount(scope => Callback {

    })
    .build
}