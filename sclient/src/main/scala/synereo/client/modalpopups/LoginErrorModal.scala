package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components._
import synereo.client.components.GlobalStyles
import synereo.client.css.{LoginCSS, SynereoCommanStylesCSS}
import synereo.client.modalpopupbackends.LoginErrorBackend

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._

/**
 * Created by mandar.k on 4/13/2016.
 */
object LoginErrorModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, loginError: String = "")

  case class State(showLogin : Boolean = false)



  private val component = ReactComponentB[Props]("ErrorModal")
    .initialState_P(p => State())
    .backend(new LoginErrorBackend(_))
      .renderPS((t,P,S)=>{
        val headerText = "Error"
        Modal(
          Modal.Props(
            // header contains a cancel button (X)
            header = hide => <.span(<.h4(headerText)),

            closed = () => t.backend.modalClosed(S, P)
          ),

          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.h3(SynereoCommanStylesCSS.Style.loginErrorHeading)(P.loginError)
                ),
                <.div(bss.modal.footer, SynereoCommanStylesCSS.Style.errorModalFooter)(
                  <.div(^.className := "row")(
                    <.div(^.className := "col-md-12 text-center")(
                      <.div()(
                        <.h5("The Api host you provided is unreachable, Please provide a new one!"),
                        <.button(^.tpe := "button", ^.className := "btn btn-default", ^.onClick --> t.backend.closeForm)("Close")
                      )
                    )
                  )
                )
              )
            )
          )
        )

      })
    .build

  def apply(props: Props) = component(props)
}

