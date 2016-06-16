package client.modals

/**
  * Created by bhagyashree.b on 2016-06-14.
  */


import shared.models.MessagePostContent
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap.{Button, CommonStyle, _}
import client.components.Icon._
import client.components.{GlobalStyles, _}
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS}
import client.handlers.PostData
import client.modals.NewMessage.State
import japgolly.scalajs.react

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import shared.sessionitems.SessionItems

object ConfirmIntroReq {
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

    def addMessage(/*postMessage:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.span(^.display.inline)(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = "profile-action-buttons"), P.buttonName),
        if (S.showNewMessageForm) ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.addMessage, "New Message"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

// #todo think about better way for getting data from selectize input
// so that you don't have to pass the parentId explicitly
object ConfirmIntroReqForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String)

  case class State(postMessage: MessagePostContent, postNewMessage: Boolean = false,
                   cnxsSelectizeParentId: String = "postNewMessageSelectizeInput", labelSelectizeParentId: String = "labelsSelectizeParent")

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
      LGCircuit.dispatch(PostData(state.postMessage, Some(state.cnxsSelectizeParentId),
        SessionItems.MessagesViewItems.MESSAGES_SESSION_URI, Some(state.labelSelectizeParentId)))
      t.modState(s => s.copy(postNewMessage = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.postMessage*/)
    }

    // scalastyle:off
    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.padding5px,DashBoardCSS.Style.modalHeaderText)("Introduction Request")),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p),
          CSSClass = "connectionModalBorder",
          addStyles =  Seq(DashBoardCSS.Style.connectionModalWidth)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(),
            <.div(^.className := "col-md-5")(
              <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")
          ),
          <.div(^.className := "col-md-7")(
            <.div(^.className := "col-md-12")(
              <.div(DashBoardCSS.Style.modalHeaderText)("Introduction Request"),
              <.div(
                "From : NodeAdmin QueenSpilicious", <.br,
                "Date : Mon June 13 2016 ", <.br
              ),
              <.div(^.className := "col-md-12")(
                <.div(^.className := "col-md-10")(
                  <.div(DashBoardCSS.Style.modalContentFont)(" asdfa ")
                ),
                <.div(^.className := "col-md-2")(
                  <.div("test"),
                  <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")
                )
              )
            ),
            <.div(^.className := "col-md-12",DashBoardCSS.Style.padding15px)(
              <.button(^.tpe := "submit", ^.className := "btn", DashBoardCSS.Style.btnDefault, DashBoardCSS.Style.marginLeftCloseBtn, /*^.onClick --> hide, */ "Accept"),
              <.button(^.tpe := "button", ^.className := "btn", DashBoardCSS.Style.btnDefault, DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Reject")
            )
          )
        )
        )
        //  <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()

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
