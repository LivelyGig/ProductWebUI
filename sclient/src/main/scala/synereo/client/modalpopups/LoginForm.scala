package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap._
import synereo.client.components._
import synereo.client.css.{SignupCSS, LoginCSS}
import synereo.client.models.UserModel
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._

/**
  * Created by Mandar on 4/19/2016.
  */
object LoginForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean, Boolean, Boolean) => Callback)

  case class State(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false,
                   showNewAgentForm: Boolean = false)

  val name: js.Object = "#Name"

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(login = true))
    }

    def hide = {
      jQuery(t.getDOMNode()).modal("hide")
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

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      props.submitHandler(state.userModel, state.login, state.showConfirmAccountCreation, state.showNewAgentForm)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Log In"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", ^.className := "hide", bss.close, ^.onClick --> hide, Icon.close), <.div(SignupCSS.Style.signUpHeading)(headerText)), /*<.div()(headerText)),*/
        closed = () => formClosed(s, p),
        addStyles = Seq(LoginCSS.Style.loginModalStyle)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12")(
              <.div()(
                <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "text", bss.formControl, ^.id := "Name",
                  ^.placeholder := "username", ^.value := s.userModel.email, ^.onChange ==> updateEmail, ^.required := true),
                <.input(SignupCSS.Style.inputStyleSignUpForm, ^.tpe := "password", bss.formControl, ^.placeholder := "password"
                  , ^.value := s.userModel.password, ^.onChange ==> updatePassword, ^.required := true),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-6 text-left", LoginCSS.Style.loginModalTextActionContainer)(
                    <.img(^.src := "./assets/synereo-images/CheckBox_Off.svg", LoginCSS.Style.checkBoxLoginModal /*, ^.onClick ==> changeCheckBox*/),
                    <.span("Keep me logged in", LoginCSS.Style.loginModalTextStyle)
                  ),
                  <.div(^.className := "col-md-6 text-right", LoginCSS.Style.loginModalTextActionContainer)(
                    <.a(^.href := "", LoginCSS.Style.loginModalTextStyle)("Forgot Password?")
                  )
                ),
                <.div(^.className := "text-center")(
                  <.button(^.tpe := "submit", LoginCSS.Style.modalLoginBtn, ^.className := "btn", "Login")
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer)()
      )

    }
  }

  private val component = ReactComponentB[Props]("AddLoginForm")
    .initialState_P(p => State(new UserModel("", "", "")))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.login || scope.currentState.showConfirmAccountCreation || scope.currentState.showNewAgentForm) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)

}