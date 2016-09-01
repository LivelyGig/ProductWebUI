package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.modalpopups.LoginFailed
import synereo.client.components.Bootstrap._

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

class LoginFailedBackend(t: BackendScope[LoginFailed.Props, LoginFailed.State]) {

  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def formClosed(state: LoginFailed.State, props: LoginFailed.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    props.submitHandler()
  }

}
