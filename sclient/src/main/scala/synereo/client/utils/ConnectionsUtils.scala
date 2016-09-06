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

  def getCnxnForReq(cnxn: Seq[Connection]): Seq[Connection] = {
    if (cnxn.isEmpty) {
      SYNEREOCircuit.zoom(_.connections.connections).value ++ Seq(getSelfConnnection())
    } else {
      cnxn ++ Seq(getSelfConnnection())
    }
  }

  def getNameImgFromJson(jsonBlob: String): (String, String) = {
    val json = JSON.parse(jsonBlob)
    val name = json.name.asInstanceOf[String]
    val imgSrc = if (jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
    (name, imgSrc)
  }

  def getCnxnFromRes(cnxn: ConnectionProfileResponse): ConnectionsModel = {
    val (name, imgSrc) = getNameImgFromJson(cnxn.jsonBlob)
    ConnectionsModel(cnxn.sessionURI, cnxn.connection,
      name, imgSrc)
  }

  def getCnxnFromNot (cnxn: ConnectNotification): ConnectionsModel = {
    val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(cnxn.introProfile)
    ConnectionsModel("", cnxn.connection, name, imgSrc)
  }
}

