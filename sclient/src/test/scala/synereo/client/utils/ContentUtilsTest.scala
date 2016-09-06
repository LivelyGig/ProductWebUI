package synereo.client.utils

import shared.dtos._
import shared.models.MessagePost
import synereo.client.UnitTest
import synereo.client.mockdata.MockData
import synereo.client.services.SYNEREOCircuit

/**
  * Created by shubham.k on 31-08-2016.
  */
class ContentUtilsTest extends UnitTest("ContentUtils") {
  val contentUtils = ContentUtils

  "processConnectResponse"

  "processRes" should "give the MessagesPost array " in {
    Given("response json contains messages ")
    val msgRes = MockData.messagesResponse
    val response = contentUtils.processRes(msgRes)
    response shouldBe a[Seq[MessagePost]]
    it should "match the response from json"
    val resFromJson = contentUtils.getMsgModel(upickle.default.read[Seq[ApiResponse[ResponseContent]]](msgRes))
    assert(resFromJson(0) == response.asInstanceOf[Seq[MessagePost]](0))
  }

  it should "add new connection" in {
    Given("response json contains connection profiles")
    val cnxnRes = MockData.cnxnRes
    contentUtils.processRes(cnxnRes)
    Then("connection root model is updated")
    assert(SYNEREOCircuit.zoom(_.connections.connectionsResponse.nonEmpty).value)
    And("the connection root model contains this new connection")
    val apires = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](cnxnRes).map(e => ConnectionsUtils.getCnxnFromRes(e.content))
    assert(SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.contains(apires(0)))
  }
  it should "add introduction notifications to Intro" in {
    Given("response json contains notifications")
    val introNot = MockData.introNot
    contentUtils.processRes(introNot)
    Then("the intro root model is updated")
    assert(SYNEREOCircuit.zoom(_.introduction.introResponse.nonEmpty).value)
    And("the introduction root model contains this new introduction")
    val apiResponse = upickle.default.read[Seq[ApiResponse[Introduction]]](introNot)
    assert(SYNEREOCircuit.zoom(_.introduction.introResponse).value.contains(apiResponse.head.content))
  }
  it should "extract add new connection" in {
    Given("response json contains connect notification")
    val cnctNot = MockData.cnctNot
    contentUtils.processRes(cnctNot)
    Then("the connection root model is updated")
    assert(SYNEREOCircuit.zoom(_.connections.connectionsResponse.nonEmpty).value)
    And("the connection root model contains this new connection")
    val apiResponse = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](cnctNot).map(e => ConnectionsUtils.getCnxnFromNot(e.content))
    assert(SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.contains(apiResponse(0)))

  }

}
