
package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.css.MessagesCSS
import livelygig.client.css.{DashBoardCSS, LftcontainerCSS, ProjectCSS}

import scalacss.ScalaCssReact._

object ProjectSearch {
  // create the React component for Dashboard

  val component = ReactComponentB[RouterCtl[Loc]]("Projects")

    .render_P(ctl =>

      <.div(^.id := "slctScrollContainer", DashBoardCSS.Style.slctContainer)(
        <.div(LftcontainerCSS.Style.fontsize12em, LftcontainerCSS.Style.slctsearchpanelabelposition)(
          <.div(DashBoardCSS.Style.slctHeaders)("Search Criteria"),
          <.div(DashBoardCSS.Style.slctHeaders)("Job Type:"),
          <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
            <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxProject"), " Project"),
          <.div(DashBoardCSS.Style.slctSubCheckboxesDiv)(
            <.label(DashBoardCSS.Style.slctSubCheckboxesLabel)(
              <.input(^.`type` := "checkbox", ^.id := "jobTypeCheckboxHourly"), " Hourly"),
            <.br(),
            <.label(DashBoardCSS.Style.slctSubCheckboxesLabel)(
              <.input(^.`type` := "checkbox", ^.value := true, ^.id := "jobTypeCheckboxFixed"), " Fixed Scope"),
            <.br()
          ),
          <.label(DashBoardCSS.Style.slctCheckboxesLabel)(
            <.input(^.`type` := "checkbox"), " Contest"),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
              <.div("Start Date")
              //                        <.span(^.className:="checkbox-lbl")
            ),
            <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
              <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
              <.div("End Date")
              //                        <.span(^.className:="checkbox-lbl")
            ),
            <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
              <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
              <.div("Skill Required")
              //                        <.span(^.className:="checkbox-lbl")
            ),
            <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
              //                  <.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth),
              <.input(^.`type` := "textarea", ProjectCSS.Style.textareaWidth, ^.lineHeight := 4, DashBoardCSS.Style.marginTop10px)
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ProjectCSS.Style.slctProjectInputWidth)(
              <.div("Project State")
              //                        <.span(^.className:="checkbox-lbl")
            ),
            <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
              //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
              <.div(^.className := "btn-group")(
                <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select One")(
                  <.span(^.className := "caret")
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#")("Item 1")),
                  <.li()(<.a(^.href := "#")("Item 2")),
                  <.li()(<.a(^.href := "#")("Item 3"))
                )
              )
            )
          ),
          <.div(DashBoardCSS.Style.slctHeaders)("Posted By"),
          <.div(LftcontainerCSS.Style.slctleftcontentdiv, LftcontainerCSS.Style.resizable, ^.id := "resizablecontainerskills")(
          ),
          <.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.floatRightbtn, "Search")
        )
      )
    )
    .componentDidMount(scope => Callback {

    })
    .build
}