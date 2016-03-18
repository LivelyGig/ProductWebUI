package client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import shared.dtos._
import client.models.Label
import client.rootmodels.{MessagesRootModel, SearchesRootModel}
import client.services.{LGCircuit, CoreApi}
import client.utils.{Utils, PrologParser}
import org.scalajs.dom._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.Success

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels :Seq[String]): SearchesRootModel ={
    if (listOfLabels != Nil) {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      try {
        upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      } catch {
        case e: Exception =>
          SearchesRootModel(Nil)
      }
      val model = upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      SearchesRootModel(model)
    }
    else {
      SearchesRootModel(Nil)
    }
  }
  def updateModel (label: Label, labels: Seq[Label]): Seq[Label] ={
    val children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      children.map(e => updateModel(e, labels))
    }
    labels
  }
  var children = Seq[Label]()
  var listE = new ListBuffer[Label]() /*Seq[Label]()*/
  /*var searchLabels = new ListBuffer[Seq[Label]]()*/
  def GetChildren(label: Label, labels: Seq[Label]):  Seq[Label]={
    children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      listE ++= children
      children.map(e => GetChildren(e, labels))
    }
    listE
  }
  def GetChildrenToParent(label: Label, labels: Seq[Label]):  Seq[Label]={
    children = labels.filter(p=>p.uid==label.parentUid)
    if (!children.isEmpty){
      listE ++= children
      children.map(e => GetChildrenToParent(e, labels))
    }
    listE
  }
  var labelFamilies = new ListBuffer[Label]()
  /*def GetLabelFamilies (label: Label,labels: Seq[Label]) : Seq[Label] = {

    labelFamilies
  }*/
}


case class CreateLabels()
case class UpdateLabel(label: Label)
case class SubscribeSearch()
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW){
  override def handle = {
    case CreateLabels() =>
      val listOfLabelFromStore = window.sessionStorage.getItem("listOfLabels")
      //      println("listOfLabelFromStore"+listOfLabelFromStore)
      if (listOfLabelFromStore != null){
        try {
          upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        } catch {
          case e: Exception =>
        }
        val listOfLabels = upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        //        println("listOfLabels"+listOfLabels)
        updated(SearchesModelHandler.GetSearchesModel(listOfLabels))
      } else {
        updated(SearchesModelHandler.GetSearchesModel(Nil))
      }
    case UpdateLabel(label) =>
      SearchesModelHandler.listE.clear()
//      value.
//      SearchesModelHandler.searchLabels.clear()
val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
      if (!children.isEmpty){
//        val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
        val test = value.searchesModel.map(e=> if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      }
      val childrenToParent = SearchesModelHandler.GetChildrenToParent(label,value.searchesModel)
//      val allLeafs = label +: childrenToParent
//      if(label.isChecked == true)
//        SearchesModelHandler.searchLabels.append(allLeafs.seq)
      val modelModified = value.searchesModel.map(e=> if (childrenToParent.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked)
        else e)
//       println(modelModified)
      val modelToUpdate = modelModified.map(e=>if (e.parentUid=="self" && childrenToParent.exists(p=>p.uid == e.uid)){
        SearchesModelHandler.listE.clear()
        val childList = SearchesModelHandler.GetChildren(e,modelModified)
        val selectedChildList = childList.filter(p=>p.isChecked == true)
        if (!selectedChildList.isEmpty){
          e.copy(isChecked = true)
        } else
          e.copy(isChecked = false)
      }else e)
      updated(SearchesRootModel(modelToUpdate))

    case SubscribeSearch() =>
      val selectedRootParents = value.searchesModel.filter(e=>e.isChecked==true && e.parentUid== "self")
      val labelFamilies = Seq[Seq[Label]]()
      selectedRootParents.foreach{selectedRootParent=>

          val selectedChildren = SearchesModelHandler.GetChildren(selectedRootParent,value.searchesModel).filter(e=>e.isChecked==true)
        labelFamilies:+(selectedChildren:+selectedRootParent)
      }
      println("labelfamilies:"+Utils.GetLabelProlog(labelFamilies))
      noChange
  }

}
