package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.Button
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.components.Bootstrap._
import livelygig.client.components.GlobalStyles
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.logger._
import livelygig.client.logger._
import livelygig.client.models.AgentLoginModel
import livelygig.client.models.EmailValidationModel
import livelygig.client.models.UserModel
import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.CoreApi
import livelygig.client.services.CoreApi._
import livelygig.client.services.CoreApi._
import livelygig.client.services._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object Legal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc])

  case class State(showTermsOfServicesForm: Boolean = false, showLoginForm: Boolean = false, showPrivacyPolicyForm: Boolean = false,
                   showPrivacyPolicyModal: Boolean = false, showAccountValidationSuccess: Boolean = false,
                   showLoginFailed: Boolean = false, showRegistrationFailed: Boolean = false,
                   showErrorModal: Boolean = false, showAccountValidationFailed: Boolean = false, showEndUserAgreementModal: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addLoginForm(): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addNewLoginForm(): Callback = {
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false): Callback = {
      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${addNewAgent}")
      if (addNewAgent) {
        createUser(userModel).onComplete {
          case Success(s) =>
            log.debug(s"createUser msg : ${s.msgType}")
            if (s.msgType == ApiResponseMsg.CreateUserWaiting) {
              t.modState(s => s.copy(showPrivacyPolicyModal = true)).runNow()
            } else {
              log.debug(s"createUser msg : ${s.content}")
              t.modState(s => s.copy(showRegistrationFailed = true)).runNow()
            }
          case Failure(s) =>
            log.debug(s"createUserFailure: ${s}")
            t.modState(s => s.copy(showErrorModal = true)).runNow()
          // now you need to refresh the UI
        }
        t.modState(s => s.copy(showTermsOfServicesForm = false))
      } else {
        t.modState(s => s.copy(showTermsOfServicesForm = false))
      }
    }

    def Login(agentLoginModel: AgentLoginModel, login: Boolean = false, showPrivacyPolicyModal: Boolean = false,
              showTermsOfServicesForm: Boolean = false, showEndUserAgreementModal: Boolean = false): Callback = {
      log.debug(s"Login agentLoginModel: ${agentLoginModel}, login: ${login}, showPrivacyPolicyModal: ${showPrivacyPolicyModal}")
      if (login) {
        CoreApi.agentLogin(agentLoginModel).onComplete {
          case Success(s) =>
            log.debug(s"loginAPISuccessMsg: ${s.msgType}")
            if (s.msgType == ApiResponseMsg.InitializeSessionResponse) {
              // todo add functionality after login may involve dispatching of certain events
              window.localStorage.setItem("sessionURI", s.content.sessionURI.getOrElse(""))
              log.debug("login successful")
              window.location.href = "/#connections"
            } else {
              log.debug("login failed")
              t.modState(s => s.copy(showLoginFailed = true)).runNow()
            }
          case Failure(s) =>
            println("internal server error")
        }
        t.modState(s => s.copy(showLoginForm = false))
      }
      else if (showPrivacyPolicyModal) {
        t.modState(s => s.copy(showLoginForm = false, showPrivacyPolicyModal = true))
      }
      else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showPrivacyPolicyModal = false, showTermsOfServicesForm = true))
      }
      else if (showEndUserAgreementModal) {
        t.modState(s => s.copy(showPrivacyPolicyModal = false, showTermsOfServicesForm = false, showEndUserAgreementModal = true))
      }
      else {
        t.modState(s => s.copy(showLoginForm = false))
      }
    }

    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false): Callback = {
      if (confirmAccountCreation) {
        emailValidation(emailValidationModel).onComplete {
          case Success(s) =>
            if (s.msgType == ApiResponseMsg.CreateUserError) {
              println(ApiResponseMsg.CreateUserError)
              t.modState(s => s.copy(showAccountValidationFailed = true)).runNow()
            }
            else {
              t.modState(s => s.copy(showAccountValidationSuccess = true)).runNow()
            }
          case Failure(s) =>
            log.debug(s"ConfirmAccontCreationAPI failure: ${s.getMessage}")
            t.modState(s => s.copy(showErrorModal = true)).runNow()
        }
        t.modState(s => s.copy(showPrivacyPolicyModal = false))
      } else {
        t.modState(s => s.copy(showPrivacyPolicyModal = false))
      }
    }

    def accountValidationSuccess(/*emailValidationModel: EmailValidationModel, accountValidationSuccess: Boolean = false*/): Callback = {
      t.modState(s => s.copy(showAccountValidationSuccess = false, showLoginForm = true))
    }

    def loginFailed(): Callback = {
      t.modState(s => s.copy(showLoginFailed = false, showLoginForm = true))
    }

    def registrationFailed(login: Boolean = false): Callback = {
      if (login) {
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false, showTermsOfServicesForm = true))
      }
    }

    def serverError(): Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }

    def accountValidationFailed(): Callback = {
      t.modState(s => s.copy(showAccountValidationFailed = false, showPrivacyPolicyModal = true))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle)), "Legal"),
        if (S.showTermsOfServicesForm) TermsOfServices(TermsOfServices.Props(B.addNewAgent))
        else if (S.showLoginForm) LegalModalForm(LegalModalForm.Props(B.Login))
        else if (S.showPrivacyPolicyModal) PrivacyPolicyModal(PrivacyPolicyModal.Props(B.confirmAccountCreation))
        else if (S.showEndUserAgreementModal) EndUserAgreement(EndUserAgreement.Props(B.confirmAccountCreation))
        else if (S.showAccountValidationSuccess) AccountValidationSuccess(AccountValidationSuccess.Props(B.accountValidationSuccess))
        else if (S.showLoginFailed) LoginFailed(LoginFailed.Props(B.loginFailed))
        else if (S.showRegistrationFailed) RegistrationFailed(RegistrationFailed.Props(B.registrationFailed))
        else if (S.showErrorModal) ErrorModal(ErrorModal.Props(B.serverError))
        else if (S.showAccountValidationFailed) AccountValidationFailed(AccountValidationFailed.Props(B.accountValidationFailed))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  def apply(props: Props) = component(props)
}

