package synereo.client.handlers

import diode.data.PotState.{ PotFailed, PotPending }
import diode.{ ActionHandler, Effect, ModelRW }
import diode.data.{ Empty, Pot, PotAction, Ready }
import diode._
import diode.data._
import org.scalajs.dom._
import shared.RootModels.MessagesRootModel
import shared.dtos.EvalSubscribeResponseContent
import shared.models.{MessagePost}
import shared.dtos.{ ApiResponse, EvalSubscribeResponseContent }
import shared.sessionitems.SessionItems
import synereo.client.modules.AppModule
import synereo.client.services.CoreApi
import diode.util.{ Retry, RetryPolicy }
import org.scalajs.dom._
import synereo.client.utils.Utils
import org.widok.moment._
import scala.concurrent.ExecutionContext.Implicits.global
import diode.util.{Retry, RetryPolicy}
import scala.scalajs.js
import scala.scalajs.js.JSON
import org.querki.jquery._
import japgolly.scalajs.react._

/**
 * Created by shubham.k on 1/25/2016.
 */
// Actions
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty) extends PotAction[MessagesRootModel, RefreshMessages] {
  println(Pot)

  override def next(value: Pot[MessagesRootModel]) = RefreshMessages(value)
}

object MessagesModelHandler {
  var momentTime = ""
  def GetMessagesModel(response: String): MessagesRootModel = {
    val messagesFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
    val model = messagesFromBackend
      .filterNot(_.content.pageOfPosts.isEmpty)
      .map(message => upickle.default.read[MessagePost](message.content.pageOfPosts(0)))
      .sortWith((x, y) => (Moment(x.created).utc()).isAfter(Moment(y.created).utc()))
      .map(message => message.copy(created = Moment(Moment.utc(message.created).toDate()).format("YYYY-MM-DD hh:mm:ss").toString))
    MessagesRootModel(model)
  }
}

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