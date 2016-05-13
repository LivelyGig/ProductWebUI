package client.modals

import client.handlers.{ CreateLabels, LoginUser, RefreshConnections }
import client.components.Bootstrap._
import client.components._
import client.css.{ DashBoardCSS, HeaderCSS }
import client.logger._
import shared.models.{ EmailValidationModel, SignUpModel, UserModel }
import client.services.CoreApi._
import client.services._
import shared.dtos._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{ Failure, Success }
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.UserModel

import scala.scalajs.js.JSON
import org.querki.jquery._

import scala.concurrent.Future

object AgentLoginSignUp {
  val LOGIN_ERROR = "LOGIN_ERROR"
  val SERVER_ERROR = "SERVER_ERROR"
  val SUCCESS = "SUCCESS"
  val loginLoader: js.Object = "#loginLoader"
  val dashboardContainer: js.Object = ".dashboard-container"
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props()

  case class State(showNewAgentForm: Boolean = false, showLoginForm: Boolean = false, showValidateForm: Boolean = false,
    showConfirmAccountCreation: Boolean = false, showAccountValidationSuccess: Boolean = false,
    showLoginFailed: Boolean = false, showRegistrationFailed: Boolean = false,
    showErrorModal: Boolean = false, showAccountValidationFailed: Boolean = false, showTermsOfServicesForm: Boolean = false,
    loginErrorMessage: String = "")

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) /*extends OnUnmount*/ {
  }

  case class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addLoginForm(): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addNewAgentForm(): Callback = {
      t.modState(s => s.copy(showNewAgentForm = true))
    }

    def addNewAgent(signUpModel: SignUpModel, addNewAgent: Boolean = false, showTermsOfServicesForm: Boolean = false): Callback = {
      log.debug(s"addNewAgent userModel : ${signUpModel} ,addNewAgent: ${addNewAgent}")
      if (addNewAgent) {
        createUser(signUpModel).onComplete {
          case Success(response) =>
            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting) {
              t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
            } else {
              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
            }
          case Failure(s) =>
            log.debug(s"createUserFailure: ${s}")
            t.modState(s => s.copy(showErrorModal = true)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showNewAgentForm = false))
      } else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showNewAgentForm = false, showTermsOfServicesForm = true))
      } else {
        t.modState(s => s.copy(showNewAgentForm = false))
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

    /**
     * Weird creation of sessions?
     * Look at description of SessionItems.getAllSessionUriExceptCnxs() mate :)
     *
     * @param userModel
     */
    def createSessions(userModel: UserModel): Unit = {
      val sessionURISeq = SessionItems.getAllSessionUriNameExceptCnxs()
      val futureArray = for (sessionURI <- sessionURISeq) yield CoreApi.agentLogin(userModel)
      Future.sequence(futureArray).map { responseArray =>
        for (responseStr <- responseArray) {
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          window.sessionStorage.setItem(sessionURISeq(responseArray.indexOf(responseStr)), response.content.sessionURI)
        }
        $(loginLoader).addClass("hidden")
        $(dashboardContainer).removeClass("hidden")
        window.location.href = "/#connections"
        log.debug("login successful")
      }
    }

    def processSuccessfulLogin(responseStr: String, userModel: UserModel): Unit = {
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
      window.sessionStorage.setItem(
        SessionItems.ConnectionViewItems.CONNECTION_LIST,
        upickle.default.write[Seq[Connection]](response.content.listOfConnections)
      )
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI, response.content.sessionURI)
      val user = UserModel(email = userModel.email, name = response.content.jsonBlob.getOrElse("name", ""),
        imgSrc = response.content.jsonBlob.getOrElse("imgSrc", ""), isLoggedIn = true)
      window.sessionStorage.setItem("userEmail", userModel.email)
      window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name", ""))
      window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc", ""))
      window.sessionStorage.setItem(SessionItems.SearchesView.LIST_OF_LABELS, JSON.stringify(response.content.listOfLabels))
      LGCircuit.dispatch(LoginUser(user))
      LGCircuit.dispatch(CreateLabels())
      LGCircuit.dispatch(RefreshConnections())
      createSessions(userModel)
    }

    def processLoginFailed(responseStr: String): Unit = {
      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      $(loginLoader).addClass("hidden")
      //  $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
      println("login error")
      t.modState(s => s.copy(showLoginFailed = true, loginErrorMessage = loginError.content.reason)).runNow()
    }
    def processServerError(): Unit = {
      $(loginLoader).addClass("hidden")
      //  $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
      println("internal server error")
      t.modState(s => s.copy(showErrorModal = true)).runNow()
    }

    def processLogin(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      // $("#bodyBackground").addClass("DashBoardCSS.Style.overlay")
      CoreApi.agentLogin(userModel).onComplete {
        //          case Success(s) =>
        case Success(responseStr) =>
          validateResponse(responseStr) match {
            case SUCCESS => processSuccessfulLogin(responseStr, userModel)
            case LOGIN_ERROR => processLoginFailed(responseStr)
            case SERVER_ERROR => processServerError()
          }
        case Failure(s) =>
          processServerError()
      }
      t.modState(s => s.copy(showLoginForm = false))
    }

    def loginUser(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false, showNewAgentForm: Boolean = false): Callback = {
      true match {
        case `login` => processLogin(userModel)
        case `showConfirmAccountCreation` => t.modState(s => s.copy(showLoginForm = false, showConfirmAccountCreation = true))
        case `showNewAgentForm` => t.modState(s => s.copy(showLoginForm = false, showNewAgentForm = true))
        case _ => t.modState(s => s.copy(showLoginForm = false))
      }
    }
    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false): Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
            try {
              upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
              log.debug(ApiResponseMsg.CreateUserError)
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
            } catch {
              case e: Exception =>
                t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }

          case Failure(s) =>
            log.debug(s"ConfirmAccountCreationAPI failure: ${s.getMessage}")
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
    def loginFailed(): Callback = {
      t.modState(s => s.copy(showLoginFailed = false, showLoginForm = true))
    }
    def registrationFailed(registrationFailed: Boolean = false): Callback = {
      if (registrationFailed) {
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showNewAgentForm = true))
      }
    }
    def serverError(): Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }
    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }
    def termsOfServices(): Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showNewAgentForm = true))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      val B = t.backend
      <.div()(
        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn), "", ""), "Log In"),
        Button(Button.Props(B.addNewAgentForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn), "", ""), "Sign Up"),
        //        <.button(^.className:="btn btn-default",^.tpe := "button", ^.onClick --> P.proxy.dispatch(LoginUser(P.proxy.value)),
        //          HeaderCSS.Style.SignUpBtn)("Login"),
        if (S.showNewAgentForm) { NewAgentForm(NewAgentForm.Props(B.addNewAgent)) }
        else if (S.showTermsOfServicesForm) { TermsOfServices(TermsOfServices.Props(B.termsOfServices)) }
        else if (S.showLoginForm) { LoginForm(LoginForm.Props(B.loginUser)) }
        else if (S.showConfirmAccountCreation) { ConfirmAccountCreation(ConfirmAccountCreation.Props(B.confirmAccountCreation)) }
        else if (S.showAccountValidationSuccess) { AccountValidationSuccess(AccountValidationSuccess.Props(B.accountValidationSuccess)) }
        else if (S.showLoginFailed) { LoginFailed(LoginFailed.Props(B.loginFailed, S.loginErrorMessage)) }
        else if (S.showRegistrationFailed) { RegistrationFailed(RegistrationFailed.Props(B.registrationFailed)) }
        else if (S.showErrorModal) { ErrorModal(ErrorModal.Props(B.serverError)) }
        else if (S.showAccountValidationFailed) { AccountValidationFailed(AccountValidationFailed.Props(B.accountValidationFailed)) }
        else {
          Seq.empty[ReactElement]
        }

      )
    })
    .build
  def apply(props: Props) = component(props)
}
