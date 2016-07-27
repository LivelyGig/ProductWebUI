package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.models.Label
import shared.RootModels.{SearchesRootModel}
import synereo.client.sessionitems.SessionItems
import synereo.client.utils.{LabelsUtils, PrologParser}
import org.scalajs.dom._
import synereo.client.logger
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON

object SearchesModelHandler {
  def getSearchesModel(listOfLabels: Seq[String]): SearchesRootModel = {

    try {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      val model = upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      logger.log.debug(s"searchesModel = ${model}")
      SearchesRootModel(model)
    } catch {
      case e: Exception =>
        SearchesRootModel(Nil)
    }
  }

  def updateModel(label: Label, labels: Seq[Label]): Seq[Label] = {
    val children = labels.filter(p => p.parentUid == label.uid)
    if (!children.isEmpty) {
      children.map(e => updateModel(e, labels))
    }
    labels
  }

  var children = Seq[Label]()
  var labelsBuffer = new ListBuffer[Label]()

  /**
    * Uses the list buffer to hold children to a particular label
    * It iterated over label collection and add the children to
    * the list buffer in recurssion till it reaches the last children.
    *
    * @param label  is the label for which we need children
    * @param labels is the collection of all labels
    * @return seq of all children label
    */
  def getChildren(label: Label, labels: Seq[Label]): Seq[Label] = {
    children = labels.filter(p => p.parentUid == label.uid)
    if (!children.isEmpty) {
      labelsBuffer ++= children
      children.map(e => getChildren(e, labels))
    }
    labelsBuffer
  }

  /**
    * This function is reverse logic of above function with a small difference
    * Here the recurssion captures parent from child to root
    * so forxample
    *
    * @param label  is the label whose parents are required
    * @param labels is the collection of all labels
    * @return
    */
  def getChildrenToParent(label: Label, labels: Seq[Label]): Seq[Label] = {
    children = labels.filter(p => p.uid == label.parentUid)
    if (!children.isEmpty) {
      labelsBuffer ++= children
      children.map(e => getChildrenToParent(e, labels))
    }
    labelsBuffer
  }

  var labelFamilies = new ListBuffer[Label]()
  /*def GetLabelFamilies (label: Label,labels: Seq[Label]) : Seq[Label] = {

    labelFamilies
  }*/
}

case class CreateLabels()

case class UpdateLabel(label: Label)

case class StoreMessagesSearchLabel()

case class StoreProjectsSearchLabel()

case class StoreProfilesSearchLabel()

// scalastyle:off
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case CreateLabels() =>
      val listOfLabelFromStore = window.sessionStorage.getItem(SessionItems.SearchesView.LIST_OF_LABELS)
      //      println(s"listOfLabelFromStore: $listOfLabelFromStore")
      if (listOfLabelFromStore != null) {
        val listOfLabels = upickle.default.read[Seq[String]](listOfLabelFromStore)
        if (value.searchesModel.isEmpty)
          updated(SearchesModelHandler.getSearchesModel(listOfLabels))
        else
          noChange
      } else {
        updated(SearchesModelHandler.getSearchesModel(Nil))
      }

    /**
      * This is the action to update the label in searches view.
      * The logic mostly deals with getting the appropriate label from childrent to parent
      * and parent to label
      * Label structure is as such that each label consists of parent uri which is the
      * uri to the parent label.
      * If a particular label is the root the parent uri is "self"
      */
    /*case UpdateLabel(label) =>
      // todo: refactor this silly!!! :)
      SearchesModelHandler.labelsBuffer.clear()
      val children = SearchesModelHandler.getChildren(label, value.searchesModel)
      if (!children.isEmpty) {
        /*set the state of all children same as the label in argument*/
        val updatedChildren = value.searchesModel.map(e => if (children.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(updatedChildren))
      }
      /*Subsequent process is aimed at selecting the proper state to all labels from children to parent
      * This is repeated in recurssion so if the parent has other children which are selected its state is
      * kept at true otherwise changed to false
      * */
      val childrenToParent = SearchesModelHandler.getChildrenToParent(label, value.searchesModel)
      val modelModified = value.searchesModel.map(e => if (childrenToParent.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked)
      else e)
      val modelToUpdate = modelModified.map(e => if (e.parentUid == "self" && childrenToParent.exists(p => p.uid == e.uid)) {
        SearchesModelHandler.labelsBuffer.clear()
        val childList = SearchesModelHandler.getChildren(e, modelModified)
        val selectedChildList = childList.filter(p => p.isChecked == true)
        if (!selectedChildList.isEmpty) {
          e.copy(isChecked = true)
        } else
          e.copy(isChecked = false)
      } else e)
      updated(SearchesRootModel(modelToUpdate))*/

    case StoreMessagesSearchLabel() =>
      val selectedRootParents = value.searchesModel.filter(e => e.isChecked == true && e.parentUid == "self")
      val labelFamilies = ListBuffer[Seq[Label]]()
      //      if (selectedRootParents.isEmpty)
      labelFamilies.append(Seq(LabelsUtils.getLabelModel(SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)))
      selectedRootParents.foreach { selectedRootParent =>
        SearchesModelHandler.labelsBuffer.clear()
        val selectedChildren = SearchesModelHandler.getChildren(selectedRootParent, value.searchesModel).filter(e => e.isChecked == true)
        val family = (selectedChildren :+ selectedRootParent)
        labelFamilies.append(family)
      }
      window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, LabelsUtils.getLabelProlog(labelFamilies))
      labelFamilies.clear()
      noChange

    case StoreProjectsSearchLabel() =>
      window.sessionStorage.setItem(SessionItems.ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH,
        LabelsUtils.buildProlog(Seq(LabelsUtils.getLabelModel(SessionItems.ProjectsViewItems.PROJECT_POST_LABEL)), LabelsUtils.PrologTypes.Any))
      noChange
    case StoreProfilesSearchLabel() =>
      window.sessionStorage.setItem(SessionItems.ProfilesViewItems.CURRENT_PROFILES_LABEL_SEARCH,
        LabelsUtils.buildProlog(Seq(LabelsUtils.getLabelModel(SessionItems.ProfilesViewItems.PROFILES_POST_LABEL)), LabelsUtils.PrologTypes.Any))
      noChange
  }

}
