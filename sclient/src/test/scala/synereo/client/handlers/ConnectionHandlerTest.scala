package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import shared.dtos.Connection
import shared.models.ConnectionsModel
import synereo.client.UnitTest
import synereo.client.rootmodels.ConnectionsRootModel

/**
  * Created by shubham.k on 29-08-2016.
  */
class ConnectionHandlerTest extends UnitTest("ConnectionHandlerTest") {
  val handler = new ConnectionHandler(new RootModelRW(ConnectionsRootModel()))
  val newCnxnSeq  = Seq(Connection("newSource1", "newLabel1", "newTarget1"),
    Connection("newSource2", "newLabel2", "newTarget2"),
    Connection("newSource3", "newLabel3", "newTarget3"))

  val newConnectionModelSeq  = Seq(ConnectionsModel("sessionUri1",newCnxnSeq(0),"name1",""),
    ConnectionsModel("sessionUri2",newCnxnSeq(1),"name2",""),
    ConnectionsModel("sessionUri3",newCnxnSeq(2),"name3",""))

  "UpdateConnectionSeq" should "update the ConnectionRootModel with the new connection Seq" in {

    val result = handler.handle(UpdateConnectionSeq(newCnxnSeq))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.connections == newCnxnSeq)
      case _ =>
        assert(false)
    }
  }

  "UpdateConnectionModelSeq" should "update the ConnectionRootModel with the new connection model seq" in {
      val result = handler.handle(UpdateConnectionModelSeq(newConnectionModelSeq))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.connectionsResponse == newConnectionModelSeq)
      case _ =>
        assert(false)

    }
  }

  "AddConnection" should "add the new connection in the ConnectionRootModel" in {
    val newCnxn = ConnectionsModel("sessionUriForAdd",Connection("sourceForAddCnxn",
      "labelForAddCnxn","targetForAddCnxn"),"nameForAddCnxn","")
    val result = handler.handle(AddConnection(newCnxn,newCnxn.connection))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.connectionsResponse.last == newCnxn)
        assert(newValue.connections.last == newCnxn.connection)
      case _ =>
        assert(false)
    }
  }


}
