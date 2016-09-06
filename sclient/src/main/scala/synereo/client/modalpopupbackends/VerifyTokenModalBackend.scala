package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import scala.language.reflectiveCalls
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.VerifyTokenModal

/**
  * Created by mandar.k on 9/6/2016.
  */
//scalastyle:off
class VerifyTokenModalBackend(t: BackendScope[VerifyTokenModal.Props, VerifyTokenModal.State]) {

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    // mark it as NOT cancelled (which is the default)
    t.modState(s => s.copy(accountValidationFailed = true))
  }

  def hideModal = {
    // instruct Bootstrap to hide the modal
    jQuery(t.getDOMNode()).modal("hide")
    t.modState(s => s.copy(showLoginForm = true))
  }

  def updateToken(e: ReactEventI) = {
    // update TodoItem content
    val value = e.target.value
    t.modState(s => s.copy(emailValidationModel = s.emailValidationModel.copy(token = value)))
  }



  def formClosed(state: VerifyTokenModal.State, props: VerifyTokenModal.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    props.submitHandler(state.emailValidationModel, state.accountValidationFailed,state.showLoginForm)
  }
}
