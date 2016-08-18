package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom.window
import shared.models.UserModel
import synereo.client.components.Bootstrap._
import synereo.client.components._
import synereo.client.css.{LoginCSS, SignupCSS}
import synereo.client.sessionitems.SessionItems

import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 4/19/2016.
  */
//scalastyle:off

object LoginForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean, Boolean, Boolean, Boolean) => Callback, isUserVerified: Boolean = false)

  case class State(userModel: UserModel, login: Boolean = false,
                   showConfirmAccountCreation: Boolean = false,
                   showNewUserForm: Boolean = false,
                   showNewInviteForm: Boolean = false,
                   hostName: String = dom.window.location.hostname,
                   portNumber: String = "9876",
                   apiURL: String = s"http://" + dom.window.location.hostname +":9876"
                  )

  val LoginForm: js.Object = "#LoginForm"

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, state.apiURL)
      //window.sessionStorage.setItem(SessionItems.ApiDetails.API_HOST, state.hostName)
      //window.sessionStorage.setItem(SessionItems.ApiDetails.API_PORT, state.portNumber)
      val LoginBtn: js.Object = "#LoginBtn"
      if ($(LoginBtn).hasClass("disabled"))
        t.modState(s => s.copy(login = false))
      else
        t.modState(s => s.copy(login = true))
    }

    def addNewUserForm(): Callback = {
      t.modState(s => s.copy(login = false, showNewUserForm = true))
      //      t.modState(s => s.copy(showNewUserForm = true))
    }

    def addNewInviteForm(): Callback = {
      t.modState(s => s.copy(login = false, showNewInviteForm = true))
      //      t.modState(s => s.copy(showNewUserForm = true))
    }

    def hide = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateEmail(e: ReactEventI) = {
      //      println(e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def showValidate(e: ReactEventI) = {
      t.modState(s => s.copy(showConfirmAccountCreation = true))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    def userNameFocus(): Unit = {
      $(LoginForm).find("input:first").focus()
    }

    def updateAPIURL(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(apiURL = value))
    }

    def updateIp(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(hostName = value))
    }

    def updatePort(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(portNumber = value))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //      println(s"state.showNewAgentForm: ${state.showNewUserForm}")
      props.submitHandler(state.userModel, state.login, state.showConfirmAccountCreation, state.showNewUserForm, state.showNewInviteForm)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Log in"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", ^.className := "hide", bss.close, ^.onClick --> hide, Icon.close), <.div(/*SignupCSS.Style.signUpHeading*/)(/*headerText*/)), /*<.div()(headerText)),*/

          closed = () => formClosed(s, p),
          addStyles = Seq(LoginCSS.Style.loginModalStyle), keyboard = false , id="loginContainer"
        ),
        <.form(^.id := "LoginForm", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div()(
                if (p.isUserVerified) {
                  <.div(^.className := "emailVerifiedContainer")(<.h5("Email address verified."), <.h5("Please login with your credentails "))
                } else
                  <.div(),
                <.div(SignupCSS.Style.loginHeading)(headerText),
//                <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
//                  <.div(^.className := "row")(
//                    <.div(/*^.className := "col-md-4"*/)(
//                      <.label(LoginCSS.Style.loginFormLabel)("Host-ip")
//                    ),
//                    <.div(/*^.className := "col-md-8"*/)(
//                      <.input(^.`type` := "text", ^.placeholder := "Host-ip", SignupCSS.Style.inputStyleSignUpForm,
//                        ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true)
//                    )
//                  )
//                ),
//                <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
//                  <.div(^.className := "row")(
//                    <.div(/*^.className := "col-md-4"*/)(
//                      <.label(LoginCSS.Style.loginFormLabel)("Port Number")
//                    ),
//                    <.div(/*^.className := "col-md-8"*/)(
//                      <.input(^.tpe := "text", ^.placeholder := "Port Number", SignupCSS.Style.inputStyleSignUpForm,
//                        ^.value := s.portNumber, ^.onChange ==> updatePort, ^.required := true)
//                    )
//                  )
//                ),
//                <.div(^.className := "form-group")(
//                  <.label(LoginCSS.Style.loginFormLabel)("Host-ip"),
//                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "Name", ^.className := "form-control",
//                    ^.placeholder := "Host-ip", "data-error".reactAttr := "IP is required", "ref".reactAttr := "", ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true),
//                  <.div(^.className := "help-block with-errors")
//                ),
//                <.div(^.className := "form-group")(
//                  <.label(LoginCSS.Style.loginFormLabel)("Port Number"),
//                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "Name", ^.className := "form-control",
//                    ^.placeholder := "Port number", "data-error".reactAttr := "PortNo is required", "ref".reactAttr := "", ^.value := s.portNumber, ^.onChange ==> updatePort, ^.required := true),
//                  <.div(^.className := "help-block with-errors")
//                ),
                <.div(SignupCSS.Style.signUPInput)(
                 // <.label(LoginCSS.Style.loginFormLabel)("API Server"),
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "apiserver", ^.className := "form-control",
                    ^.placeholder := "API-Server", "data-error".reactAttr := "Server URL is required", "ref".reactAttr := "", ^.value := s.apiURL, ^.onChange ==> updateAPIURL, ^.required := true),
                  <.div(^.className := "help-block with-errors")
                ),
                <.div(SignupCSS.Style.signUPInput)(
                //  <.label(LoginCSS.Style.loginFormLabel)("User Name"),
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "Name", ^.className := "form-control", "data-error".reactAttr := "Bruh, that email address is invalid",
                    ^.placeholder := "username", "data-error".reactAttr := "Username is required", "ref".reactAttr := "", ^.value := s.userModel.email, ^.onChange ==> updateEmail, ^.required := true, ^.ref := "nameInput"),
                  <.div(^.className := "help-block with-errors")
                ),
                <.div( SignupCSS.Style.signUPInput)(
                 // <.label(LoginCSS.Style.loginFormLabel)("Password"),
                  <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.placeholder := "password", ^.className := "form-control", ^.id := "inputPassword", "data-error".reactAttr := "Password is required",
                    ^.value := s.userModel.password, ^.onChange ==> updatePassword, ^.required := true),
                  <.div(^.className := "help-block with-errors")
                ),
