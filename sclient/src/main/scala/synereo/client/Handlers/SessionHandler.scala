package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.SessionRootModel
import synereo.client.services.SYNEREOCircuit


/*case class TogglePinger()

case class AttachPinger()*/

case class SessionPing()

case class SetSessionUri(sessionUri: String)

// scalastyle:off
class SessionHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    /*case TogglePinger() =>
      updated(value.copy(pinger = !value.pinger))

    case AttachPinger() =>
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.sessionRootModel.pinger))(_ => ping())
      def ping() = {
        SYNEREOCircuit.dispatch(RefreshMessages())
      }
      noChange*/
    case SetSessionUri(sessionUri) =>
      updated(value.copy(sessionUri = sessionUri))
  }
}