package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.RootModels.SessionRootModel
import synereo.client.services.{CoreApi, SYNEREOCircuit}


case class HandleSessionPing()

case class LockSessionPing()

case class OpenSessionPing()

case class AttachPinger()

case class SessionPing()

// scalastyle:off
class SessionPingHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case HandleSessionPing() =>
      if (!value.stopPing) {
        updated(SessionRootModel(!value.toggleToPing, value.stopPing))
      } else {
        noChange
      }
    case LockSessionPing() =>
      updated(SessionRootModel(value.toggleToPing, true))

    case OpenSessionPing() =>
      updated(SessionRootModel(value.toggleToPing, false))

    case AttachPinger() =>
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.sessionPing.toggleToPing))(_ => ping())
      def ping() = {
        SYNEREOCircuit.dispatch(RefreshMessages())
      }
      noChange

  }
}