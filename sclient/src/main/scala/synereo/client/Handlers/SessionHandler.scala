package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.SessionRootModel
import synereo.client.services.SYNEREOCircuit

case class SessionPing()

case class SetSessionUri(sessionUri: String)

// scalastyle:off
class SessionHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case SetSessionUri(sessionUri) =>
      updated(value.copy(sessionUri = sessionUri))
  }
}