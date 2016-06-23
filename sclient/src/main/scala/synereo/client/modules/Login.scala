package synereo.client.modules

import japgolly.scalajs.react._
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos._
import shared.models.{EmailValidationModel, SignUpModel, UserModel}
import shared.sessionitems.SessionItems
import synereo.client.handlers.{CreateLabels, LoginUser, RefreshConnections}
import synereo.client.modalpopups._
import synereo.client.services.{ApiTypes, CoreApi, SYNEREOCircuit}
import synereo.client.services.CoreApi._

import scala.concurrent.Future
import scala.scalajs.js
import js.JSON
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.concurrent.ExecutionContext.Implicits.global
import org.querki.jquery._
import synereo.client.components.GlobalStyles
import synereo.client.css.LoginCSS
import synereo.client.logger._

/**
  * Created by Mandar on 3/11/2016.
  */
//scalastyle:off
object Login {

  val LOGIN_ERROR = "LOGIN_ERROR"
  val SERVER_ERROR = "SERVER_ERROR"
  val SUCCESS = "SUCCESS"
  val loginLoader: js.Object = "#loginLoader"
  val loadingScreen: js.Object = "#loadingScreen"
  var isUserVerified = false

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props()

  case class State(showNewUserForm: Boolean = false, showLoginForm: Boolean = true, showValidateForm: Boolean = false,
                   showConfirmAccountCreation: Boolean = false, showAccountValidationSuccess: Boolean = false,
                   showLoginFailed: Boolean = false, showRegistrationFailed: Boolean = false,
                   showErrorModal: Boolean = false, showAccountValidationFailed: Boolean = false, showTermsOfServicesForm: Boolean = false,
                   loginErrorMessage: String = "", showNewInviteForm: Boolean = false, inviteMessage: String = "", isloggedIn: Boolean = false)

