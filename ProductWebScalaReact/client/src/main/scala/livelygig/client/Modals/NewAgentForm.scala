package livelygig.client.modals

import livelygig.client.modals.AddNewAgent
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css.{FooterCSS, DashBoardCSS, HeaderCSS}
import livelygig.client.logger._
import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import livelygig.client.services.CoreApi._
import livelygig.client.services._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

/*
*
  * Created by bhagyashree.b on 1/18/2016.
object NewAgentForm {

}
*/

object NewAgentForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (UserModel, Boolean,Boolean) => Callback)
  case class State(userModel: UserModel, addNewAgent: Boolean = false , showTermsOfServicesForm : Boolean = false)

  case class Backend(t: BackendScope[Props, State])/* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hidecomponent /*= Callback */{
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {

    }
    def updateName(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(name = e.target.value)))
    }
    def updateEmail(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(email = e.target.value)))
    }
    def updatePassword(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(password = e.target.value)))
    }
    def toggleBTCWallet(e: ReactEventI) = {
      t.modState(s => s.copy(userModel = s.userModel.copy(createBTCWallet = !s.userModel.createBTCWallet)))
    }
    def showTermsOfServices(e:ReactEventI) = {
      t.modState(s => s.copy(showTermsOfServicesForm = true))
    }
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(addNewAgent = true))
    }
    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.addNewAgent)
      props.submitHandler(state.userModel, state.addNewAgent ,state.showTermsOfServicesForm)

    }

    def render(s: State, p: Props) = {
      //      if (s.addNewAgent){
      //        jQuery(t.getDOMNode()).modal("hide")
      //      }
      val headerText = "Create New Agent"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont)("Create New Agent"))
          ),
          <.div(^.className:="row")(
            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "First name *", "First name *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "First name",^.value:= s.userModel.name,
                    ^.onChange==>updateName,^.required:=true)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Last name *", "Last name *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin,^.id := "Last name")
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Email *", "Email *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "email", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Email", ^.value:= s.userModel.email,
                    ^.onChange==>updateEmail,^.required:=true)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Password *", "Password *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "password", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Password", ^.value:= s.userModel.password,
                    ^.onChange==>updatePassword,^.required:=true)
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Confirm Password *", "Confirm Password *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "password", bss.formControl,DashBoardCSS.Style.inputModalMargin, ^.id := "Confirm Password",^.required:=true)
                )
              )
            ),//col-md-8
            <.div(^.className:="col-md-6 col-sm-6 col-xs-6")(
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  <.label(^.`for` := "Account type *", "Account type *")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.div()(
                    <.input(^.`type` := "radio")," client"
                  )
                )
              ),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",DashBoardCSS.Style.slctInputWidthLabel)(
                  //                <.label(^.`for` := "Client Name", "Client Name")
                ),
                <.div(DashBoardCSS.Style.scltInputModalLeftContainerMargin)(
                  <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin ,^.id := "clientName" , ^.placeholder :="Client Name"),
                  <.div()(<.input(^.`type` := "radio")," freelancer"),
                  <.div()(<.input(^.`type` := "radio")," both"),
                  <.div()(<.input(^.`type` := "radio")," moderator")
                )
              )
            )//col-md-4
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(DashBoardCSS.Style.marginTop10px)(
//              <.div()(
//                <.input(^.`type` := "checkbox")," Yes, I understand and agree to the LivelyGig",
//                <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
//                  <.label(^.`for` := "", DashBoardCSS.Style.marginLeftchk,
//                    <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Terms of Service ")(
//                      <.span(^.className:="caret")
//                    ),
//                    <.ul(^.className:="dropdown-menu")(
//                      <.li()(<.a(^.href:="#")("Privacy Policy and End User Agreement")),
//                      <.li()(<.a(^.href:="#")("Yes, I understand and agree to the LivelyGig Terms of Service")),
//                      <.li()(<.a(^.href:="#")("Privacy Policy and End User Agreement"))
//                    )
//                  )
//                )
//              )
            <.div()(  <.input(^.`type` := "checkbox")," Yes, I understand and agree to the LivelyGig",
                      <.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"Terms of Service ",^.onClick==>showTermsOfServices))
            ),
            <.div()(
              <.input(^.`type` := "checkbox")," Yes, Send me product updates and other related emails from LivelyGig"

            ),
            <.div(DashBoardCSS.Style.marginTop10px)(
              <.div()(
                <.input(^.tpe := "checkbox") ," Yes, Send me notifications related to projects"
              )
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign,DashBoardCSS.Style.marginTop10px)("You will receive a via email a code confirming creation of your new account shortly after completing this form"),
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              <.button(^.tpe := "submit",^.className:="btn btn-default","Submit"),
              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }
  private val component = ReactComponentB[Props]("NewAgentForm")
    .initialState_P(p => State(new UserModel("","","",false)))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.addNewAgent || scope.currentState.showTermsOfServicesForm) {
        scope.$.backend.hidecomponent
      }
    })

    .build
  def apply(props: Props) = component(props)
}