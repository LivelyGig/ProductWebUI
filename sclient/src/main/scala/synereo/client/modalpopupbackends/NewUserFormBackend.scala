package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom._
import shared.models.SignUpModel
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.NewUserForm
import synereo.client.sessionitems.SessionItems
import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

case class NewUserFormBackend(t: BackendScope[NewUserForm.Props, NewUserForm.State]) {

  var addNewUserState: Boolean = false
  var signUpModelUpdate = new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "", false)

  def hideModal = {
    addNewUserState = false
    signUpModelUpdate = new SignUpModel("", "", "", "", "", false, false, false, false, false, false, "", false)
    t.modState(s => s.copy(showLoginForm = true))
  }

  def hidecomponent = {
    // instruct Bootstrap to hide the modal
    jQuery(t.getDOMNode()).modal("hide")
  }

  def updateAPIURL(e: ReactEventI) = {
    val value = e.target.value
    t.modState(s => s.copy(apiURL = value))
  }

  def updateName(e: ReactEventI) = {
    val value = e.target.value
    t.modState(s => s.copy(signUpModel = s.signUpModel.copy(name = value)))
  }

  def updateConfirmPassword(e: ReactEventI) = {
    val value = e.target.value
    //      println(value)
    t.modState(s => s.copy(signUpModel = s.signUpModel.copy(confirmPassword = value)))
  }

  def updateEmail(e: ReactEventI) = {
    val value = e.target.value
    t.modState(s => s.copy(signUpModel = s.signUpModel.copy(email = value)))
  }

  def updatePassword(e: ReactEventI) = {
    val value = e.target.value
    //      println(value)
    t.modState(s => s.copy(signUpModel = s.signUpModel.copy(password = value)))
  }

  //    def toggleBTCWallet(e: ReactEventI) = {
  //      t.modState(s => s.copy(signUpModel = s.signUpModel.copy(createBTCWallet = !s.signUpModel.createBTCWallet)))
  //    }

  def showTermsOfServices(e: ReactEventI) = {
    addNewUserState = true
    t.modState(s => s.copy(showTermsOfServicesForm = true))
  }

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    val state = t.state.runNow()
    window.sessionStorage.setItem(SessionItems.ApiDetails.API_URL, state.apiURL)
    val SignUp: js.Object = "#SignUp"
    if ($(SignUp).hasClass("disabled"))
      t.modState(s => s.copy(addNewUser = false))
    else
      t.modState(s => s.copy(addNewUser = true))
  }

  def formClosed(state: NewUserForm.State, props: NewUserForm.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    //      println(state.addNewUser)
    signUpModelUpdate = state.signUpModel
    props.submitHandler(state.signUpModel, state.addNewUser, state.showLoginForm)
  }
}

