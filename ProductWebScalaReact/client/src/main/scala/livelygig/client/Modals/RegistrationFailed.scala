package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS

import scalacss.ScalaCssReact._


object RegistrationFailed {
  // shorthand fo
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback)
  case class State(registrationFailed : Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def hide = Callback{
      jQuery(t.getDOMNode()).modal("hide")
    }
    def login(): Callback  = {
      t.modState(s=>s.copy(registrationFailed = true))
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler(state.registrationFailed)
    }

    def render(s: State, p: Props) = {
      val headerText = "Error"

      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(/*<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), */<.div(DashBoardCSS.Style.modalHeaderText)(headerText)),

        closed = () => modalClosed(s, p)),

        <.div(^.className:="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
             <.div(^.className:="row")(
                <.div(DashBoardCSS.Style.scltInputModalContainerMargin)(
                <.div(DashBoardCSS.Style.modalBodyText)("This user already exists. Please try logging in!",
                  <.div(DashBoardCSS.Style.modalContentFont)( <.button(^.tpe := "button",^.className:="btn btn-default",  ^.onClick-->hide )("Try again"), <.button(^.tpe := "button",DashBoardCSS.Style.MarginLeftchkproduct,^.className:="btn btn-default",  ^.onClick-->login )("Login"))

                )
              )
            )
          )
        ),
        <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
      )
    }
  }
  private val component = ReactComponentB[Props]("RegistrationFailed")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.registrationFailed) {
        scope.$.backend.hide
      }
    })
    .build

  def apply(props: Props) = component(props)
}