package client.handlers

import java.util.UUID
import javax.annotation.PostConstruct

import client.logger
import diode.{ ActionHandler, ActionResult, Effect, ModelRW }
import shared.dtos.{ Expression, ExpressionContent, Label, SubscribeRequest, _ }
import shared.models.{ Post, UserModel }
import client.services.{ CoreApi, LGCircuit }
import client.utils.Utils
import org.scalajs.dom.window
import org.widok.moment.Moment

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON
import scala.util.{ Failure, Success }

case class LoginUser(userModel: UserModel)
case class LogoutUser()
case class CreateSessions(userModel2: UserModel)
case class PostMessage(content: String, connectionStringSeq: Seq[String], sessionUri: String)
case class PostContent(value: Post, connectionStringSeq: Seq[String], sessionUri: String)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp != null) {
        modelFromStore = UserModel(email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
          imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true)
      }
      updated(modelFromStore)
    case CreateSessions(userModel2) =>
      val sessionURISeq = Seq(CoreApi.MESSAGES_SESSION_URI, CoreApi.JOBS_SESSION_URI)
      for (sessionURI <- sessionURISeq) {
        CoreApi.agentLogin(userModel2).map { responseStr =>
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          window.sessionStorage.setItem(sessionURI, response.content.sessionURI)
        }
      }

      noChange

    case PostMessage(content: String, connectionStringSeq: Seq[String], sessionUri: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val createdDateTime = Moment().format("YYYY-MM-DD hh:mm:ss")
      val connectionsSeq = Seq(Utils.GetSelfConnnection(sessionUri)) ++ connectionStringSeq.map(connectionString => upickle.default.read[Connection](connectionString))
      val value = ExpressionContentValue(uid.toString, "TEXT", createdDateTime, createdDateTime, Map[Label, String]().empty, connectionsSeq, content)
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUri), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, "[1111]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => logger.log.debug("Message Post Successful")
        case Failure(response) => logger.log.error(s"Message Post Failure Message: ${response.getMessage}")
      }
      noChange

    case PostContent(value: Post, connectionStringSeq: Seq[String], sessionUri: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val connectionsSeq = Seq(Utils.GetSelfConnnection(sessionUri)) ++ connectionStringSeq.map(connectionString => upickle.default.read[Connection](connectionString))
      //      val value =  ExpressionContentValue(uid.toString,"TEXT","2016-04-15 16:31:46","2016-04-15 16:31:46",Map[Label, String]().empty,connectionsSeq,content)
      val labelToPost = sessionUri match {
        case CoreApi.MESSAGES_SESSION_URI => "MESSAGEPOST"
        case CoreApi.JOBS_SESSION_URI => "JOBPOST"
      }
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUri), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, s"[$labelToPost]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => logger.log.debug("Content Post Successful")
        case Failure(response) => logger.log.error(s"Contetnt Post Failure Message: ${response.getMessage}")
      }
      noChange

    case LogoutUser() =>
      CoreApi.cancelPreviousSubsForLabelSearch()
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false))
  }
}
