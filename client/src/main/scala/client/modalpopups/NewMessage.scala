package client.modals

import client.models.MessagesData
import client.modules.SearchesConnectionList
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components.Icon.Icon
import client.components._
import client.css.{DashBoardCSS,ProjectCSS}
import org.querki.jquery._
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.denigma.selectize._

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
    def addMessage(/*messagesData:MessagesData,*/ postMessage: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(postMessage){
        t.modState(s => s.copy(showNewMessageForm = true))
      } else {
        t.modState(s => s.copy(showNewMessageForm = false))
      }



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
  case class Props(submitHandler: (/*MessagesData ,*/ Boolean) => Callback, header: String)
  case class State(messagesData:MessagesData ,postMessage: Boolean = false)
  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def updateSubject(e:ReactEventI)  = {
      val value = e.target.value
      t.modState(s => s.copy(messagesData = s.messagesData.copy(subject = value)))
    }
    def updateContent(e:ReactEventI) ={
      val value = e.target.value
      t.modState(s => s.copy(messagesData = s.messagesData.copy(content = value)))
    }
    def hideModal =  {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(): Callback = Callback {
      /*val selectState : js.Object = ".select-state"
      $(selectState).selectize(SelectizeConfig
        .maxItems(10)
        .plugins("remove_button"))*/
    }
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postMessage = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.postMessage)
      props.submitHandler(/*state.messagesData,*/state.postMessage)

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
            <.div()(
             // <.input(^.`type` := "text",ProjectCSS.Style.textareaWidth)
              /*<.select(^.className:="select-state",^.name:="state[]", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig")(
                <.option(^.value:="")("Select"),
                <.option(^.value:="LivelyGig")("@LivelyGig"),
                <.option(^.value:="Synereo")("@Synereo")
              )*/
               LGCircuit.connect(_.connections)(conProxy => SearchesConnectionList(SearchesConnectionList.Props(conProxy)))
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Subject",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop, ^.value:=s.messagesData.subject ,^.onChange ==> updateSubject)
             ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Enter your message here:",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop, ^.value:=s.messagesData.content, ^.onChange ==> updateContent )
            )
          ),
          <.div()(
              <.div(DashBoardCSS.Style.modalHeaderPadding,^.className:="text-right")(
              <.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn,^.onClick --> hide, "Send"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
   // .initialState_P(p => State(p=> new MessagesData("","","")))
    .initialState_P(p => State(new MessagesData("", "", "")))
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
         if(scope.currentState.postMessage){
           scope.$.backend.hideModal
         }
    })
      .componentDidMount(scope => scope.backend.mounted())
    .build
  def apply(props: Props) = component(props)
}
