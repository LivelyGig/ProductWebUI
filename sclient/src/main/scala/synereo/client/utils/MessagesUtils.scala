package synereo.client.utils

import java.util.UUID

import shared.dtos.{Connection, Expression, ExpressionContent, SubscribeRequest}
import shared.models.{Label, MessagePost, MessagePostContent, PostContent}
import synereo.client.services.{ApiTypes, SYNEREOCircuit}

import scala.scalajs.js.Date

/**
  * Created by shubham.k on 15-07-2016.
  */
object MessagesUtils {
  /**
    * This  method gives the message post data which can be posted to the server
    * @param postContent the content of the post with the message
    * @param cnxns all connection to which post will be sent
    * @param labels all labels for which post will be sent
    * @return
    */
  def getPostData(postContent: PostContent, cnxns: Seq[Connection], labels: Seq[Label]): SubscribeRequest = {
    val uid = UUID.randomUUID().toString.replaceAll("-", "")
    val labelToPost = Seq(LabelsUtils.getLabelModel(AppUtils.MESSAGE_POST_LABEL)) ++ labels
    val contentToPost = upickle.default.write(MessagePost(uid, new Date().toISOString(),
      new Date().toISOString(), "", cnxns, postContent.asInstanceOf[MessagePostContent]))
    val prolog = LabelsUtils.buildProlog(labelToPost, LabelsUtils.PrologTypes.Each)
    // logger.log.debug(s"prolog = $prolog")
    SubscribeRequest(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value,
      Expression(ApiTypes.requestTypes.INSERT_CONTENT,
        ExpressionContent(cnxns, prolog, contentToPost, uid)))
  }
}
