package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import org.querki.jquery._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import synereo.client.modalpopups.AmplifyPostForm

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */



class AmplifyPostBackend(t: BackendScope[AmplifyPostForm.Props, AmplifyPostForm.State]) {
  def hideModal = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def modalClosed(state: AmplifyPostForm.State, props: AmplifyPostForm.Props): Callback = {
    props.submitHandler()
  }

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    val state = t.state.runNow()
    t.modState(state => state.copy(isAmplified = true))
  }

  def check(): Boolean = {
    if ($("body".asInstanceOf[js.Object]).hasClass("modal-open"))
      true
    else
      false
  }

  def mounted(props: AmplifyPostForm.Props) = Callback {
    logger.log.debug("AmplifyPostForm mounted")
  }
}