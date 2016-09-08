package synereo.client.modalpopups

import synereo.client.components.{Icon, GlobalStyles}
import shared.models.EmailValidationModel
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import synereo.client.components._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._

/**
  * Created by Mandar on 4/19/2016.
  */
object VerifyTokenModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false, showLoginForm: Boolean = false)

  class VerifyTokenModalBackend(t: BackendScope[Props, State]) {

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      // mark it as NOT cancelled (which is the default)
      t.modState(s => s.copy(accountValidationFailed = true))
    }

    def hideModal = {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
      t.modState(s => s.copy(showLoginForm = true))
    }

    def updateToken(e: ReactEventI) = {
      // update TodoItem content
      val value = e.target.value
      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = value)))
    }



    def formClosed(state: VerifyTokenModal.State, props: VerifyTokenModal.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler(state.emailValidationModel, state.accountValidationFailed,state.showLoginForm)
    }
  }

  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .backend(new VerifyTokenModalBackend(_))
    .renderPS((t, P, S) => {
      val headerText = "Verify Token"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(
            <.button(^.tpe := "button", bss.close, ^.onClick --> t.backend.hideModal, Icon.close),
            <.div(SignupCSS.Style.signUpHeading)(headerText)
          ),
          closed = () => t.backend.formClosed(S, P),
          addStyles = Seq(SignupCSS.Style.signUpModalStyle)
        ),
        <.form(^.onSubmit ==> t.backend.submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div()(
                <.div(SignupCSS.Style.verificationMessageContainer)(
                  <.h4("Your account has been created!"),
                  <.h4("We have sent a verification code to your email address, Please check your email and copy the code, or follow the link.")
                ),
                <.div(SignupCSS.Style.verificationMessageContainer)(
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name",
                    ^.placeholder := "Verification code", ^.value := S.emailValidationModel.token, ^.onChange ==> t.backend.updateToken)
                ),
                <.div(^.className := "pull-right")(
                  <.button(^.tpe := "submit", SignupCSS.Style.verifyBtn, ^.className := "btn", "Verify")
                )
              ),
              <.div(bss.modal.footer)()
            )
          )
        )
      )
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.accountValidationFailed) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)

}
