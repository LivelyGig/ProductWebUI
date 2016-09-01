package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import synereo.client.components.GlobalStyles
import synereo.client.css.LoginCSS
import synereo.client.modalpopups._
import synereo.client.utils.{ UserUtils}
import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 3/11/2016.
  */
//scalastyle:off
case class ApiDetails(apiURL: String = "", hostName: String = "", portNumber: String = "")

object Login {

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
                   showAccountValidationFailed: Boolean = false, /*showTermsOfServicesForm: Boolean = false,*/
                   loginErrorMessage: String = "", /*showValidateForm: Boolean = false,*/
                   showNewInviteForm: Boolean = false, registrationErrorMsg: String = "" /*hostName: String = dom.window.location.hostname, portNumber: String = "9876"*/)



  val component = ReactComponentB[Props]("NotificationView")
    .initialState(State())
    .backend(new UserUtils.Backend(_))
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
              VerifyEmailModal(VerifyEmailModal.Props(t.backend.confirmAccountCreation))
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
      //      if (scope.currentProps.proxy().introResponse.length <= 0) {
      //        window.location.href = "/#dashboard"
      //      }
      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $(".modal-backdrop .fade .in".asInstanceOf[js.Object]).removeClass(".modal-backdrop .fade .in")
    })
    .build

  def apply(props: Props) = component(props)
}
