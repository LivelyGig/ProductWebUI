package client.handlers

import client.UnitTest
import client.handler.{ApplicationHandler, ShowServerError}
import client.rootmodel.AppRootModel
import diode.ActionResult.ModelUpdate
import diode.RootModelRW

/**
  * Created by shubham.k on 14-09-2016.
  */

class ApplicationHandlerTest extends UnitTest("ApplicationHandler") {
  val appRootModel = new AppRootModel()

  def build = new ApplicationHandler(new RootModelRW(appRootModel))

  "ShowServerError" should "update app root model for the server error" in {
    val errorMsg = "Test message for the server error"
    val handler = build
    val result = handler.handle(ShowServerError(errorMsg))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.isServerError == true)
        assert(newValue.serverErrorMsg == errorMsg)
      case _ =>
        assert(false)

    }
  }

}

class AppHandlerTest {

}
