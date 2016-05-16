package client.handlers

import java.util.UUID
import javax.annotation.PostConstruct

import client.logger
import client.logger._
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import shared.dtos.{Expression, ExpressionContent, Label, SubscribeRequest, _}
import shared.models.{Post, UserModel}
import client.services.{CoreApi, LGCircuit}
import client.utils.Utils
import org.querki.jquery._
import org.scalajs.dom.window
import org.widok.moment.Moment
import shared.sessionitems.SessionItems

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
// scalastyle:off
case class LoginUser(userModel: UserModel)
case class LogoutUser()
case class PostMessage(content: String, connectionStringSeq: Seq[String], sessionUriName: String)
case class PostContent(value: Post, connectionStringSeq: Seq[String], sessionUri: String)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp != null) {
        modelFromStore = UserModel(
          email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
          imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true
        )
      }
      updated(modelFromStore)

    case PostMessage(content: String, connectionStringSeq: Seq[String], sessionUriName: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val createdDateTime = Moment().utc().format("YYYY-MM-DD hh:mm:ss")
      val connectionsSeq = Seq(Utils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName))) ++ connectionStringSeq.map(connectionString => upickle.default.read[Connection](connectionString))
      val value = ExpressionContentValue(uid.toString, "TEXT", createdDateTime, createdDateTime, Map[Label, String]().empty, connectionsSeq, content)
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUriName), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, "[1111]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => logger.log.debug("Message Post Successful")
        case Failure(response) => logger.log.error(s"Message Post Failure Message: ${response.getMessage}")
      }
      noChange

    case PostContent(value: Post, connectionStringSeq: Seq[String], sessionUriName: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val connectionsSeq = Seq(Utils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName))) ++ connectionStringSeq.map(connectionString => upickle.default.read[Connection](connectionString))
      //      val value =  ExpressionContentValue(uid.toString,"TEXT","2016-04-15 16:31:46","2016-04-15 16:31:46",Map[Label, String]().empty,connectionsSeq,content)
      val labelToPost = sessionUriName match {
        case SessionItems.MessagesViewItems.MESSAGES_SESSION_URI => SessionItems.MessagesViewItems.MESSAGE_POST_LABEL
        case SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI => SessionItems.ProjectsViewItems.PROJECT_POST_LABEL
      }
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUriName), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, s"[$labelToPost]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => logger.log.debug("Content Post Successful")
        case Failure(response) => logger.log.error(s"Contetnt Post Failure Message: ${response.getMessage}")
      }
      noChange

    case LogoutUser() =>
      // todo: Cancel all subscribe request for all sessions
      /*val sessionURISeq = SessionItems.getAllSessionUriNameExceptCnxs()
      val futureArray = for (sessionURI <- sessionURISeq) yield {
        val connectionsList = upickle.default.read[Seq[Connection]](window.sessionStorage.getItem("connectionsList")) ++ Seq(Utils.getSelfConnnection(window.sessionStorage.getItem(sessionURI))) // scalastyle:ignore
        val cancelPreviousRequest = CancelSubscribeRequest(sessionURI, connectionsList, previousSearchLabels)
        CoreApi.agentLogin(userModel)
      }
      Future.sequence(futureArray).map { responseArray =>
        for (responseStr <- responseArray) {
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          window.sessionStorage.setItem(sessionURISeq(responseArray.indexOf(responseStr)), response.content.sessionURI)
        }
      }*/
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false))
  }
}
