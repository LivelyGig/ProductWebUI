package synereo.client.handlers

import java.util.UUID
import org.querki.jquery._
import synereo.client.logger._
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import shared.dtos.{Expression, ExpressionContent, Label, SubscribeRequest, _}
import shared.models._
import org.scalajs.dom.window
import shared.sessionitems.SessionItems
import synereo.client.components.ConnectionsSelectize
import synereo.client.services.{SYNEREOCircuit, ApiTypes, CoreApi}
import synereo.client.utils.Utils
import concurrent._
import scala.scalajs.js.Object
import ExecutionContext.Implicits._
import scala.scalajs.js
import scala.scalajs.js.{Date, JSON}
import scala.util.{Failure, Success}
// scalastyle:off
case class LoginUser(userModel: UserModel)
case class LogoutUser()
case class PostData(postContent: PostContent, selectizeInputId: Option[String], sessionUri: String)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  val messageLoader = "#messageLoader"
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

    case PostData(value: PostContent, selectizeInputId: Option[String], sessionUriName: String) =>

      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val connectionsFromSelectize = selectizeInputId match {
        case Some(s) => ConnectionsSelectize.getConnectionsFromSelectizeInput(s)
        case None => Nil
      }
      val connectionsSeq =  Seq(Utils.getSelfConnnection(window.sessionStorage.getItem(sessionUriName)))++  connectionsFromSelectize
//      connectionsSeq.flatten
      val (labelToPost, contentToPost) = sessionUriName match {
        case SessionItems.MessagesViewItems.MESSAGES_SESSION_URI =>
          (SessionItems.MessagesViewItems.MESSAGE_POST_LABEL,
            upickle.default.write(MessagePost(uid, new Date().toISOString(), new Date().toISOString(),"" , connectionsSeq, value.asInstanceOf[MessagePostContent])))
        case SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI =>
          (SessionItems.ProjectsViewItems.PROJECT_POST_LABEL,
            upickle.default.write(ProjectsPost(uid, new Date().toISOString(), new Date().toISOString(),"" , connectionsSeq, value.asInstanceOf[ProjectPostContent])))
      }
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUriName), Expression(ApiTypes.INSERT_CONTENT, ExpressionContent(connectionsSeq, s"[$labelToPost]", contentToPost, uid)))).onComplete {
        case Success(response) => {
          sessionUriName match {
            case SessionItems.MessagesViewItems.MESSAGES_SESSION_URI => SYNEREOCircuit.dispatch(RefreshMessages())
          }
          $(messageLoader).addClass("hidden")
          log.debug("Content Post Successful")
        }
        case Failure(response) => log.error(s"Content Post Failure Message: ${response.getMessage}")
      }
      noChange

    case LogoutUser() =>
      // todo: Cancel all subscribe request for all sessions
      /*val sessionURISeq = SessionItems.getAllSessionUriNameExceptCnxs()
      val futureArray = for (sessionURI <- sessionURISeq) yield {
        val connectionsList = upickle.default.read[Seq[Connection]](window.sessionStorage.getItem("connectionsList")) ++ Seq(Utils.getSelfConnnection(window.sessionStorage.getItem(sessionURI))) // scalastyle:ignore
        val cancelPreviousRequest = CancelSubscribeRequest(sessionURI, connectionsList, previousSearchLabels)
        CoreApi.agentLogin(signUpModel)
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
