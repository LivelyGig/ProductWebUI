package synereo.client.handlers

import diode._
import diode.data._
import shared.models.MessagePost
import shared.RootModels.MessagesRootModel
import diode.util.{Retry, RetryPolicy}
import shared.dtos._
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import diode.AnyAction._
import synereo.client.utils.{AppUtils, ConnectionsUtils}

// Actions
//scalastyle:off
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(5))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

case class StoreCnxnAndLabels(slctzId: String, sessionUriName: String)

case class SubsForMsgAndBeginSessionPing()

case class SubsForMsg(req: SubscribeRequest)

case class CancelPreviousAndSubscribeNew(req: SubscribeRequest)

case class ClearMessages()

case class PostMessage (req: SubscribeRequest)

case class PendingMsg()

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  //  var labelFamily = LabelsUtils.getLabelProlog(Nil)

  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case action: RefreshMessages =>
      val updateF = action.effectWithRetry {
        CoreApi.sessionPing(SYNEREOCircuit.zoom(_.user.sessionUri).value)
      } { messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse)
        .asInstanceOf[Seq[MessagePost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case SubsForMsgAndBeginSessionPing() =>
      val expr = Expression("feedExpr", ExpressionContent(SYNEREOCircuit.zoom(_.connections.connections).value ++ Seq(ConnectionsUtils.getSelfConnnection()),
        s"any([${AppUtils.MESSAGE_POST_LABEL}])"))
      val req = SubscribeRequest(SYNEREOCircuit.zoom(_.user.sessionUri).value, expr)
      SYNEREOCircuit.dispatch(ClearMessages())
      var count = 1
      subscribe()
      def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
        case Success(res) =>
          SYNEREOCircuit.dispatch(UpdatePrevSearchCnxn(req.expression.content.cnxns))
          SYNEREOCircuit.dispatch(UpdatePrevSearchLabel(req.expression.content.label))
          SYNEREOCircuit.dispatch(RefreshMessages())
        case Failure(res) =>
          if (count == 3) {
            logger.log.error("Open Error modal Popup")
          } else {
            count = count + 1
            subscribe()
            logger.log.error("Error in subscription")
          }

      }

      noChange

    case SubsForMsg(req: SubscribeRequest) =>
      var count = 1
      subscribe()
      def subscribe(): Unit = CoreApi.evalSubscribeRequest(req).onComplete {
        case Success(res) =>
          SYNEREOCircuit.dispatch(UpdatePrevSearchCnxn(req.expression.content.cnxns))
          SYNEREOCircuit.dispatch(UpdatePrevSearchLabel(req.expression.content.label))
        case Failure(res) =>
          if (count == 3) {
            logger.log.error("Open Error modal Popup")
          } else {
            count = count + 1
            subscribe()
            logger.log.error("Error in subscription")
          }

      }

      noChange

    case ClearMessages() =>
      updated(Pot.empty)

    case CancelPreviousAndSubscribeNew(req: SubscribeRequest) =>
      SYNEREOCircuit.dispatch(ClearMessages())
      var count = 1
      cancelPrevious()
      def cancelPrevious(): Unit = CoreApi.cancelSubscriptionRequest(CancelSubscribeRequest(
        SYNEREOCircuit.zoom(_.user.sessionUri).value, SYNEREOCircuit.zoom(_.searches.previousSearchCnxn).value,
        SYNEREOCircuit.zoom(_.searches.previousSearchLabel).value)).onComplete {
        case Success(res) =>
          SYNEREOCircuit.dispatch(SubsForMsg(req))
        case Failure(res) =>
          if (count == 3) {
            logger.log.error("server error")
          } else {
            count = count + 1
            cancelPrevious()
          }
      }
      noChange

    case PostMessage(req) =>
      var count = 1
      postMsg()
      def postMsg(): Unit = CoreApi.evalSubscribeRequest(req).onComplete{
        case Success(res) =>
          logger.log.debug("message post success")
        case Failure(fail) =>
          if (count == 3) {
            logger.log.error("server error")
          } else {
            count = count + 1
            postMsg()
          }
      }
      noChange

  }
}