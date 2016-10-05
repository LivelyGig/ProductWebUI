package synereo.client.modalpopups

import diode.ModelR
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.GlobalStyles
import synereo.client.css.SynereoCommanStylesCSS

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}
import scala.scalajs.js

/**
  * Created by mandar.k on 4/13/2016.
  */
object LoginErrorModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, loginError: String = "")

  case class State(showLogin: Boolean = false,
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)


  class LoginErrorBackend(t: BackendScope[Props, State]) {
    def closeForm = {
      jQuery(t.getDOMNode()).modal("hide")
      t.modState(s => s.copy(showLogin = true))
    }

    def modalClosed(state: LoginErrorModal.State, props: LoginErrorModal.Props): Callback = {
      props.submitHandler(state.showLogin)
    }

    def mounted(): Callback = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

  }


  private val component = ReactComponentB[Props]("ErrorModal")
    .initialState_P(p => State())
    .backend(new LoginErrorBackend(_))
    .renderPS((t, props, state) => {
      val headerText = state.lang.selectDynamic("ERROR").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.h4(headerText)),

          closed = () => t.backend.modalClosed(state, props)
        ),

        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div()(
                <.h3(SynereoCommanStylesCSS.Style.loginErrorHeading)(props.loginError)
              ),
              <.div(bss.modal.footer, SynereoCommanStylesCSS.Style.errorModalFooter)(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 text-center")(
                    <.div()(
                      <.h5(state.lang.selectDynamic("API_HOST_UNREACHABLE").toString),
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
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = component(props)
}

