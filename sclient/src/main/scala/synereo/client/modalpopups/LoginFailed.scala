package synereo.client.modalpopups

/**
  * Created by mandar.k on 6/10/2016.
  */

import diode.ModelR
import synereo.client.components.GlobalStyles
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.LoginCSS

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}

import scala.scalajs.js

//scalastyle:off
object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, loginErrorMessage: String = "")

  case class State(lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class LoginFailedBackend(t: BackendScope[Props, State]) {

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(): Callback = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def formClosed(state: LoginFailed.State, props: LoginFailed.Props): Callback = {
      props.submitHandler()
    }

  }

  private val component = ReactComponentB[Props]("LoginFailed")
    .initialState_P(p => State())
    .backend(new LoginFailedBackend(_))
    .renderPS((t, props, state) => {
      val headerText = state.lang.selectDynamic("ERROR").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.h4()(headerText),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.h3()(
                    props.loginErrorMessage,
                    <.div(<.button(^.tpe := "button", ^.className := "btn",
                      ^.onClick --> t.backend.hide, LoginCSS.Style.modalTryAgainBtn)(state.lang.selectDynamic("TRY_AGAIN").toString))
                  )
                )
              )
            )
          ),
          <.div(bss.modal.footer)()
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = component(props)
}
