package l.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import l.client.components.Bootstrap._
import l.client.components._
import l.client.css.DashBoardCSS
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._

object AccountValidationSuccess {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: () => Callback)
  case class State()

  class Backend(t: BackendScope[Props, State]) {
    def hide = Callback{
      jQuery(t.getDOMNode()).modal("hide")
    }
    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
       props.submitHandler()
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Account Validation Success"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span( <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        closed = () => formClosed(s, p)),
        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
             <.div(^.className:="row")(
                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("Account Validation Successful!",
                  <.div(DashBoardCSS.Style.modalContentFont)(<.button(^.tpe := "button",^.className:="btn btn-default", ^.onClick --> hide)("Login")))
              )
            )
          )
        ),
        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("AccountValidationSuccessful")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}





