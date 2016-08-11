package client.modalpopups

import client.components.Icon
import client.css.{DashBoardCSS, FooterCSS, WorkContractCSS}
import japgolly.scalajs.react.vdom.prefix_<^._
import client.modules.MainMenu.{Backend, State}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handler.{LoginUser, LogoutUser}
import client.LGMain._
import client.components.Bootstrap.CommonStyle
import client.modals.{AgentLoginSignUp, ConfirmIntroReq, Legal, NewMessage}
import client.components._
import client.css.{DashBoardCSS, FooterCSS, HeaderCSS, WorkContractCSS}
import shared.models.UserModel
import client.services.LGCircuit
import shared.dtos._
import client.components.Bootstrap._
import client.modals.TermsOfServices.State

import scala.scalajs.js
import scala.util._
import scalacss.ScalaCssReact._
import org.querki.jquery._

import scala.language.reflectiveCalls

/**
  * Created by bhagyashree.b on 2016-07-18.
  */
object HeaderDropdownDialogs {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props()
  case class State()

  case class Backend(t: BackendScope[Props, State]) {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      $(t.getDOMNode()).modal("hide")
    }
    def mounted(props: Props): Callback = Callback {
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
    }



    def render(s: State, p: Props) = {
      <.div(

        <.div(^.className := "modal fade", ^.id := "myModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
          <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
            <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
              <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
                <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
                  <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
                ),
                <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                  <.h2("hello"),
                  <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
                )
              )
            )
          )
        ),
          <.div(^.className := "modal fade", ^.id := "aboutModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
        <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
          <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
            <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
              <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
                <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("About"))
              ),
              <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
                  <.ul()(
                    <.li()(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault, "data-toggle".reactAttr := "modal", "data-target".reactAttr := "#legalModal", "aria-haspopup".reactAttr := "true", FooterCSS.Style.legalModalBtn, "Privacy Policy")),
                    <.li()(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault, "data-toggle".reactAttr := "modal", "data-target".reactAttr := "#legalModal",FooterCSS.Style.legalModalBtn, "End User Agreement")),
                    <.li()(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault,"data-toggle".reactAttr := "modal", "data-target".reactAttr := "#legalModal", FooterCSS.Style.legalModalBtn, "Terms of Service")),
                    <.li()(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault,"data-toggle".reactAttr := "modal", "data-target".reactAttr := "#legalModal", FooterCSS.Style.legalModalBtn, "Trademarks and Credits")),
                    <.li()(<.button(^.tpe := "button", ^.className := "btn",DashBoardCSS.Style.btnDefault, "data-toggle".reactAttr := "modal", "data-target".reactAttr := "#legalModal",FooterCSS.Style.legalModalBtn, "Copyright"))
                  )
                ),
                <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
              )
            )
          )
        )
      ),


//        <.div(^.className := "modal fade", ^.id := "legalModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
//          <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
//            <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
//              <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
//                <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
//                  <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
//                ),
//                <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
//
//                    <.div(^.color :="black")(
//                      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
//
//                    )
//                )
//              )
//            )
//          )
//        ),



      <.div(^.className := "modal fade", ^.id := "accountModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
        <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
          <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
            <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
              <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
                <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
              ),
              <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
                  <.div(
                    <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter USER ID"/*,^.value := s.agentUid, ^.onChange ==> updateAgentUid*/)
                    //                       ,
                    //                <.div(^.id := "agentFieldError", ^.className := "hidden")
                    //                ("User with this uid is already added as your connection")
                  ),

                  <.div()(
                    <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
                      <.button(^.tpe := "submit", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn,"data-dismiss".reactAttr := "modal", "Send"),
                      <.button(^.tpe := "button", ^.className := "btn",^.marginLeft:="20.px", WorkContractCSS.Style.createWorkContractBtn,"data-dismiss".reactAttr := "modal", "Cancel")
                    )
                  )
                ),
                <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
              )
            )
          )
        )
      )
      )


    }
  }
  private val component = ReactComponentB[Props]("HeaderDropdownDialogs")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)






}
