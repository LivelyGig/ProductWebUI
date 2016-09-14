package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import synereo.client.components.GlobalStyles
import synereo.client.css.LoginCSS
import synereo.client.modalpopups._
import scala.scalajs.js
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import diode.AnyAction._
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos._
import shared.models.{EmailValidationModel, SignUpModel, UserModel}
import synereo.client.handlers._
import synereo.client.logger
import synereo.client.logger._
import synereo.client.services.CoreApi._
import synereo.client.services.{ApiTypes, CoreApi, SYNEREOCircuit}
import synereo.client.utils.{AppUtils, ContentUtils}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.util.{Failure, Success}

/**
  * Created by mandar.k on 3/11/2016.
  */
//scalastyle:off
//case class ApiDetails(apiURL: String = "", hostName: String = "", portNumber: String = "")

object LoginView {

  val LOGIN_ERROR = "LOGIN_ERROR"
  val SERVER_ERROR = "SERVER_ERROR"
  val SUCCESS = "SUCCESS"
  val loginLoader: js.Object = "#loginLoader"
  val loadingScreen: js.Object = "#loadingScreen"
  var isUserVerified = false

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props()

  case class State(showNewUserForm: Boolean = false,
                   showLoginForm: Boolean = false,
                   showConfirmAccountCreation: Boolean = false,
                   showAccountValidationSuccess: Boolean = false,
                   showLoginFailed: Boolean = false,
                   showRegistrationFailed: Boolean = false,
                   showErrorModal: Boolean = false,
                   showAccountValidationFailed: Boolean = false,
                   loginErrorMessage: String = "",
                   showNewInviteForm: Boolean = false, registrationErrorMsg: String = "" )


  class LoginViewBackend(t: BackendScope[Props, State]) {

    val LOGIN_ERROR = "LOGIN_ERROR"
    val SERVER_ERROR = "SERVER_ERROR"
    val SUCCESS = "SUCCESS"
    val loginLoader: js.Object = "#loginLoader"
    val loadingScreen: js.Object = "#loadingScreen"
    var isUserVerified = false

    def mounted(props: LoginView.Props): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    //    def addLoginForm(): Callback = {
    //      t.modState(s => s.copy(showLoginForm = true))
    //    }

    //    def addNewAgentForm(): Callback = {
    //      t.modState(s => s.copy(showNewUserForm = true))
    //    }

