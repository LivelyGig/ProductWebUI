package livelygig.client.modules

import japgolly.scalajs.react.{Callback, ReactComponentB}
import org.querki.facades.bootstrap.datepicker.{Orientation, BootstrapDatepickerOptions}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.css._
import org.querki.facades.bootstrap.datepicker._
import scala.scalajs.js
import js.{Date, UndefOr}
import org.querki.jquery._
import scalacss.ScalaCssReact._

object ConnectionsSearch {
  val baseOpts = BootstrapDatepickerOptions.
    autoclose(true).
    todayHighlight(true).
    todayBtnLinked().
    disableTouchKeyboard(true).
    orientation(Orientation.Top)
  // Iff this Date is Optional, show the Clear button:
  val opts =
    if (true)
      baseOpts.clearBtn(true)
    else
      baseOpts

  $("#addedBefore").datepicker(baseOpts)
  $("#addedAfter").datepicker(baseOpts)


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
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " Online"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " Recommended by Me"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " Recommended to Me"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " with Talent Profile"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " with Employer Profile"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " with Moderator Profile"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " Favorited"),
            <.br(),
            <.label(LftcontainerCSS.Style.checkboxlabel)(
              <.input(^.`type` := "checkbox"), " Include Hidden"),
            <.div(^.paddingLeft:="15px")(
              <.div(^.className := "row")(
                <.div(ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Added Before")
                ),
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
                  //<.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className := "input-group date")(
                  <.input("data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "addedBefore")
                )
                )
              ),
              <.div(^.className := "row")(
                <.div(ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Added After")
                ),
                // ToDo: this should be a date picker
                <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin)(
//                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className := "input-group date")(
                  <.input("data-provide".reactAttr := "datepicker", DashBoardCSS.Style.inputHeightWidth, ^.id := "addedAfter")
                )
                )
              ),
              <.div(^.className := "row")(
                <.div(ProjectCSS.Style.slctProjectInputWidth)(
                  <.div("Groups")
                ),
                // ToDo: this should be a date picker
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