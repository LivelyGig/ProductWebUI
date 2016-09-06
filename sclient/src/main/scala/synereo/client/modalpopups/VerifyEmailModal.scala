package synereo.client.modalpopups

import synereo.client.components.{Icon, GlobalStyles}
import shared.models.EmailValidationModel
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.modalpopupbackends.{VerifyEmailModalBackend => Backend}

/**
  * Created by Mandar on 4/19/2016.
  */
object VerifyEmailModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false)

  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      val headerText = "Verify email"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span()(
            <.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
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
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}
