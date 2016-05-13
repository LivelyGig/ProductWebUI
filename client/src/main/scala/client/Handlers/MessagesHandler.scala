package client.handlers

import diode.data.PotState.{ PotFailed, PotPending }
import diode._
import diode.data._
import shared.models.MessagesModel
import shared.RootModels.MessagesRootModel
import client.services.{ CoreApi, SessionItems }
import shared.dtos._
import client.utils.Utils
import diode.util.{ Retry, RetryPolicy }
import org.scalajs.dom.window
import org.widok.moment.Moment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// Actions
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

object MessagesModelHandler {
  def getMessagesModel(response: String): MessagesRootModel = {
    val messagesFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
    def filterMessages(messages: ApiResponse[EvalSubscribeResponseContent]): Option[MessagesModel] = {
      try {
        Some(upickle.default.read[MessagesModel](messages.content.pageOfPosts(0)))
      } catch {
        case e: Exception =>
          None
      }
    }
    val model = messagesFromBackend
      .filterNot(_.content.pageOfPosts.isEmpty)
      .flatMap(filterMessages)
      .sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
    MessagesRootModel(model)
  }

}

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case action: RefreshMessages =>
      val labels = window.sessionStorage.getItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH)
      val updateF = action.effectWithRetry {
        CoreApi.getContent(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      }(messages => MessagesModelHandler.getMessagesModel(messages))
      Option(labels) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          updated(Empty)
      }
    /* if (labels != null) {
        action.handleWith(this, updateF)(PotActionRetriable.handler())
      } else {
        updated(Empty)
      }*/
  }
}