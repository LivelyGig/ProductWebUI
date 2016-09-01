package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.AccountValidationSuccess

/**
  * Created by bhagyashree.b on 2016-09-01.
  */
class AccountValidationSuccessBackend(t: BackendScope[AccountValidationSuccess.Props, AccountValidationSuccess.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def formClosed(state: AccountValidationSuccess.State, props: AccountValidationSuccess.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    props.submitHandler()
  }
}