package client.modules

import client.components.Bootstrap._
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import client.handlers.RefreshMessages
import shared.RootModels.MessagesRootModel
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import client.logger._
import shared.models.{ConnectionsModel, MessagePost}
import client.modals.NewMessage
import japgolly.scalajs.react
import org.querki.jquery._
import shared.sessionitems.SessionItems
import org.scalajs.dom.window
import org.widok.moment.Moment
import scala.scalajs.js
import scalacss.ScalaCssReact._

object MessagesResults {

  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(/*selectedItem: Option[MessagesModel] = None*/)

  class Backend(t: BackendScope[Props, _]) {
    def mounted(props: Props): react.Callback = {
      log.debug("messages view mounted")
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshMessages()))
    }

    /*{
      if (props.proxy().isEmpty) {
        props.proxy.dispatch(RefreshMessages())
      } else {
        Callback.empty
      }
    }*/
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

  case class Backend(t: BackendScope[Props, _]) {

    def mounted(props: Props): Callback = Callback {
      val msgTime: js.Object = ".msgTime"
      $(msgTime).tooltip(PopoverOptions.html(true))
    }

    def render(p: Props) = {
      def renderMessages(message: MessagePost) = {
        // Get data needed to present From and To
        val userId = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI).split("/")(2)
        var selfConnectionId = message.connections(0).source.split("/")(2)
        var toReceiver = "unknown"
        var fromSender = "unknown"
        if (userId == selfConnectionId) {
          fromSender = "me"
          // get other party ID, if there is one
          if (message.connections.size > 1) {
            if (message.connections(1).source.split("/")(2) == userId) {
              toReceiver = message.connections(1).target.split("/")(2)
            } else {
              toReceiver = message.connections(1).source.split("/")(2)
            }
            // ToDo: look up name of Receiver and use friendly name
          } else {
            toReceiver = "self"
          }
        } else {
          fromSender = selfConnectionId
          // ToDo: Look up name of Sender and use friendly name
          toReceiver = "me"
        }

        <.li(^.className := "media", DashBoardCSS.Style.profileDescription, DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground*/)(
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          // <.span(^.className := "checkbox-lbl"),
          <.div(DashBoardCSS.Style.profileNameHolder)(s"${message.postContent.subject}"),
          <.br(),
          <.div(^.className := "row", ^.color := "gray", ^.fontSize := "smaller")(
            <.div(^.className := "col-md-6 col-sm-12")(s"From: ${fromSender}"),
            <.div(^.className := "col-md-6 col-sm-12")(s"To: ${toReceiver}")
          ),
          // ToDo: need DateTime library for javascript or scala.js, similar to the following?
          // <.div()(s"Created: ${DateTime.parse(message.created).toLocalDateTime}"),
          // <.div()(s"Created: ${message.created}"),
          <.div(^.className:="msgTime",DashBoardCSS.Style.msgTime, "data-toggle".reactAttr := "tooltip", ^.title := message.created, "data-placement".reactAttr := "right")(Moment(message.created).toLocaleString),
          // <.div()(s"labels: ${message.labels}"),
          // <.div()(s"uid: ${message.uid}"),
          <.div(^.className := "media-body", ^.paddingTop := "10px")(s"${message.postContent.text}",
            <.div(^.className := "col-md-12 col-sm-12 /*profile-action-buttons*/" /*,^.onClick := "sidebartry"*/)(
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Hide", Icon.userTimes),
              <.button(^.tpe := "button", ^.className := "btn profile-action-buttons pull-right", HeaderCSS.Style.rsltContainerIconBtn, ^.title := "Favorite", Icon.star),
              NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailForward, "Forward")),
              NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.mailReply, "Reply"))
            )
          )
        )
      }
      <.div(DashBoardCSS.Style.rsltSectionContainer)(
        <.ul(^.className := "media-list")(p.messages map renderMessages)
      )
    }
  }

  val MessagesList = ReactComponentB[Props]("ProjectList")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(messages: Seq[MessagePost]) = MessagesList(Props(messages))

}
