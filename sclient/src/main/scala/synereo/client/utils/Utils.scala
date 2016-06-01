package synereo.client.utils

import shared.dtos.Connection
import shared.models._
import org.scalajs.dom._
import shared.sessionitems.SessionItems.{ MessagesViewItems, ProjectsViewItems }

object Utils {

  /**
    * Method to get the self connection
    *
    * @param sessionUri uri of the session concerned. Look at CoreApi.scala for the
    *                       possible session uri names.
    * @return connection with the source and target to the user and label as alias
    */
  def getSelfConnnection(sessionUri: String): Connection = {
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://" + sessionUriSplit(2)
    Connection(sourceStr, "alias", sourceStr)
  }

  /**
    * *
    * This function is used to return the prolog used by
    * glosevel to evaluate the subscription requests.
    * @param labelFamilies This is the seq of label families e.g seq of [parent1,child1ToParent1], [parent2,child1ToParent2]
    * @return returns the prolog term e.g any([label1,label2])
    */
  def getLabelProlog(labelFamilies: Seq[Seq[LabelModel]]): String = {
    var labelsCount = labelFamilies.length - 1
    val prolog = StringBuilder.newBuilder
    prolog.append("any(")
    for { labelFamily <- labelFamilies } yield {
      prolog.append("[")
      for { label <- labelFamily } yield { prolog.append(label.text); if (label.parentUid != "self") prolog.append(",") }
      prolog.append("]")
      if (labelsCount != 0) {
        prolog.append(",")
        labelsCount = (labelsCount - 1)
      }
    }
    prolog.append(")")
    prolog.toString()
  }

  /**
    * Get the previous and current search labels for the session uri
    * these labels are then utilised to cancel previous request and create a new
    * one respectively
    * @param sessionUriName
    * @return current and previous search labels for context
    */
  def getCurrentPreviousLabel(sessionUriName: String): (String, String) = {
    val sessionStorage = window.sessionStorage
    sessionUriName match {
      case ProjectsViewItems.PROJECTS_SESSION_URI =>
        (sessionStorage.getItem(ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH), sessionStorage.getItem(ProjectsViewItems.PREVIOUS_PROJECTS_LABEL_SEARCH))
      case MessagesViewItems.MESSAGES_SESSION_URI =>
        (sessionStorage.getItem(MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH), sessionStorage.getItem(MessagesViewItems.PREVIOUS_MESSAGE_LABEL_SEARCH))
    }
  }
}

