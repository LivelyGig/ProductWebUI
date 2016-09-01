package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.LoginForm
import synereo.client.sessionitems.SessionItems

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */


class LoginFormBackend(t: BackendScope[LoginForm.Props, LoginForm.State]) {

  val LoginFormID: js.Object = "#LoginForm"

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    val state = t.state.runNow()
    window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, state.apiURL)
    //window.sessionStorage.setItem(SessionItems.ApiDetails.API_HOST, state.hostName)
    //window.sessionStorage.setItem(SessionItems.ApiDetails.API_PORT, state.portNumber)
    val LoginBtn: js.Object = "#LoginBtn"
    if ($(LoginBtn).hasClass("disabled"))
      t.modState(s => s.copy(login = false))
    else
      t.modState(s => s.copy(login = true))
  }

  def addNewUserForm(): Callback = {
    t.modState(s => s.copy(login = false, showNewUserForm = true))
    //      t.modState(s => s.copy(showNewUserForm = true))
  }

  def addNewInviteForm(): Callback = {
    t.modState(s => s.copy(login = false, showNewInviteForm = true))
    //      t.modState(s => s.copy(showNewUserForm = true))
  }

  def hide = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def updateEmail(e: ReactEventI) = {
    //      println(e.target.value)
    val value = e.target.value
    t.modState(s => s.copy(userModel = s.userModel.copy(email = value)))
  }

  def showValidate(e: ReactEventI) = {
    t.modState(s => s.copy(showConfirmAccountCreation = true))
  }

  def updatePassword(e: ReactEventI) = {
    val value = e.target.value
    t.modState(s => s.copy(userModel = s.userModel.copy(password = value)))
  }

  def userNameFocus(): Unit = {
    $(LoginFormID).find("input:first").focus()
  }

  def updateAPIURL(e: ReactEventI) = {
    val value = e.target.value
    //      println(s"value:$value")
    t.modState(s => s.copy(apiURL = value))
  }

  def updateIp(e: ReactEventI) = {
    val value = e.target.value
    //      println(s"value:$value")
    t.modState(s => s.copy(hostName = value))
  }

  def updatePort(e: ReactEventI) = {
    val value = e.target.value
    //      println(s"value:$value")
    t.modState(s => s.copy(portNumber = value))
  }

  def formClosed(state: LoginForm.State, props: LoginForm.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    //      println(s"state.showNewAgentForm: ${state.showNewUserForm}")
    props.submitHandler(state.userModel, state.login, state.showConfirmAccountCreation, state.showNewUserForm, state.showNewInviteForm)
  }
}

