package synereo.client.utils

/**
  * Created by mandar.k on 6/7/2016.
  */

import shared.dtos._
import shared.dtos.Connection
import shared.models._
import synereo.client.services.SYNEREOCircuit

import scala.scalajs.js.JSON

//scalastyle:off
object ConnectionsUtils {

  /**
    * Method to get the self connection
    *
    * @return connection with the source and target to the user and label as alias
    */
  def getSelfConnnection(): Connection = {
    val sessionUriSplit = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split('/')
    val sourceStr = "agent://" + sessionUriSplit(2)
    Connection(sourceStr, "alias", sourceStr)
  }

  def getErrorConnection(): Connection = {
    Connection("alias://ERROR/alias", "alias", "alias://ERROR/alias")
  }

  def getCnxnForReq(cnxn: Seq[Connection]): Seq[Connection] = {
    if (cnxn.isEmpty) {
      SYNEREOCircuit.zoom(_.connections.connectionsResponse).value.map(cnxnRes => cnxnRes.connection) ++ Seq(getSelfConnnection())
    } else {
      cnxn ++ Seq(getSelfConnnection())
    }
  }

  def getNameImgFromJson(jsonBlob: String): (String, String) = {
    val json = JSON.parse(jsonBlob)
    val name = json.name.asInstanceOf[String]
    val imgSrc = if (jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else "./assets/synereo-images/default_avatar.jpg"
    (name, imgSrc)
  }

  def getCnxnFromRes(cnxn: ConnectionProfileResponse): ConnectionsModel = {
    val (name, imgSrc) = getNameImgFromJson(cnxn.jsonBlob)
    ConnectionsModel(cnxn.sessionURI, cnxn.connection,
      name, imgSrc)
  }

  def getCnxnFromNot(cnxn: ConnectNotification): ConnectionsModel = {
    val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(cnxn.introProfile)
    ConnectionsModel("", cnxn.connection, name, imgSrc)
  }

  def getSenderReceivers(message: MessagePost): MessagePost = {
//    println(s"message: $message")
    try {
      val senderUri = message.connections.last.target.split("/")(2)
      val receiversUri = message.connections.dropRight(1).map(_.target.split("/")(2))
      val userId = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
      val allCnxnModel = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
      val userCnxnModel = ConnectionsModel(sessionURI = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value,
        connection = ConnectionsUtils.getSelfConnnection(),
        name = "me",
        imgSrc = SYNEREOCircuit.zoom(_.user).value.imgSrc)
      if (userId == senderUri) {
        message.copy(sender = userCnxnModel, receivers = allCnxnModel.filter(e => receiversUri.contains(e.connection.target.split("/")(2))))
      } else {
        message.copy(sender = allCnxnModel.find(_.connection.target.contains(senderUri)).head,
          receivers = Seq(userCnxnModel) ++ allCnxnModel.filter(e => receiversUri.contains(e.connection.target.split("/")(2))))
      }
    } catch {
      case e:Exception =>
        // dirty hack for json not containing the connections
        message.copy(sender = ConnectionsModel(name = "UNKNOWN", connection = getErrorConnection()), receivers = Seq(ConnectionsModel(name = "UNKNOWN", connection = getErrorConnection())))
    }
  }

  /*def getMessageDetails(message: MessagePost): (String, Seq[String], String, String) = {
    val senderUri =   message.connections.head.source.split("/")(2)
    val receiverUri = message.connections.tail.map(_.source.split("/")(2))
    val userId = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
    val (sender, receivers) = if ()
    val connections = SYNEREOCircuit.zoom(_.connections).value.connectionsResponse
    var senderName = "unknown"
    var imgContentOfMessagePost = ""
    var sendAmpsTo = ""
    var fromSenderUID = ""
    var receiverNames: Seq[String] = Seq()
    val userId = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
    if (userId == senderUri) {
      fromSenderUID = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value.split("/")(2)
      senderName = "me"
      sendAmpsTo = userId
      for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.source.split("/")(2) && a.connection.target.split("/")(2) == b.target.split("/")(2))) yield {
        receiverNames :+= a.name
      }

    } else {
      for (b <- message.connections; a <- connections; if (a.connection.source.split("/")(2) == b.target.split("/")(2) && a.connection.target.split("/")(2) == b.source.split("/")(2))) yield {
        senderName = a.name
        fromSenderUID = a.connection.source.split("/")(2)
        imgContentOfMessagePost = a.imgSrc
        sendAmpsTo = a.connection.target.split("/")(2)
      }
      // ToDo: Look up name of Sender and use friendly name
      receiverNames :+= "me"
    }
    (senderName, receiverNames, imgContentOfMessagePost,fromSenderUID)
  }*/

}

