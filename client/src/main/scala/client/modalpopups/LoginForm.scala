package client.modals

import client.LGMain.{DashboardLoc, Loc}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.{CreateAgentCSS, DashBoardCSS, HeaderCSS}
import shared.models.UserModel
import client.sessionitems.SessionItems
import scala.scalajs.js
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._


object LoginForm {
  //TodoForm
  // shorthand fo
  val modal: js.Object = "#modal"

  @inline private def bss = GlobalStyles.bootstrapStyles

  val LoginID: js.Object = "#LoginID"

  case class Props(submitHandler: (UserModel, Boolean, Boolean, Boolean) => Callback)

  case class State(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false,
                   showNewAgentForm: Boolean = false, hostName: String = s"https://${dom.window.location.hostname}:9876")

  class Backend(t: BackendScope[Props, State]) {

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, state.hostName)
      if ($(LoginID).hasClass("disabled")) {
        t.modState(s => s.copy(login = false))
      }
      else {
        t.modState(s => s.copy(login = true))
      }
    }

    def hide = {
      $(t.getDOMNode()).modal("hide")
    }

    def updateEmail(e: ReactEventI) = {
      //      println(e.target.value)
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
    }

    def showValidate(e: ReactEventI) = {
      t.modState(s => s.copy(showConfirmAccountCreation = true))
    }

    def showAddNewAgent(e: ReactEventI) = {
      t.modState(s => s.copy(showNewAgentForm = true))
    }

    def updatePassword(e: ReactEventI) = {
      val value = e.target.value
      t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
    }

    def userNameFocus(): Unit = {
      $(modal).find("input:first").focus()
    }

    def updateIp(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(hostName = value))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      //  LoginValidation(state.userModel)
      props.submitHandler(state.userModel, state.login, state.showConfirmAccountCreation, state.showNewAgentForm)
    }


    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Log In"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          closed = () => formClosed(s, p),
          id = "loginContainer"
        ),

        <.form(^.id := "loginFormID", "data-toggle".reactAttr := "validator", ^.role := "form", ^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-7 col-sm-12 col-xs-12")(
              <.div(/*DashBoardCSS.Style.scltInputModalContainerMargin */)(
                <.div(DashBoardCSS.Style.modalHeaderFont)("Log In with LivelyGig credentials"),
                <.div(^.className := "form-group")(
                    <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
                        ^.placeholder := "username", ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true)


                ),
                <.div(^.className := "form-group")(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name", ^.className := "form-control", "data-error".reactAttr := "Username is required",
                    ^.placeholder := "username", ^.value := s.userModel.email, ^.onChange ==> updateEmail, ^.required := true),
                  <.div(^.className := "help-block with-errors")
                ),
                <.div(^.className := "form-group")(
                  <.input(^.tpe := "password", DashBoardCSS.Style.inputModalMargin, bss.formControl, ^.placeholder := "password", ^.className := "form-control", "data-error".reactAttr := "Password is required",
                    ^.value := s.userModel.password, ^.onChange ==> updatePassword, ^.required := true),
                  <.div(^.className := "help-block with-errors")
                ),
                <.div(^.className := "text-center", ^.className := "form-group")(
                  <.button(^.tpe := "submit", ^.id := "LoginID", ^.className := "btn", DashBoardCSS.Style.btnBackground, "Login")
                ),
                <.div(DashBoardCSS.Style.paddingTop10px)(
                  <.div(^.className := "col-md-6 col-sm-6 col-xs-12", DashBoardCSS.Style.padding0px, DashBoardCSS.Style.paddingTop10px, ^.textAlign.center)(
                    <.button(^.tpe := "button", ^.className := "btn-link", /*DashBoardCSS.Style.btnBackground,*/ "Sign Up",
                      ^.onClick ==> showAddNewAgent)
                  ),
                  <.div(^.className := "col-md-6 col-sm-6 col-xs-12", DashBoardCSS.Style.padding0px, DashBoardCSS.Style.paddingTop10px, ^.textAlign.center)(
                    <.button(^.tpe := "button", ^.className := "btn-link", /*DashBoardCSS.Style.btnBackground,*/ "Validate Account",
                      ^.onClick ==> showValidate)
                  )
                  // <.div(^.className := "col-md-4 col-sm-4 col-xs-12", DashBoardCSS.Style.padding0px, DashBoardCSS.Style.paddingTop10px, ^.textAlign.center)(
                  //  <.button(^.tpe := "button", ^.className := "btn-link", /*DashBoardCSS.Style.btnBackground,*/ "Forgot My Password",
                  //    ^.onClick ==> showValidate)
                  // )
                )
              )
            ),
            <.div(^.className := "col-md-5 col-sm-12 col-xs-12", DashBoardCSS.Style.linksConatiner)(
              <.div(DashBoardCSS.Style.modalHeaderFont)("Log In with shared credentials"),
              <.button(^.tpe := "button", ^.className := "btn", CreateAgentCSS.Style.loginBtn, Icon.githubSquare, " GitHub"),
              <.button(^.tpe := "button", ^.className := "btn", CreateAgentCSS.Style.loginBtn, Icon.googlePlusSquare, " Google+"),
              <.button(^.tpe := "button", ^.className := "btn", CreateAgentCSS.Style.loginBtn, Icon.linkedinSquare, " LinkedIn"),
              <.button(^.tpe := "button", ^.className := "btn", CreateAgentCSS.Style.loginBtn, Icon.facebookSquare, " Facebook")
            )
          )
        ),
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }

  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .componentDidMount(scope => Callback {
      println(s"DidMount ${scope.state}")
      $(modal).on("shown.bs.modal", "", js.undefined, scope.backend.userNameFocus _)
    })
    .componentDidUpdate(scope => Callback {
      println(s"DidUpdate ${scope.currentState}")
      if (scope.currentState.login || scope.currentState.showConfirmAccountCreation || scope.currentState.showNewAgentForm) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}
