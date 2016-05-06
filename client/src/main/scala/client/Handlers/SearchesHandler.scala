package client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import shared.dtos._
import shared.models.LabelModel
import shared.RootModels.{MessagesRootModel, SearchesRootModel}
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
        upickle.default.read[Seq[LabelModel]](JSON.stringify(labelsArray))
      } catch {
        case e: Exception =>
          SearchesRootModel(Nil)
      }
      val model = upickle.default.read[Seq[LabelModel]](JSON.stringify(labelsArray))
      SearchesRootModel(model)
    }
    else {
      SearchesRootModel(Nil)
    }
  }
  def updateModel (label: LabelModel, labels: Seq[LabelModel]): Seq[LabelModel] ={
    val children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      children.map(e => updateModel(e, labels))
    }
    labels
  }
  var children = Seq[LabelModel]()
  var labelsBuffer = new ListBuffer[LabelModel]()

  /**
    * Uses the list buffer to hold children to a particular label
    * It iterated over label collection and add the children to
    * the list buffer in recurssion till it reaches the last children.
    * @param label is the label for which we need children
    * @param labels is the collection of all labels
    * @return seq of all children label
    */
  def GetChildren(label: LabelModel, labels: Seq[LabelModel]):  Seq[LabelModel]={
    children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      labelsBuffer ++= children
      children.map(e => GetChildren(e, labels))
    }
    labelsBuffer
  }

  /**
    * This function is reverse logic of above function with a small difference
    * Here the recurssion captures parent from child to root
    * so forxample
    * @param label is the label whose parents are required
    * @param labels is the collection of all labels
    * @return
    */
  def GetChildrenToParent(label: LabelModel, labels: Seq[LabelModel]):  Seq[LabelModel]={
    children = labels.filter(p=>p.uid==label.parentUid)
    if (!children.isEmpty){
      labelsBuffer ++= children
      children.map(e => GetChildrenToParent(e, labels))
    }
    labelsBuffer
  }
  var labelFamilies = new ListBuffer[LabelModel]()
  /*def GetLabelFamilies (label: Label,labels: Seq[Label]) : Seq[Label] = {

    labelFamilies
  }*/
}

case class CreateLabels()
case class UpdateLabel(label: LabelModel)
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
        if (value.searchesModel.isEmpty)
          updated(SearchesModelHandler.GetSearchesModel(listOfLabels))
        else
          noChange
      } else {
        updated(SearchesModelHandler.GetSearchesModel(Nil))
      }


    /**
      * This is the action to update the label in searches view.
      * The logic mostly deals with getting the appropriate label from childrent to parent
      * and parent to label
      * Label structure is as such that each label consists of parent uri which is the
      * uri to the parent label.
      * If a particular label is the root the parent uri is "self"
      */
    case UpdateLabel(label) =>
      SearchesModelHandler.labelsBuffer.clear()
      val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
      if (!children.isEmpty){
        /*set the state of all children same as the label in argument*/
        val updatedChildren = value.searchesModel.map(e=> if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(updatedChildren))
      }
      /*Subsequent process is aimed at selecting the proper state to all labels from children to parent
      * This is repeated in recurssion so if the parent has other children which are selected its state is
      * kept at true otherwise changed to false
      * */
      val childrenToParent = SearchesModelHandler.GetChildrenToParent(label,value.searchesModel)
      val modelModified = value.searchesModel.map(e=> if (childrenToParent.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked)
        else e)
      val modelToUpdate = modelModified.map(e=>if (e.parentUid=="self" && childrenToParent.exists(p=>p.uid == e.uid)){
        SearchesModelHandler.labelsBuffer.clear()
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
      val labelFamilies = ListBuffer[Seq[LabelModel]]()
      selectedRootParents.foreach{selectedRootParent=>
        SearchesModelHandler.labelsBuffer.clear()
          val selectedChildren = SearchesModelHandler.GetChildren(selectedRootParent,value.searchesModel).filter(e=>e.isChecked==true)
        val family = (selectedChildren:+selectedRootParent)
        labelFamilies.append(family)
      }
      window.sessionStorage.setItem("currentSearchLabel",Utils.GetLabelProlog(labelFamilies))
      labelFamilies.clear()
      noChange
  }

}
