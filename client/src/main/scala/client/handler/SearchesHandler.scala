package client.handler

import client.rootmodel.SearchesRootModel
import client.modules.AppModule
import client.services.{CoreApi, LGCircuit}
import client.utils.PrologParser
import diode.{ActionHandler, ActionResult, ModelRW}
import shared.models.Label

import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
import diode.AnyAction._
import shared.dtos.{Connection, LabelPost, SubscribeRequest}

object SearchesModelHandler {
  def getSearchesModel(listOfLabels: Seq[String]): SearchesRootModel = {

    try {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      val model = upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      // logger.log.debug(s"searchesModel = ${model}")
      SearchesRootModel(model)
    } catch {
      case e: Exception =>
        SearchesRootModel(Nil)
    }
  }
  def leaf(text: String/*, color: String = "#CC5C64"*/) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"\"),image(\"\")))"

}

case class CreateLabels(labelStrSeq: Seq[String])

case class AddLabel(label: Label)

case class UpdatePrevSearchLabel(labelStr: String, viewName: String)

case class UpdatePrevSearchCnxn(cnxns: Seq[Connection], viewName: String)

case class PostLabelsAndMsg(labelNames: Seq[String], subscribeReq: SubscribeRequest)

case class UpdateLabel(label: Label)

case class UpdateLabels(labels: Seq[Label])

// scalastyle:off
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case CreateLabels(labelStrSeq: Seq[String]) =>
      try {
        updated(SearchesModelHandler.getSearchesModel(labelStrSeq))
      } catch {
        case e:Exception =>
          noChange
      }

    case AddLabel(label: Label) =>
      updated(value.copy(searchesModel = value.searchesModel ++ Seq(label)))

    case UpdatePrevSearchLabel(labelStr, viewName) =>
      viewName match {
        case AppModule.MESSAGES_VIEW => updated(value.copy(previousMsgSearchLabel = labelStr))
        case AppModule.PROFILES_VIEW => updated(value.copy(previousProfileSearchLabel = labelStr))
        case AppModule.PROJECTS_VIEW => updated(value.copy(previousProjectSearchLabel = labelStr))
      }

    case UpdatePrevSearchCnxn(cnxns, viewName) =>
      viewName match {
        case AppModule.MESSAGES_VIEW => updated(value.copy(previousMsgSearchCnxn = cnxns))
        case AppModule.PROFILES_VIEW => updated(value.copy(previousProfileSearchCnxn = cnxns))
        case AppModule.PROJECTS_VIEW => updated(value.copy(previousProjectSearchCnxn = cnxns))
      }

    case UpdateLabel(label) =>
      updated(value.copy(searchesModel = value.searchesModel.map(e => if (e.uid == label.uid) label else e)))

//    case UpdateLabels(labels: Seq[label])

  }

}
