package synereo.client.modulebackends

import japgolly.scalajs.react.{Callback, _}
import org.querki.jquery._
import synereo.client.handlers.ShowServerError
import synereo.client.modules.Dashboard
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import scala.scalajs.js
import scala.scalajs.js.timers._

/**
  * Created by bhagyashree.b on 2016-09-02.
  */

class DashboardBackend(t: BackendScope[Dashboard.Props, Dashboard.State]) {
  //scalastyle:off

  val messageLoader: js.Object = "#messageLoader"
  val searchContainer: js.Object = "#searchContainer"
  val dashboardContainerMain: js.Object = "#dashboardContainerMain"
  val FeedTimeOut = 1500


  def updated(props: Dashboard.Props): Callback = Callback {
    if (props.proxy().isFailed) {
      SYNEREOCircuit.dispatch(ShowServerError("Failed to connect to the server!"))
    }
    //
  }

  def closeFullViewModalPopUp(): Callback = {
    $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
    t.modState(s => s.copy(ShowFullPostView = false))
  }

  def openFullViewModalPopUP(e: ReactEvent): Callback = {
    $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
    t.modState(s => s.copy(ShowFullPostView = true, preventFullPostView = true))
  }

  def preventFullViewModalPopUP(e: ReactEvent): Callback = {
    val targetLi = e.target
    setTimeout(500) {
      $(targetLi).find(".glance-view-button").addClass(".hide")
    }
    t.modState(s => s.copy(ShowFullPostView = false))
  }

  def handleScroll(e: ReactEvent): Callback = {
    Callback.empty
  }

  def handleMouseEnterEvent(e: ReactEvent): Callback = {
    val targetLi = e.target
    val collapsiblePost: js.Object = $(targetLi).find(".collapse")
    setTimeout(FeedTimeOut) {
      if (!$(collapsiblePost).hasClass("in")) {
        $(collapsiblePost).addClass("in")
      }
    }
    Callback.empty
  }


  def serverError(): Callback = {
    // SYNEREOCircuit.dispatch(ShowServerError(""))
    t.modState(s => s.copy(showErrorModal = false))
  }

}