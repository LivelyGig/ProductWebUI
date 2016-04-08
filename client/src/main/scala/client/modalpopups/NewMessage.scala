package client.modals

import java.util.UUID

import client.models.PostMessage
import client.services.{CoreApi, LGCircuit}
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS, ProjectCSS}
import client.utils.Utils
import org.querki.jquery._
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs._
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import shared.dtos._
import org.querki.jquery._


object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addNewMessageForm() : Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addMessage(/*postMessage:PostMessage*/): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      t.modState(s => s.copy(showNewMessageForm = false))

      /*if(postMessage){
        t.modState(s => s.copy(showNewMessageForm = true))
      } else {
        t.modState(s => s.copy(showNewMessageForm = false))
      }*/
    }
  }
  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(/*ProjectCSS.Style.displayInitialbtn*//*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, P.addStyles,P.addIcons,P.title,className = "profile-action-buttons"),P.buttonName),
        if (S.showNewMessageForm) PostNewMessage(PostNewMessage.Props(B.addMessage, "New Message"))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}

object PostNewMessage {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (/*PostMessage*/) => Callback, header: String)
  case class State(postMessage:PostMessage, postNewMessage: Boolean = false, selectizeInputId : String = "postNewMessageSelectizeInput")
  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      $(t.getDOMNode()).modal("hide")
    }
    /*def updateTo(e:ReactEventI)  = {
      val value = "{\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\", \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\",\"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\"}"
      t.modState(s => s.copy(messagesData = s.messagesData.copy(recipients = value)))
    }*/
    def updateSubject(e:ReactEventI)  = {
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
    }
    def updateContent(e:ReactEventI) ={
      val value = e.target.value
      t.modState(s => s.copy(postMessage = s.postMessage.copy(content = value)))
    }
    def hideModal =  {
      $(t.getDOMNode()).modal("hide")
    }
    def mounted(): Callback = Callback {

    }
    def sendMessage(content: String, connectionString: String) = {
      val uid = UUID.randomUUID().toString.replaceAll("-","")
      //      println(uid)
      //      val dummyTargetConnection = "{\n\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\",\n                  \"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\",\n                  \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\"\n}"
      val targetConnection = upickle.default.read[Connection](connectionString)
      val value =  ExpressionContentValue(uid.toString,"TEXT","","",Seq(),Seq(Utils.GetSelfConnnection(CoreApi.MESSAGES_SESSION_URI), targetConnection),content)
      CoreApi.evalSubscribeRequest(SubscribeRequest(CoreApi.MESSAGES_SESSION_URI, Expression(CoreApi.INSERT_CONTENT, ExpressionContent(Seq(Utils.GetSelfConnnection(CoreApi.MESSAGES_SESSION_URI), targetConnection),"each([LivelyGig],[Synereo])",upickle.default.write(value),"")))).onComplete{
        case Success(response) => {println("success")
           println("Responce = "+response)
//          t.modState(s => s.copy(postNewMessage = true))
        }
        case Failure(response) => println("failure")
      }
    }
    def submitForm(e: ReactEventI) = Callback{
      e.preventDefault()
      val state = t.state.runNow()
      /*if (state.postNewMessage){*/
      //        val selectState : js.Object  = s"#${state.selectizeInputId} > option"
      var selectedConnections = Seq[String]()
      val selector : js.Object  = s"#${state.selectizeInputId} > .selectize-control> .selectize-input > div"

      $(selector).each((y: Element) => selectedConnections :+= $(y).attr("data-value").toString)
      selectedConnections.foreach(e => sendMessage(state.postMessage.content, e))

      /*}*/
      //      t.modState(s => s.copy(postNewMessage = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      props.submitHandler(/*state.postMessage*/)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),
            /*val selectizeControl : js.Object =*/
            <.div(^.id:=s.selectizeInputId)(
              //              val to = "{\"source\":\"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias\", \"label\":\"34dceeb1-65d3-4fe8-98db-114ad16c1b31\",\"target\":\"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias\"}"
              LGCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy,s.selectizeInputId)))
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Subject",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop, ^.value:=s.postMessage.subject ,^.onChange ==> updateSubject, ^.required:= true)
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Enter your message here:",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop, ^.value:=s.postMessage.content, ^.onChange ==> updateContent, ^.required:= true )
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="text-right")(
              <.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn,/*^.onClick --> hide, */"Send"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
    //.initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new PostMessage("", "", "")))
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
      if(scope.currentState.postNewMessage){
        scope.$.backend.hideModal
      }
    })
    .componentDidMount(scope => scope.backend.mounted())
    //      .shouldComponentUpdate(scope => false)
    .build
  def apply(props: Props) = component(props)
}
