package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.models.Label
import shared.RootModels.SearchesRootModel
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
      // logger.log.debug(s"searchesModel = ${model}")
      SearchesRootModel(model)
    } catch {
      case e: Exception =>
        SearchesRootModel(Nil)
    }
  }
  def leaf(text: String/*, color: String = "#CC5C64"*/) = "leaf(text(\"" + s"${text}" + "\"),display(color(\"\"),image(\"\")))"
  def leafMod(text: String/*, color: String = "#CC5C64"*/) = "\"leaf(text(\\\"" + s"${text}" + "\\\"),display(color(\\\"\\\"),image(\\\"\\\")))\""

}

case class CreateLabels(labelStrSeq: Seq[String])

case class AddLabel(label: Label)

case class UpdatePrevSearchLabel(labelStr: String)

case class UpdatePrevSearchCnxn(cnxns: Seq[Connection])

case class PostLabelsAndMsg(labelNames: Seq[String], subscribeReq: SubscribeRequest)

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

    case UpdatePrevSearchLabel(labelStr) =>
      updated(value.copy(previousSearchLabel = labelStr))

    case UpdatePrevSearchCnxn(cnxns) =>
      updated(value.copy(previousSearchCnxn = cnxns))

    case PostLabelsAndMsg(labelsNames, subscribeReq) =>
      val labelPost = LabelPost(SYNEREOCircuit.zoom(_.user.sessionUri).value, labelsNames.map(SearchesModelHandler.leaf), "alias")
      var count = 1
      post()
      def post(): Unit = CoreApi.postLabel(labelPost).onComplete{
        case Success(res) =>
          SYNEREOCircuit.dispatch(PostMessage(subscribeReq))
          SYNEREOCircuit.dispatch(CreateLabels(labelsNames.map(SearchesModelHandler.leafMod)))
        case Failure(res) =>
          if (count == 3) {
//            logger.log.debug("server error")
            SYNEREOCircuit.dispatch(ShowServerError(res.getMessage))
          } else {
            count = count + 1
            post()
          }

      }
      noChange
  }

}
