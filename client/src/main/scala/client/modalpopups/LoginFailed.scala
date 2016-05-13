package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.DashBoardCSS
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import org.querki.jquery._

object LoginFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: () => Callback, loginErrorMessage: String = "")
  case class State()
  class Backend(t: BackendScope[Props, State]) {

    def hide = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }

    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Login Failed"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
          closed = () => formClosed(s, p)
        ),

        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)(
                  /*p.loginErrorMessage*/ "The username and password combination that you are using is not correct. Please check and try again.",
                  <.div(DashBoardCSS.Style.modalContentFont)(<.button(^.tpe := "button", ^.className := "btn", DashBoardCSS.Style.btnBackground, ^.onClick --> hide)("Try again"))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop5p, DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("LoginFailed")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}