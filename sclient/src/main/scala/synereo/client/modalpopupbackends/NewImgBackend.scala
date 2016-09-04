package synereo.client.modalpopupbackends

import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom.raw.{FileReader, UIEvent}
import shared.dtos.JsonBlob
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.ProfileImageUploaderForm
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.ContentUtils

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

case class NewImgBackend(t: BackendScope[ProfileImageUploaderForm.Props, ProfileImageUploaderForm.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def hideModal = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def mounted(): Callback = Callback {
    // logger.log.info("new Image modal mounted")
  }

  def updateImgSrc(e: ReactEventI): react.Callback = Callback {
    val value = e.target.files.item(0)
    val reader = new FileReader()
    reader.onload = (e: UIEvent) => {
      val contents = reader.result.asInstanceOf[String]
      val props = t.props.runNow()
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      t.modState(s => s.copy(updateUserRequest = s.updateUserRequest.copy(sessionURI = uri, jsonBlob = JsonBlob(imgSrc = contents, name = props.proxy().name)))).runNow()
    }
    reader.readAsDataURL(value)
    $("#image_upload_error".asInstanceOf[js.Object]).addClass("hidden")
  }

  def submitForm(e: ReactEventI): Callback = {
    e.preventDefault()
    if (t.state.runNow().updateUserRequest.jsonBlob.imgSrc.length < 2) {
      $("#image_upload_error".asInstanceOf[js.Object]).removeClass("hidden")
      t.modState(s => s.copy(postNewImage = false))
    } else {
      //        SYNEREOCircuit.dispatch(PostUserUpdate(t.state.runNow().updateUserRequest))
      ContentUtils.postUserUpdate(t.state.runNow().updateUserRequest)
      t.modState(s => s.copy(postNewImage = true))
    }
  }

  def formClosed(state: ProfileImageUploaderForm.State, props: ProfileImageUploaderForm.Props): Callback = {
    props.submitHandler(/*state.submitForm*/)
  }
}
