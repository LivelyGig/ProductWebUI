package livelygig.client.modules


import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.components.Icon
import livelygig.client.css.{HeaderCSS, DashBoardCSS}
import livelygig.client.modals.{NewRecommendation, NewMessage}
import scalacss.ScalaCssReact._

object TalentResults {
  // create the React component for Dashboard
  val component = ReactComponentB[Unit]("Talent")
    .render_P(ctl =>
      <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
        <.div(DashBoardCSS.Style.gigActionsContainer, ^.className := "row")(
          <.div(^.className := "col-md-4 col-sm-4 col-xs-4", ^.paddingRight := "0px", ^.paddingTop := "12px")(
            <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle, ^.verticalAlign := "middle"),
            //                      <.span(DashBoardCSS.Style.MarginLeftchkproduct, ^.className:="checkbox-lbl"),
            <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown", ^.verticalAlign := "middle")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className := "dropdown-menu")(
                <.li()(<.a(^.href := "#")("Hide")),
                <.li()(<.a(^.href := "#")("Favorite")),
                <.li()(<.a(^.href := "#")("Unhide")),
                <.li()(<.a(^.href := "#")("Unfavorite"))
              )
            ) //dropdown class
          ),
          <.div(^.className := "col-md-8 col-sm-8 col-xs-8", ^.paddingLeft := "0px")(
            <.div(DashBoardCSS.Style.rsltCountHolderDiv, ^.margin := "0px", ^.paddingTop := "19px")("2,352 Results"),
            <.div(^.display := "inline-block")(
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.borderWidth := "0px", ^.paddingTop := "0px", ^.paddingBottom := "2px")("By Date ")(
                  <.span(^.className := "caret", DashBoardCSS.Style.rsltCaretStyle)
                ),
                <.ul(^.className := "dropdown-menu")(
                  <.li()(<.a(^.href := "#")("By Date")),
                  <.li()(<.a(^.href := "#")("By Experience")),
                  <.li()(<.a(^.href := "#")("By Reputation")),
                  <.li()(<.a(^.href := "#")("By Rate")),
                  <.li()(<.a(^.href := "#")("By Projects Completed"))
                )
              ),
              <.div(DashBoardCSS.Style.rsltGigActionsDropdown, ^.className := "dropdown")(
                <.button(DashBoardCSS.Style.gigMatchButton, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown", ^.padding := "0px", ^.paddingBottom := "2px", ^.border := "0px")("Newest ")(
                  <.span(Icon.longArrowDown))
              )
            ),

            <.div(^.className := "pull-right", ^.paddingTop := "10px")(
              // todo: icon buttons should be different.  Earlier mockup on s3 had <span class="icon-List1">  2  3  ?
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")( <.span(^.className:="icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")( <.span(^.className:="icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")( <.span(^.className:="icon-List3"))
            )
          )
        ), //col-12
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.className:="rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            <.ul(^.className := "media-list")(
              for (i <- 1 to 50) yield {
                <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground */)(
                  <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                  <.span(^.className := "checkbox-lbl"),
                  <.div(DashBoardCSS.Style.profileNameHolder)("Name : job-title"),
                  <.div(^.className := "col-md-12")(
                    <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Experience: 8 years"),
                    <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Projects Completed: 24"),
                    <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)("Availability: Negotiable"),
                    <.div(DashBoardCSS.Style.rsltProfileDetailsHolder)(
                      ("Recommended By: "),
                      <.a(^.href := "#")("@Britta"),
                      (" for Project: "),
                      <.a(^.href := "#")("9347383"),
                      (" Need Videographer...")
                    )
                  ),
                  <.div(^.className := "media-left")(
                    <.a(^.href:="https://www.youtube.com/embed/0oHhD3Bk9Uc?rel=0", ^.target:="new", (
                      <.img(DashBoardCSS.Style.profileImg, ^.src := "./assets/images/profile-img2.jpg")
                      )
                    )
                  ), //media-left
                  <.div(^.className := "media-body")(
                    "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                    <.div(^.className := "col-md-12 col-sm-12 ")(
                      <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Hide")(),
                      <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
                      NewRecommendation(NewRecommendation.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.handOUp,"Recommend")),
                 //     NewMessage(NewMessage.Props("Message")))
                      NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.envelope,"Message"))
                  ) //media-body
                )
                )
              }
            ) //ul
          )
        ) //gigConversation
      )
    )
    .componentDidMount(scope => Callback {

    })
    .build
}

