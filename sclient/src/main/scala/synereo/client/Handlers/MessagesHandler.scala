package synereo.client.handlers

import diode._
import diode.data._
import shared.models.{LabelModel, MessagePost}
import shared.RootModels.MessagesRootModel
import diode.util.{Retry, RetryPolicy}
import org.scalajs.dom.window
import shared.sessionitems.SessionItems
import synereo.client.components.LabelsSelectize
import synereo.client.modules.AppModule
import synereo.client.services.{SYNEREOCircuit, CoreApi}
import synereo.client.utils.Utils

import scala.concurrent.ExecutionContext.Implicits.global

// Actions
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(5))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}


case class SearchMessagesOnLabels(selectizeInputId: Option[String])

//object MessagesModelHandler {
//  def getMessagesModel(response: String): MessagesRootModel = {
//    val messagesFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
//    def filterMessages(messages: ApiResponse[EvalSubscribeResponseContent]): Option[MessagesModel] = {
//      try {
//        Some(upickle.default.read[MessagesModel](messages.content.pageOfPosts(0)))
//      } catch {
//        case e: Exception =>
//          None
//      }
//    }
//    val model = messagesFromBackend
//      .filterNot(_.content.pageOfPosts.isEmpty)
//      .flatMap(filterMessages)
//      .sortWith((x, y) => ((Moment(x.created).utc()).isAfter(Moment(y.created).utc())))
//      .map(message => (message.copy(created = Moment(Moment.utc(message.created).toDate()).format("YYYY-MM-DD hh:mm:ss").toString)))
//    MessagesRootModel(model)
//  }
//}

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  var labelFamily = Utils.getLabelProlog(Nil)

  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case SearchMessagesOnLabels(selectizeInputId: Option[String]) =>
      val currentSearchLabelsFromSelectize = selectizeInputId match {
        case Some(s) => LabelsSelectize.getLabelsFromSelectizeInput(s)
        case None => Nil
      }
      val labelModels: Seq[Seq[LabelModel]] = currentSearchLabelsFromSelectize.map(currentLabel =>
        Seq(LabelModel("", text = currentLabel, "", imgSrc = "", "", false))
      )
//      println("labelModels = " + labelModels)
      val searchLabels = Utils.getLabelProlog(labelModels)
      labelFamily = searchLabels
      window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, searchLabels)
      //      println("searchLabels" + searchLabels)
      //      labelModels.foreach(println)
      //      window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, currentSearchLabelsFromSelectize.mkString(","))
      //      var searchLabels: Seq[Seq[LabelModel]] = Nil
      //      val currentSearchLabels = window.sessionStorage.getItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH).split(",")
      //      println(currentSearchLabelsFromSelectize.mkString(","))
      noChange

    case action: RefreshMessages =>
      //      val labels = Utils.getLabelProlog(Nil)
      //if(labelFamily!= null)
      //  window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, labelFamily)
      // else
      window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, labelFamily)
      val updateF = action.effectWithRetry {
        CoreApi.getContent(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      } { messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse, AppModule.MESSAGES_VIEW)
        .asInstanceOf[Seq[MessagePost]])
      }
      Option(labelFamily) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          updated(Empty)
      }
  }
}