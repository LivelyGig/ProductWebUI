package client.handlers

import client.modules.AppModule
import diode._
import diode.data._
import shared.models.MessagePost
import shared.RootModels.MessagesRootModel
import client.services.CoreApi
import diode.util.{Retry, RetryPolicy}
import org.scalajs.dom.window
import shared.sessionitems.SessionItems

import scala.concurrent.ExecutionContext.Implicits.global

// Actions
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}
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
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case action: RefreshMessages =>
      val labels = window.sessionStorage.getItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH)
      val updateF = action.effectWithRetry {
        CoreApi.getContent(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      }{messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse, AppModule.MESSAGES_VIEW)
        .asInstanceOf[Seq[MessagePost]])}
      Option(labels) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          updated(Empty)
      }
  }
}