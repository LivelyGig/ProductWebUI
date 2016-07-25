package synereo.client.handlers

import diode._
import diode.data._
import shared.models.{Label, MessagePost}
import shared.RootModels.MessagesRootModel
import diode.util.{Retry, RetryPolicy}
import org.scalajs.dom.window
import shared.dtos.Connection
import shared.sessionitems.SessionItems
import synereo.client.components.{ConnectionsLabelsSelectize, LabelsSelectize}
import synereo.client.modules.AppModule
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import synereo.client.utils.{ConnectionsUtils, LabelsUtils}

import scala.concurrent.ExecutionContext.Implicits.global

// Actions
//scalastyle:off
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(5))
  extends PotActionRetriable[MessagesRootModel, RefreshMessages] {
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy): RefreshMessages = RefreshMessages(value, newRetryPolicy)
}

case class StoreCnxnAndLabels(slctzId: String, sessionUriName: String)

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  //  var labelFamily = LabelsUtils.getLabelProlog(Nil)

  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case action: RefreshMessages =>
      val updateF = action.effectWithRetry {
        CoreApi.getContent(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      } { messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse, AppModule.MESSAGES_VIEW)
        .asInstanceOf[Seq[MessagePost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())
  }
}