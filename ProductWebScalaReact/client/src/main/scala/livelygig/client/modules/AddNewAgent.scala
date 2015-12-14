package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.modules.AddNewAgent.State

import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import rx._
import rx.ops._
import livelygig.client.components.Bootstrap._
import livelygig.client.components.TodoList.TodoListProps
import livelygig.client.components._
import livelygig.client.logger._
import livelygig.client.services._
import livelygig.shared._
import livelygig.client.css.{HeaderCSS, DashBoardCSS}

object AddNewAgent {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)

  case class State(showNewAgentForm: Boolean = false, showLoginForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//    protected def observe[T](): Unit = {
//      val obs = rx.foreach(_ => scope.forceUpdate.runNow())
//      // stop observing when unmounted
//      onUnmount(Callback(obs.kill()))
//    }
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = Callback {
      // hook up to TodoStore changes
     // observe(props.todos)
      // dispatch a message to refresh the todos, which will cause TodoStore to fetch todos from the server
      MainDispatcher.dispatch(RefreshTodos)
    }
    def addNewAgentForm() : Callback = {   //editTodo
      // activate the todo dialog
      t.modState(s => s.copy(showNewAgentForm = true))   //showTOdoForm
    }
    def addNewLoginForm() : Callback = {   //editTodo
      // activate the todo dialog
      t.modState(s => s.copy(showLoginForm = true))   //showTOdoForm
    }
    def addNewAgent(name: String, email: String, pw: String) : Callback = {  //todoEdited
      // activate the todo dialog
      t.modState(s => s.copy(showNewAgentForm = false))
    }
    def addNewLogin(name: String) : Callback = {  //todoEdited
      // activate the todo dialog
      t.modState(s => s.copy(showLoginForm = false))
    }
  }

  // create the React component for ToDo management
  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State()) // initial state from TodoStore
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend

         Button(Button.Props(B.addNewAgentForm(), CommonStyle.default, Seq(DashBoardCSS.Style.backgroundTransperant)),<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg"),
           if (S.showNewAgentForm) AddNewAgentForm(AddNewAgentForm.Props(B.addNewAgent))
         else // otherwise add an empty placeholder
           Seq.empty[ReactElement])

//           Button(Button.Props(B.addNewLoginForm(), CommonStyle.default, Seq(DashBoardCSS.Style.backgroundTransperant)),<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg"),
//           if (S.showLoginForm) AddLoginForm(AddLoginForm.Props(B.addNewLogin))
//           else // otherwise add an empty placeholder
//             Seq.empty[ReactElement])


    })
  //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build

  /** Returns a function compatible with router location system while using our own props */
   // def apply(store: TodoStore) = (router: RouterCtl[Loc]) => {
  //  component(Props())
  def apply(props: Props) = component(props)
  }



object AddNewAgentForm {   //TodoForm
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (String, String, String) => Callback)

  case class NewAgent(/*name: String, email: String, pw: String ,*/showNewAgentForm: Boolean = false)

  class Backend(t: BackendScope[Props, NewAgent])/* extends RxObserver(t)*/ {

    def mounted(props: Props): Callback = Callback {
      // hook up to TodoStore changes
      // observe(props.todos)
      // dispatch a message to refresh the todos, which will cause TodoStore to fetch todos from the server
      MainDispatcher.dispatch(RefreshTodos)
    }
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy())
    }

    def formClosed(newAgent: NewAgent, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler("test", "test", "test")
    }



    def render(s: NewAgent, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Create New Agent"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        footer = hide => <.span(Button(Button.Props(()), "Create My Agent"), Button(Button.Props(submitForm() >> hide), "Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
            <.label(^.`for` := "Name", "Name")
          ),
          <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "Name")
          )
        ),
        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
            <.label(^.`for` := "Email", "Email")
          ),
          <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,^.id := "Email")
          )
        ),
        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
            <.label(^.`for` := "Password", "Password")
          ),
          <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
            <.input(^.tpe := "text", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Password")
          )
        ),
        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
            //<.input(^.`type` := "checkbox")
          ),
          <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
            <.input(^.`type` := "checkbox"),
            <.label(^.`for` := "Create BTC Wallet", "Create BTC Wallet", DashBoardCSS.Style.marginLeftchk)
          )
        )
      )
    }
  }

 private val component = ReactComponentB[Props]("AddNewAgentForm")
    .initialState_P(p => NewAgent(/*name = "test",email = "test",pw="test"*/))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

object AddLoginForm {   //TodoForm
// shorthand fo
@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (String) => Callback)

  case class NewLogin(name: String)

  class Backend(t: BackendScope[Props, NewLogin]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
      // println("form submitted")
      t.modState(s => s.copy())
    }

    def formClosed(newAgent: NewLogin, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler("test")
    }

    def render(s: NewLogin, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Email Validation"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // footer has the OK button that submits the form before hiding it
        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "Validate"), Button(Button.Props(submitForm() >> hide), "Cancel")),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.div()("Your request for a Splicious Agent has been submitted. Upon reciving your conformation email, you may click the link it containes or paste the token below to valdate your email address. "),
            <.div()("Your Token"),
            <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.slctInputWidthValidateLabel,^.id := "Name")
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => NewLogin(name = "test"))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}
