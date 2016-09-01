package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.RegistrationFailed

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

class RegistrationFailedBackend(t: BackendScope[RegistrationFailed.Props, RegistrationFailed.State]) {

  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def login(): Callback = {
    jQuery(t.getDOMNode()).modal("hide")
    t.modState(s => s.copy(registrationFailed = true))
  }

  def modalClosed(state: RegistrationFailed.State, props: RegistrationFailed.Props): Callback = {
    props.submitHandler(state.registrationFailed)
  }
}
