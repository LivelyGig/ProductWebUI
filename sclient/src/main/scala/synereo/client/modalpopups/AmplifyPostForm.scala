package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, _}
import synereo.client.components.Bootstrap.Modal
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css.{DashboardCSS, NewMessageCSS, SynereoCommanStylesCSS}

import scala.language.reflectiveCalls
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import org.querki.jquery._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import synereo.client.services.{RootModel, SYNEREOCircuit}

/**
  * Created by mandar.k on 8/17/2016.
  */
//scalastyle:off
object AmplifyPostModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), /*reactChildElement: ReactTag = <.span(),*/ title: String, modalId: String = "")

  case class State(showAmplifyPostForm: Boolean = false)


  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = true))
    }

    def showAmplifyPostForm(): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = true))
    }

    def postAmplified(amount: String = "", to: String = "", isAmplified: Boolean = false): Callback = {
      t.modState(s => s.copy(showAmplifyPostForm = false))
    }
  }

  val component = ReactComponentB[Props]("AmplifyPostModal")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.showAmplifyPostForm(), CommonStyle.default, P.addStyles, "", P.title, className = "") /*, P.reactChildElement*/),
        if (S.showAmplifyPostForm)
          AmplifyPostForm(AmplifyPostForm.Props(B.postAmplified, P.modalId))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .componentDidUpdate(scope => Callback {})
    .build

  def apply(props: Props) = component(props)
}

object AmplifyPostForm {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (String, String, Boolean) => Callback, senderAddress: String = "", modalId: String = "")

  case class State(isAmplified: Boolean = false, amount: String = "", lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value, AMPCount: Int = 1)

  class AmplifyPostBackend(t: BackendScope[Props, State]) {
    def hideModal = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: AmplifyPostForm.State, props: AmplifyPostForm.Props): Callback = {
      props.submitHandler(state.amount, props.senderAddress, state.isAmplified)
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      t.modState(state => state.copy(isAmplified = true))
    }

    def check(): Boolean = {
      if ($("body".asInstanceOf[js.Object]).hasClass("modal-open"))
        true
      else
        false
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted(props: AmplifyPostForm.Props) = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
      logger.log.debug("AmplifyPostForm mounted")
    }

    def updateAmount(e: ReactEventI) = {
      val amount = e.target.value
      t.modState(state => state.copy(amount = amount))
    }

    def increaseAMPCount(): Callback = {
      val state = t.state.runNow()
      t.modState(state => state.copy(AMPCount = state.AMPCount + 1))
    }

    def decreaseAMPCount(): Callback = {
      val state = t.state.runNow()
      if (state.AMPCount != 0)
        t.modState(state => state.copy(AMPCount = state.AMPCount - 1))
      else
        t.modState(state => state.copy(AMPCount = 0))
    }
  }

  private val component = ReactComponentB[Props]("AmplifyPostForm")
    .initialState_P(p => State())
    .backend(new AmplifyPostBackend(_))
    .renderPS((t, props, state) => {

      val headerText = s"${state.lang.selectDynamic("AMPLIFY").toString}"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide =>  <.div(^.className:="model-title",SynereoCommanStylesCSS.Style.modalHeaderTitle)(headerText),
          closed = () => t.backend.modalClosed(state, props),
          id = props.modalId
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.form(^.id := "AmpForm", ^.role := "form", ^.onSubmit ==> t.backend.submitForm)(
              <.div(^.className := "input-group spinner", NewMessageCSS.Style.spinner,
                <.input(^.`type` := "text", ^.className := "form-control", ^.value := state.AMPCount,^.placeholder :=s"${state.lang.selectDynamic("AMPS_TO_DONATE").toString}"
                  , ^.onChange ==> t.backend.updateAmount),
                <.div(^.className := "input-group-btn-vertical", NewMessageCSS.Style.inputgroupbtnVertical,
                  <.button(^.className := "btn btn-default", NewMessageCSS.Style.spinnerBtn1, ^.`type` := "button",
                    <.i(^.className := "fa fa-caret-up", NewMessageCSS.Style.spinnerCaretIcon, ^.onClick --> t.backend.increaseAMPCount())
                  ),
                  <.button(^.className := "btn btn-default", NewMessageCSS.Style.spinnerBtn2, ^.`type` := "button",
                    <.i(^.className := "fa fa-caret-down", NewMessageCSS.Style.spinnerCaretIcon, ^.onClick --> t.backend.decreaseAMPCount())
                  )
                )
              ),
              <.div(bss.modal.footer)(
                <.button(^.tpe := "submit", ^.className := "btn ",SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.hideModal,
                  s"${state.lang.selectDynamic("AMPLIFY_BTN").toString}"),
                <.button(^.tpe := "button", ^.className := "btn ", SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> t.backend.hideModal,
                  s"${state.lang.selectDynamic("CANCEL_BTN").toString}")
              )
            )
          )
        )
      )
    })
    //    .componentWillMount(scope => scope.backend.mounted(scope.props))
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .shouldComponentUpdate(scope => scope.$.backend.check)
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.isAmplified)
        scope.$.backend.hideModal
    })
    .build

  def apply(props: Props) = component(props)
}
