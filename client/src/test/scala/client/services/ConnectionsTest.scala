package client.services

import shared.dtos.Connection
import shared.models.ConnectionsModel

/**
  * Created by bhagyashree.b on 2016-05-20.
  */
class ConnectionsTest extends UnitTest("ConnectionsModel") {

  "Attributes of ConnectionsModel" should "not be null " in {
    val connectionsModel = new ConnectionsModel("",Connection(),"","")
    connectionsModel.sessionURI shouldNot be(null)
    connectionsModel.connection shouldNot  be(null)
    connectionsModel.name shouldNot be(null)
    connectionsModel.imgSrc shouldNot be(null)
  }
}
