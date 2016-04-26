//package client.Modals
//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount
//import japgolly.scalajs.react.extra.router.RouterCtl
//import japgolly.scalajs.react.vdom.prefix_<^._
//import client.LGMain.Loc
//import client.LGMain.Loc
//import client.components.Bootstrap.Button
//import client.components.Bootstrap.CommonStyle
//import client.components.Bootstrap.Modal
//import client.components.Bootstrap._
//import client.components.GlobalStyles
//import client.components.Icon
//import client.components._
//import client.components._
//import client.css.DashBoardCSS
//import client.css.HeaderCSS
//import client.css.MessagesCSS
//import client.css.ProjectCSS
//import client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
//import client.logger._
//import client.modals.NewMessage.State
//import client.modals.PostNewMessage.State
//import shared.models.UserModel
//import client.services.CoreApi._
//import client.services._
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.util.{Failure, Success}
//import scalacss.ScalaCssReact._
//
//object ReplyModal {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(ctl: RouterCtl[Loc])
//
//  case class State(showNewMessageForm: Boolean = false)
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback =  {
//      t.modState(s => s.copy(showNewMessageForm = true))
//    }
//    def addNewMessageForm() : Callback = {
//      t.modState(s => s.copy(showNewMessageForm = true))
//    }
//    def addNewAgent(showNewMessageForm: Boolean = false): Callback = {
//      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
//      if(showNewMessageForm){
//        t.modState(s => s.copy(showNewMessageForm = false))
//      } else {
//        t.modState(s => s.copy(showNewMessageForm = true))
//      }
//    }
//  }
//
//  val component = ReactComponentB[Props]("AddNewAgent")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(ProjectCSS.Style.displayInitialbtn)(
//        Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"New Message"),
//        if (S.showNewMessageForm) ReplyModalForm(ReplyModalForm.Props(B.addNewAgent))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .configure(OnUnmount.install)
//    .build
//  def apply(props: Props) = component(props)
//}
//
//object ReplyModalForm {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(submitHandler: (Boolean) => Callback)
//  case class State(showNewMessageForm: Boolean = false)
//
//
//  case class Backend(t: BackendScope[Props, State]) {
//    def hide = Callback {
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def hideModal =  {
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def mounted(props: Props): Callback = Callback {
//
//    }
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      t.modState(s => s.copy(showNewMessageForm = true))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.showNewMessageForm)
//      props.submitHandler(state.showNewMessageForm)
//    }
//
//    def render(s: State, p: Props) = {
//
//      val headerText = "Message - New"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//        // this is called after the modal has been hidden (animation is completed)
//        closed = () => formClosed(s, p)),
//        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)(""))
//          ),//main row
//          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
//            <.div(DashBoardCSS.Style.marginTop10px)(
//            ),
//            <.div()(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Subject:",^.lineHeight:= 1)
//            ),
//            <.div()(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Enter your message here:",^.lineHeight:= 6)
//            ),
//            <.div(^.className:="row")(
//              <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Recipients"))
//            ),
//            <.div()(
//              <.input(^.`type` := "textarea",ProjectCSS.Style.textareaWidth,^.placeholder:="Enter your message here:",^.lineHeight:= 6)
//            )
//          ),
//          <.div()(
//            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
//              <.button(^.tpe := "submit",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, "Send"),
//              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
//            )
//          ),
//          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
//        )
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("PostNewMessage")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .componentDidUpdate(scope=> Callback{
//      if(scope.currentState.showNewMessageForm){
//        scope.$.backend.hideModal
//      }
//    })
//    .build
//  def apply(props: Props) = component(props)
//}
