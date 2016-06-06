package synereo.client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import shared.dtos._
import shared.models.LabelModel
import shared.RootModels.{SearchesRootModel, MessagesRootModel}
import shared.sessionitems.SessionItems
import synereo.client.components.LabelsSelectize
import synereo.client.services.{SYNEREOCircuit, CoreApi}
import synereo.client.utils.{Utils, PrologParser}
import org.scalajs.dom._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.Success

object SearchesModelHandler {
  def getSearchesModel(listOfLabels: Seq[String]): SearchesRootModel = {
    try {
      val model = upickle.default.read[Seq[LabelModel]](JSON.stringify(PrologParser.StringToLabel(listOfLabels.toJSArray)))
      SearchesRootModel(model)
    } catch {
      case e: Exception =>
        SearchesRootModel(Nil)
    }
  }

  def updateModel(label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] = {
    val children = labels.filter(p => p.parentUid == label.uid)
    if (!children.isEmpty) {
      children.map(e => updateModel(e, labels))
    }
    labels
  }

  var children = Seq[LabelModel]()
  var listE = new ListBuffer[LabelModel]()

  /**
    * Uses the list buffer to hold children to a particular label
    * It iterated over label collection and add the children to
    * the list buffer in recurssion till it reaches the last children.
    *
    * @param label  is the label for which we need children
    * @param labels is the collection of all labels
    * @return seq of all children label
    */

  def getChildren(label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] = {
    children = labels.filter(p => p.parentUid == label.uid)
    if (!children.isEmpty) {
      listE ++= children
      children.map(e => getChildren(e, labels))
    }
    listE
  }

  def getChildrenToParent(label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] = {
    children = labels.filter(p => p.uid == label.parentUid)
    if (!children.isEmpty) {
      listE ++= children
      children.map(e => getChildrenToParent(e, labels))
    }
    listE
  }

  var labelFamilies = new ListBuffer[LabelModel]()
  /*def GetLabelFamilies (label: Label,labels: Seq[Label]) : Seq[Label] = {
    labelFamilies
  }*/
}

case class CreateLabels()

case class UpdateLabel(label: LabelModel)

case class SubscribeSearch()


class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW) {
  // scalastyle:off
  override def handle = {

    case CreateLabels() =>
      val listOfLabelFromStore = window.sessionStorage.getItem("listOfLabels")
      //      println("listOfLabelFromStore:" + listOfLabelFromStore)
      if (listOfLabelFromStore != null) {
        try {
          upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        } catch {
          case e: Exception =>
            println("")
        }
        val listOfLabels = upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        //        println("listOfLabels"+listOfLabels)
        if (value.searchesModel.isEmpty)
          updated(SearchesModelHandler.getSearchesModel(listOfLabels))
        else
          noChange
      } else {
        updated(SearchesModelHandler.getSearchesModel(Nil))
      }
    case UpdateLabel(label) =>
      SearchesModelHandler.listE.clear()
      val children = SearchesModelHandler.getChildren(label, value.searchesModel)
      if (!children.isEmpty) {
        val test = value.searchesModel.map(e => if (children.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      }
      val childrenToParent = SearchesModelHandler.getChildrenToParent(label, value.searchesModel)
      val modelModified = value.searchesModel.map(e => if (childrenToParent.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked)
      else e)
      val modelToUpdate = modelModified.map(e => if (e.parentUid == "self" && childrenToParent.exists(p => p.uid == e.uid)) {
        SearchesModelHandler.listE.clear()
        val childList = SearchesModelHandler.getChildren(e, modelModified)
        val selectedChildList = childList.filter(p => p.isChecked == true)
        if (!selectedChildList.isEmpty) {
          e.copy(isChecked = true)
        } else
          e.copy(isChecked = false)
      } else e)
      updated(SearchesRootModel(modelToUpdate))

    case SubscribeSearch() =>
      val selectedRootParents = value.searchesModel.filter(e => e.isChecked == true && e.parentUid == "self")
      val labelFamilies = ListBuffer[Seq[LabelModel]]()
      selectedRootParents.foreach { selectedRootParent =>
        SearchesModelHandler.listE.clear()
        val selectedChildren = SearchesModelHandler.getChildren(selectedRootParent, value.searchesModel).filter(e => e.isChecked == true)
        val family = (selectedChildren :+ selectedRootParent)
        labelFamilies.append(family)
      }
      window.sessionStorage.setItem("currentSearchLabel", Utils.getLabelProlog(labelFamilies))
      labelFamilies.clear()
      noChange
  }

}