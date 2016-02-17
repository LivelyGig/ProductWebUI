package livelygig.client.modals

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.Handlers.LoginUser
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, HeaderCSS}
import livelygig.client.logger._
import livelygig.client.models.{EmailValidationModel, UserModel}
import livelygig.client.services.CoreApi._
import livelygig.client.services._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.models.UserModel
import scala.scalajs.js
import scala.scalajs.js.{JSON, Date, UndefOr}
import org.querki.jquery._

object AgentLogin {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props()

  case class State(showNewAgentForm: Boolean = false, showLoginForm: Boolean = false, showValidateForm: Boolean = false,
                   showConfirmAccountCreation: Boolean= false, showAccountValidationSuccess : Boolean =false,
                   showLoginFailed: Boolean = false, showRegistrationFailed: Boolean = false,
                   showErrorModal: Boolean = false, showAccountValidationFailed : Boolean = false , showTermsOfServicesForm : Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addLoginForm() : Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }
    def addNewLoginForm() : Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false , showTermsOfServicesForm : Boolean = false ): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
      if(addNewAgent){
        createUser(userModel).onComplete {
          case Success(s) =>
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
      log.debug(s"Login agentLoginModel: ${userModel}, login: ${login}, showConfirmAccountCreation: ${showConfirmAccountCreation}")
      if (login){
        $("#loginLoader").removeClass("hidden")
       // $("#bodyBackground").addClass("DashBoardCSS.Style.overlay")
        CoreApi.agentLogin(userModel).onComplete {
          case Success(s) =>
            log.debug(s"loginAPISuccessMsg: ${s.msgType}")
            if (s.msgType == ApiResponseMsg.InitializeSessionResponse){
              $("#loginLoader").addClass("hidden")
              $(".dashboard-container").removeClass("hidden")
              $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
              window.localStorage.setItem("sessionURI",s.content.sessionURI.getOrElse(""))
              /*val user = Map("email"->userModel.email,"name"-> s.content.jsonBlob.get("name"),
                "imgSrc"-> s.content.jsonBlob.get("imgSrc"), "isLoggedIn" -> true)*/
              val user = UserModel(email = userModel.email, name = s.content.jsonBlob.get("name"),
                imgSrc = s.content.jsonBlob.get("imgSrc"), isLoggedIn = true)
//              window.sessionStorage.setItem("user", upickle.default.write(user))
              window.sessionStorage.setItem("userEmail", userModel.email)
              window.sessionStorage.setItem("userName", s.content.jsonBlob.get("name"))
              window.sessionStorage.setItem("userImgSrc", s.content.jsonBlob.get("imgSrc"))
              LGCircuit.dispatch(LoginUser(user))
              log.debug("login successful")
//              window.location.href = "/"
            } else {
              $("#loginLoader").addClass("hidden")
            //  $("#bodyBackground").removeClass("overlay")
              log.debug("login failed")
              t.modState(s => s.copy(showLoginFailed = true)).runNow()
            }
          case Failure(s) =>
            $("#loginLoader").addClass("hidden")
            $("#bodyBackground").removeClass("DashBoardCSS.Style.overlay")
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
          case Success(s) =>
            if (s.msgType == ApiResponseMsg.CreateUserError) {
              println(ApiResponseMsg.CreateUserError)
              t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }
            else {
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
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
      t.modState(s => s.copy(showTermsOfServicesForm = false))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      val B = t.backend
      <.div()(
        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn)),"Login"),
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
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}
