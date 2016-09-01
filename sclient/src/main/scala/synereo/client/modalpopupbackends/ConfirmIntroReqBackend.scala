package synereo.client.modalpopupbackends

import japgolly.scalajs.react
import japgolly.scalajs.react.{Callback, _}
import shared.dtos.IntroConfirmReq
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.modalpopups.ConfirmIntroReqForm
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._

/**
  * Created by bhagyashree.b on 2016-09-01.
  */
//    toDo: Think of some better logic to reduce verbosity in accept on form submit or reject --> hide like get  event target source and modify only accepted field of case class
case class ConfirmIntroReqBackend(t: BackendScope[ConfirmIntroReqForm.Props, ConfirmIntroReqForm.State]) {
  def hide: Callback = Callback {
    //      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val props = t.props.runNow()
    val introConfirmReq = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = false)
    //      CoreApi.postIntroduction(introConfirmReq).onComplete {
    //        case Success(response) =>
    //
    //      }
    SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    jQuery(t.getDOMNode()).modal("hide")
  }

  def hideModal(): Unit = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def mounted(props: ConfirmIntroReqForm.Props): Callback = Callback {
  }

  def submitForm(e: ReactEventI): react.Callback = {
    e.preventDefault()
    //      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val props = t.props.runNow()
    val content = IntroConfirmReq(uri, alias = "alias", props.introduction.introSessionId, props.introduction.correlationId, accepted = true)
    SYNEREOCircuit.dispatch(UpdateIntroductionsModel(content))
    t.modState(s => s.copy(confirmIntroReq = true))
  }

  def formClosed(state: ConfirmIntroReqForm.State, props: ConfirmIntroReqForm.Props): Callback = {
    props.submitHandler(/*state.postMessage*/)
  }
}