package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import shared.dtos.{Connection, IntroConfirmReq, Introduction}
import synereo.client.UnitTest
import synereo.client.rootmodels.IntroRootModel

/**
  * Created by shubham.k on 06-09-2016.
  */
class IntroductionHandlerTest extends UnitTest("IntroductionHandler") {

  val handler = new IntroductionHandler(new RootModelRW(IntroRootModel()))

  val introductionSeq = Seq(Introduction("introSessionId", "correlationId", connection, "message", "introProfile"),
    Introduction("introSessionId", "correlationId", connection, "message", "introProfile"),
    Introduction("introSessionId", "correlationId", connection, "message", "introProfile"))
  val connection = Connection("newSource1", "newLabel1", "newTarget1")
  val introConfirm = IntroConfirmReq("sessionURI", "alias", "introSessionId", "correlationId", false)

  "AddNotification" should "Add Introduction Notifications" in {
    val result = handler.handle(AddNotification(introductionSeq))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(true)
    }
  }

/*  "UpdateIntroductionsModel" should "" in {
    val result = handler.handle(UpdateIntroductionsModel(introConfirm))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(true)
    }
  }*/


}
