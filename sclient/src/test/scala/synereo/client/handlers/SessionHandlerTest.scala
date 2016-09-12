package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import synereo.client.UnitTest
import synereo.client.rootmodels.SessionRootModel

/**
  * Created by shubham.k on 31-08-2016.
  */
class SessionHandlerTest extends UnitTest("SessionPingHandler") {
  val handler = new SessionHandler(new RootModelRW(SessionRootModel()))


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
