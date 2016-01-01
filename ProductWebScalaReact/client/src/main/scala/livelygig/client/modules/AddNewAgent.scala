package livelygig.client.modules

import livelygig.client.components.Bootstrap.Button
import livelygig.client.components.Bootstrap.Panel
import livelygig.client.components.Icon
import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}

import japgolly.scalajs.react.extra.router.RouterCtl
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.modules.AddNewAgent.State
//import livelygig.client.services.ApiService

import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import rx._
import rx.ops._
import livelygig.client.components.Bootstrap._
//import livelygig.client.components.TodoList.TodoListProps
import livelygig.client.components._
import livelygig.client.logger._
import livelygig.client.services._
import livelygig.shared._
import org.querki.jquery._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}

object AddNewAgent {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc])

  case class State(showNewAgentForm: Boolean = false, showLoginForm: Boolean = false, showValidateForm: Boolean = false, showConfirmAccountCreation: Boolean= false, showaccountValidationSuccess : Boolean =false
    ,showLoginFailed: Boolean = false,showRegistrationFailed: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
    //    protected def observe[T](): Unit = {
    //      val obs = rx.foreach(_ => scope.forceUpdate.runNow())
    //      // stop observing when unmounted
    //      onUnmount(Callback(obs.kill()))
    //    }
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = Callback {

    }
    def addNewAgentForm() : Callback = {

      t.modState(s => s.copy(showNewAgentForm = true))
    }
    def addNewLoginForm() : Callback = {
      // activate the todo dialog
      t.modState(s => s.copy(showLoginForm = true))
    }

    def addNewAgent(userModel: UserModel, addNewAgent: Boolean = false): Callback = {
     // println("in addNewAgent")
      if(addNewAgent){
        t.modState(s => s.copy(showNewAgentForm = false,showValidateForm = true))
      } else {
        t.modState(s => s.copy(showNewAgentForm = false))
      }

    }
    def Login(agentLoginModel: AgentLoginModel, login: Boolean = false) : Callback = {
      if (login){
        t.modState(s => s.copy(showLoginForm = false))
      } else {
        t.modState(s => s.copy(showLoginForm = false))
      }

    }
    def validateAccount(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false) : Callback = {
      if (validateAccount) {
        t.modState(s => s.copy(showValidateForm = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showValidateForm = false))
      }

    }
    def confirmAccountCreation(emailValidationModel: EmailValidationModel, confirmAccountCreation: Boolean = false) : Callback = {
      if (confirmAccountCreation) {
        t.modState(s => s.copy(showConfirmAccountCreation = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showConfirmAccountCreation = false))
      }
    }

    def accountValidationSuccess(emailValidationModel: EmailValidationModel, accountValidationSuccess: Boolean = false) : Callback = {
      if (accountValidationSuccess) {
        t.modState(s => s.copy(showaccountValidationSuccess = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showaccountValidationSuccess = false))
      }
    }

    def loginFailed(emailValidationModel: EmailValidationModel, loginFailed: Boolean = false) : Callback = {
      if (loginFailed) {
        t.modState(s => s.copy(showLoginFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showLoginFailed = false))
      }
    }

    def registrationFailed(emailValidationModel: EmailValidationModel, registrationFailed: Boolean = false) : Callback = {
      if (registrationFailed) {
        t.modState(s => s.copy(showRegistrationFailed = false, showLoginForm = true))
      } else {
        t.modState(s => s.copy(showRegistrationFailed = false))
      }
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State()) // initial state from TodoStore
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.addNewAgentForm(), CommonStyle.default, Seq(HeaderCSS.Style.SignUpBtn)),"Sign Up"),
         if (S.showNewAgentForm) NewAgentForm(NewAgentForm.Props(B.addNewAgent))
        else  if (S.showLoginForm  ) LoginForm(LoginForm.Props(B.Login))
        else  if (S.showValidateForm) ValidateAccount(ValidateAccount.Props(B.validateAccount))
        else   if (S.showConfirmAccountCreation  ) ConfirmAccountCreation(ConfirmAccountCreation.Props(B.confirmAccountCreation))
          else
        if (S.showaccountValidationSuccess) AccountValidationSuccess(AccountValidationSuccess.Props(B.accountValidationSuccess))
          else   if (S.showLoginFailed ) LoginFailed(LoginFailed.Props(B.loginFailed))
        else if (S.showRegistrationFailed ) RegistrationFailed(RegistrationFailed.Props(B.registrationFailed))
          else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  /** Returns a function compatible with router location system while using our own props */
  // def apply(store: TodoStore) = (router: RouterCtl[Loc]) => {
  //  component(Props())
  def apply(props: Props) = component(props)
}

