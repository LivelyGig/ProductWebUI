package synereo.client.handlers

import org.widok.moment.Moment
import shared.models.{MessagePost, Post}
import synereo.client.UnitTest
import synereo.client.mockdata.MockData
import synereo.client.services.SYNEREOCircuit

/**
  * Created by shubham.k on 31-08-2016.
  */
class ContentHandlerTest extends UnitTest("ContentHandlerTest") {
    val handler = ContentHandler
  "getContentModel" should "give the MessagesPost array " in {
    Given(" messages response json")
    val msgRes = MockData.getMessagesResponse()
    val currentPingerState = SYNEREOCircuit.zoom(_.sessionRootModel.pinger).value
    val response = handler.getContentModel(msgRes)
    val modPingerState = SYNEREOCircuit.zoom(_.sessionRootModel.pinger).value
    Then("it should toggle the pinger")
    assert(currentPingerState!=modPingerState)
    Then("it should give the response which is a array of post")
    assert(response.isInstanceOf[Seq[Post]])
    Then("the output should cast as seq of MessagePost")
    intercept[Exception] {
      upickle.default.read[Seq[MessagePost]](msgRes)
    }
    Then("the output should match the response from get post method")
    val messagesFromMock = handler.getPostFromRes(msgRes).sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
    assert(response.asInstanceOf[Seq[MessagePost]].head == messagesFromMock(0))

  }
}
