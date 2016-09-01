package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.PostNewInvite

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

class PostNewInviteBackend(t: BackendScope[PostNewInvite.Props, PostNewInvite.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")

  }

  def hideModal = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def mounted(props: PostNewInvite.Props): Callback = Callback {

  }

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    t.modState(s => s.copy(postMessage = false))
  }

  def formClosed(state: PostNewInvite.State, props: PostNewInvite.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    //      println(state.postMessage)
    props.submitHandler(state.postMessage)

  }
}