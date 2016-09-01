package synereo.client.modalpopups

import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._

import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import synereo.client.modalpopupbackends.RegistrationFailedBackend

/**
  * Created by bhagyashree.b on 4/19/2016.
  */
object RegistrationFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, registrationErrorMsg: String = "")

  case class State(registrationFailed: Boolean = false)



  private val component = ReactComponentB[Props]("RegistrationFailed")
    .initialState_P(p => State())
    .backend(new RegistrationFailedBackend(_))
      .renderPS((t,P,S)=>{
        val headerText = "Error"

        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.div()(headerText)),

            closed = () => t.backend.modalClosed(S, P), "static", true, addStyles = (Seq(SignupCSS.Style.signUpModalStyle))
          ),

          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.div(LoginCSS.Style.message)(P.registrationErrorMsg),
                  <.div(^.className := "pull-right")(<.button(^.tpe := "button", ^.className := "btn", SignupCSS.Style.SignUpBtn, ^.onClick --> t.backend.hide)("Try again")),
                  <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.marginRight15px)(<.button(^.tpe := "button", ^.className := "btn", SignupCSS.Style.SignUpBtn, ^.onClick --> t.backend.login)("Login"))
                )
              )
            )
          ),
          <.div(bss.modal.footer)()
        )
      })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.registrationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}
