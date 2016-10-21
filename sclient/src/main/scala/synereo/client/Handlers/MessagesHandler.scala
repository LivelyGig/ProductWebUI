package synereo.client.handlers

import diode._
import diode.data._
import shared.models.MessagePost
import synereo.client.rootmodels.MessagesRootModel
import diode.util.{Retry, RetryPolicy}
import shared.dtos._
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import diode.AnyAction._
import org.widok.moment.Moment
import synereo.client.utils.{AppUtils, ConnectionsUtils, ContentUtils}

// Actions
//scalastyle:off
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

//case class StoreCnxnAndLabels(slctzId: String, sessionUriName: String)

case class ClearMessages()


class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any, ActionResult[M]] = {
    /**
      * This is the prime action which maintains the ping cycle.
      * It extends the PotActionRetriable trait which makes it retry the action
      * if a failure occurs in the session ping
      * However it is observed that the retry action is triggered for any error in the
      * dom even if it is not bound to the MessagesRootModel.
      * This is not a problem as of now, however if you are seeing too many session ping in
      * your network tab then probably this action is the culprit. This may be a bug in
      * Diode library. Need to investigate.
      */
    case action: RefreshMessages =>
      val updateF = action.effectWithRetry {
        CoreApi.sessionPing(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value)
      } { messagesResponse =>
        SYNEREOCircuit.dispatch(RefreshMessages())
        val currentVal = if (value.nonEmpty) value.get.messagesModelList else Nil
        val msg = currentVal ++ ContentUtils
          .processRes(messagesResponse)
          .filterNot(_.pageOfPosts.isEmpty)
          .flatMap(content => Try(upickle.default.read[MessagePost](content.pageOfPosts(0))).toOption)
          .map(ConnectionsUtils.getSenderReceivers)
        MessagesRootModel(msg.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created))))
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearMessages() =>
      updated(Pot.empty)
  }
}