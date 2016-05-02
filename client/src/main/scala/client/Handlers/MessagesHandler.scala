package client.handlers

import diode.data.PotState.{PotFailed, PotPending}
import diode._
import diode.data._
import shared.models.MessagesModel
import shared.RootModels.MessagesRootModel
import client.services.CoreApi
import shared.dtos._
import client.utils.Utils
import diode.util.{Retry, RetryPolicy}
import org.scalajs.dom._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// Actions
case class RefreshMessages(potResult: Pot[MessagesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3)) extends PotActionRetriable[MessagesRootModel, RefreshMessages]{
  override def next(value: Pot[MessagesRootModel], newRetryPolicy: RetryPolicy) = RefreshMessages(value, newRetryPolicy)
}

object MessagesModelHandler{
  def GetMessagesModel(response: String): MessagesRootModel = {
    try {
      upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
    } catch {
      case e: Exception =>
        println(e)
    }
    val messagesFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)

    //      println(model(0).content.pageOfPosts(0))
    //      println(upickle.default.read[PageOfPosts](model(0).content.pageOfPosts(0)))
    var model = Seq[MessagesModel]()
    for(projectFromBackend <- messagesFromBackend){
      //      println(upickle.default.read[PageOfPosts](projectFromBackend.content.pageOfPosts(0)))
      try {
        if (!projectFromBackend.content.pageOfPosts.isEmpty){
          val project = upickle.default.read[MessagesModel](projectFromBackend.content.pageOfPosts(0))
          model:+= project
        }
      } catch {
        case e: Exception =>
//          println(projectFromBackend.content.pageOfPosts(0))
      }
      //      if (!projectFromBackend.content.pageOfPosts.isEmpty)

    }
    //        println(model)
    MessagesRootModel(model)
  }

}

class MessagesHandler[M](modelRW: ModelRW[M, Pot[MessagesRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action : RefreshMessages =>
      // todo investigate calling of this method due to callback
      //      println("in refresh messages")
      val labels = window.sessionStorage.getItem("currentSearchLabel")
      val updateF =  action.effectWithRetry(CoreApi.getMessages())(messages=>MessagesModelHandler.GetMessagesModel(messages))
      if (labels!=null)
      {
        action.handleWith(this,updateF)(PotActionRetriable.handler())
      } else {
        updated(Empty)
      }
  }
}