package synereo.client.modalpopups

/**
  * Created by Mandar on 4/11/2016.
  */

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Button
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.Bootstrap._
import synereo.client.components.GlobalStyles
import synereo.client.components.Icon
import synereo.client.components.Icon.Icon
import synereo.client.components.Icon._
import synereo.client.components._
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.LoginCSS
import synereo.client.components.jQuery

import scalacss.Defaults._
import scalacss.ScalaCssReact._

object InviteFriend {
  @inline private def bss = GlobalStyles.bootstrapStyles


  case class Props(addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String)

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
      //log.debug(s"addNewAgent userModel : t{userModel} ,addNewAgent: t{showNewInviteForm}")
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
      <.div(/*ProjectCSS.Style.displayInitialbtn*//*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewRequestForm(), CommonStyle.default, P.addStyles, "", P.title, className = ""), ""),
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

object InviteNewFriend {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, header: String)

  case class State(postMessage: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
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
      println(state.postMessage)
      props.submitHandler(state.postMessage)

    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div("")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", LoginCSS.Style.requestInviteModalStyle)(
            <.div(^.className := "col-md-12")(
              <.div(LoginCSS.Style.requestInviteModalText)(
                "Invites aren't quite ready, however we're eager for you to join us on this journey!"
              ),
              <.div()(
                <.input(^.`type` := "text",^.className:="form-control", LoginCSS.Style.requestInviteTextarea, ^.placeholder := "you@email.com")
              ),
              <.div()(
                <.button(^.tpe := "submit", ^.className := "btn btn-default", LoginCSS.Style.subscribeButton, ^.onClick --> hide, "Subscribe")
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
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}
