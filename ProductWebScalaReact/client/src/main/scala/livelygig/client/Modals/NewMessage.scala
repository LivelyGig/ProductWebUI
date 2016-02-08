package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles

//  @js.native
//  trait BootstrapJQuery extends JQueryBtn {
//    def show(action: String): BootstrapJQuery = js.native
//    def show(options: js.Any): BootstrapJQuery = js.native
//  }

//  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]

  case class Props(ctl: RouterCtl[Loc], buttonName: String)

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {

//    def displayBtn = Callback {
//      jQuery(t.getDOMNode()).show("hide")
//    }

    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addNewMessageForm() : Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addMessage(postMessage: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(!postMessage){
        t.modState(s => s.copy(showNewMessageForm = false))
      } else {
        t.modState(s => s.copy(showNewMessageForm = true))
      }
    }
  }
  val component = ReactComponentB[Props]("NewMessage")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div(ProjectCSS.Style.displayInitialbtn/*, ^.onMouseOver --> B.displayBtn*/)(
        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn),className = "profile-action-buttons"),P.buttonName),
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
  case class Props(submitHandler: (Boolean) => Callback, header: String)
  case class State(postMessage: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hideModal =  {
      jQuery(t.getDOMNode()).modal("hide")
    }


    def mounted(props: Props): Callback = Callback {

    }
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postMessage = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postMessage)
      props.submitHandler(state.postMessage)
    }

    def render(s: State, p: Props) = {

      val headerText = p.header
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)(""))
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("To"))
            ),
            <.div()(
              <.input(^.`type` := "text",ProjectCSS.Style.textareaWidth)
            ),
            <.div()(
              <.input(^.`type` := "text",ProjectCSS.Style.textareaWidth, DashBoardCSS.Style.replyMarginTop, ^.placeholder:="Subject")
            ),
            <.div()(
              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,DashBoardCSS.Style.replyMarginTop , ^.placeholder:="Enter your message here:",^.lineHeight:= 6)
            )
          ),
          <.div()(
              <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              <.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, "Send"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("PostNewMessage")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope=> Callback{
         if(scope.currentState.postMessage){
           scope.$.backend.hideModal
         }
    })
    .build
  def apply(props: Props) = component(props)
}