    def addNewUser(signUpModel: SignUpModel, addNewAgent: Boolean = false, showLoginForm: Boolean = false): Callback = {
      $(loadingScreen).removeClass("hidden")
      $(loginLoader).removeClass("hidden")
      //      log.debug(s"addNewUser userModel : ${signUpModel} ,addNewUser: ${addNewAgent}")
      if (addNewAgent) {
        createUser(signUpModel).onComplete {
          case Success(response) =>
            try {
              val s = upickle.default.read[ApiResponse[CreateUserResponse]](response)
              log.debug(s"createUser msg : ${s.msgType}")
              if (s.msgType == ApiTypes.responseTypes.CREATE_USER_WAITING) {
                t.modState(s => s.copy(showConfirmAccountCreation = true)).runNow()
                $(loginLoader).addClass("hidden")
                $(loadingScreen).addClass("hidden")
              } else if (s.msgType == ApiTypes.responseTypes.CREATE_USER_ERROR) {
                val errorResponse = upickle.default.read[ApiResponse[CreateUserError]](response)
                //              val msg = JSON.parse(errorResponse.content.reason).reason.asInstanceOf[String]
                t.modState(s => s.copy(showRegistrationFailed = true, registrationErrorMsg = errorResponse.content.reason)).runNow()
                $(loginLoader).addClass("hidden")
                $(loadingScreen).addClass("hidden")
              } else if (s.msgType == ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR) {
                processServerError(s.msgType)
              }
              else {
                log.debug(s"createUser msg : ${s.content}")
                t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
              }
            } catch {
              case e: Exception =>
                val error = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
                processServerError(error.msgType)

            }
          case Failure(res) =>
            log.debug(s"createUserFailure: ${res}")
            t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = res.getMessage)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showNewUserForm = false))
      } else if (showLoginForm) {
        t.modState(s => s.copy(showNewUserForm = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showNewUserForm = false, showLoginForm = true))
      }
    }

    def validateInitializeSessionResponse(response: String): String = {
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


    def processLogin(userModel: UserModel): Callback = {
      $(loginLoader).removeClass("hidden")
      $(loadingScreen).removeClass("hidden")
      CoreApi.agentLogin(userModel).onComplete {
        case Success(response) =>
          validateInitializeSessionResponse(response) match {
            case SUCCESS => processSuccessfulLogin(response, userModel)
            case LOGIN_ERROR => processLoginFailed(response)
            case SERVER_ERROR =>
              val error = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
              processServerError(error.msgType)
          }
        case Failure(res) =>
          println("res = " + res.getMessage)
          processServerError(res.getMessage)
      }
      t.modState(s => s.copy(showLoginForm = false, showConfirmAccountCreation = false))
    }


    def processSuccessfulLogin(responseStr: String, userModel: UserModel): Unit = {
      val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
      SYNEREOCircuit.dispatch(SetSessionUri(response.content.sessionURI))
      CoreApi.sessionPing(response.content.sessionURI).onComplete {
        case Success(res) =>
          SYNEREOCircuit.dispatch(LoginUser(UserModel(name = response.content.jsonBlob.getOrElse("name", ""),
            imgSrc = response.content.jsonBlob.getOrElse("imgSrc", ""), isLoggedIn = true, email = userModel.email, balance = response.content.balance, address = response.content.address /*, sessionUri = response.content.sessionURI*/)))
          ContentUtils.processRes(res)
          SYNEREOCircuit.dispatch(CreateLabels(response.content.listOfLabels))
//          SYNEREOCircuit.dispatch(AttachPinger())
          ContentUtils.subsForMsgAndBeginSessionPing()
          $(loginLoader).addClass("hidden")
          $(loadingScreen).addClass("hidden")
          window.location.replace("#dashboard")
          log.debug("login successful")
        case Failure(res) =>
          console.log(s"login failure : ${res.getMessage}")
          processServerError(res.getMessage)
      }
    }


    def processLoginFailed(responseStr: String): Unit = {
      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)

      $(loginLoader).addClass("hidden")
      println("login error")
      t.modState(s => s.copy(showLoginFailed = true, loginErrorMessage = loginError.content.reason)).runNow()
    }

    def processServerError(responseStr: String): Unit = {
      //      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      $(loginLoader).addClass("hidden")
      logger.log.error(s"internal server error  ${responseStr}")
      t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = responseStr)).runNow()
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

    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false, showLoginForm: Boolean = false): Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(responseStr) =>
            try {
              upickle.default.read[ApiResponse[ConfirmEmailResponse]](responseStr)
              //              isUserVerified = true changed to false to make toastr hidden
              isUserVerified = false
              log.debug(ApiTypes.responseTypes.CREATE_USER_ERROR)
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
            } catch {
              case e: Exception =>
                t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }

          case Failure(res) =>
            log.debug(s"ConfirmAccountCreationAPI failure: ${res.getMessage}")
            t.modState(s => s.copy(showErrorModal = true, loginErrorMessage = res.toString)).runNow()
        }
        t.modState(s => s.copy(showConfirmAccountCreation = false))
      } else if (showLoginForm) {
        t.modState(s => s.copy(showLoginForm = true))
      }
      else {
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

    def serverError(showLogin: Boolean = false): Callback = {
      $(loginLoader).addClass("hidden")
      $(loadingScreen).addClass("hidden")
      if (showLogin)
        t.modState(s => s.copy(showErrorModal = false, showLoginForm = true))
      else
        t.modState(s => s.copy(showErrorModal = false, showLoginForm = false))
    }

    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showConfirmAccountCreation = true))
    }

    //    def termsOfServices(): Callback = {
    //      t.modState(s => s.copy(showTermsOfServicesForm = false, showNewUserForm = true))
    //    }

    def submitApiForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      //      window.sessionStorage.setItem(SessionItems.ApiDetails.API_HOST, state.hostName)
      //      window.sessionStorage.setItem(SessionItems.ApiDetails.API_PORT, state.portNumber)
      t.modState(s => s.copy(showLoginForm = true))
    }
  }


  val component = ReactComponentB[Props]("NotificationView")
    .initialState(State())
    .backend(new LoginViewBackend(_))
    .renderPS((t, P, s) => {
      <.div(^.className := "container-fluid", LoginCSS.Style.loginPageContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12")(
            //            <.img(^.src := "./assets/synereo-images/LogInBox.png", ^.className := "img-responsive", LoginCSS.Style.loginScreenBgImage)
            //            <.div(LoginCSS.Style.loginContainer)(
            //
            //
            //          )
            //            <.div(LoginCSS.Style.loginDilog)(
            //              <.div(LoginCSS.Style.formPadding)(
            //                <.div(LoginCSS.Style.loginDilogContainerDiv)(
            //                  <.div(^.className := "row")(
            //                    <.div(^.className := "col-md-12")(
            //                      <.div(LoginCSS.Style.loginFormContainerDiv)(
            //                        <.h1(^.className := "text-center", LoginCSS.Style.textWhite)("API DETAILS"),
            //                        <.form(^.role := "form", ^.onSubmit ==> submitApiForm)(
            //                          <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
            //                            <.div(^.className := "row")(
            //                              <.div(^.className := "col-md-4")(
            //                                <.label(LoginCSS.Style.loginFormLabel)("Host-ip")
            //                              ),
            //                              <.div(^.className := "col-md-8")(
            //                                <.input(^.`type` := "text", ^.placeholder := "Host-ip", LoginCSS.Style.inputStyleLoginForm,
            //                                  ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true)
            //                              )
            //                            )
            //                          ),
            //                          <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
            //                            <.div(^.className := "row")(
            //                              <.div(^.className := "col-md-4")(
            //                                <.label(LoginCSS.Style.loginFormLabel)("Port Number")
            //                              ),
            //                              <.div(^.className := "col-md-8")(
            //                                <.input(^.tpe := "text", ^.placeholder := "Port Number", LoginCSS.Style.inputStyleLoginForm,
            //                                  ^.value := s.portNumber, ^.onChange ==> updatePort, ^.required := true)
            //                              )
            //                            )
            //                          ),
            //                          <.div(^.className := "col-md-12 text-right")(
            //                            <.button(^.tpe := "submit", ^.id := "LoginBtn", LoginCSS.Style.apiSubmitBtn, ^.className := "btn", "Submit")
            //                          )
            //                        )
            //                      )
            //                    )
            //                  )
            //                )
            //              )
            //            )

          ),
          <.div()(
            if (s.showNewUserForm) {
              NewUserForm(NewUserForm.Props(t.backend.addNewUser))
            }
            else if (s.showNewInviteForm) {
              PostNewInvite(PostNewInvite.Props(t.backend.closeRequestInvitePopup))
            }
            else if (s.showLoginForm) {
              LoginForm(LoginForm.Props(t.backend.loginUser, isUserVerified))
            }
            else if (s.showConfirmAccountCreation) {
              VerifyTokenModal(VerifyTokenModal.Props(t.backend.confirmAccountCreation))
            }
            else if (s.showAccountValidationSuccess) {
              AccountValidationSuccess(AccountValidationSuccess.Props(t.backend.accountValidationSuccess))
            }
            else if (s.showLoginFailed) {
              LoginFailed(LoginFailed.Props(t.backend.loginFailed, s.loginErrorMessage))
            }
            else if (s.showRegistrationFailed) {
              RegistrationFailed(RegistrationFailed.Props(t.backend.registrationFailed, s.registrationErrorMsg))
            }
            else if (s.showErrorModal) {
              LoginErrorModal(LoginErrorModal.Props(t.backend.serverError, s.loginErrorMessage))
            }
            else if (s.showAccountValidationFailed) {
              AccountValidationFailed(AccountValidationFailed.Props(t.backend.accountValidationFailed))
            }
            else {
              Seq.empty[ReactElement]
            }
          )
        )
      )

    })
    .componentWillMount(scope =>
      scope.backend.mounted(scope.props)
    )
    .componentDidMount(scope => Callback {
      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
    })
    .build

  def apply(props: Props) = component(props)
}
