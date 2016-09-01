package synereo.client.modalpopups

/**
  * Created by mandar.k on 6/10/2016.
  */

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}

import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import synereo.client.modalpopupbackends.LoginFailedBackend

object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, loginErrorMessage: String = "")

  case class State()

  private val component = ReactComponentB[Props]("LoginFailed")
    .initialState_P(p => State())
    .backend(new LoginFailedBackend(_))
      .renderPS((t,P,S)=>{
        val headerText = "Login Failed"
        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.div()(headerText)),
            closed = () => t.backend.formClosed(S, P)
          ),

          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.h3()(
                    P.loginErrorMessage,/* "The username and password combination that you are using is not correct. Please check and try again."*/
                    <.div()(<.button(^.tpe := "button", ^.className := "btn", ^.onClick --> t.backend.hide, LoginCSS.Style.modalLoginBtn,^.marginBottom:="20.px")("Try again"))
                  )
                )
              )
            )
          ),
          <.div(bss.modal.footer)()
        )
      })
    .build

  def apply(props: Props) = component(props)
}