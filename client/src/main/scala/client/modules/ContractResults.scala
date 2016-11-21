package client.modules

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import client.components.Icon
import client.css.{DashBoardCSS, HeaderCSS, PresetsCSS}
import client.modals.{NewMessage, WorkContractModal}
import org.querki.jquery._

import scala.scalajs.js
import scalacss.ScalaCssReact._

object ContractResults {

  // create the React component for Dashboard
  val component = ReactComponentB[Unit]("ContractResults")
    .render_P(ctl =>
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(
            <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.displayInlineText, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                  <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a()("Hide")),
                  <.li()(<.a()("Favorite")),
                  <.li()(<.a()("Unhide")),
                  <.li()(<.a()("Unfavorite"))
                )
              ),
              <.div(PresetsCSS.Style.modalBtn)(
                WorkContractModal(WorkContractModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.edit, "Create Contract")),
                <.div(PresetsCSS.Style.overlay, ^.top:="0.1em")(
                  Icon.plus
                )
              ),
              <.div(DashBoardCSS.Style.displayInlineText, DashBoardCSS.Style.rsltCountHolderDiv, DashBoardCSS.Style.marginResults)("2,352 Results")
            )
          ),
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(
            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.displayInlineText, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("By Date ")(
                  <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a()("By Date")),
                  <.li()(<.a()("By Experience")),
                  <.li()(<.a()("By Reputation")),
                  <.li()(<.a()("By Rate")),
                  <.li()(<.a()("By Projects Completed"))
                )
              ),
              <.div(DashBoardCSS.Style.displayInlineText, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, DashBoardCSS.Style.padding0px, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Newest ")(
                  <.span(Icon.longArrowDown)
                )
              )
            ),
            <.div(^.className := "pull-right")(
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")(<.span(Icon.minus)),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")(<.span(Icon.minus)),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")(<.span(Icon.minus))
            )
          )
        ), //col-12
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12", DashBoardCSS.Style.padding0px,DashBoardCSS.Style.rsltSectionContainer)(
            <.ul(^.className := "media-list")(
              for (i <- 1 to 50) yield {
              //  <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p)(
                <.li(^.className := "media",DashBoardCSS.Style.profileDescription, DashBoardCSS.Style.rsltpaddingTop10p)(
                  <.div(^.className := "media-body")(
                    <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                    <.div(DashBoardCSS.Style.profileNameHolder)("Buyer : Pam Seller: Abed Project/Offering: Project One"),
                    <.div()("lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
                    <.br(),
                    "Status: Initiating   11:20am 12/08/2015",
                    <.div(^.className := "col-md-12 col-sm-12 ")(
                      <.div( /*^.className:="profile-action-buttons"*/ )(
                        <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Hide", Icon.remove),
                        <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Favorite", Icon.star),
                        WorkContractModal(WorkContractModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.fileTextO, "Manage")),
                        NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Message"))
                      )
                    )
                  )
                )
              }
            )
          )
        )
      ))
    .build
}

