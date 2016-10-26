package synereo.client.modalpopups

/**
  * Created by mandar.k on 6/10/2016.
  */

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._


object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, loginErrorMessage: String = "")

  case class State()

  class LoginFailedBackend(t: BackendScope[Props, State]) {

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def formClosed(state: LoginFailed.State, props: LoginFailed.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }

  }

  private val component = ReactComponentB[Props]("LoginFailed")
    .initialState_P(p => State())
    .backend(new LoginFailedBackend(_))
    .renderPS((t, P, S) => {
      val headerText = "Login Failed"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.h4()(headerText),
          closed = () => t.backend.formClosed(S, P)
        ),

        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div()(
                <.h3()(
                  P.loginErrorMessage,
                  <.div(<.button(^.tpe := "button", ^.className := "btn", ^.onClick --> t.backend.hide, LoginCSS.Style.modalTryAgainBtn)("Try again"))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer)()
      )
    })
    .build

  def apply(props: Props) = component(props)
}