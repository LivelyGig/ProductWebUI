//package client.modals
//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount
//import japgolly.scalajs.react.extra.router.RouterCtl
//import japgolly.scalajs.react.vdom.prefix_<^._
//import client.LGMain.Loc
//import client.components.Bootstrap._
//import client.components._
//import client.css.{DashBoardCSS, HeaderCSS, MessagesCSS, ProjectCSS}
//import client.logger._
//import shared.models.UserModel
//import client.services.CoreApi._
//import client.services._
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.util.{Failure, Success}
//import scalacss.ScalaCssReact._
//
//object TermsAndConditions {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(ctl: RouterCtl[Loc])
//
//  case class State(showTermsofServiceFlag: Boolean = false
//                  )
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback =  {
//      t.modState(s => s.copy(showTermsofServiceFlag = true))
//    }
//    def addTermsofServiceForm() : Callback = {
//      t.modState(s => s.copy(showTermsofServiceFlag = true))
//    }
//    def addNewLoginForm() : Callback = {
//      t.modState(s => s.copy(showTermsofServiceFlag = true))
//    }
//
//    def addTermsofService(userModel: UserModel, showTermsofServiceFlag: Boolean = false): Callback = {
//      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showTermsofServiceFlag}")
//      if(showTermsofServiceFlag){
//        createUser(userModel).onComplete {
//          case Success(s) =>
//            log.debug(s"createUser msg : ${s.msgType}")
//            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
//              t.modState(s => s.copy(showTermsofServiceFlag = true)).runNow()
//            } else {
//              log.debug(s"createUser msg : ${s.content}")
//              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
//            }
//          case Failure(s) =>
//            log.debug(s"createUserFailure: ${s}")
//            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
//          // now you need to refresh the UI
//        }
//        t.modState(s => s.copy(showTermsofServiceFlag = true))
//      } else {
//        t.modState(s => s.copy(showTermsofServiceFlag = false))
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
//        Button(Button.Props(B.addTermsofServiceForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"Terms of Service"),
//        if (S.showTermsofServiceFlag) TermsAndConditionsForm(TermsAndConditionsForm.Props(B.addTermsofService))
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
//object TermsAndConditionsForm {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(submitHandler: (UserModel, Boolean) => Callback)
//  case class State(userModel: UserModel, postProject: Boolean = false)
//
//
//  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
//    def hide = Callback {
//      // instruct Bootstrap to hide the modal
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def mounted(props: Props): Callback = Callback {
//
//    }
//    def updateName(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
//    }
//    def updateEmail(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
//    }
//    def updatePassword(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
//    }
//    def toggleBTCWallet(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
//    }
//
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      t.modState(s => s.copy(postProject = false))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.postProject)
//      props.submitHandler(state.userModel, state.postProject)
//    }
//
//    def render(s: State, p: Props) = {
//      if (s.postProject){
//        jQuery(t.getDOMNode()).modal("hide")
//      }
//      val headerText = "Terms of Service"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//        // this is called after the modal has been hidden (animation is completed)
//        closed = () => formClosed(s, p)),
//        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("Terms of Service"))
//          ),//main row
//          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
//            <.div(DashBoardCSS.Style.marginTop10px)(
//            ),
//            <.div()(
//              "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
//            )
//          ),
//          <.div()(
//            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
//              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
//              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
//            )
//          ),
//          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
//        )
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("TermsofService")
//    .initialState_P(p => State(new UserModel("","","",false)))
//    .renderBackend[Backend]
//    .componentDidMount(scope => Callback {
//      val P = scope.props
//      val S=scope.state
//      val B=scope.backend
//
//      def hideModal = Callback {
//        if (S.postProject) {
//          def hide = Callback {
//            jQuery(scope.getDOMNode()).modal("hide")
//          }
//        }
//      }
//    })
//    .componentDidUpdate(scope=> Callback{
//
//    })
//    .build
//  def apply(props: Props) = component(props)
//}
//
