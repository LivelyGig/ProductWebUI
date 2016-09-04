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
import scala.util.{Failure, Success}
import diode.AnyAction._
import synereo.client.utils.{AppUtils, ConnectionsUtils, ContentUtils}

// Actions
//scalastyle:off
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

case class StoreCnxnAndLabels(slctzId: String, sessionUriName: String)

case class ClearMessages()


class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {

  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case action: RefreshMessages =>
      val updateF = action.effectWithRetry {
        CoreApi.sessionPing(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value)
      } { messagesResponse =>
        // toggle pinger to re issue session ping
        SYNEREOCircuit.dispatch(TogglePinger())
        MessagesRootModel(ContentUtils
        .processRes(messagesResponse)
        .asInstanceOf[Seq[MessagePost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearMessages() =>
      updated(Pot.empty)
  }
}