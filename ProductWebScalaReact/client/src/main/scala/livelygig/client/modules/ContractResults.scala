package livelygig.client.modules


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.components.Icon
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.modals.{BiddingScreenModal, NewMessage}

import scalacss.ScalaCssReact._

object ContractResults {
  // create the React component for Dashboard
  val component = ReactComponentB[Unit]("ContractResults")
    .render_P(ctl =>
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12", ^.paddingRight := "0px"/*, ^.paddingTop := "12px"*/)(
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
            <.div(DashBoardCSS.Style.rsltCountHolderDiv, DashBoardCSS.Style.marginResults/*, ^.paddingTop := "19px"*/)("2,352 Results")
          ),
          <.div(^.className := "col-md-6 col-sm-6 col-xs-12")(

            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.borderWidth := "0px", ^.paddingTop := "0px", ^.paddingBottom := "2px")("By Date ")(
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
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.padding := "0px"/*, ^.paddingBottom := "2px"*/, ^.border := "0px")("Newest ")(
                  <.span(Icon.longArrowDown))
              )
            ),
            <.div(^.className := "pull-right"/*, ^.paddingTop := "10px"*/)(
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
            )
          )
        ),
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            <.ul(^.className := "media-list")(
              //var i = 0
              for (i <- 1 to 50) yield {
                <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p /*DashBoardCSS.Style.rsltContentBackground, */)(
                  <.div(^.className := "media-body")(
                    <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                    <.div(DashBoardCSS.Style.profileNameHolder)("Buyer : Pam Seller: Abed Project/Offering: Project One"),
                    <.div()("lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
                    <.br(),
                    "Status: Initiating   11:20am 12/08/2015",
                    <.div(^.className := "col-md-12 col-sm-12 ")(
                      <.div(/*^.className:="profile-action-buttons"*/)(
                        <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Hide", Icon.remove),
                        <.button(^.tpe := "button", ^.className := "btn profile-action-buttons", HeaderCSS.Style.rsltContainerIconBtn, HeaderCSS.Style.floatBtn, ^.title := "Favorite", Icon.star),
                        BiddingScreenModal(BiddingScreenModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.fileTextO, "Manage")),
                        NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Message"))
                      )
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

