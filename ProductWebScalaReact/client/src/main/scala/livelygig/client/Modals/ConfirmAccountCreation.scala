package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.models.EmailValidationModel
import org.scalajs.dom._

import scalacss.ScalaCssReact._


object ConfirmAccountCreation {
   @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (EmailValidationModel, Boolean) => Callback)

  case class State(emailValidationModel: EmailValidationModel, accountValidationFailed: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      // mark it as NOT cancelled (which is the default)
         t.modState(s => s.copy(accountValidationFailed = true))
    }
    def hide /*= Callback*/ {
      console.log("hide")
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def updateToken(e: ReactEventI) = {
      // update TodoItem content
      t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = e.target.value)))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
         props.submitHandler(state.emailValidationModel, state.accountValidationFailed)
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Confirm Account Creation"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
            closed = () => formClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
              <.div(DashBoardCSS.Style.modalHeaderFont)("Confirm Account Creation"),
              <.h5("After registration, you were emailed a confirmation code. Please enter the code below"),
              <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,DashBoardCSS.Style.marginTop10px ,
                ^.id := "Name", ^.placeholder:="Enter validation code",^.value:=s.emailValidationModel.token,^.onChange==>updateToken),
              <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnWidth, "Confirm", ^.onClick--> submitForm)
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
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.accountValidationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}

