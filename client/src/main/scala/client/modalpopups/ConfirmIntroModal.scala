package client.modules

/**
  * Created by bhagyashree.b on 2016-08-12.
  */


import client.components.Bootstrap.{Button, CommonStyle, Modal}
import client.components.{GlobalStyles, Icon}
import shared.dtos.{IntroConfirmReq, Introduction}
import scala.language.reflectiveCalls
import client.services.{CoreApi, LGCircuit}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap.{Button, CommonStyle, _}
import client.components.Icon._
import client.components.{GlobalStyles, _}
import client.css.{DashBoardCSS, HeaderCSS, ProjectCSS, WorkContractCSS}
import japgolly.scalajs.react
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import client.handler.UpdateIntroductionsModel
import shared.dtos.{IntroConfirmReq, Introduction}
import client.utils.{AppUtils, ConnectionsUtils}
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
import diode.AnyAction._
import scala.scalajs.js

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
        //          introductionProxy(proxy => ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.introConfirmed, "New Intro", proxy)))
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
      val uri = LGCircuit.zoom(_.session.messagesSessionUri).value
      val props = t.props.runNow()
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = false)
      //      CoreApi.postIntroduction(introConfirmReq).onComplete {
      //        case Success(response) =>
      //
      //      }
      LGCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      $(t.getDOMNode()).modal("hide")
    }

    def hideModal(): Unit = {
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      val uri = LGCircuit.zoom(_.session.messagesSessionUri).value
      val props = t.props.runNow()
      val content = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = true)
      LGCircuit.dispatch(UpdateIntroductionsModel(content))
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
          CSSClass = "connectionModalBorder",
          addStyles = Seq(DashBoardCSS.Style.connectionModalWidth),
          id = "ConfirmIntroReq"
        ),
//        <.form(^.onSubmit ==> submitForm)(
//
//          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
//            <.div(DashBoardCSS.Style.marginTop10px)(),
//            <.div(^.className := "col-md-5")(
//              <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")
//            ),
//
//          <.div(^.className := "row", ^.fontSize := "0.8.em")(
//            <.div(^.className := "col-md-12")(
//              <.div(p.introduction.message),
//              <.div(
//                s"From : ${JSON.parse(p.introduction.introProfile).name.asInstanceOf[String]}", <.br,
//                "Date : Mon July 27 2016 ", <.br
//              )
//            ),
//            <.div()(
//              <.div(^.className := "text-right")(
//                <.button(^.tpe := "submit", ^.className := "btn btn-default", /* ^.onClick --> hide*/ "Accept"),
//                <.button(^.tpe := "button", ^.className := "btn btn-default",^.onClick --> hide, "Reject")
//              )
//            )
//          )
//        )
//      )
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(),
            <.div(^.className := "col-md-5")(
              <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")
            ),
            <.div(^.className := "col-md-7")(
              <.div(^.className := "col-md-12")(
                <.div(DashBoardCSS.Style.modalHeaderText)("Introduction Request"),
                <.div(
                  s"From : ${JSON.parse(p.introduction.introProfile).name.asInstanceOf[String]}",
                  <.div(DashBoardCSS.Style.displayInlineText, DashBoardCSS.Style.marginLeftchk)(<.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")), <.br,
                  <.div(p.introduction.message),
                  "Date : Mon June 13 2016 ", <.br
                ),
                <.div(^.className := "col-md-12")(
                  <.div(^.className := "col-md-12")(
                    <.div(DashBoardCSS.Style.modalContentFont)(" asdfa ")
                  ) /*,
                <.div(^.className := "col-md-2")(
                  <.div("test"),
                  <.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, ^.src := "./assets/images/LivelyGig-logo-symbol.svg")
                )*/
                )
              ),
              <.div(^.className := "col-md-12 col-sm-12", DashBoardCSS.Style.modalContentFont)("Accept introduction to \"Frank Howardzo\"?"),
              <.div(^.className := "col-md-12", DashBoardCSS.Style.padding15px)(
                <.button(^.tpe := "submit", ^.className := "btn", DashBoardCSS.Style.btnDefault, WorkContractCSS.Style.createWorkContractBtn, DashBoardCSS.Style.marginLeftCloseBtn, /*^.onClick --> hide, */ "Accept"),
                <.button(^.tpe := "button", ^.className := "btn", DashBoardCSS.Style.btnDefault, WorkContractCSS.Style.createWorkContractBtn, DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide, "Reject")
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