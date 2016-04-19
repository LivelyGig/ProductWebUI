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
object VerifyEmailModal {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)
  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      // mark it as NOT cancelled (which is the default)
      t.modState(s => s.copy(accountValidationFailed = true))
    }
    def hide = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def updateToken(e: ReactEventI) = {
      // update TodoItem content
      val value = e.target.value
      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = value)))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler(state.emailValidationModel, state.accountValidationFailed)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Verify Email"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span()(<.button(^.tpe := "button", ^.className := "hide", bss.close, ^.onClick --> hide, Icon.close),
          <.div(SignupCSS.Style.signUpHeading)(headerText)),
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
              <.div()(
                // <.div(DashBoardCSS.Style.modalHeaderFont)("Confirm Account Creation"),
                <.h5("Your account has been created!"),
                <.h5("We have sent a verification code to your email address, Please check your email and copy the code, or follow the link."),
                <.div()(
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name",^.placeholder:="Enter validation code",^.value:=s.emailValidationModel.token,^.onChange==>updateToken)
                ),
              //  <.input(^.tpe := "text", bss.formControl,^.id := "Name", ^.placeholder:="Enter validation code",^.value:=s.emailValidationModel.token,^.onChange==>updateToken),
               // <.button(^.tpe := "submit",^.className:="btn", "Confirm")
                <.div(^.className := "pull-right")(
                  <.button(^.tpe := "submit", SignupCSS.Style.SignUpBtn, ^.className := "btn", "Confirm")
                )
              )
              ,
              <.div(bss.modal.footer)()
            )
          )
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.accountValidationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}
