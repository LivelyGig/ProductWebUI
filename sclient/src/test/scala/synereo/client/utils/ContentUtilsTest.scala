package client.utils

import synereo.client.UnitTest
import synereo.client.mockdata.MockData
import synereo.client.services.SYNEREOCircuit
import shared.dtos._
import synereo.client.utils.{ConnectionsUtils, ContentUtils}

/**
  * Created by shubham.k on 14-09-2016.
  */
class ContentUtilsTest extends UnitTest("ContentUtils") {
  val contentUtils = ContentUtils

  "processConnectResponse"

  "processRes" should "give the MessagesPost array " in {
    Given("response json contains messages ")
    val msgRes = MockData.messagesResponse
    val response = contentUtils.processRes(msgRes)
    it should "match the response from json"
    val resFromJson = upickle.default.read[Seq[ApiResponse[ResponseContent]]](msgRes)
    assert(resFromJson.map(_.content) == response)
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
  it should "update the balance" in {
    Given("response json contains balance changed")
    val balChanged = MockData.balChanged
    contentUtils.processRes(balChanged)
    Then("the user root model is updated with the changed balance")
    assert(SYNEREOCircuit.zoom(_.user.balance.nonEmpty).value)
    And("the updated balance is same as the changed balance from response")
    val apiResponse = upickle.default.read[Seq[ApiResponse[BalanceChange]]](balChanged)
    assert(SYNEREOCircuit.zoom(_.user.balance).value == apiResponse(0).content.newBalance)
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

  "sortContent" should "give a tuple with different types for mixed responses" in {
    val mixedRes = MockData.mixedReponse
    val responseArray = upickle.json.read(mixedRes).arr.map(e => upickle.json.write(e))
    val (cnxn, msg, intro, cnctNot, balanceChanged) = contentUtils.sortContent(responseArray)
    assert(cnxn.nonEmpty && msg.nonEmpty && intro.nonEmpty && cnctNot.nonEmpty && balanceChanged.nonEmpty)
  }


}