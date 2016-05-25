package synereo.client.modules

import japgolly.scalajs.react._
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos._
import shared.models.{SignUpModel, UserModel, EmailValidationModel}
import shared.sessionitems.SessionItems
import synereo.client.handlers.{CreateLabels, LoginUser, RefreshConnections}
import synereo.client.components.Bootstrap.{Button, CommonStyle}
import synereo.client.components.{Icon, MIcon}
import synereo.client.css.LoginCSS
import synereo.client.modalpopups._
import synereo.client.services.{ApiTypes, CoreApi, SYNEREOCircuit}
import synereo.client.services.CoreApi._
import scala.concurrent.Future
import scala.scalajs.js
import js.{Date, JSON, UndefOr}
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
  val loadingScreen: js.Object = "#loadingScreen"

  var showLoginContent: Boolean = false

  case class Props()

  case class State(userModel: UserModel,isloggedIn: Boolean = false, showLoginForm: Boolean = true, showNewUserForm: Boolean = false,
                   showErrorModal: Boolean = false, loginErrorMessage: String = "", showServerErrorModal: Boolean = false, showConfirmAccountCreation: Boolean = false,
                   showRegistrationFailed: Boolean = false,
                   showAccountValidationSuccess: Boolean = false, showAccountValidationFailed: Boolean = false, showNewInviteForm: Boolean = false, inviteMessage: String = "")

  class Backend(t: BackendScope[Props, State]) {

//    def updateEmail(e: ReactEventI) = {
//      val value = e.target.value
//      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(email = value)))
//    }

//    def updatePassword(e: ReactEventI) = {
//      val value = e.target.value
//      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(password = value)))
//    }

    def closeRequestInvitePopup(postInvite: Boolean): Callback = {
      true match {
        case `postInvite` => t.modState(s => s.copy(showLoginForm = true, showNewInviteForm = false))
        case _ => t.modState(s => s.copy(showLoginForm = true, showNewInviteForm = false))
      }
      t.modState(s => s.copy(showNewInviteForm = false, showLoginForm = true))
    }

    def Login(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false, showNewUserForm: Boolean = false, showNewInviteForm: Boolean = false): Callback = {
      //      println(s"showNewUserForm: $showNewUserForm")
      true match {
        case `login` => processLogin(userModel)
        case `showConfirmAccountCreation` => t.modState(s => s.copy(showLoginForm = false))
        case `showNewUserForm` => t.modState(s => s.copy(showLoginForm = false, showNewUserForm = true))
        case `showNewInviteForm` => t.modState(s => s.copy(showLoginForm = false, showNewInviteForm = true))
        case _ => t.modState(s => s.copy(showLoginForm = true))
      }
    }

    def processLogin(userModel: UserModel): Callback = {
//      val state = t.state.runNow()
//      val userModel = state.userModel
      $(loadingScreen).removeClass("hidden")
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

    def registrationFailed(registrationFailed: Boolean = false): Callback = {
      if (registrationFailed) {
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showNewUserForm = true))
      }
    }

    def closeServerErrorPopup(): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def closeLoginErrorPopup(): Callback = {
      t.modState(s => s.copy(showErrorModal = false, showLoginForm = true))
    }

    def processLoginFailed(responseStr: String): Unit = {
      println("in processLoginFailed")
      $(loadingScreen).addClass("hidden")
      $(loginLoader).addClass("hidden")
      val loginErrorMessage = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      println(loginErrorMessage.content.reason)
      t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = loginErrorMessage.content.reason)).runNow()
    }

    def processServerError(responseStr: String): Unit = {
      println("in processServerError")
      $(loadingScreen).addClass("hidden")
      $(loginLoader).addClass("hidden")
      //      val serverErrorMessage = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      t.modState(s => s.copy(showServerErrorModal = true)).runNow()
    }

    def createSessions(userModel: UserModel): Unit = {
      val sessionURISeq = Seq(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      var futureArray = Seq[Future[String]]()
      sessionURISeq.map { sessionURI => futureArray :+= CoreApi.agentLogin(userModel) }
      Future.sequence(futureArray).map { responseArray =>
        for (responseStr <- responseArray) {
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          val index = sessionURISeq(responseArray.indexOf(responseStr))
          window.sessionStorage.setItem(index, response.content.sessionURI)
        }
        //        println("login successful")
        $(loadingScreen).addClass("hidden")
        $(loginLoader).addClass("hidden")
        window.location.href = "/#dashboard"
        SYNEREOCircuit.dispatch(LoginUser(userModel))
      }
    }

    def processSuccessfulLogin(responseStr: String, userModel: UserModel): Unit = {
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
      //      response.content.listOfConnections.foreach(e => ListOfConnections.connections:+=e)
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CONNECTION_LIST, upickle.default.write[Seq[Connection]](response.content.listOfConnections))
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI, response.content.sessionURI)
      val user = UserModel(email = userModel.email, name = response.content.jsonBlob.getOrElse("name", ""),
        imgSrc = response.content.jsonBlob.getOrElse("imgSrc", ""), isLoggedIn = true)
      t.modState(s => s.copy(showLoginForm = false))
      window.sessionStorage.setItem("userEmail", userModel.email)
      window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name", ""))
      window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc", ""))
      window.sessionStorage.setItem("listOfLabels", JSON.stringify(response.content.listOfLabels))
      //      SYNEREOCircuit.dispatch(CreateLabels())
      //      SYNEREOCircuit.dispatch(CreateSessions(signUpModel))
      SYNEREOCircuit.dispatch(RefreshConnections())
      createSessions(userModel)

    }

    def addNewUserForm(): Callback = {
      t.modState(s => s.copy(showNewUserForm = true))
    }

    def addNewUser(signUpModel: SignUpModel, addNewUser: Boolean = false, showTermsOfServicesForm: Boolean = false): Callback = {
      //  log.debug(s"addNewUser signUpModel : ${signUpModel} ,addNewUser: ${addNewUser}")
      $(loadingScreen).removeClass("hidden")
      $(loginLoader).removeClass("hidden")
      //      println("usermodal " + signUpModel + "addNewUser " + addNewUser)
      //      println("in addNewUser method")
      if (addNewUser) {
        CoreApi.createUser(signUpModel).onComplete {
          case Success(response) =>
            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
            println(" In success value s = " + s)
            //           log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiTypes.CreateUserWaiting) {
              $(loadingScreen).addClass("hidden")
              $(loginLoader).addClass("hidden")
              t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
            } else {
              //              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
            }
          case Failure(s) =>
            //            log.debug(s"createUserFailure: ${s}")
            println(s"In faliure =$s")
            t.modState(s => s.copy(showErrorModal = true)).runNow()

        }
        t.modState(s => s.copy(showNewUserForm = false))
      } else {
        t.modState(s => s.copy(showNewUserForm = false, showLoginForm = true))
      }
    }

    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false): Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
            try {
              upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
              //  log.debug(ApiTypes.CreateUserError)
              showLoginContent = true
              t.modState(s => s.copy(/*showAccountValidationSuccess = true*/ showLoginForm = true)).runNow()
            } catch {
              case e: Exception =>
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

    def accountValidationSuccess(): Callback = {
      t.modState(s => s.copy(showAccountValidationSuccess = false, showLoginForm = true))
    }

    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }

    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", LoginCSS.Style.loginPageContainerMain)(
        //        <.div(^.className := "row")(
        //          <.div(LoginCSS.Style.loginDilog)(
        //            <.div(LoginCSS.Style.formPadding)(
        //              <.div(LoginCSS.Style.loginDilogContainerDiv)(
        //                <.img(LoginCSS.Style.loginFormImg, ^.src := "./assets/synereo-images/Synereo-logo.png"),
        //                <.div(^.className := "row")(
        //                  <.div(^.className := "col-md-12")(
        //                    <.div(LoginCSS.Style.loginFormContainerDiv)(
        //                      <.h1(^.className := "text-center", LoginCSS.Style.textWhite)("Log in"),
        //                      <.h3(^.className := "text-center", LoginCSS.Style.textBlue)("Social Self-determination"),
        //
        //                      <.form(^.onSubmit ==> loginUser)(
        //                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
        //                          <.input(^.`type` := "text", ^.placeholder := "User Name", ^.required := true, ^.value := s.signUpModel.email, ^.onChange ==> updateEmail, LoginCSS.Style.inputStyleLoginForm), <.br()
        //                        ),
        //                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
        //                          <.input(^.`type` := "Password", ^.placeholder := "Password", ^.required := true, LoginCSS.Style.inputStyleLoginForm, ^.value := s.signUpModel.password, ^.onChange ==> updatePassword),
        //                          <.button(^.`type` := "submit")(MIcon.playCircleOutline, LoginCSS.Style.iconStylePasswordInputBox)
        //                          //<.button(^.`type`:="button",^.className:="btn btn-default")("login")
        //                          //<.button(^.`type`:="button",^.className:="btn btn-default")("login")
        //                          //<.a(^.href := "/#synereodashboard")("Login Here")
        //                        ),
        //                        <.div(^.className := "col-md-12", LoginCSS.Style.loginFormFooter)(
        //                          <.div(LoginCSS.Style.keepMeLoggedIn)(
        //                            <.input(^.`type` := "radio"), <.span("Keep me logged in", LoginCSS.Style.keepMeLoggedInText)
        //                          ),
        //                          <.a(^.href := "#", "Forgot Your Password?", LoginCSS.Style.forgotMyPassLink)
        //                        )
        //                      )
        //                    )
        //                  )
        //                )
        //              )
        //            )
        //          )
        //        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12")(
            <.img(^.src := "./assets/synereo-images/login_nodeDecoration.png", ^.className := "img-responsive", LoginCSS.Style.loginScreenBgImage)
          ),
          <.div(
            //            Button(Button.Props(addNewUserForm(), CommonStyle.default, Seq(LoginCSS.Style.dontHaveAccount), "", ""), "Dont have an account?"),
            //            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")),
            if (s.showErrorModal) ErrorModal(ErrorModal.Props(closeLoginErrorPopup, s.loginErrorMessage))
            else if (s.showLoginForm) LoginForm(LoginForm.Props(Login, showLoginContent))
            else if (s.showServerErrorModal) ServerErrorModal(ServerErrorModal.Props(closeServerErrorPopup))
            else if (s.showNewUserForm) NewUserForm(NewUserForm.Props(addNewUser))
            else if (s.showConfirmAccountCreation) VerifyEmailModal(VerifyEmailModal.Props(confirmAccountCreation))
            else if (s.showRegistrationFailed) RegistrationFailed(RegistrationFailed.Props(registrationFailed))
            else if (s.showAccountValidationSuccess) AccountValidationSuccess(AccountValidationSuccess.Props(accountValidationSuccess))
            else if (s.showAccountValidationFailed) AccountValidationFailed(AccountValidationFailed.Props(accountValidationFailed))
            else if (s.showNewInviteForm) PostNewInvite(PostNewInvite.Props(closeRequestInvitePopup))
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
