package synereo.client.modalpopups

import diode.ModelR
import synereo.client.components.{GlobalStyles, Icon}
import shared.models.EmailValidationModel
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{LoginCSS, SignupCSS}

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._
import synereo.client.components._

import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}
import synereo.client.sessionitems.SessionItems

import scala.scalajs.js

/**
  * Created by mandar.k on 4/19/2016.
  */
//scalastyle:off
object VerifyTokenModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  val editApiDetailBtn: js.Object = "#editApiDetailBtn"

  case class Props(submitHandler: (EmailValidationModel, Boolean, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false, showLoginForm: Boolean = false,
                   portNumber: String = "9876",
                   apiURL: String = "",
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class VerifyTokenModalBackend(t: BackendScope[Props, State]) {

    def submitForm(e: ReactEventI) = {
      if ($("#verify-code-submit-btn".asInstanceOf[js.Object]).hasClass("disabled"))
        t.modState(s => s.copy(accountValidationFailed = false))
      else {
        e.preventDefault()
        window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, t.state.runNow().apiURL)
        t.modState(s => s.copy(accountValidationFailed = true))
      }
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

    def updateAPIURL(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(apiURL = value))
    }


    def closeAPITextbox(e: ReactEventI) = {
      $(editApiDetailBtn).show()
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)))
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.hostname}"))
    }

    def mounted(): Callback = Callback {
      if (window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL) != null)
        t.modState(s => s.copy(apiURL = window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL))).runNow()
      else
        t.modState(s => s.copy(apiURL = s"https://${dom.window.location.hostname}")).runNow()
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def formClosed(state: VerifyTokenModal.State, props: VerifyTokenModal.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler(state.emailValidationModel, state.accountValidationFailed, state.showLoginForm)
    }
  }

  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .backend(new VerifyTokenModalBackend(_))
    .renderPS((t, props, state) => {
      val headerText = state.lang.selectDynamic("VERIFY_TOKEN").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(
            <.button(^.tpe := "button", bss.close, ^.onClick --> t.backend.hideModal, Icon.close),
            <.div(SignupCSS.Style.signUpHeading)(headerText)
          ),
          closed = () => t.backend.formClosed(state, props),
          addStyles = Seq(SignupCSS.Style.signUpModalStyle)
        ),
        <.div(^.className := "container-fluid")(
          <.form(^.onSubmit ==> t.backend.submitForm, "data-toggle".reactAttr := "validator", ^.role := "form")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                <.div()(
                  <.div(SignupCSS.Style.verificationMessageContainer)(
                    <.h4(state.lang.selectDynamic("CHECK_EMAIL_REGISTERED").toString) /*,
                  <.h4("We have sent a verification code to your email address, please check your email and enter the code below.")*/
                  ),
                  <.div(SignupCSS.Style.verificationMessageContainer)(
                    <.div(LoginCSS.Style.loginFormInputText, LoginCSS.Style.apiDetailsContainer)(
                      <.div(^.id := "addLabel", ^.className := "collapse")(
                        <.div(^.className := "input-group")(
                          // <.label(LoginCSS.Style.loginFormLabel)("API Server"),
                          <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "apiserver", ^.className := "form-control",
                            ^.placeholder := "API-Server", "data-error".reactAttr := "Server URL is required", "ref".reactAttr := "", ^.value := state.apiURL, ^.onChange ==> t.backend.updateAPIURL, ^.required := true),
                          <.div(^.className := "help-block with-errors"),
                          <.span(^.className := "input-group-addon", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.className := "btn", ^.onClick ==> t.backend.closeAPITextbox)(Icon.times),
                          <.span(^.className := "input-group-addon", ^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.className := "btn", ^.onClick --> Callback {
                            $(editApiDetailBtn).show()
                          })(Icon.check)
                        )
                      ),
                      <.button(^.id := "editApiDetailBtn", ^.`type` := "button", LoginCSS.Style.editApiDetailBtn, ^.className := "btn btn-default", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel", ^.onClick --> Callback {
                        $(editApiDetailBtn).hide()
                      })(state.lang.selectDynamic("EDIT_API_DETAILS").toString)
                    ),
                    <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "First name", ^.required := true,
                      ^.placeholder := state.lang.selectDynamic("VERIFICATION_CODE").toString, ^.value := state.emailValidationModel.token, ^.onChange ==> t.backend.updateToken)
                  ), "data-error".reactAttr := "Code can not be empty", ^.value := state.emailValidationModel.token, ^.onChange ==> t.backend.updateToken),
                <.div(^.className := "help-block with-errors")
              ),
              <.div(^.className := "pull-right")(
                <.button(^.id := "verify-code-submit-btn", ^.tpe := "submit", SignupCSS.Style.verifyBtn, ^.className := "btn", state.lang.selectDynamic("VERIFY").toString)
              )
            ),
            <.div(bss.modal.footer)()
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.accountValidationFailed) {
        scope.$.backend.hideModal
      }
    })
    .build

  def apply(props: Props) = component(props)

}
