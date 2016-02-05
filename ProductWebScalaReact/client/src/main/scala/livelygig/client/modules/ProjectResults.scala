
package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.{HeaderCSS, DashBoardCSS, LftcontainerCSS}
import livelygig.client.modals.{NewRecommendation, NewMessage, BiddingScreenModal}
import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import scalacss.ScalaCssReact._

object ProjectResults {

//
//  @js.native
//  trait BootstrapJQuery extends JQueryBtn {
//    def show(action: String): BootstrapJQuery = js.native
//    def show(options: js.Any): BootstrapJQuery = js.native
//  }
//
//  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]
//
//  class Backend(t: BackendScope[Unit, Unit]) {
//    def displayBtn = Callback {
//      jQuery(t.getDOMNode()).show("hide")
//    }
//  }

  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Projects")
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
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")(<.span(Icon.list))
            )
          )
        ), //col-12
        <.div(^.className := "container-fluid", ^.id := "resultsContainer")(
          <.div(^.id := "rsltSectionContainer", ^.className := "col-md-12 col-sm-12 col-xs-12", ^.paddingLeft := "0px", ^.paddingRight := "0px")(
            <.ul(^.className := "media-list")(
              for (i <- 1 to 50) yield {
                <.li(^.className := "media", DashBoardCSS.Style.rsltpaddingTop10p)(
                  <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
                  <.span(^.className := "checkbox-lbl"),
                  <.div(DashBoardCSS.Style.profileNameHolder)("Project: Four State: Posted  11:00am 12/05/2015"),

                  <.div(^.className := "media-body")(
                    "lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                    <.div(/*^.className := "col-md-4 col-sm-4",*/ DashBoardCSS.Style.marginTop10px)(
                      <.div(DashBoardCSS.Style.profileNameHolder)("Recommended By: Tom")
                    ),

                    <.div(/*^.onMouseOver ==> displayBtn*/ /*^.onMouseOver --> displayBtn*/)(
                      <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn")("Hide")(),
                      <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn")("Favorite")(),
                      NewRecommendation(NewRecommendation.Props(ctl, "Recommend")),
                      <.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn")("Find Matching Talent")(),
                      BiddingScreenModal(BiddingScreenModal.Props(ctl, "Apply")),
                      NewMessage(NewMessage.Props(ctl, "Message")
                      )
                    )
                  ) //media-body
                ) //li
              }
            ) //ul
          )
        ) //gigConversation
      )
    )
//    .componentDidMount(scope => Callback {
//      val P = scope.props
//      // register event listener to be notified when the modal is closed
//      jQuery(scope.getDOMNode()).off("hide")
//    })
//    .componentDidUpdate(scope=> Callback{
////      if(scope.currentState.postMessage){
////        scope.$.backend.hideModal
////      }
//    })
    .build

}

