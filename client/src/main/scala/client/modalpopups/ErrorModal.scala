package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Bootstrap._
import client.components._
import client.css.DashBoardCSS
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._

object ErrorModal {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)
  case class State()
  class Backend(t: BackendScope[Props, State]) {
    def closeForm = Callback{
      jQuery(t.getDOMNode()).modal("hide")
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def render(s: State, p: Props) = {
      val headerText = "Error"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),

        closed = () => modalClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
             <.div(^.className:="row")(
                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("Encountering problems in serving request. Please try after sometime!",
                  <.div(DashBoardCSS.Style.modalContentFont)( <.button(^.tpe := "button",^.className:="btn btn-default",DashBoardCSS.Style.btnBackground,^.onClick-->closeForm )("Close"))
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("ErrorModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


