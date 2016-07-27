package synereo.client.utils

/**
  * Created by mandar.k on 6/7/2016.
  */

import shared.dtos._
import shared.dtos.Connection
import shared.models._
import org.scalajs.dom._
import synereo.client.sessionitems.SessionItems
import synereo.client.components.ConnectionsSelectize
import shared.RootModels.ConnectionsRootModel

import scala.scalajs.js.JSON

//scalastyle:off
object ConnectionsUtils {

  /**
    * Method to get the self connection
    *
    * @param sessionUri uri of the session concerned. Look at CoreApi.scala for the
    *                   possible session uri names.
    * @return connection with the source and target to the user and label as alias
    */
  def getSelfConnnection(sessionUri: String): Connection = {
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://" + sessionUriSplit(2)
    Connection(sourceStr, "alias", sourceStr)
  }

  def getCnxsSeq(id: Option[String], sessionUriName: String): Seq[Connection] = {
    id match {
      case None =>
        Seq(ConnectionsUtils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName)))
      case Some(res) =>
        Seq(ConnectionsUtils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName))) ++ ConnectionsSelectize.getConnectionsFromSelectizeInput(res)
    }
  }

  def getCnxnForReq(sessionUri: String): Seq[Connection] = {
    val currentSearch = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST)
    if (currentSearch != None) {
      upickle.default.read[Seq[Connection]](currentSearch) ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri))
    } else {
      upickle.default.read[Seq[Connection]](
        window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTION_LIST
        )) ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri))

    }
  }

  /*def checkIntroductionNotification(): Unit = {
    val sessionUri = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
    if (window.sessionStorage.getItem("sessionPingTriggered") == null) {
      window.sessionStorage.setItem("sessionPingTriggered", "true")
      def sessionPing(): Unit = {
        CoreApi.getConnections().onComplete {
          case Success(response) => {
            processIntroductionNotification(response)
            sessionPing()
          }
          case Failure(failureMessage) => println(s"failureMessage: $failureMessage")
          case _ => println("something went wrong in session ping")
        }
      }
      setTimeout(7000) {
        sessionPing()
      }
    }
  }

  def processIntroductionNotification(response: String = ""): Unit = {
    //    toDo: Think of some better logic to identify different responses from session ping
    try {
      if (response.contains("sessionPong")) {
        val sessionPong = upickle.default.read[Seq[ApiResponse[SessionPong]]](response)
      } else if (response.contains("introductionNotification")) {
        val intro = upickle.default.read[Seq[ApiResponse[Introduction]]](response)
        SYNEREOCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        SYNEREOCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
        println(s"IntroductionConfirmationResponse: $introductionConfirmationResponse")
      } else if (response.contains("connectNotification")) {
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        SYNEREOCircuit.dispatch(AcceptConnectNotification(connectNotification(0).content))
        SYNEREOCircuit.dispatch(AddConnection(connectNotification(0).content))
        println(s"connectNotification: $connectNotification")
      }
    } catch {
      case e: Exception => /*println("into exception for upickle read session ping response")*/
        println("")
    }
  }*/

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

  // #todo think about better structure for the label prolog
  //

}