//                <.div(^.className := "row")(
//                  <.div(^.className := "col-md-6 col-sm-6 col-xs-6 text-left", LoginCSS.Style.loginModalTextActionContainer)(
//                    // <.img(^.src := "./assets/synereo-images/CheckBox_Off.svg", LoginCSS.Style.checkBoxLoginModal /*, ^.onClick ==> changeCheckBox*/),
//                    <.input(^.`type` := "checkbox", ^.id := "KeepMeLoggedIn"), <.label(^.`for` := "KeepMeLoggedIn", LoginCSS.Style.loginModalTextStyle)("Keep me logged in")
//                  ),
//                  <.div(^.className := "col-md-6 col-sm-6 col-xs-6 text-right", LoginCSS.Style.loginModalTextActionContainer)(
//                    <.a(^.href := "", LoginCSS.Style.loginModalTextStyle)("Forgot Password?")
//                  )
//                ),
                <.div(^.className := "text-center", ^.className := "form-group")(
                  <.button(^.tpe := "submit", ^.id := "LoginBtn", LoginCSS.Style.modalLoginBtn, ^.className := "btn", "Log in")
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, LoginCSS.Style.loginModalFooter)(
          Button(Button.Props(addNewUserForm(), CommonStyle.default, Seq(LoginCSS.Style.dontHaveAccountBtnLoginModal), "", ""), "Dont have an account?"),
          Button(Button.Props(addNewInviteForm(), CommonStyle.default, Seq(LoginCSS.Style.requestInviteBtnLoginModal), "", "", className = ""), "Request invite")
          //            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtnLoginModal), Icon.mailForward, "Request invite"))
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      $(scope.getDOMNode()).on("shown.bs.modal", "", js.undefined, scope.backend.userNameFocus _)
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.login || scope.currentState.showConfirmAccountCreation || scope.currentState.showNewUserForm || scope.currentState.showNewInviteForm) {
        scope.$.backend.hide
      }
      //      if (scope.currentState.showNewAgentForm) NewUserForm(NewUserForm.Props(scope.component.backend.addNewUser()))
    })
    .build

  def apply(props: Props) = component(props)

}