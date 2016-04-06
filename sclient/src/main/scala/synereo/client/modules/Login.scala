package synereo.client.modules

import japgolly.scalajs.react._
import org.scalajs.dom.window
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos.{CreateUser, InitializeSessionResponse, ApiResponse}
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
import org.querki.jquery.$

/**
  * Created by Mandar on 3/11/2016.
  */
object Login {

  case class Props()

  case class State(userModel: UserModel, login: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def updateEmail(e: ReactEventI) = {
      println("updateEmail = " + e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def updatePassword(e: ReactEventI) = {
      println("updatePassword = " + e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    def login(userModel: UserModel) = {
      CoreApi.agentLogin(userModel).onComplete {
        case Success(responseStr) =>
          try {
           // log.debug("login successful")
            val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
            println("the response is := " + response)
            window.sessionStorage.setItem(CoreApi.CONNECTIONS_SESSION_URI, response.content.sessionURI)
            val user = UserModel(email = userModel.email, name = response.content.jsonBlob.getOrElse("name", ""),
              imgSrc = response.content.jsonBlob.getOrElse("imgSrc", ""), isLoggedIn = true)
            window.sessionStorage.setItem("userEmail", userModel.email)
            window.sessionStorage.setItem("userName", response.content.jsonBlob.getOrElse("name", ""))
            window.sessionStorage.setItem("userImgSrc", response.content.jsonBlob.getOrElse("imgSrc", ""))
            window.sessionStorage.setItem("listOfLabels", js.JSON.stringify(response.content.listOfLabels))
          //  SYNEREOCircuit.dispatch()
            window.location.href = "/#synereodashboard"
            //  t.modState(s => s.copy(userModel = s.userModel.copy()))
          } catch {
            case e: Exception =>
             /* log.debug("login failed")*/
              e.printStackTrace()
          }
        case Failure(s) =>
          println("internal server error")
      }
      //      window.location.href = "/#synereodashboard"
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
                <.img(LoginCSS.Style.loginFormImg, ^.src := "./assets/images/Synereo-logo.png"),
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
                            <.input(^.`type` := "radio"), "Keep me logged in"
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
            RequestInvite(RequestInvite.Props(Seq(LoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")
            )
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
