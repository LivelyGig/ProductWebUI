package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components._
import synereo.client.components.GlobalStyles
import synereo.client.css.{SynereoCommanStylesCSS}
import synereo.client.handlers.LogoutUser
import synereo.client.services.SYNEREOCircuit
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import diode.AnyAction._

/**
  * Created by mandar.k on 4/13/2016.
  */
//scalastyle:off
object ServerErrorModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback /*, proxy: ModelProxy[AppRootModel]*/)

  case class State()

  class Backend(t: BackendScope[Props, State]) {
    def closeForm = Callback {
      SYNEREOCircuit.dispatch(LogoutUser())
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
          header = hide => <.div(^.className:="model-title ",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)(headerText),

          closed = () => t.backend.modalClosed(S, P)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row ",SynereoCommanStylesCSS.Style.modalBodyFontSize)(
                <.div()(
                  <.div(SynereoCommanStylesCSS.Style.loginErrorHeading)(
                    "Encountering problems in serving request: ERR_CONNECTION_REFUSED. Check the server availability."
                  )
                ),
                <.div(bss.modal.footer)(
                  <.button(^.tpe := "button", ^.className := "btn btn-default",SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.closeForm)("Close")
                )
              )
            )
          )
        )
      )
    })
    // .shouldComponentUpdate(scope => scope.currentProps.proxy().isServerError)
    .build

  def apply(props: Props) = component(props)
}

