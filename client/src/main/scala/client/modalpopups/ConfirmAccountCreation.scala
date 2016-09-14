package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.DashBoardCSS
import client.sessionitems.SessionItems
import shared.models.EmailValidationModel

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import org.querki.jquery._
import diode.AnyAction._
import org.querki.jquery._
import org.scalajs.dom
import org.scalajs.dom._

object ConfirmAccountCreation {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false,
                   hostName: String = s"https://${dom.window.location.hostname}:9876")

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, t.state.runNow().hostName)
      // mark it as NOT cancelled (which is the default)
      t.modState(s => s.copy(accountValidationFailed = true))
    }


    def updateIp(e: ReactEventI) = {
      val value = e.target.value
      //      println(s"value:$value")
      t.modState(s => s.copy(hostName = value))
    }

    def hide = {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }

    def updateToken(e: ReactEventI) = {
      // update TodoItem content
      val value = e.target.value
      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = value)))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler(state.emailValidationModel, state.accountValidationFailed)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Confirm Account Creation"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          closed = () => formClosed(s, p)
        ),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                // <.div(DashBoardCSS.Style.modalHeaderFont)("Confirm Account Creation"),
                <.h5("After registration, you were emailed a confirmation code. Please enter the code below"),
                <.div(^.className := "form-group")(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
                    ^.placeholder := "username", ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true)),
                <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, DashBoardCSS.Style.marginTop10px,
                  ^.id := "Name", ^.placeholder := "Enter validation code", ^.value := s.emailValidationModel.token, ^.onChange ==> updateToken),
                <.button(^.tpe := "submit", ^.className := "btn", DashBoardCSS.Style.btnWidth, DashBoardCSS.Style.btnBackground, "Confirm")
              ),
              <.div(bss.modal.footer, DashBoardCSS.Style.marginTop5p, DashBoardCSS.Style.marginLeftRight)()
            )
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("ConfirmAccountCreation")
    .initialState_P(p => State(new EmailValidationModel("")))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.accountValidationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}

