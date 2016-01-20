package livelygig.client.modals

import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import livelygig.client.LGMain.{Loc}
import org.scalajs.dom._
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by bhagyashree.b on 1/18/2016.
  */

object LegalModal {   //TodoForm
// shorthand fo
@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: (AgentLoginModel, Boolean, Boolean, Boolean , Boolean , Boolean, Boolean) => Callback)

  case class State(agentloginModel: AgentLoginModel, login: Boolean= false, showPrivacyPolicyModal: Boolean = false,
                   showTermsOfServicesForm: Boolean = false , showEndUserAgreementModal: Boolean = false,showTrademarksModal : Boolean = false,showCopyrightModal : Boolean = false)

  class Backend(t: BackendScope[Props, State]) {
    def submitForm(e:ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(login = true))
      }

    def hide /*= Callback*/ {
      console.log("hide")
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
//    def updateEmail(e:ReactEventI) = {
//      t.modState(s => s.copy(agentloginModel = s.agentloginModel.copy(email = e.target.value)))
//    }
    def showPrivacyPolicy(e:ReactEventI) = {
      console.log("in showPrivacyPolicy ")
      t.modState(s => s.copy(showPrivacyPolicyModal = true))
    }
    def showTrademarks(e:ReactEventI) = {
      console.log("in tradeMarks ")
      t.modState(s => s.copy(showTrademarksModal = true))
    }
    def showCopyright(e:ReactEventI) = {
      console.log("in tradeMarks ")
      t.modState(s => s.copy(showCopyrightModal = true))
    }
    def showEndUserAgreement(e:ReactEventI) = {
      t.modState(s => s.copy(showEndUserAgreementModal = true))
    }
    def showTermsOfServices(e:ReactEventI) = {
      t.modState(s => s.copy(showTermsOfServicesForm = true))
    }
//    def updatePassword(e:ReactEventI) = {
//      t.modState(s => s.copy(agentloginModel = s.agentloginModel.copy(password = e.target.value)))
//    }


    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      //println("form closed")
      println("state.showTrademarksModal : " + state.showTrademarksModal)
      props.submitHandler(state.agentloginModel, state.login, state.showPrivacyPolicyModal, state.showTermsOfServicesForm , state.showEndUserAgreementModal ,state.showTrademarksModal,state.showCopyrightModal)
    }
    def render(s: State, p: Props) = {
      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
      val headerText = "Legal"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className:="row")(
            <.div(^.className:="col-md-12 col-sm-12")(<.div(DashBoardCSS.Style.modalHeaderFont,MessagesCSS.Style.paddingLeftModalHeaderbtn)("Legal"))
          ),//main row
          <.div(^.className:="row" , DashBoardCSS.Style.MarginLeftchkproduct)(
            <.ul()(
              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"Privacy Policy",^.onClick==>showPrivacyPolicy)),
              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"End User Agreement",^.onClick==>showEndUserAgreement)),
              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"Terms of Service" ,^.onClick==>showTermsOfServices)),
              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"Trademarks",^.onClick==>showTrademarks)),
              <.li()(<.button(^.tpe := "button",^.className:="btn btn-default",FooterCSS.Style.legalModalBtn,"Copyright",^.onClick==>showCopyright))
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding,DashBoardCSS.Style.footTextAlign)(
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ), <.div(bss.modal.footer,DashBoardCSS.Style.marginTop5p,DashBoardCSS.Style.marginLeftRight)()
        ) //submitform
      )

    }
  }
  private val component = ReactComponentB[Props]("LegalModal")
    .initialState_P(p => State(new AgentLoginModel("","")))
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback{
      if (scope.currentState.login || scope.currentState.showPrivacyPolicyModal || scope.currentState.showTermsOfServicesForm || scope.currentState.showEndUserAgreementModal
        || scope.currentState.showTrademarksModal || scope.currentState.showCopyrightModal) {
        scope.$.backend.hide
      }
    })
    .build
  def apply(props: Props) = component(props)
}

