package client.handlers

import client.UnitTest
import client.handler.ConnectionHandler
import client.rootmodel.ConnectionsRootModel
import diode.RootModelRW
import shared.dtos.Connection
import shared.models.ConnectionsModel

/**
  * Created by shubham.k on 14-09-2016.
  */
class ConnectionHandlerTest extends UnitTest("ConnectionHandlerTest") {
  val handler = new ConnectionHandler(new RootModelRW(ConnectionsRootModel(Nil)))
  val newCnxnSeq  = Seq(Connection("newSource1", "newLabel1", "newTarget1"),
    Connection("newSource2", "newLabel2", "newTarget2"),
    Connection("newSource3", "newLabel3", "newTarget3"))

  val newConnectionModelSeq  = Seq(ConnectionsModel("sessionUri1",newCnxnSeq(0),"name1",""),
    ConnectionsModel("sessionUri2",newCnxnSeq(1),"name2",""),
    ConnectionsModel("sessionUri3",newCnxnSeq(2),"name3",""))


}
