package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.Button
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.components.Bootstrap._
import livelygig.client.components.GlobalStyles
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.logger._
import livelygig.client.logger._
import livelygig.client.models.AgentLoginModel
import livelygig.client.models.EmailValidationModel
import livelygig.client.models.UserModel
import livelygig.client.models.{AgentLoginModel, EmailValidationModel, UserModel}
import livelygig.client.services.ApiResponseMsg
import livelygig.client.services.CoreApi
import livelygig.client.services.CoreApi._
import livelygig.client.services.CoreApi._
import livelygig.client.services._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

object Legal {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc])

  case class State(showTermsOfServicesForm: Boolean = false, showLegalForm: Boolean = false, showPrivacyPolicyForm: Boolean = false,
                   showPrivacyPolicyModal: Boolean= false,showErrorModal: Boolean = false,showEndUserAgreementModal : Boolean = false ,
                   showTrademarksModal : Boolean = false, showCopyrightModal: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showLegalForm = true))
    }
    def addLoginForm() : Callback = {
      t.modState(s => s.copy(showLegalForm = true))
    }
    def addNewLoginForm() : Callback = {
      t.modState(s => s.copy(showLegalForm = true))
    }

    def Login(agentLoginModel: AgentLoginModel, login: Boolean = false, showPrivacyPolicyModal: Boolean = false,
              showTermsOfServicesForm: Boolean = false ,showEndUserAgreementModal: Boolean = false ,showTrademarksModal : Boolean = false,
              showCopyrightModal : Boolean = false) : Callback = {
      log.debug(s"Login agentLoginModel: ${agentLoginModel}, login: ${login}, showPrivacyPolicyModal: ${showPrivacyPolicyModal}")
      if (login){
        CoreApi.agentLogin(agentLoginModel).onComplete {
          case Success(s) =>
            log.debug(s"loginAPISuccessMsg: ${s.msgType}")
            if (s.msgType == ApiResponseMsg.InitializeSessionResponse){
              // todo add functionality after login may involve dispatching of certain events
              window.localStorage.setItem("sessionURI",s.content.sessionURI.getOrElse(""))
              log.debug("login successful")
              window.location.href = "/#connections"
            }/* else {
              log.debug("login failed")
              t.modState(s => s.copy(showLoginFailed = true)).runNow()
            }*/
          case Failure(s) =>
            println("internal server error")
        }
        t.modState(s => s.copy(showLegalForm = false))
      }
      else if (showTrademarksModal) {
        console.log("Legal showtrademarks" + showTrademarksModal)
        t.modState(s => s.copy(showLegalForm = false, showTrademarksModal = true))
      }
      else if (showPrivacyPolicyModal) {
        t.modState(s => s.copy(showLegalForm = false, showPrivacyPolicyModal = true))
      }
      else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showPrivacyPolicyModal = false, showTermsOfServicesForm = true))
      }
      else if (showEndUserAgreementModal) {
        console.log("if(showEndUserAgreementModal) : " + showEndUserAgreementModal)
        t.modState(s => s.copy(showLegalForm = false,showEndUserAgreementModal = true))
      }
      else if (showCopyrightModal) {
        console.log("if(showCopyrightModal) : " + showCopyrightModal)
        t.modState(s => s.copy(showLegalForm = false,showCopyrightModal = true))
      }
      else {
        t.modState(s => s.copy(showLegalForm = false))
      }
    }

     def serverError() : Callback = {
      t.modState(s => s.copy(showErrorModal = false))
    }
    def privacyPolicyModal() : Callback = {
      t.modState(s => s.copy(showPrivacyPolicyModal = false, showLegalForm = false))
    }
    def endUserAgreement() : Callback = {
      t.modState(s => s.copy(showEndUserAgreementModal = false, showLegalForm = false))
    }
    def termsOfServices() : Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showLegalForm = false))
    }
    def tradeMarks() : Callback = {
      t.modState(s => s.copy(showTrademarksModal = false, showLegalForm = false))
    }
    def copyrights() : Callback = {
      t.modState(s => s.copy(showCopyrightModal = false, showLegalForm = false))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
        Button(Button.Props(B.addLoginForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle)),"Legal"),
        if (S.showTermsOfServicesForm) TermsOfServices(TermsOfServices.Props(B.termsOfServices))
        else if (S.showTrademarksModal) TrademarksModal(TrademarksModal.Props(B.tradeMarks))
        else if (S.showEndUserAgreementModal) EndUserAgreement(EndUserAgreement.Props(B.endUserAgreement))
        else if (S.showCopyrightModal) CopyrightModal(CopyrightModal.Props(B.copyrights))
        else if (S.showLegalForm) LegalModal(LegalModal.Props(B.Login))
        else if (S.showPrivacyPolicyModal) PrivacyPolicyModal(PrivacyPolicyModal.Props(B.privacyPolicyModal))
        else if (S.showErrorModal) ErrorModal(ErrorModal.Props(B.serverError))
        else
          Seq.empty[ReactElement]
      )
    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}
