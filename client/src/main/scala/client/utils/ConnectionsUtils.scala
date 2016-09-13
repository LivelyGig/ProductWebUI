package client.utils

import client.components.{ConnectionsSelectize, LabelsSelectize}
import client.handler._
import client.modules.AppModule
import client.services.{CoreApi, LGCircuit}
import shared.dtos._
import shared.models._
import org.scalajs.dom._
import client.sessionitems.SessionItems.{MessagesViewItems, ProfilesViewItems, ProjectsViewItems}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.timers._
import scala.util.{Failure, Success}
import diode.AnyAction._
import client.sessionitems.SessionItems

import scala.scalajs.js.JSON

//scalastyle:off
object ConnectionsUtils {

  /**
    * Method to get the self connection
    * @return connection with the source and target to the user and label as alias
    */
  def getSelfConnnection(viewName: String): Connection = {
    val sessionUriSplit = viewName match {
      case AppModule.MESSAGES_VIEW => LGCircuit.zoom(_.session.messagesSessionUri).value.split('/')
      case AppModule.PROJECTS_VIEW => LGCircuit.zoom(_.session.projectSessionUri).value.split('/')
      case AppModule.PROFILES_VIEW => LGCircuit.zoom(_.session.profileSessionUri).value.split('/')
    }

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



  def getCnxnForReq(cnxn: Seq[Connection], viewName: String): Seq[Connection] = {
    if (cnxn.isEmpty) {
      LGCircuit.zoom(_.connections.connectionsResponse).value.map(_.connection) ++ Seq(getSelfConnnection(viewName))
    } else {
      cnxn ++ Seq(getSelfConnnection(viewName))
    }
  }

  def getNameImgFromJson (jsonBlob: String) :(String, String) = {
    val json = JSON.parse(jsonBlob)
    val name = json.name.asInstanceOf[String]
    val imgSrc = if (jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
    (name, imgSrc)
  }

  def getCnxnFromRes (cnxn: ConnectionProfileResponse): ConnectionsModel = {
    val (name, imgSrc) = getNameImgFromJson(cnxn.jsonBlob)
    ConnectionsModel(cnxn.sessionURI, cnxn.connection,
      name, imgSrc)
  }

  def getConnectionsModel(response: String): Seq[ConnectionsModel] = {

    try {
      val connections = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](response)
      connections.map(e => getCnxnFromRes(e.content))
        .sortBy(_.name)
    } catch {
      case e:Exception =>
        Nil
    }
  }

  def getCnxnFromNot (cnxn: ConnectNotification): ConnectionsModel = {
    val (name, imgSrc) = ConnectionsUtils.getNameImgFromJson(cnxn.introProfile)
    ConnectionsModel("", cnxn.connection, name, imgSrc)
  }

  // #todo think about better structure for the label prolog
  //

}
