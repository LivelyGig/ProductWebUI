package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.components.PrologParser
import livelygig.client.dtos.{Connection, ExpressionContent, Expression, SubscribeRequest}
import livelygig.client.models.{Leaf, Node, SearchesModel}
import org.scalajs.dom._
import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 2/23/2016.
  */

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels :Seq[String]): SearchesRootModel ={
    if (listOfLabels != Nil) {
      val labelsObj = listOfLabels.map(obj => PrologParser.StringToLabel(obj))
      val model = labelsObj.map { label =>
        val labelStr = JSON.stringify(label)
        val labelJson = JSON.parse(labelStr)
        val labelType = labelJson.labelType.asInstanceOf[String]
        if (labelType == "node") {
          //          SearchesModel(upickle.default.read[Seq[SearchesModel]](JSON.stringify(labelsObj(0))))
          try {
            upickle.default.read[Node](labelStr)
          } catch {
            case e: Exception =>
              //              println(e)
              SearchesModel(None,None,"")
          }
          val node = upickle.default.read[Node](labelStr)
          SearchesModel(Some(node),None,node.uid)
        } else {
          val leaf = upickle.default.read[Leaf](labelStr)
          SearchesModel(None,Some(leaf),leaf.uid)
        }
        //        SearchesModel(None,None,"")
      }
      SearchesRootModel(model)
    } else {
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
case class UpdateLabels(searchesRootModel: SearchesRootModel)
case class UpdateLabel(searchesModel: SearchesModel)
case class CheckNode (node: Node)
case class CheckLeaf (leaf: Leaf)
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
    case UpdateLabels(searchRootModel) =>
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
      }
    case SearchWithLabels() =>
      //      val modelToQuery = value.searchesModel.filter(SearchesModelHandler.IsChecked)
      SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(Connection("","","")),"alias")))
      updated(value)
  }

}
