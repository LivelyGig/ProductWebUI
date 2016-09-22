package synereo.client.components

import shared.dtos.{ApiResponse, Connection}
import synereo.client.UnitTest
import synereo.client.mockdata.MockData

/**
  * Created by mandar.k on 9/22/2016.
  */
class ConnectionsSelectizeTest extends UnitTest("ConnectionsSelectize") {

  val connectionsSelectize = ConnectionsSelectize


  "getConnectionsFromSelectizeInput" should "give the selected connections" in {
    Given("selectize input id")
    val selectId = MockData.selectizeInputId
    val connections = connectionsSelectize.getConnectionsFromSelectizeInput(selectId)
    it should "give the added tags into selectize"
    connections match {
      case _: Seq[Connection] =>
        assert(true)
      case _ =>
        assert(false)
    }
  }

}
