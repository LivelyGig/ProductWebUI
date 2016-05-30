package client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import client.handlers.{RefreshConnections, RefreshMessages, RefreshProjects, StoreMessagesSearchLabel}
import shared.RootModels.{ConnectionsRootModel, MessagesRootModel, ProjectsRootModel}
import client.css.{DashBoardCSS, HeaderCSS, LftcontainerCSS}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import shared.models.MessagePost
import client.services.LGCircuit

import scala.scalajs.js
import scala.util.{Failure, Success}
import client.modals.NewMessage
import japgolly.scalajs.react

import scalacss.ScalaCssReact._
import org.querki.jquery._

object MessagesResults {

  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(/*selectedItem: Option[MessagesModel] = None*/)

  class Backend($: BackendScope[Props, _]) {
    def mounted(props: Props): react.Callback = {
      if (props.proxy().isEmpty) {
        props.proxy.dispatch(RefreshMessages())
      } else {
        Callback.empty
      }
    }
  }

  val component = ReactComponentB[Props]("Messages")
    .backend(new Backend(_))
    .renderPS((B, P, S) => {
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
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Summary")(<.span(^.className := "icon-List1")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Brief")(<.span(^.className := "icon-List2")),
              <.button(DashBoardCSS.Style.btn, "data-toggle".reactAttr := "tooltip", "title".reactAttr := "View Full Posts")(<.span(^.className := "icon-List3"))
            )
          )
        ), //col-12
        <.div(^.id := "resultsContainer")(
          P.proxy().render(messagesRootModel =>
            MessagesList(messagesRootModel.messagesModelList)),
          P.proxy().renderFailed(ex => <.div()(<.span(Icon.warning), " Error loading")),
          P.proxy().renderPending(ex => <.div()(
            <.img(^.src := "./assets/images/processing.gif", DashBoardCSS.Style.imgc)
          ))
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[Pot[MessagesRootModel]]) = component(Props(proxy))
}

object MessagesList {

  case class Props(messages: Seq[MessagePost])

  private val MessagesList = ReactComponentB[Props]("ProjectList")
    .render_P(p => {
      def renderMessages(message: MessagePost) = {
        <.li(^.className := "media profile-description", DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground*/ )(
          // if even row  DashBoardCSS.Style.rsltContentBackground
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          <.span(^.className := "checkbox-lbl"),
          <.div(DashBoardCSS.Style.profileNameHolder)(s"From: Pam   To: Abed"),
          <.div(^.className := "media-body")(s"Created: ${message.created}"),
          <.div(^.className := "media-body")(s"Subject: ${message.postContent.subject}"),
          <.div(^.className := "media-body")(s"Message: ${message.postContent.text}",
            <.div(^.className := "col-md-12 col-sm-12 /*profile-action-buttons*/" /*,^.onClick := "sidebartry"*/ )(
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Hide", Icon.userTimes),
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Favorite", Icon.star),
              //<.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Hide")(),
              //<.button(HeaderCSS.Style.rsltContainerBtn, HeaderCSS.Style.floatBtn, ^.className := "btn profile-action-buttons")("Favorite")(),
              //NewMessage(NewMessage.Props("Forward",Seq(HeaderCSS.Style.createNewProjectBtn),"","Forward")),
              //NewMessage(NewMessage.Props("Reply",Seq(HeaderCSS.Style.createNewProjectBtn),"","Reply"))

              NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailForward, "Forward")),
              NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailReply, "Reply"))
            /* <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Forward")()*/
            )
          ) //media-body
        ) //li
      }
      <.div(^.className := "rsltSectionContainer")(
        <.ul(^.className := "media-list")(p.messages map renderMessages)
      )
    })
    .build

  def apply(messages: Seq[MessagePost]) =
    MessagesList(Props(messages))

}
