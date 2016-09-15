package client.utils

import java.util.UUID

import client.handler.AcceptNotification
import client.logger
import client.modules.AppModule
import client.services.{ApiTypes, LGCircuit}
import shared.dtos.{Connection, Expression, ExpressionContent, SubscribeRequest}
import shared.models._

import scala.scalajs.js.Date
import shared.dtos.{ApiResponse, ConnectionProfileResponse, Introduction}
import shared.models.ConnectionsModel
import client.handler._
import client.logger._
import client.services.LGCircuit
import diode.AnyAction._

/**
  * Created by shubham.k on 05-08-2016.
  */

object AppUtils {
  val MESSAGE_POST_LABEL = "MESSAGEPOSTLABEL"
  val PROJECT_POST_LABEL = "PROJECTPOSTLABEL"
  val PROFILE_POST_LABEL = "PROFILESPOSTLABEL"

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
    SubscribeRequest(AppUtils.getSessionUri(viewName),
      Expression(ApiTypes.requestTypes.INSERT_CONTENT,
        ExpressionContent(cnxns, prolog, contentToPost, uid)))
  }

    def handleInitialSessionPingRes(response: String): Unit = {
      var cnxnSeq: Seq[ConnectionsModel] = Nil
      var introSeq: Seq[Introduction] = Nil
      val responseArray = upickle.json.read(response)
      responseArray.arr.foreach {
        obj =>
          try {
            val jsonObj = upickle.json.write(obj)
            if(jsonObj.contains("connectionProfileResponse")) {
              val apiRes = upickle.default.read[ApiResponse[ConnectionProfileResponse]](upickle.json.write(obj))
              cnxnSeq :+= ConnectionsUtils.getCnxnFromRes(apiRes.content)
            }
            if (jsonObj.contains("introductionNotification")) {
              val apiRes = upickle.default.read[ApiResponse[Introduction]](upickle.json.write(obj))
              introSeq :+= apiRes.content
            }

          } catch {
            case e: Exception =>
              logger.log.error(e.toString)

          }
      }
      LGCircuit.dispatch(UpdateConnections(cnxnSeq))
      LGCircuit.dispatch(AcceptNotification(introSeq))

  }



}
