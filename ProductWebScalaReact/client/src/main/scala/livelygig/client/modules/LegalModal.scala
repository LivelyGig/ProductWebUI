//package livelygig.client.modules
//
//import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
//import japgolly.scalajs.react.extra.router.RouterCtl
//import livelygig.client.LGMain.{Loc}
//import livelygig.client.services.ApiResponseMsg
//import livelygig.client.services.CoreApi
//import livelygig.client.services.CoreApi._
//import org.scalajs.dom._
//import scala.scalajs.js
//import scala.util.{Failure, Success}
//import scalacss.ScalaCssReact._
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount
//import japgolly.scalajs.react.vdom.prefix_<^._
//import livelygig.client.components.Bootstrap._
//import livelygig.client.components._
//import livelygig.client.logger._
//import livelygig.client.services._
//import livelygig.client.css.{HeaderCSS, DashBoardCSS,ProjectCSS,MessagesCSS}
//import scala.concurrent.ExecutionContext.Implicits.global
//
//object LegalModal {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(ctl: RouterCtl[Loc])
//
//  case class State(showLegalForm: Boolean = false,showPrivacyPolicyForm: Boolean = false)
//
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback =  {
//      t.modState(s => s.copy(showLegalForm = true))
//    }
//    def addLegalForm() : Callback = {
//      t.modState(s => s.copy(showLegalForm = true))
//    }
//    def addNewLoginForm() : Callback = {
//      t.modState(s => s.copy(showLegalForm = true))
//    }
////
////    def addLegal(userModel: UserModel, showLegalForm: Boolean = false , showPrivacyPolicyForm: Boolean = false): Callback = {
////      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showLegalForm}")
////      if(showLegalForm){
////        createUser(userModel).onComplete {
////          case Success(s) =>
////            log.debug(s"createUser msg : ${s.msgType}")
////            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
////              t.modState(s => s.copy(showLegalForm = true)).runNow()
////            } else {
////              log.debug(s"createUser msg : ${s.content}")
////              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
////            }
////          case Failure(s) =>
////            log.debug(s"createUserFailure: ${s}")
////            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
////          // now you need to refresh the UI
////        }
////        t.modState(s => s.copy(showLegalForm = true))
////      } else {
////        t.modState(s => s.copy(showPrivacyPolicyForm = true))
////      }
////    }
//
//    def addLegal(agentLoginModel : AgentLoginModel , showLegalForm: Boolean = false , showPrivacyPolicyForm: Boolean = false) : Callback = {
//     // log.debug(s"Login agentLoginModel: ${agentLoginModel}, login: ${login}, showConfirmAccountCreation: ${showConfirmAccountCreation}")
//      if (showLegalForm){
//        CoreApi.agentLogin(agentLoginModel).onComplete {
//          case Success(s) =>
//            log.debug(s"loginAPISuccessMsg: ${s.msgType}")
//            if (s.msgType == ApiResponseMsg.InitializeSessionResponse){
//              // todo add functionality after login may involve dispatching of certain events
//              window.localStorage.setItem("sessionURI",s.content.sessionURI.getOrElse(""))
//              log.debug("login successful")
//              window.location.href = "/#connections"
//            } else {
//              log.debug("login failed")
//              t.modState(s => s.copy(showLegalForm = true)).runNow()
//            }
//          case Failure(s) =>
//            println("internal server error")
//        }
//        t.modState(s => s.copy(showLegalForm = false))
//      }
//      else if (showPrivacyPolicyForm) {
//        t.modState(s => s.copy(showPrivacyPolicyForm = false/*, showConfirmAccountCreation = true*/))
//      }
//      else if (showPrivacyPolicyForm) {
//        t.modState(s => s.copy(showPrivacyPolicyForm = false/*, showConfirmAccountCreation = true*/))
//      }
//          else {
//        t.modState(s => s.copy(showLegalForm = false))
//      }
//    }
//
//    def addPrivacyPolicy(userModel: UserModel, showPrivacyPolicyForm: Boolean = false): Callback = {
//      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showPrivacyPolicyForm}")
//      if(showPrivacyPolicyForm){
//        createUser(userModel).onComplete {
//          case Success(s) =>
//            log.debug(s"createUser msg : ${s.msgType}")
//            if (s.msgType == ApiResponseMsg.CreateUserWaiting){
//              t.modState(s => s.copy(showPrivacyPolicyForm = true)).runNow()
//            } else {
//              log.debug(s"createUser msg : ${s.content}")
//              t.modState(s => s.copy(/*showRegistrationFailed = true*/)).runNow()
//            }
//          case Failure(s) =>
//            log.debug(s"createUserFailure: ${s}")
//            t.modState(s => s.copy(/*showErrorModal = true*/)).runNow()
//          // now you need to refresh the UI
//        }
//        t.modState(s => s.copy(showPrivacyPolicyForm = true))
//      } else {
//        t.modState(s => s.copy(showPrivacyPolicyForm = false))
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
//
//        Button(Button.Props(B.addLegalForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle)),"Legal"),
//        if (S.showLegalForm) LegalModalForm(LegalModalForm.Props(B.addLegal))
//        else
//        if(S.showPrivacyPolicyForm) PrivacyPolicyModalForm(PrivacyPolicyModalForm.Props(B.addPrivacyPolicy))
//          else
//          Seq.empty[ReactElement]
//      )
//    })
//    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .configure(OnUnmount.install)
//    .build
//  def apply(props: Props) = component(props)
//}
//
//object LegalModalForm {
//  // shorthand for styles
//  @inline private def bss = GlobalStyles.bootstrapStyles
//  case class Props(submitHandler: (AgentLoginModel, Boolean , Boolean) => Callback)
//  case class State(agentLoginModel : AgentLoginModel, showLegalForm: Boolean = false , showPrivacyPolicyForm: Boolean = false)
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
//    def PrivacyPolicy(e:ReactEventI) = Callback {
//      t.modState(s => s.copy(showLegalForm = false , showPrivacyPolicyForm = true ))
//    }
////    def updateName(e: ReactEventI) = {
////      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(name = e.target.value)))
////    }
//    def updateEmail(e: ReactEventI) = {
//      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(email = e.target.value)))
//    }
//    def updatePassword(e: ReactEventI) = {
//      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(password = e.target.value)))
//    }
////    def toggleBTCWallet(e: ReactEventI) = {
////      t.modState(s => s.copy(agentLoginModel = s.agentLoginModel.copy(createBTCWallet = !s.agentLoginModel.createBTCWallet)))
////    }
//
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      t.modState(s => s.copy(showLegalForm = false))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      println(state.showLegalForm)
//      props.submitHandler(state.agentLoginModel, state.showLegalForm , state.showPrivacyPolicyForm)
//    }
//
//    def render(s: State, p: Props) = {
//      if (s.showLegalForm){
//        jQuery(t.getDOMNode()).modal("hide")
//      }
//      val headerText = "Legal"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//        // this is called after the modal has been hidden (animation is completed)
//        closed = () => formClosed(s, p)),
//        <.form(^.onSubmit ==> submitForm)(
//          <.div(^.className:="row")(
//            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("Legal"))
//          ),//main row
//          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
//            <.ul()(
//              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick ==> PrivacyPolicy ,"Privacy Policy"))),
//              <.li()(<.a(^.href:="#")("End User Agreement")),
//              <.li()(<.a(^.href:="#")("Terms of Service")),
//              <.li()(<.a(^.href:="#")("Trademarks")),
//              <.li()(<.a(^.href:="#")("Copyright")),
//              <.li()( <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"))
//            )
//          ),
//          <.div()(
//            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
////              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
////              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
//            )
//          ),
//          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
//        )
//      }
//  }
//  private val component = ReactComponentB[Props]("PostNewMessage")
//    .initialState_P(p => State(new AgentLoginModel("","")))
//    .renderBackend[Backend]
//    .componentDidMount(scope => Callback {
//      val P = scope.props
//      val S=scope.state
//      val B=scope.backend
//
//      def hideModal = Callback {
//        if (S.showLegalForm) {
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
