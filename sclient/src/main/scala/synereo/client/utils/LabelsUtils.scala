package synereo.client.utils

import org.scalajs.dom._
import shared.models.Label
import synereo.client.sessionitems.SessionItems.{MessagesViewItems, ProfilesViewItems, ProjectsViewItems}
import synereo.client.components.LabelsSelectize


/**
  * Created by Mandar on 6/7/2016.
  */
object LabelsUtils {

  object PrologTypes {
    val Any = "any"
    val Each = "each"
  }

  /**
    * Get the previous and current search labels for the session uri
    * these labels are then utilised to cancel previous request and create a new
    * one respectively
    *
    * @param sessionUriName ri name of the view associated
    *                       see SessionItems with Session uri
    *                       eg. SessionItems.ProfilesViewItems.PROFILES_SESSION_URI,
    *                       SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI,etc
    * @return current and previous search labels for context
    */
  def getCurrentPreviousLabel(sessionUriName: String): (String, String) = {
    val sessionStorage = window.sessionStorage
    sessionUriName match {
//      case ProjectsViewItems.PROJECTS_SESSION_URI =>
//        (sessionStorage.getItem(ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH), sessionStorage.getItem(ProjectsViewItems.PREVIOUS_PROJECTS_LABEL_SEARCH))
      case MessagesViewItems.MESSAGES_SESSION_URI =>
        (sessionStorage.getItem(MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH), sessionStorage.getItem(MessagesViewItems.PREVIOUS_MESSAGE_LABEL_SEARCH))
//      case ProfilesViewItems.PROFILES_SESSION_URI =>
//        (sessionStorage.getItem(ProfilesViewItems.CURRENT_PROFILES_LABEL_SEARCH), sessionStorage.getItem(ProfilesViewItems.PREVIOUS_PROFILES_LABEL_SEARCH))
    }
  }

  /**
    * This is a temporary method will modify later
    *
    * @param labels
    * @param queryType
    * @return
    */
  def buildProlog(labels: Seq[Label], queryType: String): String = {
    val labelPrologItems = labels.map(e => if (e != labels.last) s"[${e.text}]," else s"[${e.text}]").mkString
    s"$queryType($labelPrologItems)"
  }

  /*def getLabelsSeq(id: Option[String], sessionUriName: String): Seq[Label] = {
    id match {
      case None =>
        Nil
      case Some(res) =>
        LabelsSelectize.getLabelsFromSelectizeInput(res)
    }
  }*/

  def getLabelModel(labelName: String): Label = {
    Label(parentUid = "self", text = labelName)
  }

  def getSystemLabels(): Seq[String] = {
    Seq(MessagesViewItems.MESSAGE_POST_LABEL, ProjectsViewItems.PROJECT_POST_LABEL, ProfilesViewItems.PROFILES_POST_LABEL)
  }

  /**
    * *
    * This function is used to return the prolog used by
    * glosevel to evaluate the subscription requests.
    *
    * @param labelFamilies This is the seq of label families e.g seq of [parent1,child1ToParent1], [parent2,child1ToParent2]
    * @return returns the prolog term e.g any([label1,label2])
    */
  def getLabelProlog(labelFamilies: Seq[Seq[Label]]): String = {
    var labelsCount = labelFamilies.length - 1
    val prolog = StringBuilder.newBuilder
    prolog.append("any(")
    for {labelFamily <- labelFamilies} yield {
      prolog.append("[")
      for {label <- labelFamily} yield {
        prolog.append(label.text); if (label.parentUid != "self") prolog.append(",")
      }
      prolog.append("]")
      if (labelsCount != 0) {
        prolog.append(",")
        labelsCount = (labelsCount - 1)
      }
    }
    prolog.append(")")
    prolog.toString()
  }

}
