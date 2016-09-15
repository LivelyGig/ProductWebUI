package client.handler

import client.modules.AppModule
import diode._
import diode.data._
import shared.models.MessagePost
import client.rootmodel.MessagesRootModel
import client.logger
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.utils.{AppUtils, ConnectionsUtils, ContentUtils}
import org.widok.moment.Moment
import shared.dtos.{CancelSubscribeRequest, Expression, ExpressionContent, SubscribeRequest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

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
      } { messagesResponse =>
        LGCircuit.dispatch(RefreshMessages())
        val currentVal = if (value.nonEmpty) value.get.messagesModelList else Nil
        val msg = currentVal ++ ContentUtils
          .processRes(messagesResponse)
          .filterNot(_.pageOfPosts.isEmpty)
          .flatMap(content => Try(upickle.default.read[MessagePost](content.pageOfPosts(0))).toOption)
        MessagesRootModel(msg.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created))))
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearMessages() =>
      updated(Pot.empty)

  }
}