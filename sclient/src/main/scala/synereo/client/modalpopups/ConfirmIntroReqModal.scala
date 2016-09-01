package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.{Button, CommonStyle, _}
import synereo.client.components.{GlobalStyles, _}
import japgolly.scalajs.react
import shared.dtos.{IntroConfirmReq, Introduction}
import diode.AnyAction._

import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.css.{DashboardCSS, NewMessageCSS}
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.modalpopupbackends.ConfirmIntroReqBackend
import synereo.client.services.SYNEREOCircuit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

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



  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State())
    .backend(new ConfirmIntroReqBackend(_))
    .renderPS((t, P, S) => {
      val headerText = P.header
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div("Introduction Request")),
          closed = () => t.backend.formClosed(S, P),
          addStyles = Seq()
        ),
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
                <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Accept"),
                <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.hide, "Reject")
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