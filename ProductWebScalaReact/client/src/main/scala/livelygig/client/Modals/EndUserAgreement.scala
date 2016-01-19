package livelygig.client.modals

import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import japgolly.scalajs.react.extra.router.RouterCtl
import livelygig.client.LGMain.{Loc}
import livelygig.client.services.CoreApi._
import org.scalajs.dom._
import scala.scalajs.js
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.logger._
import livelygig.client.services._
import livelygig.client.css.{HeaderCSS, DashBoardCSS,ProjectCSS,MessagesCSS}
import scala.concurrent.ExecutionContext.Implicits.global



object EndUserAgreement {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: () => Callback)
  case class State()
  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }
//    def updateName(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
//    }
//    def updateEmail(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
//    }
//    def updatePassword(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
//    }
//    def toggleBTCWallet(e: ReactEventI) = {
//      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
//    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
     // t.modState(s => s.copy(showEndUserAgreementModal = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
   //   println(state.showEndUserAgreementModal)
      props.submitHandler()
    }

    def render(s: State, p: Props) = {
          val headerText = "End User Agreement"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
       // <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("End User Agreement"))
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
            ),
            <.div()(
              "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
      //  )
      )
    }
  }
  private val component = ReactComponentB[Props]("EndUserAgreement")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}

