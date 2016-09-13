package client.handler

/**
  * Created by shubham.k on 05-08-2016.
  */

import client.rootmodel.SessionRootModel
import client.modules.AppModule
import diode.{ActionHandler, ActionResult, ModelRW}
import client.services.LGCircuit


case class SetSessionUri(sessionUriSeq: Seq[String])

// scalastyle:off
class SessionPingHandler[M](modelRW: ModelRW[M, SessionRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case SetSessionUri(sessionUriSeq) =>
//      println(s"sessionUriSeq **** ${sessionUriSeq}")
      updated(value.copy(messagesSessionUri = sessionUriSeq(0), projectSessionUri = sessionUriSeq(1), profileSessionUri = sessionUriSeq(2)))

  }
}
