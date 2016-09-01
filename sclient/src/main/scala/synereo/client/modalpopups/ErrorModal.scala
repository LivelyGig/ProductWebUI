package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css.SynereoCommanStylesCSS

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 2016-07-29.
  */
object ErrorModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, loginError: String = "")

  case class State()

  class Backend(t: BackendScope[Props, State]) {
    def closeForm = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

  }

  private val component = ReactComponentB[Props]("ErrorModal")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
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
                        <.h5("We are encountering problems in serving your request. Please try after sometime."),
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

