package client.handler

import client.modules.AppModule
import diode._
import diode.data._
import shared.models.MessagePost
import client.RootModels.MessagesRootModel
import client.logger
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.utils.{AppUtils, ConnectionsUtils}
import shared.dtos.{CancelSubscribeRequest, Expression, ExpressionContent, SubscribeRequest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
// scalastyle:off
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(5))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

case class ClearMessages()


class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  //  var labelFamily = LabelsUtils.getLabelProlog(Nil)

  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case action: RefreshMessages =>
      val updateF = action.effectWithRetry {
        CoreApi.sessionPing(LGCircuit.zoom(_.session.messagesSessionUri).value)
      } { messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse, AppModule.MESSAGES_VIEW)
        .asInstanceOf[Seq[MessagePost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearMessages() =>
      updated(Pot.empty)

  }
}