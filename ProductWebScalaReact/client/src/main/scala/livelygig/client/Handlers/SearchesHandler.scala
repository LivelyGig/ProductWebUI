package livelygig.client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import livelygig.client.dtos._
import livelygig.client.models.Label
import livelygig.client.rootmodels.{MessagesRootModel, SearchesRootModel}
import livelygig.client.services.{LGCircuit, CoreApi}
import livelygig.client.utils.{Utils, PrologParser}
import org.scalajs.dom._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.Success

/**
  * Created by shubham.k on 2/23/2016.
  */

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

  def GetChildren(label: Label, labels: Seq[Label]): Seq[Label] ={
    //  labels.filter(e => e.parentUid == label.uid)
    children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      println("shilderbn" + children)

      children.map(e => GetChildren(e, labels))
    }

    //    GetLeafs(labels)


    // for loop execution with a yield
    val retVal= for{ a <- labels
                     if (a.uid == label.uid)
    }yield a
    // Now print returned values using another loop.
    for( a <- retVal){
      listE.append(a)
      println( "Value of a: " + a)
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
      if (label.parentUid == "self"){
        val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
        println("children" + children)
        val test = value.searchesModel.map(e=> if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid ) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      } else {
        updated(value.updated(label))
      }

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
