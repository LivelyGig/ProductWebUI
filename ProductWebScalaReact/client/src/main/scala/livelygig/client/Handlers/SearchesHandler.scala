package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.components.PrologParser
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
          SearchesModel(Some(node),None,"")
        } else {
          val leaf = upickle.default.read[Leaf](labelStr)
           SearchesModel(None,Some(leaf),"")
        }
//        SearchesModel(None,None,"")
      }
      SearchesRootModel(model)
    } else {
      SearchesRootModel(Nil)
    }

  }
  /*upickle.default.read[Seq[Node]](JSON.stringify(something(0)))*/
}

case class CreateLabels()
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


  }

}
