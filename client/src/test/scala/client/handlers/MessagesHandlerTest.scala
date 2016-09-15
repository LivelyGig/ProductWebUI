package client.handlers

import client.UnitTest
import client.handler.{ClearMessages, MessagesHandler}
import client.rootmodel.MessagesRootModel
import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import diode.data.{Pot, Ready}
import shared.dtos.{Connection, Expression, ExpressionContent, SubscribeRequest}
import shared.models.{MessagePost, MessagePostContent}

/**
  * Created by shubham.k on 14-09-2016.
  */
class MessagesHandlerTest extends UnitTest("MessagesHandler") {

  val model: Pot[MessagesRootModel] = Ready(MessagesRootModel(Nil))
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

  val newSubscribeRequest =  SubscribeRequest("",newExpression)
  val newExpression =  Expression("",newExpressionContent)
  val newExpressionContent =  ExpressionContent(newCnxnSeq,"label","value","uid")


  "ClearMessages" should "clear messages" in {
    val result = handler.handle(ClearMessages())
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.isEmpty == true)
      case _ =>
        assert(false)
    }
  }

}
