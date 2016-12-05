package client.modals

import client.handler._
import client.components._
import client.logger
import client.logger._
import shared.models.{EmailValidationModel, SignUpModel}
import client.services.CoreApi._
import client.services._
import shared.dtos._
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.UserModel
import org.querki.jquery._
import client.sessionitems.SessionItems
import scala.concurrent.Future
import diode.AnyAction._
import client.utils.{AppUtils, ConnectionsUtils}

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
                   showErrorModal: Boolean = false, showAccountValidationFailed: Boolean = false, showTermsOfServicesForm: Boolean = false, showPrivacyPolicyModal: Boolean = false,
                   loginErrorMessage: String = "", showApiDetailsForm: Boolean = false, registrationErrorMsg: String = "")

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) /*extends OnUnmount*/ {
  }

  // scalastyle:off
  case class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addLoginForm(): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    //    def addApiDetailsForm(): Callback = {
    //      t.modState(s => s.copy(showApiDetailsForm = true))
    //    }

    def addLoginDetails(showLoginForm: Boolean = false): Callback = {
      if (showLoginForm)
        t.modState(s => s.copy(/*showApiDetailsForm = false,*/ showLoginForm = true))
      else
        t.modState(s => s.copy(showLoginForm = false))
    }

    def addNewAgentForm(): Callback = {
      t.modState(s => s.copy(showNewAgentForm = true))
    }

    def addNewAgent(signUpModel: SignUpModel, addNewAgent: Boolean = false, showTermsOfServicesForm: Boolean = false, showPrivacyPolicyModal: Boolean = false): Callback = {
      //      log.debug(s"addNewAgent userModel : ${signUpModel} ,addNewAgent: ${addNewAgent}")
      if (addNewAgent) {
        createUser(signUpModel).onComplete {
          case Success(response) =>
            try {
              val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
              //            log.debug(s"createUser msg : ${s.msgType}")
              if (s.msgType == ApiTypes.responseTypes.CREATE_USER_WAITING) {
                t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
              } else if (s.msgType == ApiTypes.responseTypes.CREATE_USER_ERROR) {
                val errorResponse = upickle.default.read[ApiResponse[CreateUserError]](response)
                //              val msg = JSON.parse(errorResponse.content.reason).reason.asInstanceOf[String]
                t.modState(s => s.copy(showRegistrationFailed = true, registrationErrorMsg = errorResponse.content.reason)).runNow()
                //                $(loginLoader).addClass("hidden")
                //                $(loadingScreen).addClass("hidden")
              } else if (s.msgType == ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR) {
                processServerError(s.msgType)
              }
              else {
                // log.debug(s"createUser msg : ${s.content}")
                t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
              }
            } catch {
              case e: Exception =>
                val error = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
                processServerError(error.msgType)
            }
          case Failure(s) =>
            processServerError(ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR)
          //            processServerError(s.)
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showNewAgentForm = false))
      } else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showNewAgentForm = false, showTermsOfServicesForm = true))
      } else if (showPrivacyPolicyModal) {
        t.modState(s => s.copy(showNewAgentForm = false, showPrivacyPolicyModal = true))
      } else {
        t.modState(s => s.copy(showNewAgentForm = false))
      }
    }

    def validateResponse(response: String): String = {
//      println(response)
      try {
        val error = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
        if (error.msgType == ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR)
          SERVER_ERROR
        else
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


    def setUserDetails(cnxnResponseStr: String, cnxnModelStr: String): Unit = {
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](cnxnResponseStr)
      // println(s"SetUserDetails initialresponse sessionURI ==== ${response.content.sessionURI}")
      LGCircuit.dispatch(LoginUser(UserModel(name = response.content.jsonBlob.getOrElse("name", ""), imgSrc = response.content.jsonBlob.getOrElse("imgSrc", "") /*,sessionUri = response.content.sessionURI*/)))
      LGCircuit.dispatch(UpdateConnections(ConnectionsUtils.getConnectionsModel(cnxnModelStr)))
      LGCircuit.dispatch(CreateLabels(response.content.listOfLabels))
    }

    def processLogin(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      val sessionURISeq = SessionItems.getAllSessionUriName()
      val futureArray = for (sessionURI <- sessionURISeq) yield CoreApi.agentLogin(userModel)
      Future.sequence(futureArray).onComplete {
        case Success(responseArray) =>
          validateResponse(responseArray(0)) match {
            case SUCCESS => processSuccessfulLogin(responseArray, userModel)
            case LOGIN_ERROR => processLoginFailed(responseArray(0))
            case SERVER_ERROR =>
              val error = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseArray(0))
//              println(error)
              processServerError(error.msgType)

          }
        case Failure(res) =>
          processServerError(ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR)
      }
      t.modState(s => s.copy(showLoginForm = false))
    }

    def processSuccessfulLogin(responseArray: Seq[String], userModel: UserModel): Unit = {
      val responseSeq = responseArray.map(e => upickle.default.read[ApiResponse[InitializeSessionResponse]](e))
      LGCircuit.dispatch(SetSessionUri(responseSeq.map(_.content.sessionURI)))
      // do a session ping to get the connections
      val futureArray = for (response <- responseSeq) yield CoreApi.sessionPing(response.content.sessionURI)
      Future.sequence(futureArray).onComplete {
        case Success(sessionPingResponseStr) =>
          AppUtils.handleInitialSessionPingRes(sessionPingResponseStr(0))
          setUserDetails(responseArray(0), sessionPingResponseStr(0))
          LGCircuit.dispatch(SubscribeForDefaultAndBeginPing())
          $(loginLoader).addClass("hidden")
          $(dashboardContainer).removeClass("hidden")
          window.location.replace("/#messages")
          log.debug("login successful")

        case Failure(res) =>
          processServerError(res.getMessage)
      }
    }

    def processLoginFailed(responseStr: String): Unit = {
      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      $(loginLoader).addClass("hidden")
      //  $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
      println("login error")
      t.modState(s => s.copy(showLoginFailed = true, loginErrorMessage = loginError.content.reason)).runNow()
    }

    def processServerError(responseStr: String): Unit = {
      //      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      $(loginLoader).addClass("hidden")
      logger.log.error(s"internal server error  ${responseStr}")
      t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = responseStr)).runNow()

    }

    /*def processLogin(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      CoreApi.agentLogin(userModel).onComplete {
        //          case Success(s) =>
        case Success(responseStr) =>
          validateResponse(responseStr) match {
            case SUCCESS => createSessions(responseStr, userModel)
            case LOGIN_ERROR => processLoginFailed(responseStr)
            case SERVER_ERROR => processServerError()
          }
        case Failure(s) =>
          processServerError()
      }
      t.modState(s => s.copy(showLoginForm = false))
    }*/

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
              //log.debug(ApiTypes.CreateUserError)
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
            } catch {
              case e: Exception =>
                t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }

          case Failure(s) =>
            // log.debug(s"ConfirmAccountCreationAPI failure: ${s.getMessage}")
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

    def serverError(showLoginForm: Boolean = false): Callback = {
      t.modState(s => s.copy(showErrorModal = false, showLoginForm = true))
    }

    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }

    def termsOfServices(): Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showNewAgentForm = true))
    }

    def privacyPolicy(): Callback = {
      t.modState(s => s.copy(showPrivacyPolicyModal = false, showNewAgentForm = true))
    }

  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      val B = t.backend
      <.div()(
        // Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn), "", ""), "Log In"),
        // Button(Button.Props(B.addNewAgentForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn), "", ""), "Sign Up"),
        //        <.button(^.className:="btn btn-default",^.tpe := "button", ^.onClick --> P.proxy.dispatch(LoginUser(P.proxy.value)),
        //          HeaderCSS.Style.SignUpBtn)("Login"),
        /*if(S.showApiDetailsForm){
          ApiDetailsForm(ApiDetailsForm.Props(B.addLoginDetails))
        }
        else*/
        if (S.showNewAgentForm) {
          NewAgentForm(NewAgentForm.Props(B.addNewAgent))
        }
        else if (S.showTermsOfServicesForm) {
          TermsOfServices(TermsOfServices.Props(B.termsOfServices))
        }
        else if (S.showPrivacyPolicyModal) {
          PrivacyPolicyModal(PrivacyPolicyModal.Props(B.privacyPolicy))
        }
        else if (S.showLoginForm) {
          LoginForm(LoginForm.Props(B.loginUser))
        }
        else if (S.showConfirmAccountCreation) {
          ConfirmAccountCreation(ConfirmAccountCreation.Props(B.confirmAccountCreation))
        }
        else if (S.showAccountValidationSuccess) {
          AccountValidationSuccess(AccountValidationSuccess.Props(B.accountValidationSuccess))
        }
        else if (S.showLoginFailed) {
          LoginFailed(LoginFailed.Props(B.loginFailed, S.loginErrorMessage))
        }
        else if (S.showRegistrationFailed) {
          RegistrationFailed(RegistrationFailed.Props(B.registrationFailed, S.registrationErrorMsg))
        }
        else if (S.showErrorModal) {
          ServerErrorModal(ServerErrorModal.Props(B.serverError, S.loginErrorMessage))
        }
        else if (S.showAccountValidationFailed) {
          AccountValidationFailed(AccountValidationFailed.Props(B.accountValidationFailed))
        }
        else {
          // Show the Log In modal by default
          LoginForm(LoginForm.Props(B.loginUser))
          //  Seq.empty[ReactElement]
        }
      )
    })
    .build

  def apply(props: Props) = component(props)
}
