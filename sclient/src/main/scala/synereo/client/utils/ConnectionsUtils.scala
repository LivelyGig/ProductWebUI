package synereo.client.utils

/**
  * Created by Mandar on 6/7/2016.
  */

import shared.dtos.Connection
import shared.models._
import org.scalajs.dom._
import shared.sessionitems.SessionItems
import shared.sessionitems.SessionItems.{MessagesViewItems, ProfilesViewItems, ProjectsViewItems}
import synereo.client.components.ConnectionsSelectize

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
    if ( currentSearch != None){
      upickle.default.read[Seq[Connection]](currentSearch)  ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri))
    } else {
      upickle.default.read[Seq[Connection]](
        window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTION_LIST
        )) ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri))

    }
  }

  // #todo think about better structure for the label prolog
  //

}

