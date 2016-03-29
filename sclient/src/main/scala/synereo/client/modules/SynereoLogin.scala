package synereo.client.modules

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain.Loc
import synereo.client.components.Icon
import synereo.client.css.SynereoLoginCSS
import synereo.client.modalpopups.RequestInvite

import scalacss.ScalaCssReact._

/**
  * Created by Mandar on 3/11/2016.
  */
object SynereoLogin {
  val component = ReactComponentB[RouterCtl[Loc]]("Dashboard")
    .render_P { ctr =>
      <.div(^.className := "container-fluid")(
        <.div(^.className := "row")(
          <.div(SynereoLoginCSS.Style.loginDilog)(
            <.div(SynereoLoginCSS.Style.formPadding)(
              <.div(SynereoLoginCSS.Style.loginDilogContainerDiv)(
                <.img(SynereoLoginCSS.Style.loginFormImg, ^.src := "./assets/images/Synereo-logo.png"),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12")(
                    <.div(SynereoLoginCSS.Style.loginFormContainerDiv)(
                      <.h1(^.className := "text-center", SynereoLoginCSS.Style.textWhite)("Log in"),
                      <.h3(^.className := "text-center", SynereoLoginCSS.Style.textBlue)("Social Self-determination"),
                      <.form(^.role := "form")(
                        <.div(^.className := "form-group", SynereoLoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "text", ^.placeholder := "User Name", SynereoLoginCSS.Style.inputStyleLoginForm), <.br()
                        ),
                        <.div(^.className := "form-group", SynereoLoginCSS.Style.inputFormLoginForm)(
                          <.input(^.`type` := "Password", ^.placeholder := "Password", SynereoLoginCSS.Style.inputStyleLoginForm)
                        ),
                        <.div(^.className := "col-md-12", SynereoLoginCSS.Style.loginFormFooter)(
                          <.div( SynereoLoginCSS.Style.keepMeLoggedIn)(
                            <.input(^.`type` := "radio"), "Keep me logged in"
                          ),
                          <.a(^.href := "#", "Forgot Your Password?", SynereoLoginCSS.Style.forgotMyPassLink)
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
              <.a(^.href := "#", "Dont have an account?", SynereoLoginCSS.Style.dontHaveAccount)
            ),
            //   <.button(^.className := "btn text-center", "",),
           /* NewMessage(NewMessage.Props("Request invite", Seq(SynereoLoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")),*/
            RequestInvite(RequestInvite.Props(Seq(SynereoLoginCSS.Style.requestInviteBtn), Icon.mailForward, "Request invite")
            )
          )
        )
      )
    }.build

  def apply(router: RouterCtl[Loc]) = component(router)
}