object LegalModalForm {
  //TodoForm
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (AgentLoginModel, Boolean, Boolean, Boolean, Boolean) => Callback)

  case class State(agentloginModel: AgentLoginModel, login: Boolean = false, showPrivacyPolicyModal: Boolean = false,
                   showTermsOfServicesForm: Boolean = false, showEndUserAgreementModal: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(login = true))
      //  jQuery(t.getDOMNode()).modal("hide")
    }

    def hide /*= Callback*/ {
      console.log("hide")
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateEmail(e: ReactEventI) = {
      t.modState(s => s.copy(agentloginModel = s.agentloginModel.copy(email = e.target.value)))
    }

    def showPrivacyPolicy(e: ReactEventI) = {
      t.modState(s => s.copy(showPrivacyPolicyModal = true))
    }

    def showEndUserAgreement(e: ReactEventI) = {
      t.modState(s => s.copy(showEndUserAgreementModal = true))
    }

    def showTermsOfServices(e: ReactEventI) = {
      t.modState(s => s.copy(showTermsOfServicesForm = true))
    }

    def updatePassword(e: ReactEventI) = {
      t.modState(s => s.copy(agentloginModel = s.agentloginModel.copy(password = e.target.value)))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.agentloginModel, state.login, state.showPrivacyPolicyModal, state.showTermsOfServicesForm, state.showEndUserAgreementModal)
    }


    def render(s: State, p: Props) = {

      //      if (s.login || s.showPrivacyPolicyModal || s.showTermsOfServicesForm) {
      //        jQuery(t.getDOMNode()).modal("hide")
      //      }

      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Legal"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          //          <.div(^.className:="row")(
          //            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
          //              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
          //                <.div(DashBoardCSS.Style.modalHeaderFont)("Login"),
          //                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name",
          //                  ^.placeholder:="username", ^.value:= s.agentloginModel.email, ^.onChange==>updateEmail, ^.required:=true),
          //                <.input(^.tpe := "password", bss.formControl, DashBoardCSS.Style.inputModalMargin , ^.placeholder:="password"
          //                  , ^.value:= s.agentloginModel.password, ^.onChange==>updatePassword, ^.required:=true) ,
          //                <.button(^.tpe := "submit",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth,"Login")),
          //
          //              <.div(^.className:="row",DashBoardCSS.Style.scltInputModalContainerMargin,DashBoardCSS.Style.marginTop10px)(
          //                <.div(^.className:="col-md-4",DashBoardCSS.Style.paddingLeftLoginbtn)(
          //                  <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth,"Validate Account",
          //                    ^.onClick==>showPrivacyPolicy)
          //                ),
          //                <.div(^.className:="col-md-4")(
          //                  <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth,"Sign Up",
          //                    ^.onClick==>showTermsOfServices)
          //                ),
          //                <.div(^.className:="col-md-4",DashBoardCSS.Style.paddingRightLoginbtn)(
          //                  <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth,"Forgot My Password",
          //                    ^.onClick==>showPrivacyPolicy)
          //                )
          //              ),
          //              <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
          //            )
          //          )

          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont, MessagesCSS.Style.paddingLeftModalHeaderbtn)(""))
          ), //main row
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.ul()(
              <.li()(<.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "Privacy Policy", ^.onClick ==> showPrivacyPolicy)),
              // ToDo: End User Agreement button doesn't work  2016-01-18
              <.li()(<.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "End User Agreement", ^.onClick ==> showEndUserAgreement)),
              <.li()(<.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "Terms of Service", ^.onClick ==> showTermsOfServices)),
              <.li()(<.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "Trademarks", ^.onClick ==> showPrivacyPolicy)),
              // ToDo: Need a Copyright modal. 2016-01-18
              <.li()(<.button(^.tpe := "button", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "Copyright", ^.onClick ==> showPrivacyPolicy)) /*,
            <.li()( <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"))*/
            )
          )
        ) //submitform
      )

    }
  }

  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new AgentLoginModel("", "")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      console.log("componentDidMount called")
      // jQuery(scope.getDOMNode()).modal("hide")
      console.log("mountLogin : " + scope.state.login)

      if (scope.state.login || scope.state.showPrivacyPolicyModal || scope.state.showTermsOfServicesForm || scope.state.showEndUserAgreementModal) {
        // instruct Bootstrap to show the modal data-backdrop="static" data-keyboard="false"
        scope.modState(s => s.copy(login = true))
      }
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.login || scope.currentState.showPrivacyPolicyModal || scope.currentState.showTermsOfServicesForm || scope.currentState.showEndUserAgreementModal) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}

