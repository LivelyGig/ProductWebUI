package synereo.client.utils

import shared.dtos._
import shared.models.ConnectionsModel
import synereo.client.UnitTest
import synereo.client.handlers.{SetSessionUri, UpdateConnections}
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._

/**
  * Created by shubham.k on 05-09-2016.
  */
class ConnectionUtilsTest extends UnitTest("ConnectionUtils") {
  val connectionUtils = ConnectionsUtils

  val connections = Seq(Connection("alias://ff03586051a338dd7577f8d99cd661a0dbea/alias", "3e827cf6-0be2-4f19-883f-50bc78957ff3",
    "alias://3a5343e917f79c68111ca028e775bf1c259e/alias"),
    Connection("alias://ff03586051a338dd7577f8d99cd661a0dbea/alias", "3e827cf6-0be2-4f19-883f-50bc78957ff3",
      "alias://3a5343e917f79c68111ca028e775bf1c259e/alias"),
    Connection("alias://ff03586051a338dd7577f8d99cd661a0dbea/alias", "3e827cf6-0be2-4f19-883f-50bc78957ff3",
      "alias://3a5343e917f79c68111ca028e775bf1c259e/alias"))

  val connection = Connection("alias://ff03586051a338dd7577f8d99cd661a0dbea/alias", "3e827cf6-0be2-4f19-883f-50bc78957ff3",
    "alias://3a5343e917f79c68111ca028e775bf1c259e/alias")

  val connectionProfileResponse = ConnectionProfileResponse("sessionURI", connection, "\"jsonBlob\":\"{\\\"name\\\":\\\"c\\\"}\"")

  /*"getCnxnForReq" should "give connection sequence with self connection added" in {
    Given("sequence of connections ")
    SYNEREOCircuit.dispatch(SetSessionUri("sessionUri"))
    val response = ConnectionsUtils.getCnxnForReq(connections)
    val preVerified = connections ++ Seq(ConnectionsUtils.getSelfConnnection())
    assert(response == preVerified)
  }*/

  it should "give the all connection along with self connection added" in {
    Given("no connection sequence")
    SYNEREOCircuit.dispatch(UpdateConnections(Seq(ConnectionsModel("sessionUri", connection, "name", "imgSrc"))))
    SYNEREOCircuit.dispatch(SetSessionUri("sessionUri"))
    val response = ConnectionsUtils.getCnxnForReq(Nil)
    val preVerified = Seq(connection) ++ Seq(ConnectionsUtils.getSelfConnnection())
    assert(response == preVerified)
  }

//  "getCnxnFromRes" should "get connections from response" in {
//    Given("connectionProfileResponse")
//    val response = ConnectionsUtils.getCnxnFromRes(connectionProfileResponse)
//    val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(connectionProfileResponse.jsonBlob)
//    val preVerified = ConnectionsModel(connectionProfileResponse.sessionURI, connectionProfileResponse.connection, name, imgSrc)
//    assert(response == preVerified)
//
//  }
}
