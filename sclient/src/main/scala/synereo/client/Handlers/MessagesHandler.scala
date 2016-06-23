package synereo.client.handlers

import diode._
import diode.data._
import shared.models.{Label, MessagePost}
import shared.RootModels.MessagesRootModel
import diode.util.{Retry, RetryPolicy}
import org.scalajs.dom.window
import shared.dtos.Connection
import shared.sessionitems.SessionItems
import synereo.client.components.LabelsSelectize
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


case class StoreMessagesLabels(selectizeInputId: Option[String])

case class StoreCnxnAndLabels(lblslctzId: Option[String], cnxnslctzId: Option[String], sessionUriName: String)

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  //  var labelFamily = LabelsUtils.getLabelProlog(Nil)

  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case StoreMessagesLabels(selectizeInputId: Option[String]) =>
      val crntSearchLblsFrmSelctize = selectizeInputId match {
        case Some(lblSelectizeInputId) => LabelsSelectize.getLabelsFromSelectizeInput(lblSelectizeInputId)
        case None => Nil
      }
      //      val searchLabels = LabelsUtils.buildProlog(crntSearchLblsFrmSelctize.map(currentLabel =>
      //        Label(text = currentLabel.text)), LabelsUtils.PrologTypes.Each)
      val searchLabels = LabelsUtils.buildProlog(
        Seq(Label(text = SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)) ++ crntSearchLblsFrmSelctize.map(currentLabel => Label(text = currentLabel.text)
        ), LabelsUtils.PrologTypes.Each)
      window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH, searchLabels)

      noChange

    case StoreCnxnAndLabels(lblslctzId: Option[String], cnxnslctzId: Option[String], sessionUriName: String) =>
      val crntSearchLblsFrmSelctize = lblslctzId match {
        case Some(lblSelectizeInputId) => LabelsSelectize.getLabelsFromSelectizeInput(lblSelectizeInputId)
        case None => Nil
      }
      //      println(s"crntSearchLblsFrmSelctize :$crntSearchLblsFrmSelctize")
      val connectionsSeq = ConnectionsUtils.getCnxsSeq(cnxnslctzId, sessionUriName)
      window.sessionStorage.setItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST,
        upickle.default.write[Seq[Connection]](connectionsSeq)
      )

      noChange

    case action: RefreshMessages =>
      //      println(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH)
      val labels = window.sessionStorage.getItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH)
      //      println(labels)
      val updateF = action.effectWithRetry {
        CoreApi.getContent(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI)
      } { messagesResponse => MessagesRootModel(ContentModelHandler
        .getContentModel(messagesResponse, AppModule.MESSAGES_VIEW)
        .asInstanceOf[Seq[MessagePost]])
      }
      Option(labels) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          //          println("in empty")
          window.sessionStorage.setItem(SessionItems.MessagesViewItems.CURRENT_MESSAGE_LABEL_SEARCH,
            LabelsUtils.buildProlog(Seq(Label(text = SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)), LabelsUtils.PrologTypes.Any))
          action.handleWith(this, updateF)(PotActionRetriable.handler())
      }
  }
}