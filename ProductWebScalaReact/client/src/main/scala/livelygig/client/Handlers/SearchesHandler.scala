package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.components.PrologParser
import livelygig.client.dtos.{Connection, ExpressionContent, Expression, SubscribeRequest}
import livelygig.client.models.Label
import org.scalajs.dom._

import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 2/23/2016.
  */

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels :Seq[String]): SearchesRootModel ={
    if (listOfLabels != Nil) {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
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
        val test = value.searchesModel.map(e=> if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid ) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      } else {
        updated(value.updated(label))
      }

    case SearchWithLabels() =>
      //      val modelToQuery = value.searchesModel.filter(SearchesModelHandler.IsChecked)
      SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(Connection("","","")),"alias")))
      updated(value)
  }

}
