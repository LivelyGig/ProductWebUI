package l.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import l.client.components.Bootstrap._
import l.client.components._
import l.client.css.{HeaderCSS, DashBoardCSS,CreateAgentCSS}
import l.client.models.UserModel
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls

object LoginForm {
  //TodoForm
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (UserModel, Boolean, Boolean, Boolean) => Callback)

  case class State(userModel: UserModel, login: Boolean = false, showConfirmAccountCreation: Boolean = false,
                   showNewAgentForm: Boolean = false)

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
      val headerText = "Login"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            //left
            <.div(^.className := "col-md-7 col-sm-7 col-xs-7")(

              <.div(/*DashBoardCSS.Style.scltInputModalContainerMargin */)(
                <.div(DashBoardCSS.Style.modalHeaderFont)("Sign in with LivelyGig credentials"),
                <.input(^.tpe := "text", bss.formControl,  DashBoardCSS.Style.inputModalMargin,  ^.id := "Name",
                  ^.placeholder := "username", ^.value := s.userModel.email, ^.onChange ==> updateEmail, ^.required := true),
                <.input(^.tpe := "password", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.placeholder := "password"
                  , ^.value := s.userModel.password, ^.onChange ==> updatePassword, ^.required := true),
                <.button(^.tpe := "submit", ^.className := "btn btn-default", DashBoardCSS.Style.btnWidth, "Login"),

                <.div(^.paddingTop := "10px")(
                  <.div(^.className := "col-md-4 col-sm-4 col-xs-4",DashBoardCSS.Style.padding0px)(
                    <.button(^.tpe := "button", ^.className := "btn btn-default", /* DashBoardCSS.Style.btnWidth,*/ "Validate Account",
                      ^.onClick ==> showValidate)
                  ),
                  <.div(^.className := "col-md-3 col-sm-3 col-xs-3")(
                    <.button(^.tpe := "button", ^.className := "btn btn-default", /* DashBoardCSS.Style.btnWidth,*/ "Sign Up",
                      ^.onClick ==> showAddNewAgent)
                  ),
                  <.div(^.className := "col-md-4 col-sm-4 col-xs-4")(
                    <.button(^.tpe := "button", ^.className := "btn btn-default", /* DashBoardCSS.Style.btnWidth,*/ "Forgot My Password",
                      ^.onClick ==> showValidate)
                  )
                )
              )
            ),
            // right
            <.div(^.className := "col-md-5 col-sm-5 col-xs-5", ^.borderLeft := "solid")(
              <.div(DashBoardCSS.Style.modalHeaderFont)("Sign in with shared credentials"),
              <.button(^.tpe := "button", ^.className := "btn",CreateAgentCSS.Style.loginBtn, Icon.githubSquare, " GitHub"),
              <.button(^.tpe := "button", ^.className := "btn",CreateAgentCSS.Style.loginBtn, Icon.googlePlusSquare, " Google+"),
              <.button(^.tpe := "button", ^.className := "btn",CreateAgentCSS.Style.loginBtn, Icon.linkedinSquare, " LinkedIn"),
              <.button(^.tpe := "button", ^.className := "btn",CreateAgentCSS.Style.loginBtn, Icon.facebookSquare, " Facebook")
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
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.login || scope.currentState.showConfirmAccountCreation || scope.currentState.showNewAgentForm) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}
