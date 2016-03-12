package l.client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import l.client.dtos._
import l.client.models.Label
import l.client.rootmodels.{MessagesRootModel, SearchesRootModel}
import l.client.services.{LGCircuit, CoreApi}
import l.client.utils.{Utils, PrologParser}
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
  //   def GetChildren(label: Label, labels: Seq[Label]):  Seq[Label]={
  // //  labels.filter(e => e.parentUid == label.uid)
  //    children = labels.filter(p=>p.parentUid==label.uid)
  //    if (!children.isEmpty){
  //      listE ++= children
  //      //println("childerbn" + children)
  //     // println("listE" + listE)
  //      children.map(e => GetChildren(e, labels))
  //    }
  //        listE
  //  }

  def GetChildrenToParent(label: Label, labels: Seq[Label]):  Seq[Label]={
    //  labels.filter(e => e.parentUid == label.uid)
    // println(labels)
    children = labels.filter(p=>p.uid==label.parentUid)
    if (!children.isEmpty){
      listE ++= children
      // println("childernToParent" + children)
      // println("listEToParent" + listE)
      children.map(e => GetChildrenToParent(e, labels))
    }
    listE
  }
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
      //      if (label.parentUid == ""){
      // val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
      val childrenToParent = SearchesModelHandler.GetChildrenToParent(label,value.searchesModel)
      // println("children" + children)
      // println("childrenToParent" + childrenToParent)
      val test = value.searchesModel.map(e=> /*if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked) else*/
        if (childrenToParent.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked)
        else e)
      updated(SearchesRootModel(test))

    case SubscribeSearch() =>
      val labels = window.sessionStorage.getItem("messageSearchLabel")
      if (labels!=null){
        val selfConnection = Utils.GetSelfConnnection()
//        println("in searchWithLabel")
        val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(selfConnection),"any([Splicious])")))
        effectOnly(Effect(CoreApi.evalSubscribeRequest(getMessagesSubscription)))
      } else {
        noChange
      }
  }

}
