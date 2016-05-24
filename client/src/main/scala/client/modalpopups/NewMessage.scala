package client.modals

import shared.models.MessagePostContent
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS, ProjectCSS}
import client.handlers.PostData
import japgolly.scalajs.react
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import shared.sessionitems.SessionItems

object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addNewMessageForm(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addMessage( /*postMessage:PostMessage*/ ): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }
  }
  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div( /*ProjectCSS.Style.displayInitialbtn*/ /*, ^.onMouseOver --> B.displayBtn*/ )(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = "profile-action-buttons"), P.buttonName),
        if (S.showNewMessageForm) NewMessageForm(NewMessageForm.Props(B.addMessage, "New Message"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object NewMessageForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String)
  case class State(postMessage: MessagePostContent, postNewMessage: Boolean = false, selectizeInputId: String = "postNewMessageSelectizeInput")
  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }
    def updateSubject(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }
    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value)))
    }
    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
    }
    def mounted(): Callback = Callback {

    }
    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val state = t.state.runNow()
      LGCircuit.dispatch(PostData(state.postMessage, state.selectizeInputId, SessionItems.MessagesViewItems.MESSAGES_SESSION_URI))
      t.modState(s => s.copy(postNewMessage = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler( /*state.postMessage*/ )
    }
    // scalastyle:off
    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(),
            /*<.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),*/
            /*val selectizeControl : js.Object =*/
            <.div(^.id := s.selectizeInputId)(
              //              val to = "{\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\", \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\",\"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\"}"
              LGCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.selectizeInputId)))
            ),
            <.div()(
              <.textarea(^.rows := 6, ^.placeholder := "Subject", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.postMessage.subject, ^.onChange ==> updateSubject, ^.required := true)
            ),
            <.div()(
              <.textarea(^.rows := 6, ^.placeholder := "Enter your message here:", ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.value := s.postMessage.text, ^.onChange ==> updateContent, ^.required := true)
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
              <.button(^.tpe := "submit", ^.className := "btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, /*^.onClick --> hide, */ "Send"),
              <.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
    //.initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new MessagePostContent()))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postNewMessage) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted())
    //      .shouldComponentUpdate(scope => false)
    .build
  def apply(props: Props) = component(props)
}
