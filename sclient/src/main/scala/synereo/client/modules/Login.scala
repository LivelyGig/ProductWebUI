package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.{MIcon, Icon}
import synereo.client.css.LoginCSS
import synereo.client.modalpopups.RequestInvite

import scalacss.Color
import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 3/11/2016.
  */
object Login {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P { ctr =>
      <.div(^.className := "container-fluid")(
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
                      <.form(^.role := "form")(
                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "text", ^.placeholder := "User Name", LoginCSS.Style.inputStyleLoginForm), <.br()
                        ),
                        <.div(^.className := "form-group", LoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "Password", ^.placeholder := "Password", LoginCSS.Style.inputStyleLoginForm),
                          <.span(MIcon.playCircleOutline, LoginCSS.Style.iconStylePasswordInputBox)
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
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)
}
