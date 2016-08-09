package client.utils

import java.util.UUID

import client.logger
import client.modules.AppModule
import client.services.{ApiTypes, LGCircuit}
import shared.dtos.{Connection, Expression, ExpressionContent, SubscribeRequest}
import shared.models._

import scala.scalajs.js.Date

/**
  * Created by shubham.k on 05-08-2016.
  */
object AppUtils {
  val MESSAGE_POST_LABEL = "MESSAGEPOSTLABEL"
  val PROJECT_POST_LABEL = "PROJECTPOSTLABEL"
  val PROFILE_POST_LABEL = "PROFILEPOSTLABEL"

  def getSessionUri (viewName: String): String = {
    viewName match {
      case AppModule.MESSAGES_VIEW => LGCircuit.zoom(_.session.messagesSessionUri).value
      case AppModule.PROFILES_VIEW => LGCircuit.zoom(_.session.profileSessionUri).value
      case AppModule.PROJECTS_VIEW => LGCircuit.zoom(_.session.projectSessionUri).value
    }
  }

  def getContentToPost(postContent: PostContent, cnxns: Seq[Connection], uid:String, viewName: String): String = {
    viewName match {
      case AppModule.MESSAGES_VIEW => upickle.default.write(MessagePost(uid, new Date().toISOString(), new Date().toISOString(), "", cnxns, postContent.asInstanceOf[MessagePostContent]))
      case AppModule.PROFILES_VIEW => upickle.default.write(ProfilesPost(uid, new Date().toISOString(), new Date().toISOString(), "", cnxns, postContent.asInstanceOf[ProfilePostContent]))
      case AppModule.PROJECTS_VIEW => upickle.default.write(ProjectsPost(uid, new Date().toISOString(), new Date().toISOString(), "", cnxns, postContent.asInstanceOf[ProjectPostContent]))

    }

  }

  def getPostData(postContent: PostContent, cnxns: Seq[Connection], labels: Seq[Label], viewName: String): SubscribeRequest = {
    val uid = UUID.randomUUID().toString.replaceAll("-", "")
    val labelToPost = Seq(LabelsUtils.getSystemLabelModel(viewName)) ++ labels
    val contentToPost = getContentToPost(postContent, cnxns,uid,viewName)
    val prolog = LabelsUtils.buildProlog(labelToPost, LabelsUtils.PrologTypes.Each)
    logger.log.debug(s"prolog = $prolog")
    SubscribeRequest(LGCircuit.zoom(_.user.sessionUri).value,
      Expression(ApiTypes.INSERT_CONTENT,
        ExpressionContent(cnxns, prolog, contentToPost, uid)))
  }
}
