package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.SearchesRootModel
import livelygig.client.components.PrologParser
import livelygig.client.models.Node

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 2/23/2016.
  */

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels :Seq[String]): SearchesRootModel ={
    val labelsObj = listOfLabels.map(obj => PrologParser.StringToLabel(obj))
    val model = SearchesRootModel(upickle.default.read[Seq[Node]](JSON.stringify(labelsObj(0))))
    println(model)
    model
  }
  /*upickle.default.read[Seq[Node]](JSON.stringify(something(0)))*/
}

case class CreateLabels(listOfLabels :Seq[String])
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW){
  override def handle = {
    case CreateLabels(listOfLabels) =>
      updated(SearchesModelHandler.GetSearchesModel(listOfLabels))

  }

}
