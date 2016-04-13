package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.{RouterCtl, Resolution}
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos.{InitializeSessionErrorResponse, CreateUser, InitializeSessionResponse, ApiResponse}
import synereo.client.Handlers.{CreateLabels, LoginUser}
import synereo.client.SYNEREOMain.Loc
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.LoginCSS
import synereo.client.modalpopups.RequestInvite
import synereo.client.models.UserModel
import synereo.client.services.{SYNEREOCircuit, CoreApi}
import scala.scalajs.js
import js.{Date, UndefOr}
import scala.util.parsing.json.JSON
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

  case class Props()

  case class State(userModel: UserModel, login: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def updateEmail(e: ReactEventI) = {
      //      println("updateEmail = " + e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      //      println("updatePassword = " + e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
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

    def processLoginFailed(responseStr: String) = {
      println("in processLoginFailed")
      $(loginLoader).removeClass("hidden")
      val loginError = upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](responseStr)
      window.alert("please enter valid credentials")
//      val inputs = window.document.getElementsByTagName("input")
    }

    def processServerError() = {
      println("in processServerError")
      window.alert("Server Error Occoured")
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

    def processLogin(userModel: UserModel): Unit = {
      $(loginLoader).removeClass("hidden")
      CoreApi.agentLogin(userModel).onComplete {
        case Success(responseStr) =>
          validateResponse(responseStr) match {
            case SUCCESS => processSuccessfulLogin(responseStr, userModel)
            case LOGIN_ERROR => processLoginFailed(responseStr)
            case SERVER_ERROR => processServerError()
          }
        case Failure(s) =>
          println("internal server error")
          window.alert("internal server error")
      }

    }

    def login(userModel: UserModel) = {
      val user = UserModel(email = userModel.email, name = "", imgSrc = "", isLoggedIn = true, password = userModel.password)
      processLogin(user)
    }

    def loginUser(e: ReactEventI) = Callback {
      e.preventDefault()
      val user = t.state.runNow().userModel
      login(user)
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
              <.a(^.href := "#", "Dont have an account?", LoginCSS.Style.dontHaveAccount)
            ),
            //   <.button(^.className := "btn text-center", "",),
            /* NewMessage(NewMessage.Props("Request invite", Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")),*/
            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite"))
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
