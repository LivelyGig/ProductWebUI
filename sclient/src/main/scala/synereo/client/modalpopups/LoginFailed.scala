package synereo.client.modalpopups

/**
  * Created by mandar.k on 6/10/2016.
  */
import synereo.client.components.{ Icon, GlobalStyles }
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{ SynereoCommanStylesCSS, SignupCSS }
import scala.util.{ Failure, Success }
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, loginErrorMessage: String = "")

  case class State()

  class Backend(t: BackendScope[Props, State]) {

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }

    def render(s: State, p: Props) = {
      val headerText = "Login Failed"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.div()(headerText)),
          closed = () => formClosed(s, p)
        ),

        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div()(
                <.div()(
                  /*p.loginErrorMessage*/ "The username and password combination that you are using is not correct. Please check and try again.",
                  <.div()(<.button(^.tpe := "button", ^.className := "btn", ^.onClick --> hide)("Try again"))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer)()
      )
    }
  }

  private val component = ReactComponentB[Props]("LoginFailed")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}