package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.SessionRootModel
import synereo.client.services.SYNEREOCircuit


case class TogglePinger()

case class AttachPinger()

case class SessionPing()

// scalastyle:off
class SessionPingHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case TogglePinger() =>
      updated(value.copy(pinger = !value.pinger))

    case AttachPinger() =>
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.sessionPing.pinger))(_ => ping())
      def ping() = {
        SYNEREOCircuit.dispatch(RefreshMessages())
      }
      noChange

  }
}