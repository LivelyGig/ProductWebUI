package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import synereo.client.components.GlobalStyles
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.SignupCSS

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import synereo.client.services.{RootModel, SYNEREOCircuit}

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 4/19/2016.
  */


object AccountValidationSuccess {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class AccountValidationSuccessBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted(props: Props) = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))

    }

    def formClosed(state: AccountValidationSuccess.State, props: AccountValidationSuccess.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }
  }

  private val component = ReactComponentB[Props]("AccountValidationSuccess")
    .initialState_P(p => State())
    .backend(new AccountValidationSuccessBackend(_))
    .renderPS((t, props, state) => {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = s"${state.lang.selectDynamic("ACCOUNT_VALIDATION_SUCCESS").toString}"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.div(SignupCSS.Style.accountValidationSuccessText)(headerText),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row")(
                <.div()(
                  <.div(^.className := "pull-right")(
                    <.button(^.tpe := "button", SignupCSS.Style.signUpBtn,
                      ^.className := "btn", ^.onClick --> t.backend.hide, state.lang.selectDynamic("LOGIN").toString)
                  )
                )
              )
            )
          ),
          <.div(bss.modal.footer)()
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
