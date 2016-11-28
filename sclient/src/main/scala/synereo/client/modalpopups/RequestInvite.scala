package synereo.client.modalpopups

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Icon.Icon
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{DashboardCSS, LoginCSS, SynereoCommanStylesCSS}
import synereo.client.components.jQuery

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import synereo.client.components.Bootstrap._


object RequestInvite {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String ="Request invite")

  case class State(showNewInviteForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewInviteForm = true))
    }

    def addNewRequestForm(): Callback = {
      t.modState(s => s.copy(showNewInviteForm = true))
    }

    def addMessage(postMessage: Boolean = false): Callback = {
      //log.debug(s"addNewUser signUpModel : t{signUpModel} ,addNewUser: t{showAmpsForm}")
      if (postMessage) {
        t.modState(s => s.copy(showNewInviteForm = true))
      } else {
        t.modState(s => s.copy(showNewInviteForm = false))
      }
    }
  }

  val component = ReactComponentB[Props]("NewInvite")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewRequestForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), "Request invite"),
        if (S.showNewInviteForm) PostNewInvite(PostNewInvite.Props(B.addMessage))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object PostNewInvite {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback)

  case class State(postMessage: Boolean = false,requestInviteModalId: String = "request-invite-modal")

  class PostNewInviteBackend(t: BackendScope[PostNewInvite.Props, PostNewInvite.State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")

    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: PostNewInvite.Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postMessage = false))
    }

    def formClosed(state: PostNewInvite.State, props: PostNewInvite.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.postMessage)
      props.submitHandler(state.postMessage)

    }
  }

  private val component = ReactComponentB[Props]("RequestNewInvite")
    .initialState_P(p => State())
    .backend(new PostNewInviteBackend(_))
    .renderPS((t,P,S)=>{
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(^.className:="model-title ",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)("")),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => t.backend.formClosed(S, P),id = S.requestInviteModalId
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "row", LoginCSS.Style.requestInviteModalStyle)(
            <.div(^.className := "col-md-12")(
              <.div(LoginCSS.Style.requestInviteModalText)(
                "Invites aren't quite ready, however we're eager for you to join us on this journey!"
              ),
              <.div()(
                <.input(^.`type` := "text", ^.className := "form-control", LoginCSS.Style.requestInviteTextarea, ^.placeholder := "you@email.com")
              ),
              <.div(bss.modal.footer)(
                <.button(^.tpe := "submit", ^.className := "btn ", SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.hide, "Subscribe")
              )
            )
          )
        )
      )
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postMessage) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}
