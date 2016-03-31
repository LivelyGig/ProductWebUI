package client.modals

//import japgolly.scalajs.react.extra.OnUnmount
import client.handlers.{RefreshConnections, CreateSessionForMessages, CreateLabels, LoginUser}
import client.components.Bootstrap._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import client.logger._
import client.models.{EmailValidationModel, UserModel}
import client.services.CoreApi._
import client.services._
import shared.dtos._
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.models.UserModel
import scala.scalajs.js.{JSON}
import org.querki.jquery.$

object AgentLoginSignUp {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props()

  case class State(showNewAgentForm: Boolean = false, showLoginForm: Boolean = false, showValidateForm: Boolean = false,
                   showConfirmAccountCreation: Boolean= false, showAccountValidationSuccess : Boolean =false,
                   showLoginFailed: Boolean = false, showRegistrationFailed: Boolean = false,
                   showErrorModal: Boolean = false, showAccountValidationFailed : Boolean = false , showTermsOfServicesForm : Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) /*extends OnUnmount*/ {
  }

  case class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addLoginForm() : Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addNewAgentForm() : Callback = {
      t.modState(s => s.copy(showNewAgentForm = true))
    }

    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false , showTermsOfServicesForm : Boolean = false ): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
      if(addNewAgent){
        createUser(userModel).onComplete {
          case Success(response) =>
            val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
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
      }  else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showNewAgentForm = false, showTermsOfServicesForm = true ))
      } else {
        t.modState(s => s.copy(showNewAgentForm = false))
      }
    }

    def Login(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false,
              showNewAgentForm: Boolean = false) : Callback = {
//      log.debug(s"Login agentLoginModel: ${userModel}, login: ${login}, showConfirmAccountCreation: ${showConfirmAccountCreation}")
      if (login){
        val loginLoader:js.Object = "#loginLoader"
        val dashboardContainer:js.Object = ".dashboard-container"

        $(loginLoader).removeClass("hidden")
       // $("#bodyBackground").addClass("DashBoardCSS.Style.overlay")
        CoreApi.agentLogin(userModel).onComplete {
//          case Success(s) =>
          case Success(responseStr) =>
         //   val responseError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
             try {
               log.debug("login successful")
               val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
               $(loginLoader).addClass("hidden")
               $(dashboardContainer).removeClass("hidden")
               //$("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
               window.sessionStorage.setItem(CoreApi.CONNECTIONS_SESSION_URI,response.content.sessionURI)
                val user = UserModel(email = userModel.email, name = response.content.jsonBlob.getOrElse("name",""),
                 imgSrc = response.content.jsonBlob.getOrElse("imgSrc",""), isLoggedIn = true)
               window.sessionStorage.setItem("userEmail", userModel.email)
               window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name",""))
               window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc",""))
               window.sessionStorage.setItem("listOfLabels", JSON.stringify(response.content.listOfLabels))
               LGCircuit.dispatch(LoginUser(user))
//               println(userModel)
               LGCircuit.dispatch(CreateSessionForMessages(userModel))
               LGCircuit.dispatch(CreateLabels())
               LGCircuit.dispatch(RefreshConnections())
               window.location.href = "/#dashboard"


            } catch {
              case e: Exception  =>
                log.debug("login failed")
                $(loginLoader).addClass("hidden")
                t.modState(s => s.copy(showLoginFailed = true)).runNow()
            }
          case Failure(s) =>
            $(loginLoader).addClass("hidden")
          //  $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
            println("internal server error")
            t.modState(s => s.copy(showErrorModal = true)).runNow()
        }
        t.modState(s => s.copy(showLoginForm = false))
      }
      else if (showConfirmAccountCreation) {
        t.modState(s => s.copy(showLoginForm = false, showConfirmAccountCreation = true))
      }
      else if (showNewAgentForm) {
        t.modState(s => s.copy(showLoginForm = false, showNewAgentForm = true))
      }
      else {
        t.modState(s => s.copy(showLoginForm = false))
      }
    }
    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false) : Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
             try {
              val response = upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
              println(ApiResponseMsg.CreateUserError)
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
            } catch {
              case e: Exception  =>
                t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
                }

          case Failure(s) =>
            log.debug(s"ConfirmAccontCreationAPI failure: ${s.getMessage}")
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
    def loginFailed() : Callback = {
        t.modState(s => s.copy(showLoginFailed = false, showLoginForm = true))
    }
    def registrationFailed(registrationFailed : Boolean = false) : Callback = {
      if (registrationFailed){
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showNewAgentForm = true))
      }
    }
    def serverError() : Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }
    def accountValidationFailed() : Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }
    def termsOfServices() : Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showNewAgentForm = true))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      val B = t.backend
      <.div()(
        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn),"",""),"Login"),
        Button(Button.Props(B.addNewAgentForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn),"",""),"Sign Up"),
//        <.button(^.className:="btn btn-default",^.tpe := "button", ^.onClick --> P.proxy.dispatch(LoginUser(P.proxy.value)),
//          HeaderCSS.Style.SignUpBtn)("Login"),
        if (S.showNewAgentForm) NewAgentForm(NewAgentForm.Props(B.addNewAgent))
        else if (S.showTermsOfServicesForm) TermsOfServices(TermsOfServices.Props(B.termsOfServices))
        else if (S.showLoginForm) LoginForm(LoginForm.Props(B.Login))
        else if (S.showConfirmAccountCreation) ConfirmAccountCreation(ConfirmAccountCreation.Props(B.confirmAccountCreation))
        else if (S.showAccountValidationSuccess) AccountValidationSuccess(AccountValidationSuccess.Props(B.accountValidationSuccess))
        else if (S.showLoginFailed) LoginFailed(LoginFailed.Props(B.loginFailed))
        else if (S.showRegistrationFailed) RegistrationFailed(RegistrationFailed.Props(B.registrationFailed))
        else if (S.showErrorModal) ErrorModal(ErrorModal.Props(B.serverError))
        else if (S.showAccountValidationFailed) AccountValidationFailed(AccountValidationFailed.Props(B.accountValidationFailed))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
  //  .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}
