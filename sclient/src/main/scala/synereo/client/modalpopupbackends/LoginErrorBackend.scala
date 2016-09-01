package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.LoginErrorModal

/**
  * Created by bhagyashree.b on 2016-09-01.
  */


class LoginErrorBackend(t: BackendScope[LoginErrorModal.Props, LoginErrorModal.State]) {
  def closeForm = {
    jQuery(t.getDOMNode()).modal("hide")
    t.modState(s => s.copy(showLogin = true))
  }

  def modalClosed(state: LoginErrorModal.State, props: LoginErrorModal.Props): Callback = {
    props.submitHandler(state.showLogin)
  }
}
