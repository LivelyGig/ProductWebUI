package synereo.client.modulebackends

import japgolly.scalajs.react.BackendScope
import org.querki.jquery._
import shared.dtos.{IntroConfirmReq, Introduction}
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.modules.NotificationList
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-02.
  */


class NotificationsListBackend(t: BackendScope[NotificationList.Props, NotificationList.State]) {

  def deleteIntroduction(introduction: Introduction) = {
    val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = false)
    SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
  }

  def handleAllIntroduction(areAccepted: Boolean = false) = {
    val props = t.props.runNow()
    val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    props.introductions.foreach {
      introduction =>
        val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = areAccepted)
        SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    }
    $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
  }
}