package synereo.client.handlers

import diode.ActionResult.{ModelUpdate, ModelUpdateEffect}
import diode.RootModelRW
import synereo.client.UnitTest
import synereo.client.rootmodels.AppRootModel

/**
  * Created by shubham.k on 2016-08-23.
  */
class AppHandlerTest extends UnitTest("AppHandler") {
  val appRootModel = new AppRootModel()

  def build = new AppHandler(new RootModelRW(appRootModel))

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
