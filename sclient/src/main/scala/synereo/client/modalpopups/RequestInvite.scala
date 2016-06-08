package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components.Icon.Icon
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{DashboardCSS, LoginCSS}
import synereo.client.components.jQuery

import scalacss.Defaults._
import scalacss.ScalaCssReact._

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
      //log.debug(s"addNewAgent signUpModel : t{signUpModel} ,addNewAgent: t{showConnectionsForm}")
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

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")

    }

    def hideModal = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postMessage = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(state.postMessage)
      props.submitHandler(state.postMessage)

    }

    def render(s: State, p: Props) = {
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div("")),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p),id = s.requestInviteModalId
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", LoginCSS.Style.requestInviteModalStyle)(
            <.div(^.className := "col-md-12")(
              <.div(LoginCSS.Style.requestInviteModalText)(
                "Invites aren't quite ready, however we're eager for you to join us on this journey!"
              ),
              <.div()(
                <.input(^.`type` := "text", ^.className := "form-control", LoginCSS.Style.requestInviteTextarea, ^.placeholder := "you@email.com")
              ),
              <.div()(
                <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, ^.onClick --> hide, "Subscribe")
              )
            )
          )

        )
      )
    }
  }

  private val component = ReactComponentB[Props]("RequestNewInvite")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.postMessage) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)
}
