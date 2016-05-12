package synereo.client.handlers

import diode.{ Effect, ActionHandler, ModelRW }
import diode.data.PotAction
import shared.dtos._
import shared.models.LabelModel
import shared.RootModels.{ SearchesRootModel, MessagesRootModel }
import synereo.client.services.{ SYNEREOCircuit, CoreApi }
import synereo.client.utils.{ Utils, PrologParser }
import org.scalajs.dom._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.Success

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels: Seq[String]): SearchesRootModel = {
    if (listOfLabels != Nil) {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      try {
        upickle.default.read[Seq[LabelModel]](JSON.stringify(labelsArray))
      } catch {
        case e: Exception =>
          SearchesRootModel(Nil)
      }
      val model = upickle.default.read[Seq[LabelModel]](JSON.stringify(labelsArray))
      SearchesRootModel(model)
    } else {
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
  var listE = new ListBuffer[LabelModel]() /*Seq[Label]()*/
  /*var searchLabels = new ListBuffer[Seq[Label]]()*/
  def GetChildren(label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] = {
    children = labels.filter(p => p.parentUid == label.uid)
    if (!children.isEmpty) {
      listE ++= children
      children.map(e => GetChildren(e, labels))
    }
    listE
  }
  def GetChildrenToParent(label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] = {
    children = labels.filter(p => p.uid == label.parentUid)
    if (!children.isEmpty) {
      listE ++= children
      children.map(e => GetChildrenToParent(e, labels))
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
  override def handle = {
    case CreateLabels() =>
      val listOfLabelFromStore = window.sessionStorage.getItem("listOfLabels")
      //      println("listOfLabelFromStore"+listOfLabelFromStore)
      if (listOfLabelFromStore != null) {
        try {
          upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        } catch {
          case e: Exception =>
        }
        val listOfLabels = upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        //        println("listOfLabels"+listOfLabels)
        if (value.searchesModel.isEmpty)
          updated(SearchesModelHandler.GetSearchesModel(listOfLabels))
        else
          noChange
      } else {
        updated(SearchesModelHandler.GetSearchesModel(Nil))
      }
    case UpdateLabel(label) =>
      SearchesModelHandler.listE.clear()
      val children = SearchesModelHandler.GetChildren(label, value.searchesModel)
      if (!children.isEmpty) {
        val test = value.searchesModel.map(e => if (children.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      }
      val childrenToParent = SearchesModelHandler.GetChildrenToParent(label, value.searchesModel)
      val modelModified = value.searchesModel.map(e => if (childrenToParent.exists(p => p.uid == e.uid) || e.uid == label.uid) e.copy(isChecked = label.isChecked)
      else e)
      val modelToUpdate = modelModified.map(e => if (e.parentUid == "self" && childrenToParent.exists(p => p.uid == e.uid)) {
        SearchesModelHandler.listE.clear()
        val childList = SearchesModelHandler.GetChildren(e, modelModified)
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
        val selectedChildren = SearchesModelHandler.GetChildren(selectedRootParent, value.searchesModel).filter(e => e.isChecked == true)
        val family = (selectedChildren :+ selectedRootParent)
        labelFamilies.append(family)
      }
      window.sessionStorage.setItem("currentSearchLabel", Utils.GetLabelProlog(labelFamilies))
      labelFamilies.clear()
      noChange
  }

}