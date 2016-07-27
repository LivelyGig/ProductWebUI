package synereo.client.handlers

import java.util.UUID

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos.{Expression, ExpressionContent, SubscribeRequest, _}
import shared.models.{Label, _}
import org.scalajs.dom.window
import synereo.client.sessionitems.SessionItems
import synereo.client.logger
import synereo.client.services.{ApiTypes, CoreApi}
import synereo.client.utils.LabelsUtils

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.Date
import scala.util.{Failure, Success}

// scalastyle:off
case class LoginUser(userModel: UserModel)

case class LogoutUser()

case class UpdateUser(updateUserRequest: UpdateUserRequest)

//case class PostData(postContent: PostContent, sessionUriName: String,cnnxns : Seq[Connection], labels: Seq[Label])

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  //  val messageLoader = "#messageLoader"
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp != null) {
        modelFromStore = UserModel(
          email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
          imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true, isAvailable = true
        )
      }
      updated(modelFromStore)

    /*case PostData(postContent: PostContent, sessionUriName: String,cnnxns : Seq[Connection], labels: Seq[Label]) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val (labelToPost, contentToPost) = sessionUriName match {
        case SessionItems.MessagesViewItems.MESSAGES_SESSION_URI =>
          (Seq(LabelsUtils.getLabelModel(SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)) ++ labels,
            upickle.default.write(MessagePost(uid, new Date().toISOString(), new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[MessagePostContent])))
        case SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI =>
          (Seq(LabelsUtils.getLabelModel(SessionItems.ProjectsViewItems.PROJECT_POST_LABEL)),
            upickle.default.write(ProjectsPost(uid, new Date().toISOString(), new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[ProjectPostContent])))
        case SessionItems.ProfilesViewItems.PROFILES_SESSION_URI =>
          (Seq(LabelsUtils.getLabelModel(SessionItems.ProfilesViewItems.PROFILES_POST_LABEL)),
            upickle.default.write(ProfilesPost(uid, new Date().toISOString(), new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[ProfilePostContent])))
      }
      val prolog = LabelsUtils.buildProlog(labelToPost, LabelsUtils.PrologTypes.Each)
      logger.log.debug(s"prolog = $prolog")
      CoreApi.evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUriName), Expression(ApiTypes.INSERT_CONTENT, ExpressionContent(cnnxns, prolog, contentToPost, uid)))).onComplete {
        case Success(response) => logger.log.debug("Content Post Successful")
        case Failure(response) => logger.log.error(s"Content Post Failure Message: ${response.getMessage}")
      }
      noChange*/

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

    case UpdateUser(updateUserRequest: UpdateUserRequest) =>
      val updatedUser = value.copy(imgSrc = updateUserRequest.jsonBlob.imgSrc)
      updated(updatedUser)
  }
}
