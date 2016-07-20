package synereo.client.modalpopups

import diode.react.ModelProxy
import shared.models.MessagePostContent
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.{Button, CommonStyle, _}
import synereo.client.components.Icon._
import synereo.client.components.{GlobalStyles, _}
import japgolly.scalajs.react
import shared.RootModels.IntroRootModel
import shared.dtos.IntroConfirmReq
import org.scalajs.dom.window
import shared.sessionitems.SessionItems
import diode.AnyAction._
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.MIcon.MIcon
import synereo.client.css.{DashboardCSS, NewMessageCSS}
import synereo.client.handlers.UpdateIntroduction
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON
import scala.util.Success

/**
  * Created by mandar.k on 6/29/2016.
  */
object ConfirmIntroReqModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  val getIntroduction = SYNEREOCircuit.connect(_.introduction)

  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: MIcon, title: String)

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addNewMessageForm(): Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addMessage(/*postMessage:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }
  }

  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles, P.addIcons, P.title, className = ""), P.buttonName),
        if (S.showNewMessageForm)
        //          ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.addMessage, "New Message"))
          getIntroduction(proxy => ConfirmIntroReqForm(ConfirmIntroReqForm.Props(B.addMessage, "New Message", proxy)))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

// #todo think about better way for getting data from selectize input
// so that you don't have to pass the parentId explicitly
object ConfirmIntroReqForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: ( /*PostMessage*/ ) => Callback, header: String, proxy: ModelProxy[IntroRootModel])

  case class State(postMessage: MessagePostContent, confirmIntroReq: Boolean = false,
                   cnxsSelectizeParentId: String = "postNewMessageSelectizeInput", labelSelectizeParentId: String = "labelsSelectizeParent")

  case class Backend(t: BackendScope[Props, State]) {
    def hide: Callback = Callback {
      val connectionSessionURI = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
      val props = t.props.runNow()
      val introConfirmReq = IntroConfirmReq(connectionSessionURI, alias = "alias", props.proxy().introResponse(0).introSessionId, props.proxy().introResponse(0).correlationId, accepted = false)
      println(s"introConfirmReq: $introConfirmReq")
      CoreApi.postIntroduction(introConfirmReq).onComplete{
        case Success(response) => println("introRequest Rejected successfully ")
          SYNEREOCircuit.dispatch(UpdateIntroduction(introConfirmReq))
      }
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateSubject(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }

    def updateContent(e: ReactEventI): react.Callback = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value)))
    }

    def hideModal(): Unit = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {
    }

    def submitForm(e: ReactEventI): react.Callback = {
      e.preventDefault()
      //      e.target.value.
      val connectionSessionURI = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
      val props = t.props.runNow()
      val introConfirmReq = IntroConfirmReq(connectionSessionURI, alias = "alias", props.proxy().introResponse(0).introSessionId, props.proxy().introResponse(0).correlationId, accepted = true)
      CoreApi.postIntroduction(introConfirmReq).onComplete{
        case Success(response) =>
          println("introRequest sent successfully ")
          SYNEREOCircuit.dispatch(UpdateIntroduction(introConfirmReq))
      }
      val state = t.state.runNow()

      t.modState(s => s.copy(confirmIntroReq = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.postMessage*/)
    }

    // scalastyle:off
    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div("Introduction Request")),
          // this is called after the modal has been hidden (animation is completed)
          closed = () => formClosed(s, p),
          CSSClass = "connectionModalBorder",
          addStyles = Seq()
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div(p.proxy().introResponse(0).message),
              <.div(
                s"From : ${JSON.parse(p.proxy().introResponse(0).introProfile).name.asInstanceOf[String]}", <.br,
                "Date : Mon June 13 2016 ", <.br
              ),
              <.div(^.className := "col-md-12")(
                <.div(^.className := "col-md-10")(
                  <.div()(" asdfa ")
                ),
                <.div(^.className := "col-md-2")(
                  <.div("test")
                )
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
    //.initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new MessagePostContent()))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.confirmIntroReq) {
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    //      .shouldComponentUpdate(scope => false)
    .build

  def apply(props: Props) = component(props)
}
