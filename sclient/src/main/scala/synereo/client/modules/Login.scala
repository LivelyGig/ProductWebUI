package synereo.client.modules


import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.{RouterCtl, Resolution}
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos._
import synereo.client.Handlers.{CreateLabels, LoginUser}
import synereo.client.components.Bootstrap.{Button, CommonStyle}
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.LoginCSS
import synereo.client.modalpopups._
import synereo.client.models.{EmailValidationModel, UserModel}
import synereo.client.services.{ApiResponseMsg, SYNEREOCircuit, CoreApi}
import synereo.client.services.CoreApi._
import scala.scalajs.js
import js.{Date, UndefOr}
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.concurrent.ExecutionContext.Implicits.global
import org.querki.jquery._

/**
  * Created by Mandar on 3/11/2016.
  */
object Login {
  val LOGIN_ERROR = "LOGIN_ERROR"
  val SERVER_ERROR = "SERVER_ERROR"
  val SUCCESS = "SUCCESS"
  val loginLoader: js.Object = "#loginLoader"

  var showLoginContent :Boolean=false

  case class Props()

 
  case class State(userModel: UserModel, isloggedIn: Boolean = false, showLoginForm: Boolean = true, showNewUserForm: Boolean = false,
                   showErrorModal: Boolean = false, loginErrorMessage: String = "", showServerErrorModal: Boolean = false,showConfirmAccountCreation:Boolean=false,showRegistrationFailed:Boolean=false,
                   showAccountValidationSuccess:Boolean=false,showAccountValidationFailed:Boolean=false)

  class Backend(t: BackendScope[Props, State]) {

    def updateEmail(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    def loginUser(e: ReactEventI) = Callback {
      e.preventDefault()
      val user = t.state.runNow().userModel
      processLogin(user)
    }

    def LoginViaModal(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false, showNewAgentForm: Boolean = false): Callback = {
      true match {
        case `login` => processLoginForModal(userModel)
        case `showConfirmAccountCreation` => t.modState(s => s.copy(showLoginForm = false))
        case `showNewAgentForm` => t.modState(s => s.copy(showLoginForm = false))
        case _ => t.modState(s => s.copy(showLoginForm = false))
      }
    }

    def processLoginForModal(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      // $("#bodyBackground").addClass("DashBoardCSS.Style.overlay")
      CoreApi.agentLogin(userModel).onComplete {
        //          case Success(s) =>
        case Success(responseStr) =>
          validateResponse(responseStr) match {
            case SUCCESS => processSuccessfulLogin(responseStr, userModel)
            case LOGIN_ERROR => processLoginFailed(responseStr)
            case SERVER_ERROR => processServerError(responseStr)
          }
        case Failure(s) =>
          processServerError(responseStr = "")
      }
      t.modState(s => s.copy(showLoginForm = false))
    }

    def processLogin(userModel: UserModel): Unit = {
      $(loginLoader).removeClass("hidden")
      CoreApi.agentLogin(userModel).onComplete {
        case Success(responseStr) =>
          validateResponse(responseStr) match {
            case SUCCESS => processSuccessfulLogin(responseStr, userModel)
            case LOGIN_ERROR => processLoginFailed(responseStr)
            case SERVER_ERROR => processServerError(responseStr)
          }
        case Failure(s) =>
          $(loginLoader).addClass("hidden")
          t.modState(s => s.copy(showServerErrorModal = true)).runNow()
      }
    }

    def validateResponse(response: String): String = {
      try {
        upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
        LOGIN_ERROR
      } catch {
        case e: Exception =>
          try {
            upickle.default.read[ApiResponse[InitializeSessionResponse]](response)
            SUCCESS
          } catch {
            case p: Exception =>
              SERVER_ERROR
          }
      }
    }
    def registrationFailed(registrationFailed : Boolean = false) : Callback = {
      if (registrationFailed){
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showNewUserForm = true))
      }
    }
    def closeServerErrorPopup(): Callback = {
      t.modState(s => s.copy(showServerErrorModal = false))
    }

