package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.{Button, CommonStyle, _}
import synereo.client.components.{GlobalStyles, _}
import japgolly.scalajs.react
import shared.dtos.{IntroConfirmReq, Introduction}
import org.scalajs.dom.window
import synereo.client.sessionitems.SessionItems
import diode.AnyAction._

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.css.{DashboardCSS, NewMessageCSS}
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON
import scala.util.Success

/**
  * Created by mandar.k on 6/29/2016.
  */
//scalastyle:off
object ConfirmIntroReqModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  //  val introductionProxy = SYNEREOCircuit.connect(_.introduction)

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

    def introConfirmed(/*postMessage:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewIntroForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
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

// #todo think about better way for getting data from selectize input
// so that you don't have to pass the parentId explicitly
object ConfirmIntroReqForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback, header: String, introduction: Introduction)

  case class State(confirmIntroReq: Boolean = false)

  //    toDo: Think of some better logic to reduce verbosity in accept on form submit or reject --> hide like get  event target source and modify only accepted field of case class
  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      val uri = SYNEREOCircuit.zoom(_.user.sessionUri).value
      val props = t.props.runNow()
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = false)
      //      CoreApi.postIntroduction(introConfirmReq).onComplete {
      //        case Success(response) =>
      //
      //      }
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      jQuery(t.getDOMNode()).modal("hide")
    }

    def hideModal(): Unit = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val uri = SYNEREOCircuit.zoom(_.user.sessionUri).value
      val props = t.props.runNow()
      val content = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = true)
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(content))
      t.modState(s => s.copy(confirmIntroReq = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.postMessage*/)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div("Introduction Request")),
          closed = () => formClosed(s, p),
          addStyles = Seq()
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", ^.fontSize := "0.8.em")(
            <.div(^.className := "col-md-12")(
              <.div(p.introduction.message),
              <.div(
                s"From : ${JSON.parse(p.introduction.introProfile).name.asInstanceOf[String]}", <.br,
                "Date : Mon July 27 2016 ", <.br
              )
            ),
            <.div()(
              <.div(^.className := "text-right")(
                <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Accept"),
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> hide, "Reject")
              )
            )
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.confirmIntroReq) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}