object NewAgentForm {

// shorthand for styles
@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean) => Callback)

  case class State(userModel: UserModel, addNewAgent: Boolean = false)

  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {
      // hook up to TodoStore changes
      // observe(props.todos)
      // dispatch a message to refresh the todos, which will cause TodoStore to fetch todos from the server
      //MainDispatcher.dispatch(RefreshTodos)
    }
    def updateName(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }
    def updateEmail(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }
    def toggleBTCWallet(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }

    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("in AddNewAgentForm submitForm")
      //   ApiService.createUser("testemail@testemail.com", "pw", Map("name" -> "test"), true)
      t.modState(s => s.copy(addNewAgent = true))
    }


    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      // println("in AddNewAgetn form closed")
      props.submitHandler(state.userModel, state.addNewAgent)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Create New Agent"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit--> submitForm())(
        <.div(^.className:="row")(
         <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Create New Agent"))
        ),
        <.div(^.className:="row")(
          <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "First name *", "First name *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "First name",^.value:= s.userModel.name,
                  ^.onChange==>updateName,^.required:=true)
              )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Last name *", "Last name *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,^.id := "Last name")
              )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Email *", "Email *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email", ^.value:= s.userModel.email,
                  ^.onChange==>updateEmail,^.required:=true)
              )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Password *", "Password *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Password")
              )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Confirm Password *", "Confirm Password *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Confirm Password")
              )
            )
          ),//col-md-8
          <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                <.label(^.`for` := "Account type *", "Account type *")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
//                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
                <.input(^.`type` := "radio")(" client")
              )
            ),
            <.div(^.className:="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
//                <.label(^.`for` := "Client Name", "Client Name")
              ),
              <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "clientName" , ^.placeholder :="Client Name"),
                <.div()(<.input(^.`type` := "radio")(" freelancer")),
                <.div()(<.input(^.`type` := "radio")(" both")),
                <.div()(<.input(^.`type` := "radio")(" moderator"))
              )
            )
          )//col-md-4
        ),//main row
        <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
          <.div(DashBoardCSS.Style.MarginLeftchkagree,DashBoardCSS.Style.marginTop10px)(
            <.input(^.`type` := "checkbox")(" Yes, I understand and agree to the LivelyGig"),
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.label(^.`for` := "", DashBoardCSS.Style.marginLeftchk,
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Terms of Service ")(
                <.span(^.className:="caret")
              ),
              <.ul(^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("Privacy Policy and End User Agreement")),
                <.li()(<.a(^.href:="#")("Yes, I understand and agree to the LivelyGig Terms of Service")),
                <.li()(<.a(^.href:="#")("Privacy Policy and End User Agreement"))
              )
            )
            )
          ),
          <.div()(
            <.input(^.`type` := "checkbox")(" Yes, Send me product updates and other related emails from LivelyGig")

          ),
          <.div(DashBoardCSS.Style.MarginLeftchknotification, DashBoardCSS.Style.marginTop10px)(
            <.input(^.`type` := "checkbox")(" Yes, Send me notifications related to projects")

          )
        ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign,DashBoardCSS.Style.marginTop10px)("You will receive a via email a code confirming creation of your new account shortly after completing this form"),
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
            <.button(^.tpe := "submit",^.className:="btn btn-default","Submit"),
            <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
          )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
      )
      )
    }
  }
  private val component = ReactComponentB[Props]("NewAgentForm")
    .initialState_P(p => State(new UserModel("","","",false), false))
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}

