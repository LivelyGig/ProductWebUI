package client.modules

import client.components.Bootstrap._
import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import client.rootmodel.MessagesRootModel
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import shared.models.{ConnectionsModel, MessagePost}
import client.modals.{NewMessage, ServerErrorModal}
import client.services.LGCircuit
import japgolly.scalajs.react
import org.querki.jquery._
import org.widok.moment.Moment
import scala.scalajs.js
import scalacss.ScalaCssReact._
import scala.language.existentials

object MessagesResults {

  case class Props(proxy: ModelProxy[Pot[MessagesRootModel]])

  case class State(showErrorModal:Boolean =false)

  val getServerError = LGCircuit.zoom(_.appRootModel).value

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props): react.Callback = Callback {
//      log.debug("messages view mounted")
      /*if (props.proxy().isEmpty) {
        ContentModelHandler.subsForContentAndBeginSessionPing(AppModule.MESSAGES_VIEW)
      }*/
    }

    /*{
      if (props.proxy().isEmpty) {
        props.proxy.dispatch(RefreshMessages())
      } else {
        Callback.empty
      }
    }*/

    def serverError(showErrorModal :Boolean = false): Callback = {
      if(showErrorModal)
        t.modState(s => s.copy(showErrorModal = false))
      else
        t.modState(s => s.copy(showErrorModal = true))
    }


    def render(P:Props,S:State) ={
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
          P.proxy().renderFailed(ex => <.div()(
            //<.span(Icon.warning), " Error loading"
            if(!getServerError.isServerError){
              ServerErrorModal(ServerErrorModal.Props(serverError))
            }
            else
              <.div()

          )),
          if (P.proxy().isEmpty) {
            <.div()(
              <.img(^.src := "./assets/images/processing.gif", DashBoardCSS.Style.imgc)
            )
          } else {
            <.div()
          }
        )
      )
    }
  }

  val component = ReactComponentB[Props]("Messages")
    .initialState_P(p => State())
    .renderBackend[Backend]
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
        val userId = LGCircuit.zoom(_.session.messagesSessionUri).value.split("/")(2)
        var selfConnectionId = message.connections(0).source.split("/")(2)
        val connections = LGCircuit.zoom(_.connections).value.connectionsResponse
        var toReceiver = "unknown"
        var fromSender = "unknown"
        if (userId == selfConnectionId) {
          fromSender = "me"
          for(b <- message.connections ; a <- connections  ; if (a.connection.source.split("/")(2) == b.source.split("/")(2) && a.connection.target.split("/")(2) == b.target.split("/")(2))) yield
            toReceiver = a.name
        } else {
          for(b <- message.connections ; a <- connections  ; if (a.connection.source.split("/")(2) == b.target.split("/")(2) && a.connection.target.split("/")(2) == b.source.split("/")(2))) yield
            fromSender = a.name
          // ToDo: Look up name of Sender and use friendly name
          toReceiver = "me"
        }

        <.li(^.className := "media", DashBoardCSS.Style.profileDescription, DashBoardCSS.Style.rsltpaddingTop10p /*, DashBoardCSS.Style.rsltContentBackground*/)(
          <.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle),
          // <.span(^.className := "checkbox-lbl"),
          <.div(DashBoardCSS.Style.profileNameHolder)(s"${message.postContent.subject}"),
          <.br(),
          //          <.div(DashBoardCSS.Style.profileNameHolder)(s"${<.img(^.src:= message.postContent.imgSrc)}"),
          <.div(^.className := "row", ^.color := "gray", ^.fontSize := "smaller")(
            <.div(^.className := "col-md-6 col-sm-12")(s"From: ${fromSender}"),
            <.div(^.className := "col-md-6 col-sm-12")(s"To: ${toReceiver}")
          ),
          <.div(^.className := "msgTime", DashBoardCSS.Style.msgTime, "data-toggle".reactAttr := "tooltip", ^.title := message.created, "data-placement".reactAttr := "right")(Moment(message.created).format("LLL").toLocaleString),
          // <.div()(s"labels: ${message.labels}"),
          // <.div()(s"uid: ${message.uid}"),
          <.div(^.className := "media-body", ^.paddingTop := "10px")(
            <.div(
              <.div(^.className := "col-md-6 col-sm-12")(
                s"${message.postContent.text}"
              ),
              <.div(^.className := "col-md-6 col-sm-12")(
                <.div(
                  if (message.postContent.imgSrc != "") {
                    <.img(^.src := message.postContent.imgSrc, ^.height := "100.px", ^.width := "100.px")
                  } else {
                    <.div("")
                  }
                )
              )
            ),

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
