package livelygig.client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import livelygig.client.dtos._
import livelygig.client.models.Label
import livelygig.client.rootmodels.SearchesRootModel
import livelygig.client.services.CoreApi
import livelygig.client.utils.{Utils, PrologParser}
import org.scalajs.dom._
//import scala.concurrent.ExecutionContext.Implicits.global

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
  def GetChildren(label: Label, labels: Seq[Label]): Seq[Label] ={
   labels.filter(e => e.parentUid == label.uid)


  }
}

case class CreateLabels()
case class UpdateLabel(label: Label)
case class SearchWithLabels()
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

    case action: SearchWithLabels =>
      //      val modelToQuery = value.searchesModel.filter(SearchesModelHandler.IsChecked)
//      any(Seq("Splicious"))

//      println(Utils.GetLabelProlog(Seq(value.searchesModel.filter(p=>p.isChecked==true))))
//      println(any(Seq("Splicious")).toString)
      val selfConnection = Utils.GetSelfConnnection()
      println(selfConnection)
      val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(selfConnection),"any([Splicious])")))
//      action.effect()
//      Effect(CoreApi.evalSubscribeRequest(getMessagesSubscription))
//      action.effect()
////      .onComplete{
////        case Success(response) =>
////          CoreApi.sessionPing()
////      }
      updated(value)
  }

}
