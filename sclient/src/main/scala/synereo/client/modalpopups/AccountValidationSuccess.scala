package synereo.client.modalpopups

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.components._
import synereo.client.css.{SignupCSS, SynereoCommanStylesCSS}

import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._

/**
  * Created by bhagyashree.b on 4/19/2016.
  */
//object AccountValidationSuccess {
//
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(submitHandler: () => Callback)
//
//  case class State()
//
//  class Backend(t: BackendScope[Props, State]) {
//    def hide = Callback {
//      jQuery(t.getDOMNode()).modal("hide")
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      // call parent handler with the new item and whether form was OK or cancelled
//      props.submitHandler()
//    }
//
//    def render(s: State, p: Props) = {
//      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
//      val headerText = "Account Validation Success"
//      Modal(
//        Modal.Props(
//          // header contains a cancel button (X)
//          header = hide => <.div(SignupCSS.Style.accountValidationSuccessText)(headerText),
//          closed = () => formClosed(s, p)
//        ),
//        <.div(^.className := "row")(
//          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
//            <.div(^.className := "row")(
//              <.div()(
//                <.div(^.className := "pull-right")(
//                  <.button(^.tpe := "button", SignupCSS.Style.signUpBtn, ^.className := "btn", ^.onClick --> hide, "Login")
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer)()
//      )
//    }
//  }
//
//  private val component = ReactComponentB[Props]("AccountValidationSuccessful")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .build
//
//  def apply(props: Props) = component(props)
//}



object AccountValidationSuccess {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State()

  class AccountValidationSuccessBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def formClosed(state: AccountValidationSuccess.State, props: AccountValidationSuccess.Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      props.submitHandler()
    }
  }

  private val component = ReactComponentB[Props]("AccountValidationFailed")
    .initialState_P(p => State())
    .backend(new AccountValidationSuccessBackend(_))
    .renderPS((t, P, S) => {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Account Validation Success"
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.div(SignupCSS.Style.accountValidationSuccessText)(headerText),
          closed = () => t.backend.formClosed(S, P)
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row")(
              <.div()(
                <.div(^.className := "pull-right")(
                  <.button(^.tpe := "button", SignupCSS.Style.signUpBtn, ^.className := "btn", ^.onClick --> t.backend.hide, "Login")
                )
              )
            )
          )
        ),
        <.div(bss.modal.footer)()
      )
    })
    .build

  def apply(props: Props) = component(props)
}
