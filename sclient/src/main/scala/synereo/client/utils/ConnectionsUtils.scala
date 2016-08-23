package synereo.client.utils

/**
  * Created by mandar.k on 6/7/2016.
  */

import shared.dtos._
import shared.dtos.Connection
import shared.models._

import synereo.client.services.SYNEREOCircuit
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

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

  /* def getCnxsSeq(id: Option[String], sessionUriName: String): Seq[Connection] = {
     id match {
       case None =>
         Seq(ConnectionsUtils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName)))
       case Some(res) =>
         Seq(ConnectionsUtils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName))) ++ ConnectionsSelectize.getConnectionsFromSelectizeInput(res)
     }
   }*/


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

  /*def getConnectionsModel(response: String): Seq[ConnectionsModel] = {
    //    println(s"response: $response")
    var cnxnSeq = Seq[ConnectionsModel]()
    var introSeq = Seq[Introduction]()
    val responseArray = upickle.json.read(response) /*.asInstanceOf[js.Array[ApiResponse[Content]]]*/
    responseArray.arr.foreach {
      obj =>
        try {
          val apiRes = upickle.default.read[ApiResponse[ConnectionProfileResponse]](upickle.json.write(obj))
          cnxnSeq :+= getCnxnFromRes(apiRes.content)
        } catch {
          case e:Exception =>
            val apiRes = upickle.default.read[ApiResponse[Introduction]](upickle.json.write(obj))
            introSeq :+= apiRes.content

        }
    }
    /*try {
      val connections = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](response)
      connections.map(e => getCnxnFromRes(e.content))
        .sortBy(_.name)
    } catch {
      case e: Exception =>
        println(e)
        Nil
    }*/
  }*/

  // #todo think about better structure for the label prolog
  //

}

