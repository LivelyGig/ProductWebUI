package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.models.Label
import synereo.client.rootmodels.SearchesRootModel
import synereo.client.utils.PrologParser
import shared.dtos.{Connection, LabelPost, SubscribeRequest}
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
import diode.AnyAction._

object SearchesModelHandler {
  def getSearchesModel(listOfLabels: Seq[String]): SearchesRootModel = {

    try {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      val model = upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      SearchesRootModel(searchesModel = model)
    } catch {
      case e: Exception =>
        println("error in method getsearchesModel")
        SearchesRootModel(Nil)
    }
  }

  def leaf(text: String /*, color: String = "#CC5C64"*/) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"\"),image(\"\")))"

  def leafMod(text: String /*, color: String = "#CC5C64"*/) = "\"leaf(text(\\\"" + s"${text}" + "\\\"),display(color(\\\"\\\"),image(\\\"\\\")))\""

}

case class CreateLabels(labelStrSeq: Seq[String])

case class AddLabel(label: Label)

case class UpdatePrevSearchLabel(labelStr: String)

case class UpdatePrevSearchCnxn(cnxns: Seq[Connection])


// scalastyle:off
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case CreateLabels(labelStrSeq: Seq[String]) =>
      try {
        updated(SearchesModelHandler.getSearchesModel(labelStrSeq))
        //        noChange
      } catch {
        case e: Exception =>
          println(s" exception in Create Label action $e")
          noChange
      }

    case AddLabel(label: Label) =>
      updated(value.copy(searchesModel = value.searchesModel ++ Seq(label)))

    case UpdatePrevSearchLabel(labelStr) =>
      updated(value.copy(previousSearchLabel = labelStr))

    case UpdatePrevSearchCnxn(cnxns) =>
      updated(value.copy(previousSearchCnxn = cnxns))

  }

}
