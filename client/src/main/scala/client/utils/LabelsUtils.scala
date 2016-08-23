package client.utils

import client.modules.AppModule
import shared.models.Label


/**
  * Created by Mandar on 6/7/2016.
  */
object LabelsUtils {

  object PrologTypes {
    val Any = "any"
    val Each = "each"
  }

  def getSystemLabels(): Seq[String] = {
    Seq(AppUtils.MESSAGE_POST_LABEL, AppUtils.PROJECT_POST_LABEL, AppUtils.PROFILE_POST_LABEL)
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

  def getSystemLabelModel(viewName: String): Label = {
    val labelName = viewName match {
      case AppModule.MESSAGES_VIEW => AppUtils.MESSAGE_POST_LABEL
      case AppModule.PROFILES_VIEW => AppUtils.PROFILE_POST_LABEL
      case AppModule.PROJECTS_VIEW => AppUtils.PROJECT_POST_LABEL
    }
    getLabelModel(labelName)
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
