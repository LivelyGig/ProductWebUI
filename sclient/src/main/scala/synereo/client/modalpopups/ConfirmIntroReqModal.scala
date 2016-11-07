package synereo.client.modalpopups

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.{Button, CommonStyle, _}
import synereo.client.components.{GlobalStyles, _}
import shared.dtos.Introduction
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.css.{DashboardCSS, NewMessageCSS}
import scala.scalajs.js.JSON
import japgolly.scalajs.react
import japgolly.scalajs.react.{Callback, _}
import shared.dtos.IntroConfirmReq
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.services.{RootModel, SYNEREOCircuit}
import diode.AnyAction._
import diode.ModelR
import scala.scalajs.js

/**
  * Created by mandar.k on 6/29/2016.
  */
//scalastyle:off
object ConfirmIntroReqModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), reactChildElement: ReactTag = <.span(), title: String, introduction: Introduction)

  case class State(showNewIntroForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewIntroForm = true))
    }

    def addNewIntroForm(): Callback = {
      t.modState(s => s.copy(showNewIntroForm = true))
    }

    def introConfirmed(): Callback = {
      t.modState(s => s.copy(showNewIntroForm = false))
    }
  }

  val component = ReactComponentB[Props]("ConfirmIntroReqModal")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.addNewIntroForm(), CommonStyle.default, P.addStyles, P.reactChildElement, P.title, className = ""), P.buttonName),
        if (S.showNewIntroForm)
        //          introductionProxy(proxy => ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.postAmplified, "New Intro", proxy)))
          ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.introConfirmed, "New Intro Request", P.introduction))
        else
          Seq.empty[ReactElement]
      )
    })
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object ConfirmIntroReqForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String, introduction: Introduction)

  case class State(confirmIntroReq: Boolean = false, lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  //    toDo: Think of some better logic to reduce verbosity in accept on form submit or reject --> hide like get  event target source and modify only accepted field of case class
  case class ConfirmIntroReqBackend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      val props = t.props.runNow()
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = false)
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal(): Unit = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: ConfirmIntroReqForm.Props): Callback = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      val props = t.props.runNow()
      val content = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = true)
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(content))
      t.modState(s => s.copy(confirmIntroReq = true))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def formClosed(state: ConfirmIntroReqForm.State, props: ConfirmIntroReqForm.Props): Callback = {
      props.submitHandler()
    }
  }


  private val component = ReactComponentB[Props]("ConfirmIntroReqForm")
    .initialState_P(p => State())
    .backend(new ConfirmIntroReqBackend(_))
    .renderPS((t, P, S) => {
      val headerText = P.header
      Modal(
        Modal.Props(
          header = hide => <.div(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(s"${S.lang.selectDynamic("INTRODUCTION_REQUEST").toString}")),
          closed = () => t.backend.formClosed(S, P),
          addStyles = Seq()
        ),
        <.div(^.className := "container-fluid")(
          <.form(^.onSubmit ==> t.backend.submitForm)(
            <.div(^.className := "row", ^.fontSize := "0.8.em")(
              <.div(^.className := "col-md-12")(
                <.div(P.introduction.message),
                <.div(
                  s"From : ${JSON.parse(P.introduction.introProfile).name.asInstanceOf[String]}", <.br,
                  "Date : Mon July 27 2016 ", <.br
                )
              ),
              <.div()(
                <.div(^.className := "text-right")(
                  <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/
                    s"${S.lang.selectDynamic("ACCEPT").toString}"),
                  <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide,
                    s"${S.lang.selectDynamic("REJECT").toString}")
                )
              )
            )
          )
        )
      )

    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.confirmIntroReq) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