object LoginForm {   //TodoForm
// shorthand fo
@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (AgentLoginModel, Boolean) => Callback)

  case class State(agentloginModel: AgentLoginModel, login: Boolean= false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(login = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.agentloginModel, state.login)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Login"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
//        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"), Button(Button.Props(submitForm() >> hide ), "Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalHeaderFont)("Login"),
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name", ^.placeholder:="username"),
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name", ^.placeholder:="password") ,
                <.button(^.tpe := "submit",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth,"Login")),

            <.div(^.className:="row",DashBoardCSS.Style.scltInputModalContainerMargin,DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.marginTop10px)(

                   <.div(^.className:="col-md-4")(
                     <.a("Validate Account")
                   ),
                  <.div(^.className:="col-md-4")(
                    <.a("Sign Up")
                  ),
                  <.div(^.className:="col-md-4")(
                    <.a("Forgot My Password")
                  )
            ),
            <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()

          )
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new AgentLoginModel("","")))
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}

object ValidateAccount {
// shorthand fo
@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(validateAccount = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.emailValidationModel, state.validateAccount)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Validate Account"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
//        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"),
//          <.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide,"Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            //            <.div()("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address. "),
            //            <.div()("Your Token"),
            //            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.slctInputWidthValidateLabel,^.id := "Name"),
            <.div(^.className:="row")(
              //              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              //                <.label(^.`for` := "Client Name", "Client Name")
              //              ),
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name", ^.placeholder:="Enter validation code")
              )
            )
          )
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("ValidateAccount")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}



object ConfirmAccountCreation {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(validateAccount = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.emailValidationModel, state.validateAccount)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Confirm Account Creation"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        //        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"),
        //          <.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide,"Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalHeaderFont)("Confirm Account Creation"),
                <.h5("After registration, you were emailed a confirmation code. Please enter the code below"),
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.marginTop10px ,^.id := "Name", ^.placeholder:="Enter validation code"),
                <.button(^.tpe := "submit",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth, "Confirm")
              )
            ,
            <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
          )
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


object AccountValidationSuccess {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(validateAccount = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.emailValidationModel, state.validateAccount)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Account Validation Success"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        //        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"),
        //          <.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide,"Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            //            <.div()("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address. "),
            //            <.div()("Your Token"),
            //            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.slctInputWidthValidateLabel,^.id := "Name"),
            <.div(^.className:="row")(
              //              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              //                <.label(^.`for` := "Client Name", "Client Name")
              //              ),
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("Account Validation Successful!")
              )
            )

          )
        ),
        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


object RegistrationFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(validateAccount = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.emailValidationModel, state.validateAccount)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Registration Failed"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        //        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"),
        //          <.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide,"Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            //            <.div()("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address. "),
            //            <.div()("Your Token"),
            //            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.slctInputWidthValidateLabel,^.id := "Name"),
            <.div(^.className:="row")(
              //              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              //                <.label(^.`for` := "Client Name", "Client Name")
              //              ),
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("Registration failed !",
                <.div(DashBoardCSS.Style.modalContentFont)( <.a("Go Back"))
                )
              )
            )
          )
        ),
      <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, validateAccount: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy(validateAccount = true))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.emailValidationModel, state.validateAccount)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Login Failed"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        //        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"),
        //          <.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide,"Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            //            <.div()("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address. "),
            //            <.div()("Your Token"),
            //            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.slctInputWidthValidateLabel,^.id := "Name"),
            <.div(^.className:="row")(
              //              <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
              //                <.label(^.`for` := "Client Name", "Client Name")
              //              ),
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("Login failed !",
                <.div(DashBoardCSS.Style.modalContentFont)(<.a("Go Back") )
              )
              )
            )
          )
        ),
        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}