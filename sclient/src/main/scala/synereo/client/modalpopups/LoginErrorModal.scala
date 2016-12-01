package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
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
//scalastyle:off
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

    def updateLang(reader: ModelRO[js.Dynamic]) = {
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
          header = hide => <.div(^.className:="model-title",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)(headerText),

          closed = () => t.backend.modalClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row" ,SynereoCommanStylesCSS.Style.modalBodyFontSize)(
                <.div()(
                  <.div(SynereoCommanStylesCSS.Style.loginErrorHeading)(props.loginError)
                ),
                <.div(SynereoCommanStylesCSS.Style.errorModalFooter)(
                  <.div(^.className := "row" ,SynereoCommanStylesCSS.Style.modalBodyFontSize)(
                    <.div(^.className := "col-md-12 text-center")(
                      <.div()(
                        <.div(state.lang.selectDynamic("API_HOST_UNREACHABLE").toString)
                      )
                    )
                  )
                )
              )
            )
          ),
            <.div(bss.modal.footer)(
          <.button(^.tpe := "button", ^.className := "btn",SynereoCommanStylesCSS.Style.modalFooterBtn,
            ^.onClick --> t.backend.closeForm)(state.lang.selectDynamic("CLOSE").toString)
        )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = component(props)
}

