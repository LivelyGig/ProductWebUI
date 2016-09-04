package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom.window
import shared.models.UserModel
import synereo.client.components.Bootstrap._
import synereo.client.components._
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopupbackends.LoginFormBackend
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
                   apiURL: String = s"https://192.168.99.100:9876"
                  )


  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new UserModel("", "", "")))
    .backend(new LoginFormBackend(_))
      .renderPS((t,P,S)=>{
        // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
        val headerText = "Log in"
        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.button(^.tpe := "button", ^.className := "hide", bss.close, ^.onClick --> hide, Icon.close), <.div(/*SignupCSS.Style.signUpHeading*/)(/*headerText*/)), /*<.div()(headerText)),*/

            closed = () => t.backend.formClosed(S, P),
            addStyles = Seq(LoginCSS.Style.loginModalStyle), keyboard = false, id = "loginContainer"
          ),
          <.form(^.id := "LoginForm", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> t.backend.submitForm)(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12")(
                <.div()(
                  if (P.isUserVerified) {
                    <.div(^.className := "emailVerifiedContainer")(<.h5("Email address verified."), <.h5("Please login with your credentails "))
                  } else
                    <.div(),
                  <.img(^.src := "./assets/synereo-images/amptoken.png", SynereoCommanStylesCSS.Style.loginImg),
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
                      ^.placeholder := "API-Server", "data-error".reactAttr := "Server URL is required", "ref".reactAttr := "", ^.value := S.apiURL, ^.onChange ==> t.backend.updateAPIURL, ^.required := true),
                    <.div(^.className := "help-block with-errors")
                  ),
                  <.div(SignupCSS.Style.signUPInput)(
                    //  <.label(LoginCSS.Style.loginFormLabel)("User Name"),
                    <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "Name", ^.className := "form-control", "data-error".reactAttr := "Bruh, that email address is invalid",
                      ^.placeholder := "username", "data-error".reactAttr := "Username is required", "ref".reactAttr := "", ^.value := S.userModel.email, ^.onChange ==> t.backend.updateEmail, ^.required := true, ^.ref := "nameInput"),
                    <.div(^.className := "help-block with-errors")
                  ),
                  <.div(SignupCSS.Style.signUPInput)(
                    // <.label(LoginCSS.Style.loginFormLabel)("Password"),
                    <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.placeholder := "password", ^.className := "form-control", ^.id := "inputPassword", "data-error".reactAttr := "Password is required",
                      ^.value := S.userModel.password, ^.onChange ==> t.backend.updatePassword, ^.required := true),
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
            Button(Button.Props(t.backend.addNewUserForm(), CommonStyle.default, Seq(LoginCSS.Style.dontHaveAccountBtnLoginModal), "", ""), "Dont have an account?"),
            Button(Button.Props(t.backend.addNewInviteForm(), CommonStyle.default, Seq(LoginCSS.Style.requestInviteBtnLoginModal), "", "", className = ""), "Request invite")
            //            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtnLoginModal), Icon.mailForward, "Request invite"))
          )
        )

      })
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