//
//object ConfirmAccountCreation {
//   @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)
//
//  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false)
//
//  class Backend(t: BackendScope[Props, State]) {
//    def submitForm(): Callback = {
//      // mark it as NOT cancelled (which is the default)
//         t.modState(s => s.copy(accountValidationFailed = true))
//    }
//    def hide /*= Callback*/ {
//      console.log("hide")
//      // instruct Bootstrap to hide the modal
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def updateToken(e: ReactEventI) = {
//      // update TodoItem content
//      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = e.target.value)))
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//         props.submitHandler(state.emailValidationModel, state.accountValidationFailed)
//    }
//
//    def render(s: State, p: Props) = {
//      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
////      if (s.accountValidationFailed) {
////        jQuery(t.getDOMNode()).modal("hide")
////      }
//      val headerText = "Confirm Account Creation"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//            closed = () => formClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//            <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//              <.div(DashBoardCSS.Style.modalHeaderFont)("Confirm Account Creation"),
//              <.h5("After registration, you were emailed a confirmation code. Please enter the code below"),
//              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.marginTop10px ,
//                ^.id := "Name", ^.placeholder:="Enter validation code",^.value:=s.emailValidationModel.token,^.onChange==>updateToken),
//              <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth, "Confirm", ^.onClick--> submitForm)
//            )
//            ,
//            <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//          )
//        )
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State(new EmailValidationModel("")))
//    .renderBackend[Backend]
//    .componentDidUpdate(scope => Callback{
//      if (scope.currentState.accountValidationFailed) {
//        scope.$.backend.hide
//      }
//    })
//    .build
//
//  def apply(props: Props) = component(props)
//}


