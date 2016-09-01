package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import org.querki.jquery._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.modalpopups.FullPostViewModal
import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

class FullPostViewBackend(t: BackendScope[FullPostViewModal.Props, FullPostViewModal.State]) {
  val fullPostViewContainer: js.Object = "#fullPostViewContainer"
  val fullViewModalNavBar: js.Object = "#fullViewModalNavBar"
  val fullViewImage: js.Object = "#fullViewImage"
  val fullViewImageHeight = $(fullViewImage).height()
  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val modalCloseButton: js.Object = "#modal-close-button"
  var scrollY: Int = 0

  def mounted() = Callback {
    $(fullPostViewContainer).height($(dashboardContainerMain).height() + 25)
  }

  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def modalClosed(state: FullPostViewModal.State, props: FullPostViewModal.Props): Callback = {
    props.submitHandler()
  }

  def handleScroll(e: ReactEventI): Callback = {
    val fullViewImageHeight = $(fullViewImage).height()
    scrollY = $(fullPostViewContainer).scrollTop()
    //      println(scrollY)
    if (scrollY > fullViewImageHeight) {
      $(fullViewModalNavBar).addClass("postedUserInfoNavModalScroll").css("top", fullViewImageHeight.toInt + (scrollY - fullViewImageHeight.toInt))
      $(modalCloseButton).css("position", "absolute")
      $(modalCloseButton).css("top", fullViewImageHeight.toInt + (scrollY - fullViewImageHeight.toInt - 20))
      $(modalCloseButton).css("right", "20px")
    } else if (scrollY < fullViewImageHeight - 160) {
      $(fullViewModalNavBar).removeClass("postedUserInfoNavModalScroll").css("top", "0")
      $(modalCloseButton).addClass("").css("position", "initial")
    }
    Callback.empty
  }

}