  //  case class State(isloggedIn: Boolean = false, showLoginForm: Boolean = true, showNewUserForm: Boolean = false,
  //                   showErrorModal: Boolean = false, loginErrorMessage: String = "", showServerErrorModal: Boolean = false, showConfirmAccountCreation: Boolean = false,
  //                   showRegistrationFailed: Boolean = false,
  //                   showAccountValidationSuccess: Boolean = false, showAccountValidationFailed: Boolean = false, showNewInviteForm: Boolean = false, inviteMessage: String = "")


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
      t.modState(s => s.copy(showNewUserForm = true))
    }

    def addNewAgent(signUpModel: SignUpModel, addNewAgent: Boolean = false, showLoginForm: Boolean = false): Callback = {
      $(loadingScreen).removeClass("hidden")
      $(loginLoader).removeClass("hidden")
      log.debug(s"addNewAgent userModel : ${signUpModel} ,addNewAgent: ${addNewAgent}")
      if (addNewAgent) {
        createUser(signUpModel).onComplete {
          case Success(response) =>
            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiTypes.CreateUserWaiting) {
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
        t.modState(s => s.copy(showNewUserForm = false))
      } else if (showLoginForm) {
        t.modState(s => s.copy(showNewUserForm = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showNewUserForm = false, showLoginForm = true))
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

    def setUserDetailsInSession(responseStr: String, userModel: UserModel): Unit = {
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
      window.sessionStorage.setItem(SessionItems.SearchesView.LIST_OF_LABELS, JSON.stringify(response.content.listOfLabels))
      window.sessionStorage.setItem(
        SessionItems.ConnectionViewItems.CONNECTION_LIST,
        upickle.default.write[Seq[Connection]](response.content.listOfConnections)
      )
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST, upickle.default.write[Seq[Connection]](response.content.listOfConnections))
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI, response.content.sessionURI)
      window.sessionStorage.setItem("userEmail", userModel.email)
      window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name", ""))
      window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc", ""))
    }

    def processLogin(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      $(loadingScreen).removeClass("hidden")
      val sessionURISeq = SessionItems.getAllSessionUriName()
      val futureArray = for (sessionURI <- sessionURISeq) yield CoreApi.agentLogin(userModel)
      Future.sequence(futureArray).onComplete {
        case Success(responseArray) =>
          validateResponse(responseArray(0)) match {
            case SUCCESS => processSuccessfulLogin(responseArray, userModel)
            case LOGIN_ERROR => processLoginFailed(responseArray(0))
            case SERVER_ERROR => processServerError()
          }
        case Failure(res) =>
          processServerError()
      }
      t.modState(s => s.copy(showLoginForm = false))
    }

    def setSessionsUri(responseArray: Seq[String]): Unit = {
      val sessionUriNames = SessionItems.getAllSessionUriName()
      for (responseStr <- responseArray) {
        val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
        window.sessionStorage.setItem(sessionUriNames(responseArray.indexOf(responseStr)), response.content.sessionURI)
      }
    }

    def processSuccessfulLogin(responseArray: Seq[String], userModel: UserModel): Unit = {
      setSessionsUri(responseArray)
      val responseStr = responseArray(0)
      setUserDetailsInSession(responseStr, userModel)
      SYNEREOCircuit.dispatch(RefreshConnections())
      $(loginLoader).addClass("hidden")
      $(loadingScreen).addClass("hidden")
      window.location.href = "/#dashboard"
      log.debug("login successful")
    }

    def processLoginFailed(responseStr: String): Unit = {
      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      $(loginLoader).addClass("hidden")
      println("login error")
      t.modState(s => s.copy(showLoginFailed = true, loginErrorMessage = loginError.content.reason)).runNow()
    }

    def processServerError(): Unit = {
      $(loginLoader).addClass("hidden")
      println("internal server error")
      t.modState(s => s.copy(showErrorModal = true)).runNow()
    }

    def loginUser(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false, showNewUserForm: Boolean = false, showNewInviteForm: Boolean = false): Callback = {
      true match {
        case `login` => processLogin(userModel)
        case `showConfirmAccountCreation` => t.modState(s => s.copy(showLoginForm = false, showConfirmAccountCreation = true))
        case `showNewUserForm` => t.modState(s => s.copy(showLoginForm = false, showNewUserForm = true))
        case `showNewInviteForm` => t.modState(s => s.copy(showLoginForm = false, showNewInviteForm = true))
        case _ => t.modState(s => s.copy(showLoginForm = true))
      }
    }

    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false): Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
            try {
              upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
              isUserVerified = true
              log.debug(ApiTypes.CreateUserError)
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

    def closeRequestInvitePopup(postInvite: Boolean): Callback = {
      true match {
        case `postInvite` => t.modState(s => s.copy(showLoginForm = true, showNewInviteForm = false))
        case _ => t.modState(s => s.copy(showLoginForm = true, showNewInviteForm = false))
      }
      t.modState(s => s.copy(showNewInviteForm = false, showLoginForm = true))
    }

    def loginFailed(): Callback = {
      t.modState(s => s.copy(showLoginFailed = false, showLoginForm = true))
    }

    def registrationFailed(registrationFailed: Boolean = false): Callback = {
      if (registrationFailed) {
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showNewUserForm = true))
      }
    }

    def serverError(): Callback = {
      t.modState(s => s.copy(showErrorModal = false, showLoginForm = true))
    }

    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }

    def termsOfServices(): Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showNewUserForm = true))
    }

    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", LoginCSS.Style.loginPageContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12")(
            <.img(^.src := "./assets/synereo-images/login_nodeDecoration.png", ^.className := "img-responsive", LoginCSS.Style.loginScreenBgImage)
          ),
          <.div()(
            if (s.showNewUserForm) {
              NewUserForm(NewUserForm.Props(addNewAgent))
            }
            else if (s.showNewInviteForm) {
              //          TermsOfServices(TermsOfServices.Props(B.termsOfServices))
              //              <.div()
              PostNewInvite(PostNewInvite.Props(closeRequestInvitePopup))
            }
            else if (s.showLoginForm) {
              LoginForm(LoginForm.Props(loginUser, isUserVerified))
            }
            else if (s.showConfirmAccountCreation) {
              VerifyEmailModal(VerifyEmailModal.Props(confirmAccountCreation))
            }
            else if (s.showAccountValidationSuccess) {
              AccountValidationSuccess(AccountValidationSuccess.Props(accountValidationSuccess))
            }
            else if (s.showLoginFailed) {
              LoginFailed(LoginFailed.Props(loginFailed, s.loginErrorMessage))
            }
            else if (s.showRegistrationFailed) {
              RegistrationFailed(RegistrationFailed.Props(registrationFailed))
            }
            else if (s.showErrorModal) {
              ErrorModal(ErrorModal.Props(serverError))
            }
            else if (s.showAccountValidationFailed) {
              AccountValidationFailed(AccountValidationFailed.Props(accountValidationFailed))
            }
            else {
              Seq.empty[ReactElement]
            }
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("SynereoLogin")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}
