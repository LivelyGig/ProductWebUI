package client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.LGMain.Loc
import client.LGMain.Loc
import client.components.Bootstrap.Button
import client.components.Bootstrap.CommonStyle
import client.components.Bootstrap._
import client.components.GlobalStyles
import client.components.Icon._
import client.components._
import client.css._
import client.logger._
import client.logger._
import shared.models.EmailValidationModel
import shared.models.UserModel
import shared.models.{ EmailValidationModel, UserModel }
import client.services.ApiTypes
import client.services.CoreApi
import client.services.CoreApi._
import client.services.CoreApi._
import client.services._
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Legal {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(buttonName: String, addStyles: Seq[StyleA] = Seq(), addIcons: Icon, title: String, dropDownModalDialog : Option[Boolean] = None)

  case class State(showTermsOfServicesForm: Boolean = false, showLegalForm: Boolean = false, showPrivacyPolicyForm: Boolean = false,
    showPrivacyPolicyModal: Boolean = false, showErrorModal: Boolean = false, showEndUserAgreementModal: Boolean = false,
    showTrademarksModal: Boolean = false, showCopyrightModal: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showLegalForm = true))
    }
    def addLegalForm(): Callback = {
      t.modState(s => s.copy(showLegalForm = true))
    }

    def Login(legal: Boolean = false, showPrivacyPolicyModal: Boolean = false,
      showTermsOfServicesForm: Boolean = false, showEndUserAgreementModal: Boolean = false, showTrademarksModal: Boolean = false,
      showCopyrightModal: Boolean = false): Callback = {
      //log.debug(s"Login agentLoginModel: ${agentLoginModel}, login: ${login}, showPrivacyPolicyModal: ${showPrivacyPolicyModal}")
      if (legal) {
        t.modState(s => s.copy(showLegalForm = false))
      } else if (showTrademarksModal) {
        console.log("Legal showtrademarks" + showTrademarksModal)
        t.modState(s => s.copy(showLegalForm = false, showTrademarksModal = true))
      } else if (showPrivacyPolicyModal) {
        t.modState(s => s.copy(showLegalForm = false, showPrivacyPolicyModal = true))
      } else if (showTermsOfServicesForm) {
        t.modState(s => s.copy(showPrivacyPolicyModal = false, showTermsOfServicesForm = true))
      } else if (showEndUserAgreementModal) {
        console.log("if(showEndUserAgreementModal) : " + showEndUserAgreementModal)
        t.modState(s => s.copy(showLegalForm = false, showEndUserAgreementModal = true))
      } else if (showCopyrightModal) {
        console.log("if(showCopyrightModal) : " + showCopyrightModal)
        t.modState(s => s.copy(showLegalForm = false, showCopyrightModal = true))
      } else {
        t.modState(s => s.copy(showLegalForm = false))
      }
    }

    def privacyPolicyModal(): Callback = {
      t.modState(s => s.copy(showPrivacyPolicyModal = false, showLegalForm = false))
    }
    def endUserAgreement(): Callback = {
      t.modState(s => s.copy(showEndUserAgreementModal = false, showLegalForm = false))
    }
    def termsOfServices(): Callback = {
      t.modState(s => s.copy(showTermsOfServicesForm = false, showLegalForm = true))
    }
    def tradeMarks(): Callback = {
      t.modState(s => s.copy(showTrademarksModal = false, showLegalForm = false))
    }
    def copyrights(): Callback = {
      t.modState(s => s.copy(showCopyrightModal = false, showLegalForm = false))
    }
  }

  val component = ReactComponentB[Props]("AddNewAgent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      <.div()(
//          P.dropDownModalDialog match {
//            case Some(true) =>
//              if (S.showTermsOfServicesForm) TermsOfServices(TermsOfServices.Props(B.termsOfServices))
//              else if (S.showTrademarksModal) TrademarksModal(TrademarksModal.Props(B.tradeMarks))
//              else if (S.showEndUserAgreementModal) EndUserAgreement(EndUserAgreement.Props(B.endUserAgreement))
//              else if (S.showCopyrightModal) CopyrightModal(CopyrightModal.Props(B.copyrights))
//              else if (true) LegalModal(LegalModal.Props(B.Login))
//              else if (S.showPrivacyPolicyModal) PrivacyPolicyModal(PrivacyPolicyModal.Props(B.privacyPolicyModal))
//              else
//                Seq.empty[ReactElement]
//
//            case None => Button(Button.Props(B.addLegalForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle), " ", ""), P.buttonName),
//          if (S.showTermsOfServicesForm) TermsOfServices (TermsOfServices.Props (B.termsOfServices) )
//          else if (S.showTrademarksModal) TrademarksModal (TrademarksModal.Props (B.tradeMarks) )
//          else if (S.showEndUserAgreementModal) EndUserAgreement (EndUserAgreement.Props (B.endUserAgreement) )
//          else if (S.showCopyrightModal) CopyrightModal (CopyrightModal.Props (B.copyrights) )
//          else if (S.showLegalForm) LegalModal (LegalModal.Props (B.Login) )
//          else if (S.showPrivacyPolicyModal) PrivacyPolicyModal (PrivacyPolicyModal.Props (B.privacyPolicyModal) )
//          else
//          Seq.empty[ReactElement]
//          }
//          )

      Button(Button.Props(B.addLegalForm(), CommonStyle.default, Seq(DashBoardCSS.Style.footLegalStyle), " ", ""), P.buttonName),
      if (S.showTermsOfServicesForm) TermsOfServices (TermsOfServices.Props (B.termsOfServices) )
      else if (S.showTrademarksModal) TrademarksModal (TrademarksModal.Props (B.tradeMarks) )
      else if (S.showEndUserAgreementModal) EndUserAgreement (EndUserAgreement.Props (B.endUserAgreement) )
      else if (S.showCopyrightModal) CopyrightModal (CopyrightModal.Props (B.copyrights) )
      else if (S.showLegalForm) LegalModal (LegalModal.Props (B.Login) )
      else if (S.showPrivacyPolicyModal) PrivacyPolicyModal (PrivacyPolicyModal.Props (B.privacyPolicyModal) )
      else
        Seq.empty[ReactElement]
      )

    })
    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)
}
