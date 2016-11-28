package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS, SynereoCommanStylesCSS}

import scalajs.js
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
object AccountValidationFailed {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class AccountValidationFailedBacked(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted() = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def formClosed(state: AccountValidationFailed.State, props: AccountValidationFailed.Props): Callback = {
      props.submitHandler()
    }
  }


  private val component = ReactComponentB[Props]("AccountValidationFailed")
    .initialState_P(p => State())
    .backend(new AccountValidationFailedBacked(_))
    .renderPS((t, props, state) => {
      val headerText = state.lang.selectDynamic("ERROR").toString
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span( <.div(^.className:="model-title",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)(headerText) ),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row",SynereoCommanStylesCSS.Style.modalBodyFontSize)(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div()(
                <.div()( state.lang.selectDynamic("VALIDATION_CODE_IS_INCORRECT").toString)
              ),
              <.div(bss.modal.footer)(
                <.button(^.tpe := "button", SynereoCommanStylesCSS.Style.modalFooterBtn, ^.className := "btn", ^.onClick --> t.backend.hide, state.lang.selectDynamic("TRY_AGAIN").toString)
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
