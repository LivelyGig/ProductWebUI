package synereo.client.modalpopups

import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.SignupCSS
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._

/**
  * Created by bhagyashree.b on 4/19/2016.
  */

object AccountValidationFailed {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State()

  class AccountValidationFailedBacked(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def formClosed(state: AccountValidationFailed.State, props: AccountValidationFailed.Props): Callback = {
      props.submitHandler()
    }
  }


  private val component = ReactComponentB[Props]("AccountValidationFailed")
    .initialState_P(p => State())
    .backend(new AccountValidationFailedBacked(_))
    .renderPS((t, P, S) => {
      val headerText = "Error"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */ <.div(SignupCSS.Style.signUpHeading)(headerText)),
          closed = () => t.backend.formClosed(S, P)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div()(
                <.div()(^.fontSize := "18.px", "Validation code you entered is incorrect, please check your email and enter valid code"),
                //<.div()( <.button(^.tpe := "button",^.className:="btn",   ^.onClick-->hide )("Try again"))
                <.div(^.className := "pull-right")(
                  <.button(^.tpe := "button", SignupCSS.Style.signUpBtn, ^.className := "btn", ^.onClick --> t.backend.hide, "Try again")
                )
              ),
              <.div(bss.modal.footer)()
            )
          )
        )
      )
    })
    .build

  def apply(props: Props) = component(props)
}