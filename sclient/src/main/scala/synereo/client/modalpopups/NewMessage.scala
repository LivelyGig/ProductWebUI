package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components.Icon.Icon
import synereo.client.components.{GlobalStyles, Icon}
import synereo.client.css.SynereoCommanStylesCSS
import synereo.client.components.jQuery
import scala.language.reflectiveCalls

import scalacss.Defaults._
import scalacss.ScalaCssReact._

object NewMessage {
  @inline private def bss = GlobalStyles.bootstrapStyles


  case class Props(buttonName: String,addStyles: Seq[StyleA] = Seq() , addIcons : Icon,title: String)

  case class State(addRequestForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {


    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(addRequestForm = true))
    }
    def addNewMessageForm() : Callback = {
      t.modState(s => s.copy(addRequestForm = true))
    }
    def addMessage(postMessage: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addRequestForm}")
      if(postMessage){
        t.modState(s => s.copy(addRequestForm = true))
      } else {
        t.modState(s => s.copy(addRequestForm = false))
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
        if (S.addRequestForm) PostNewMessage(PostNewMessage.Props(B.addMessage, "New Request"))
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
      t.modState(s => s.copy(postMessage = false))
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
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(SynereoCommanStylesCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row" , SynereoCommanStylesCSS.Style.MarginLeftchkproduct)(
            <.div(SynereoCommanStylesCSS.Style.marginTop10px)(
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12")(<.div(SynereoCommanStylesCSS.Style.modalHeaderFont)("To"))
            ),
            <.div()(
              <.input(^.`type` := "text")
            ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Subject",SynereoCommanStylesCSS.Style.replyMarginTop )
             ),
            <.div()(
              <.textarea(^.rows:= 6,^.placeholder:="Enter your message here:",SynereoCommanStylesCSS.Style.replyMarginTop )
            )
          ),
          <.div()(
              <.div(SynereoCommanStylesCSS.Style.modalHeaderPadding,SynereoCommanStylesCSS.Style.footTextAlign)(
              <.button(^.tpe := "submit",^.className:="btn btn-default", SynereoCommanStylesCSS.Style.marginLeftCloseBtn,^.onClick --> hide, "Send"),
              <.button(^.tpe := "button",^.className:="btn btn-default", SynereoCommanStylesCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,SynereoCommanStylesCSS.Style.marginTop10px,SynereoCommanStylesCSS.Style.marginLeftRight)()
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