//object AccountValidationSuccess {
//  // shorthand fo
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: () => Callback)
//
//  case class State(/*emailValidationModel: EmailValidationModel, validateAccount: Boolean = false*/)
//
//  class Backend(t: BackendScope[Props, State]) {
//    def hide = Callback{
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      //println("form closed")
//      props.submitHandler(/*state.emailValidationModel, state.validateAccount*/)
//    }
//
//    def render(s: State, p: Props) = {
//      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
//      val headerText = "Account Validation Success"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),*/ <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//        closed = () => formClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//             <.div(^.className:="row")(
//                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//                <.div(DashBoardCSS.Style.modalBodyText)("Account Validation Successful!",
//                  <.div(DashBoardCSS.Style.modalContentFont)(<.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide)("Login")))
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//}

//
//object RegistrationFailed {
//  // shorthand fo
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: (Boolean) => Callback)
//
//  case class State(login : Boolean = false)
//
//  class Backend(t: BackendScope[Props, State]) {
//
//    def hide = Callback{
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//    def login(): Callback  = {
//      t.modState(s=>s.copy(login = true))
//    }
//
//    def modalClosed(state: State, props: Props): Callback = {
//      props.submitHandler(state.login)
//    }
//
//    def render(s: State, p: Props) = {
//      val headerText = "Error"
////      if (s.login) {
////        jQuery(t.getDOMNode()).modal("hide")
////      }
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//
//        closed = () => modalClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//             <.div(^.className:="row")(
//                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//                <.div(DashBoardCSS.Style.modalBodyText)("This user already exists. Please try logging in!",
//                  <.div(DashBoardCSS.Style.modalContentFont)( <.button(^.tpe := "button",^.className:="btn btn-default",  ^.onClick-->hide )("Try again"), <.button(^.tpe := "button",DashBoardCSS.Style.MarginLeftchkproduct,^.className:="btn btn-default",  ^.onClick-->login )("Login"))
//
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .componentDidUpdate(scope => Callback{
//      if (scope.currentState.login) {
//        scope.$.backend.hide
//      }
//    })
//    .build
//
//  def apply(props: Props) = component(props)
//}
//object AccountValidationFailed {
//  // shorthand fo
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: () => Callback)
//
//  case class State()
//
//  class Backend(t: BackendScope[Props, State]) {
//
//    def hide = Callback{
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//
//    def modalClosed(state: State, props: Props): Callback = {
//      props.submitHandler()
//    }
//
//    def render(s: State, p: Props) = {
//      val headerText = "Error"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//
//        closed = () => modalClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//            <.div(^.className:="row")(
//              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//                <.div(DashBoardCSS.Style.modalBodyText)("Validation code you entered is incorrect, please check your email and enter valid code",
//                  <.div(DashBoardCSS.Style.modalContentFont)( <.button(^.tpe := "button",^.className:="btn btn-default",  ^.onClick-->hide )("Try again"))
//
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//}
//
//object ErrorModal {
//  // shorthand fo
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: () => Callback)
//
//  case class State()
//
//  class Backend(t: BackendScope[Props, State]) {
//
//    def closeForm = Callback{
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//
//    def modalClosed(state: State, props: Props): Callback = {
//      props.submitHandler()
//    }
//
//    def render(s: State, p: Props) = {
//      val headerText = "Error"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//
//        closed = () => modalClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//             <.div(^.className:="row")(
//                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//                <.div(DashBoardCSS.Style.modalBodyText)("Encountering problems in serving request. Please try after sometime!",
//                  <.div(DashBoardCSS.Style.modalContentFont)( <.button(^.tpe := "button",^.className:="btn btn-default",  ^.onClick-->closeForm )("Close"))
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//}

//
//object LoginFailed {
//  // shorthand fo
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: () => Callback)
//
//  case class State()
//
//  class Backend(t: BackendScope[Props, State]) {
//
//
//    def hide = Callback{
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//        props.submitHandler()
//    }
//
//    def render(s: State, p: Props) = {
//      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
//      val headerText = "Login Failed"
//      Modal(Modal.Props(
//        // header contains a cancel button (X)
//        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),*/ <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//             closed = () => formClosed(s, p)),
//
//        <.div(^.className:="row")(
//          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
//             <.div(^.className:="row")(
//                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
//                <.div(DashBoardCSS.Style.modalBodyText)("Login failed !",
//                  <.div(DashBoardCSS.Style.modalContentFont)(<.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide)("Try again"))
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//}