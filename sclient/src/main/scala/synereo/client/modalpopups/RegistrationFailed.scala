package synereo.client.modalpopups



import synereo.client.components.{Icon, GlobalStyles}
import synereo.client.css.SignupCSS
import synereo.client.models.EmailValidationModel
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
object RegistrationFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback)
  case class State(registrationFailed : Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def hide = Callback{
      jQuery(t.getDOMNode()).modal("hide")
    }
    def login(): Callback  = {
      t.modState(s=>s.copy(registrationFailed = true))
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler(state.registrationFailed)
    }

    def render(s: State, p: Props) = {
      val headerText = "Error"

      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.div()(headerText)),

        closed = () => modalClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className:="row")(
              <.div()(
                <.div()("This user already exists. Please try logging in!" ),
                  <.div()( <.button(^.tpe := "button",^.className:="btn",^.onClick-->hide )("Try again"), <.button(^.tpe := "button",^.className:="btn btn-default",  ^.onClick-->login )("Login"))
              )
            )
          )
        ),
        <.div(bss.modal.footer)()
      )
    }
  }
  private val component = ReactComponentB[Props]("RegistrationFailed")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.registrationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}
