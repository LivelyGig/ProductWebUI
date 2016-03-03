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
//      for (labelObj <- labelsArray) {
////        val labelStr = JSON.stringify(labelObj)
//        /*val labelJson = JSON.parse(labelStr)*/
////        val label = upickle.default.read[Label](labelStr)
////        labelObj.
//        println(JSON.stringify(labelObj))
//
//      }
      SearchesRootModel(model)
    }
    else {
      SearchesRootModel(Nil)
    }

  }
  /*def IsChecked (searchModel: SearchesModel): Boolean = {
    searchModel.node match {
      case None =>
        searchModel.leaf match {
          case Some(leaf) =>
            return leaf.isChecked
        }
      case Some(node) =>
        if (node.isChecked)
          return node.isChecked
        else {
          var isChecked = false
          node.progeny.foreach{leaf =>
            if (leaf.isChecked) isChecked = true}
          return isChecked
        }

    }

  }*/
  /*upickle.default.read[Seq[Node]](JSON.stringify(something(0)))*/
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
        val listOfLabels = upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        //        println("listOfLabels"+listOfLabels)
        updated(SearchesModelHandler.GetSearchesModel(listOfLabels))
      } else {
        updated(SearchesModelHandler.GetSearchesModel(Nil))
      }
    case UpdateLabel(label) =>
      updated(value.updated(label))
    /*case UpdateLabels(searchRootModel) =>
      updated(searchRootModel)
    case UpdateLabel(searchModel) =>
      updated(value.updated(searchModel) )
    case CheckNode(node) =>
      val progenyModified = node.progeny.map(p=>p.copy(isChecked = node.isChecked))
      val newNode = node.copy(progeny = progenyModified)
      updated(value.updated(SearchesModel(Some(newNode),None,node.uid)))
    case CheckLeaf(leaf) =>
      if (leaf.parentUid == "self")
        updated(value.updated(SearchesModel(None,Some(leaf),leaf.uid)))
      else{
        val model = value.searchesModel.find(p=>p.uid == leaf.parentUid).get
        val node = model.node.get
        val leafIndex = node.progeny.indexWhere(p=>p.uid== leaf.uid)
        val progenyModified = node.progeny.patch(leafIndex,Seq(leaf),1)
        var isNodeChecked = false
        progenyModified.find(p=>p.isChecked == true) match {
          case Some(term) =>
            isNodeChecked = true
          case None =>
            isNodeChecked = false
        }
        val newNode = node.copy(isChecked = isNodeChecked ,progeny = progenyModified)
        updated(value.updated(model.copy(node = Some(newNode))))
      }*/
    case SearchWithLabels() =>
      //      val modelToQuery = value.searchesModel.filter(SearchesModelHandler.IsChecked)
      SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(Connection("","","")),"alias")))
      updated(value)
  }

}