    def closeLoginErrorPopup(): Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }

    def processLoginFailed(responseStr: String) = {
      println("in processLoginFailed")
      $(loginLoader).addClass("hidden")
      val loginErrorMessage = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      //window.alert("please enter valid credentials")
      //      println(loginErrorMessage)
      println(loginErrorMessage.content.reason)
      t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = loginErrorMessage.content.reason)).runNow()

    }

    def processServerError(responseStr: String): Unit = {
      println("in processServerError")
      $(loginLoader).addClass("hidden")
      val serverErrorMessage = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      t.modState(s => s.copy(showServerErrorModal = true)).runNow()
    }

    def processSuccessfulLogin(responseStr: String, userModel: UserModel): Unit = {
      println("in processSuccessfulLogin")
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
      window.sessionStorage.setItem(CoreApi.CONNECTIONS_SESSION_URI, response.content.sessionURI)
      val user = UserModel(email = userModel.email, name = response.content.jsonBlob.getOrElse("name", ""),
        imgSrc = response.content.jsonBlob.getOrElse("imgSrc", ""), isLoggedIn = true)
      window.sessionStorage.setItem("userEmail", userModel.email)
      window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name", ""))
      window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc", ""))
      window.sessionStorage.setItem("listOfLabels", js.JSON.stringify(response.content.listOfLabels))
      SYNEREOCircuit.dispatch(LoginUser(user))
      println(userModel)
      $(loginLoader).addClass("hidden")
      window.location.href = "/#synereodashboard"
    }

    def addNewUserForm(): Callback = {
      t.modState(s => s.copy(showNewUserForm = true))
    }

    def addNewUser(userModel: UserModel, addNewUser: Boolean = false, showTermsOfServicesForm: Boolean = false): Callback = {
         //  log.debug(s"addNewUser userModel : ${userModel} ,addNewUser: ${addNewUser}")
      println("usermodal " + userModel + "addNewUser " + addNewUser)
      println("in add new user methiood")
      if (addNewUser) {
        CoreApi.createUser(userModel).onComplete {
          case Success(response) =>
            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
            println(" In success value s = " + s)
            //           log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting) {
                           t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
            } else {
              //              log.debug(s"createUser msg : ${s.content}")
                            t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
            }
          case Failure(s) =>
            //            log.debug(s"createUserFailure: ${s}")
            println(" In faliure = " + s)
            t.modState(s => s.copy(showErrorModal = true)).runNow()

        }
        t.modState(s => s.copy(showNewUserForm = false))
      }
      else {
        t.modState(s => s.copy(showNewUserForm = false))
      }
    }

    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false) : Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
            try {
              upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
            //  log.debug(ApiResponseMsg.CreateUserError)
              showLoginContent =true
              t.modState(s => s.copy(/*showAccountValidationSuccess = true*/showLoginForm = true)).runNow()
            } catch {
              case e: Exception  =>
                t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }

          case Failure(s) =>
          //  log.debug(s"ConfirmAccountCreationAPI failure: ${s.getMessage}")
            t.modState(s => s.copy(showErrorModal = true)).runNow()
        }
        t.modState(s => s.copy(showConfirmAccountCreation = false))
      } else {
        t.modState(s => s.copy(showConfirmAccountCreation = false))
      }
    }
    def accountValidationSuccess() : Callback = {
      t.modState(s => s.copy(showAccountValidationSuccess = false, showLoginForm = true))
    }
    def accountValidationFailed() : Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }

    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", LoginCSS.Style.loginPageContainerMain)(
        <.div(^.className := "row")(
          <.div(LoginCSS.Style.loginDilog)(
            <.div(LoginCSS.Style.formPadding)(
              <.div(LoginCSS.Style.loginDilogContainerDiv)(
                <.img(LoginCSS.Style.loginFormImg, ^.src := "./assets/synereo-images/Synereo-logo.png"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12")(
                    <.div(LoginCSS.Style.loginFormContainerDiv)(
                      <.h1(^.className := "text-center", LoginCSS.Style.textWhite)("Log in"),
                      <.h3(^.className := "text-center", LoginCSS.Style.textBlue)("Social Self-determination"),

                      <.form(^.onSubmit ==> loginUser)(
                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "text", ^.placeholder := "User Name", ^.required := true, ^.value := s.userModel.email, ^.onChange ==> updateEmail, LoginCSS.Style.inputStyleLoginForm), <.br()
                        ),
                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "Password", ^.placeholder := "Password", ^.required := true, LoginCSS.Style.inputStyleLoginForm, ^.value := s.userModel.password, ^.onChange ==> updatePassword),
                          <.button(^.`type` := "submit")(MIcon.playCircleOutline, LoginCSS.Style.iconStylePasswordInputBox)
                          //<.button(^.`type`:="button",^.className:="btn btn-default")("login")
                          //<.button(^.`type`:="button",^.className:="btn btn-default")("login")
                          //<.a(^.href := "/#synereodashboard")("Login Here")
                        ),
                        <.div(^.className := "col-md-12", LoginCSS.Style.loginFormFooter)(
                          <.div(LoginCSS.Style.keepMeLoggedIn)(
                            <.input(^.`type` := "radio"), <.span("Keep me logged in", LoginCSS.Style.keepMeLoggedInText)
                          ),
                          <.a(^.href := "#", "Forgot Your Password?", LoginCSS.Style.forgotMyPassLink)
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 text-center")(
            <.div(^.className := "col-md-12")(
              //              <.a(^.href := "/#signup", "Dont have an account?", LoginCSS.Style.dontHaveAccount)
              Button(Button.Props(addNewUserForm(), CommonStyle.default, Seq(LoginCSS.Style.dontHaveAccount), "", ""), "Dont have an account?")
            ),
            //   <.button(^.className := "btn text-center", "",),
            /* NewMessage(NewMessage.Props("Request invite", Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")),*/
            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")),
            if (s.showErrorModal) ErrorModal(ErrorModal.Props(closeLoginErrorPopup, s.loginErrorMessage))
            else if (s.showLoginForm) LoginForm(LoginForm.Props(LoginViaModal,showLoginContent))
            else if (s.showServerErrorModal) ServerErrorModal(ServerErrorModal.Props(closeServerErrorPopup))
            else if (s.showNewUserForm) NewUserForm(NewUserForm.Props(addNewUser))
            else if(s.showConfirmAccountCreation) VerifyEmailModal(VerifyEmailModal.Props(confirmAccountCreation))
            else if(s.showRegistrationFailed) RegistrationFailed(RegistrationFailed.Props(registrationFailed))
            else if (s.showAccountValidationSuccess) AccountValidationSuccess(AccountValidationSuccess.Props(accountValidationSuccess))
            else if (s.showAccountValidationFailed) AccountValidationFailed(AccountValidationFailed.Props(accountValidationFailed))
            else Seq.empty[ReactElement]
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("SynereoLogin")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]

    .build

  def apply(props: Props) = component(props)
}
