package synereo.client.handlers

import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import diode.data.{Pot, Ready}
import shared.dtos.{Connection, Expression, ExpressionContent, SubscribeRequest}
import shared.models.{MessagePost, MessagePostContent}
import synereo.client.UnitTest
import synereo.client.rootmodels.MessagesRootModel

/**
  * Created by bhagyashree.b on 2016-08-29.
  */

class MessagesHandlerTest extends UnitTest("MessagesHandlerTest") {

  val model: Pot[MessagesRootModel] = Ready(MessagesRootModel())
  //def build = new MotdHandler(new RootModelRW(model))


  val handler = new MessagesHandler(new RootModelRW(model))

  val newCnxnSeq = Seq(Connection("newSource1", "newLabel1", "newTarget1"),
    Connection("newSource2", "newLabel2", "newTarget2"),
    Connection("newSource3", "newLabel3", "newTarget3"))

  val newMessagePostContent =  MessagePostContent("text", "subject", "imgSrc")

  val newMessagePostSeq = Seq(MessagePost("uid1", "created", "modified", "label1", newCnxnSeq, newMessagePostContent),
    MessagePost("uid2", "created", "modified", "label2", newCnxnSeq, newMessagePostContent),
    MessagePost("uid3", "created", "modified", "label3", newCnxnSeq, newMessagePostContent)
  )

  val newSubscribeRequest = new SubscribeRequest("",newExpression)
  val newExpression = new Expression("",newExpressionContent)
  val newExpressionContent = new ExpressionContent(newCnxnSeq,"label","value","uid")


  "ClearMessages" should "clear messages" in {
    val result = handler.handle(ClearMessages())
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.isEmpty == true)
      case _ =>
        assert(false)
    }
  }
  //todo think of simulating the ajax behaviour

//  "PostMessage" should "Post a message" in {
//    val result = handler.handle(PostMessage(newSubscribeRequest))
//    result match {
//      case ModelUpdate(newValue) =>
//        assert(newValue.isReady == true)
//      case _=>
//        assert(false)
//    }
//  }

//  "CancelPreviousAndSubscribeNew" should "Cancel previous request and subscribe new request" in {
//    val result = handler.handle(CancelPreviousAndSubscribeNew(newSubscribeRequest))
//    result match {
//      case ModelUpdate(newValue) =>
//        assert(newValue.isReady == true)
//      case _=>
//        assert(false)
//    }
//  }

//  "SubsForMsg" should "Subscribe for message " in {
//    val result = handler.handle(SubsForMsg(newSubscribeRequest))
//    result match {
//      case ModelUpdate(newValue) =>
//        assert(newValue.isReady == true)
//      case _=>
//        assert(false)
//    }
//  }

}
