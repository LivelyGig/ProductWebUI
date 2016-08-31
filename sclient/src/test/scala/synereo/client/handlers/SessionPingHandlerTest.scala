package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import synereo.client.UnitTest
import synereo.client.rootmodels.SessionRootModel

/**
  * Created by shubham.k on 31-08-2016.
  */
class SessionPingHandlerTest extends UnitTest("SessionPingHandlerTest") {
  val handler = new SessionPingHandler(new RootModelRW(SessionRootModel(pinger = false)))


  "TogglePinger" should "update the SessionRootModel with toggle of pinger flag" in {
    val result = handler.handle(TogglePinger())
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.pinger == true)
      case _ =>
        assert(false)
    }
  }
  "SetSessionUri" should "set the session uri in the SessionRootModel" in {
    val result = handler.handle(SetSessionUri("test"))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.sessionUri == "test")
      case _ =>
        assert(false)
    }
  }
}
