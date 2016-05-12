package synereo.client.handlers

import diode.data.PotState.{ PotFailed, PotPending }
import diode.{ ActionHandler, Effect, ModelRW }
import diode.data.{ Empty, Pot, PotAction, Ready }
import diode._
import diode.data._
import org.scalajs.dom._
import shared.RootModels.MessagesRootModel
import shared.dtos.EvalSubscribeResponseContent
import shared.models.MessagesModel
import shared.dtos.{ ApiResponse, EvalSubscribeResponseContent }
import synereo.client.services.CoreApi
import diode.util.{ Retry, RetryPolicy }
import org.scalajs.dom._
import synereo.client.utils.Utils
import org.widok.moment._
import scala.concurrent.ExecutionContext.Implicits.global
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
  def GetMessagesModel(response: String): MessagesRootModel = {
    val messagesFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
    val model = messagesFromBackend
      .filterNot(_.content.pageOfPosts.isEmpty)
      .map(message => upickle.default.read[MessagesModel](message.content.pageOfPosts(0)))
      .sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
    MessagesRootModel(model)
  }
}

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action: RefreshMessages =>
      val labels = Utils.GetLabelProlog(Nil)
      window.sessionStorage.setItem("currentSearchLabel", labels)
      if (labels != null) {
        val updateF = action.effect(CoreApi.getMessages())(messages => MessagesModelHandler.GetMessagesModel(messages))
        action.handleWith(this, updateF)(PotAction.handler())
      } else {
        updated(Empty)
      }
  }
}