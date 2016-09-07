//package client.modalpopups
//
//import client.components.Bootstrap.Modal
//import client.components.{GlobalStyles, Icon}
////import client.components.Validator.ValidatorOptions
//import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, _}
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
//import client.components.Bootstrap._
////import client.components.Validator._
//import client.css.{CreateAgentCSS, DashBoardCSS, HeaderCSS}
//import shared.models.UserModel
//import scala.scalajs.js
//import scalacss.ScalaCssReact._
//import scala.language.reflectiveCalls
//import org.querki.jquery._
//import org.scalajs.dom
//import org.scalajs.dom._
//import client.sessionitems.SessionItems
//
//
///**
//  * Created by bhagyashree.b on 2016-08-03.
//  */
//object ApiDetailsForm {
//  //TodoForm
//  // shorthand fo
//  val modal: js.Object = "#modal"
//
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
// // $("#loginFormID".asInstanceOf[js.Object]).validator(ValidatorOptions.validate())
//
//  case class Props(submitHandler: (Boolean) => Callback)
//
//  case class State( login: Boolean = false, hostName: String = dom.window.location.hostname, portNumber: String = "9876")
//
//  class Backend(t: BackendScope[Props, State]) {
//
//    def submitForm(e: ReactEventI) = {
//      e.preventDefault()
//      val state = t.state.runNow()
//      window.sessionStorage.setItem(SessionItems.ApiDetails.API_HOST, state.hostName)
//      window.sessionStorage.setItem(SessionItems.ApiDetails.API_PORT, state.portNumber)
//      t.modState(s => s.copy(login = true))
//    }
//
//    def hideModal = Callback{
//      $(t.getDOMNode()).modal("hide")
//    }
//    def hide = {
//      $(t.getDOMNode()).modal("hide")
//    }
//
//    def userNameFocus(): Unit = {
//      $(modal).find("input:first").focus()
//    }
//
//    def formClosed(state: State, props: Props): Callback = {
//      props.submitHandler(state.login)
//    }
//
//    def updateIp(e: ReactEventI) = {
//      val value = e.target.value
//      //      println(s"value:$value")
//      t.modState(s => s.copy(hostName = value))
//    }
//
//    def updatePort(e: ReactEventI) = {
//      val value = e.target.value
//      //      println(s"value:$value")
//      t.modState(s => s.copy(portNumber = value))
//    }
//
//    def render(s: State, p: Props) = {
//      // log.debug(s"User is ${if (s.item.id == "") "adding" else "editing"} a todo")
//      val headerText = "API Details"
//      Modal(
//        Modal.Props(
//          // header contains a cancel button (X)
//          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
//          closed = () => formClosed(s, p)
//        ),
//        <.form(/*"data-toggle".reactAttr := "validator",*/ ^.role := "form", ^.onSubmit ==> submitForm)(
//          <.div(^.className := "row", DashBoardCSS.Style.marginLR)(
//            //left
//            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
//
//              <.div(/*DashBoardCSS.Style.scltInputModalContainerMargin */)(
//                <.div(^.className := "form-group")(
//                  <.div(^.className := "row")(
//                    <.div(^.className := "col-md-4")(
//                      <.div(DashBoardCSS.Style.modalHeaderFont)("Host Name")
//                    ),
//                    <.div(^.className := "col-md-8")(
//                      <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
//                        ^.placeholder := "username", ^.value := s.hostName, ^.onChange ==> updateIp, ^.required := true)
//                    )
//                  )
//                ),
//                <.div(^.className := "form-group")(
//                  <.div(^.className := "row")(
//                    <.div(^.className := "col-md-4")(
//                      <.div(DashBoardCSS.Style.modalHeaderFont)("Port number")
//                    ),
//                    <.div(^.className := "col-md-8")(
//                      <.input(^.tpe := "text", bss.formControl, DashBoardCSS.Style.inputModalMargin, ^.id := "Name",
//                        ^.placeholder := "username", ^.value := s.portNumber, ^.onChange ==> updatePort, ^.required := true)
//                    )
//                  )
//                ),
//                <.div(^.className := "text-center form-group")(
//                  <.button(^.tpe := "submit", ^.id := "LoginID", ^.className := "btn", DashBoardCSS.Style.btnBackground, "Submit"),
//                  <.button(^.tpe := "button", DashBoardCSS.Style.marginLeftCloseBtn , ^.className := "btn",^.onClick --> hideModal, DashBoardCSS.Style.btnBackground, "Cancel")
//                )
//              )
//            )
//          )
//        ),
//        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
//      )
//    }
//  }
//
//  private val component = ReactComponentB[Props]("AddLoginForm")
//    .initialState_P(p => State())
//    .renderBackend[Backend]
//    .componentDidMount(scope => Callback {
////      $(modal).on("shown.bs.modal", "", js.undefined, scope.backend.userNameFocus _)
//
//    })
//    .componentDidUpdate(scope => Callback{
//      if(scope.currentState.login)
//        scope.$.backend.hide
//    })
//    .build
//
//  def apply(props: Props) = component(props)
//}
