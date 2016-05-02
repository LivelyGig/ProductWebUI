package synereo.client.modalpopups


import synereo.client.components.{Icon, GlobalStyles}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{SynereoCommanStylesCSS, SignupCSS}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._

/**
  * Created by bhagyashree.b on 4/19/2016.
  */
object AccountValidationFailed {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: () => Callback)
  case class State()

  class Backend(t: BackendScope[Props, State]) {
    def hide = Callback{
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def render(s: State, p: Props) = {
      val headerText = "Error"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */<.div(SignupCSS.Style.signUpHeading)(headerText)),
        closed = () => modalClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
              <.div()(
                <.div()("Validation code you entered is incorrect, please check your email and enter valid code"),
                  //<.div()( <.button(^.tpe := "button",^.className:="btn",   ^.onClick-->hide )("Try again"))
                  <.div(^.className := "pull-right")(
                    <.button(^.tpe := "button", SignupCSS.Style.SignUpBtn, ^.className := "btn", ^.onClick-->hide, "Try again")
                  )
              ),
            <.div(bss.modal.footer)()
            )
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("AccountValidationFailed")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)

}
