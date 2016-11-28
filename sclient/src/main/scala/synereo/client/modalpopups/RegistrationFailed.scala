package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import synereo.client.components.GlobalStyles
import synereo.client.css.{LoginCSS, SignupCSS, SynereoCommanStylesCSS}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 4/19/2016.
  */
//scalastyle:off
object RegistrationFailed {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, registrationErrorMsg: String = "")

  case class State(registrationFailed: Boolean = false, lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class RegistrationFailedBackend(t: BackendScope[Props, State]) {

    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted(props: Props) = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def login(): Callback = {
      jQuery(t.getDOMNode()).modal("hide")
      t.modState(s => s.copy(registrationFailed = true))
    }

    def modalClosed(state: RegistrationFailed.State, props: RegistrationFailed.Props): Callback = {
      props.submitHandler(state.registrationFailed)
    }
  }

  private val component = ReactComponentB[Props]("RegistrationFailed")
    .initialState_P(p => State())
    .backend(new RegistrationFailedBackend(_))
    .renderPS((t, props, state) => {
      val headerText = state.lang.selectDynamic("REGISTRATION_FAILED").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.div(^.className:="model-title ",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)(headerText),
          closed = () => t.backend.modalClosed(state, props), "static", true, addStyles = (Seq(SignupCSS.Style.signUpModalStyle))
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.div(LoginCSS.Style.message)(props.registrationErrorMsg)
                )
              )
            )
          ),
          <.div(bss.modal.footer)(
            <.button(^.tpe := "button", ^.className := "btn",SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.hide)("Try again"),
            <.button(^.tpe := "button", ^.className := "btn", SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.login)("Login")
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.registrationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}
