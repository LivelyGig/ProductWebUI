package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.AccountValidationFailed

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

class AccountValidationFailedBacked(t: BackendScope[AccountValidationFailed.Props, AccountValidationFailed.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def formClosed(state: AccountValidationFailed.State, props: AccountValidationFailed.Props): Callback = {
    props.submitHandler()
  }
}