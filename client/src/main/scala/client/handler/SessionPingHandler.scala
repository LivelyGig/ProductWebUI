package client.handler

/**
  * Created by shubham.k on 05-08-2016.
  */

import client.rootmodel.SessionRootModel
import client.modules.AppModule
import diode.{ActionHandler, ActionResult, ModelRW}
import client.services.LGCircuit


case class TogglePinger(viewName: String)

case class AttachPingers()

case class SessionPing()

case class SetSessionUri(sessionUriSeq: Seq[String])

// scalastyle:off
class SessionPingHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case TogglePinger(viewName) =>
      viewName match {
        case AppModule.MESSAGES_VIEW => updated(value.copy(msgPinger = !value.msgPinger))
        case AppModule.PROFILES_VIEW => updated(value.copy(profilePinger = !value.profilePinger))
        case AppModule.PROJECTS_VIEW => updated(value.copy(projectPinger = !value.projectPinger))
      }

    case AttachPingers() =>
      LGCircuit.subscribe(LGCircuit.zoom(_.session.msgPinger))(_ => msgPing())
      def msgPing() = LGCircuit.dispatch(RefreshMessages())
      LGCircuit.subscribe(LGCircuit.zoom(_.session.profilePinger))(_ => profilePing())
      def profilePing() = LGCircuit.dispatch(RefreshProfiles())
      LGCircuit.subscribe(LGCircuit.zoom(_.session.projectPinger))(_ => projectPing())
      def projectPing() = LGCircuit.dispatch(RefreshProjects())
      noChange

    case SetSessionUri(sessionUriSeq) =>
//      println(s"sessionUriSeq **** ${sessionUriSeq}")
      updated(value.copy(messagesSessionUri = sessionUriSeq(0), projectSessionUri = sessionUriSeq(1), profileSessionUri = sessionUriSeq(2)))

  }
}
