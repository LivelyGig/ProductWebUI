package client.utils

import client.components.{ConnectionsSelectize, LabelsSelectize}
import client.handlers._
import client.services.{CoreApi, LGCircuit}
import shared.dtos._
import shared.models._
import org.scalajs.dom._
import shared.sessionitems.SessionItems.{MessagesViewItems, ProfilesViewItems, ProjectsViewItems}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.timers._
import scala.util.{Failure, Success}
import diode.AnyAction._
import shared.sessionitems.SessionItems

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

  def checkIntroductionNotification(): Unit = {
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
        LGCircuit.dispatch(AcceptNotification(Seq(intro(0).content)))
      } else if (response.contains("introductionConfirmationResponse")) {
        val introductionConfirmationResponse = upickle.default.read[Seq[ApiResponse[IntroductionConfirmationResponse]]](response)
        LGCircuit.dispatch(AcceptIntroductionConfirmationResponse(introductionConfirmationResponse(0).content))
        println(s"IntroductionConfirmationResponse: $introductionConfirmationResponse")
      } else if (response.contains("connectNotification")) {
        val connectNotification = upickle.default.read[Seq[ApiResponse[ConnectNotification]]](response)
        LGCircuit.dispatch(AcceptConnectNotification(connectNotification(0).content))
        LGCircuit.dispatch(AddConnection(connectNotification(0).content))
        println(s"connectNotification: $connectNotification")
      }
    } catch {
      case e: Exception => /*println("into exception for upickle read session ping response")*/
        println("")
    }
  }

  // #todo think about better structure for the label prolog
  //

}
