package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.DashBoardCSS
import client.services.LGCircuit

import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import org.querki.jquery._

object ServerErrorModal {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (Boolean) => Callback, msg: String)
  case class State(/*showApiDetailsForm:Boolean=false,*/showLoginForm:Boolean=false)
  class Backend(t: BackendScope[Props, State]) {
    def closeForm() =  {
      $(t.getDOMNode()).modal("hide")
      t.modState(s => s.copy(showLoginForm = true))
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler(state.showLoginForm)
    }

    def addLoginDetails(): Callback = Callback{
      $(t.getDOMNode()).modal("hide")
      //t.modState(s => s.copy(showApiDetailsForm = false, showLoginForm = true))
    }

    def render(s: State, p: Props) = {
      val headerText = "Error"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),

          closed = () => modalClosed(s, p)
        ),

        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)(
                  s"We are currently encountering problems in serving your request. ${p.msg}",
                  <.div(DashBoardCSS.Style.modalContentFont)(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault,DashBoardCSS.Style.btnBackground, ^.onClick --> closeForm)("Close")
                   // ApiDetailsForm(ApiDetailsForm.Props(addLoginDetails))

                  )
//                    ApiDetailsForm(ApiDetailsForm.Props(addLoginDetails))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop5p, DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("ErrorModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

