package livelygig.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Icon
import livelygig.client.css.{DashBoardCSS, HeaderCSS}
import livelygig.client.modals.{NewMessage, NewRecommendation}

import scalacss.ScalaCssReact._

object OfferingResults {
  val component = ReactComponentB[Unit]("Offerings")
    .render_P(ctl =>
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12"/*, ^.paddingRight := "0px"*/)(
            <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle, ^.verticalAlign := "middle"),
            <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown", ^.verticalAlign := "middle")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className := "dropdown-menu")(
                <.li()(<.a()("Hide")),
                <.li()(<.a()("Favorite")),
                <.li()(<.a()("Unhide")),
                <.li()(<.a()("Unfavorite"))
              )
            ), //dropdown class
            <.div(DashBoardCSS.Style.rsltCountHolderDiv, DashBoardCSS.Style.marginResults)("2,352 Results")
          ),
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(

            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
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
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, DashBoardCSS.Style.padding0px, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Newest ")(
                  <.span(Icon.longArrowDown))
              )
            ),
            <.div(^.className := "pull-right")(
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
            )
          )
        ),
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            <.ul(^.className := "media-list")(
              for (i <- 1 to 50) yield {
                <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p)(
                  <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                  <.span(^.className := "checkbox-lbl"),
                  <.div(DashBoardCSS.Style.profileNameHolder)("New company branding - icon and website... $250.  Posted  11:00am 12/05/2015"),
                  <.div(^.className := "media-body")(
                    "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                    <.br(),
                    "Recommended By: Tom",
                    <.div()(
                      <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn,  ^.title := "Hide", Icon.remove),
                      <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Favorite", Icon.star),
                      NewRecommendation(NewRecommendation.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.thumbsOUp, "Recommend")),
                      <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn,  ^.title := "Purchase", Icon.cartPlus),
                      NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Messages"))
                    )
                  )
                )
              }
            )
          )
        )
      )
    )
    .build
}

