package synereo.client.services

import java.util.UUID

import shared.dtos._
import shared.models.{Label, _}
import org.scalajs.dom._
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import org.scalajs.dom.ext.Ajax
import shared.sessionitems.SessionItems
import shared.sessionitems.SessionItems.ProfilesViewItems
import synereo.client.logger
import synereo.client.modules.{ConnectionList, Login}
import synereo.client.utils.{ConnectionsUtils, LabelsUtils}

import scala.scalajs.js.Date
import scala.util.{Failure, Success}

object CoreApi {
  var BASE_URL = s"http://${window.sessionStorage.getItem(SessionItems.ApiDetails.API_HOST)}:${window.sessionStorage.getItem(SessionItems.ApiDetails.API_PORT)}/api"
  // scalastyle:ignore
  var CREATE_USER_REQUEST = "createUserRequest"

  private def ajaxPost(requestContent: String): Future[String] = {
    Ajax.post(
      url = BASE_URL,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
    ).map(_.responseText)
  }

  //  val handleApiError: PartialFunction[Throwable, Unit] = {
  //    case t: Throwable => println(t.printStackTrace)
  //  }

  def getConnections(): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.SESSION_PING, SessionPing(window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI))))
    ajaxPost(requestContent)
  }

  def createUser(signUpModel: SignUpModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.CREATE_USER_REQUEST, CreateUser(signUpModel.email, signUpModel.password,
      Map("name" -> signUpModel.name), true)))
    ajaxPost(requestContent)
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.CONFIRM_EMAIL, ConfirmEmail(emailValidationModel.token)))
    ajaxPost(requestContent)
  }

  def agentLogin(userModel: UserModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.INITIALIZE_SESSION_REQUEST, InitializeSession(s"agent://email/${userModel.email}" +
      s"?password=${userModel.password}")))
    ajaxPost(requestContent)
  }

  def sessionPing(uri: String): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.SESSION_PING, SessionPing(uri)))
    ajaxPost(requestContent)
  }

  /**
    * Generic method to get content data from the backend
    * used to get search results on different views.
    *
    * @param sessionUriName uri name of the view associated
    *                       see SessionItems with Session uri
    *                       eg. SessionItems.ProfilesViewItems.PROFILES_SESSION_URI,
    *                       SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI,etc
    * @return Future with the response data
    */
  def getContent(sessionUriName: String): Future[String] = {
    val sessionUri = window.sessionStorage.getItem(sessionUriName)
    val connectionListTo = ConnectionsUtils.getCnxnForReq(sessionUri)
    val (currentSearchLabels, previousSearchLabels) = LabelsUtils.getCurrentPreviousLabel(sessionUriName)
    val getMessagesSubscription = SubscribeRequest(sessionUri, Expression(msgType = "feedExpr", ExpressionContent(connectionListTo, currentSearchLabels)))
    val cancelPreviousRequest = CancelSubscribeRequest(sessionUri, connectionListTo, previousSearchLabels)
    Option(previousSearchLabels) match {
      case Some(s) =>
        window.sessionStorage.setItem(ProfilesViewItems.PREVIOUS_PROFILES_LABEL_SEARCH, currentSearchLabels)
        for {
          cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
          messages <- evalSubscribeRequestAndSessionPing(getMessagesSubscription)
        } yield messages
      case None =>
        evalSubscribeRequestAndSessionPing(getMessagesSubscription)

    }
  }

  def evalSubscribeRequest(subscribeRequest: SubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.EVAL_SUBS_REQUEST, subscribeRequest))
    ajaxPost(requestContent)
  }

  def evalSubscribeRequestAndSessionPing(subscribeRequest: SubscribeRequest): Future[String] = {
    for {
      subscription <- evalSubscribeRequest(subscribeRequest)
      response <- sessionPing(subscribeRequest.sessionURI)
    } yield response
  }

  def cancelSubscriptionRequestAndSessionPing(cancelSubscribeRequest: CancelSubscribeRequest): Future[String] = {
    for {
      cancelRequest <- cancelSubscriptionRequest(cancelSubscribeRequest)
      response <- sessionPing(cancelSubscribeRequest.sessionURI)
    } yield response
  }

  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    ajaxPost(requestContent)
  }

  def postIntroduction(introductionModel: Content): Future[String] = {
    val msg = introductionModel match {
      case _: IntroConnections => ApiTypes.BEGIN_INTRODUCTION_REQUEST
      case _: EstablishConnection => ApiTypes.ESTABLISH_CONNECTION_REQ
      case _: IntroConfirmReq => ApiTypes.INTRODUCTION_CONFIRMATION_REQUEST
    }
    println(s"introductionModel : $introductionModel")
    ajaxPost(upickle.default.write(ApiRequest(msg, introductionModel)))
  }

  def postLabel(labelPost: LabelPost): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_ALIAS_LABEL_REQ, labelPost))
    ajaxPost(requestContent)
  }

  def postData(postContent: PostContent, sessionUriName: String, cnnxns: Seq[Connection], labels: Seq[Label]): Future[String] = {
    val uid = UUID.randomUUID().toString.replaceAll("-", "")
    val (labelToPost, contentToPost) = sessionUriName match {
      case SessionItems.MessagesViewItems.MESSAGES_SESSION_URI =>
        (Seq(LabelsUtils.getLabelModel(SessionItems.MessagesViewItems.MESSAGE_POST_LABEL)) ++ labels,
          upickle.default.write(MessagePost(uid, new Date().toISOString(), new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[MessagePostContent])))
      case SessionItems.ProjectsViewItems.PROJECTS_SESSION_URI =>
        (Seq(LabelsUtils.getLabelModel(SessionItems.ProjectsViewItems.PROJECT_POST_LABEL)),
          upickle.default.write(ProjectsPost(uid, new Date().toISOString(),
            new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[ProjectPostContent])))
      case SessionItems.ProfilesViewItems.PROFILES_SESSION_URI =>
        (Seq(LabelsUtils.getLabelModel(SessionItems.ProfilesViewItems.PROFILES_POST_LABEL)),
          upickle.default.write(ProfilesPost(uid, new Date().toISOString(),
            new Date().toISOString(), "", cnnxns, postContent.asInstanceOf[ProfilePostContent])))
    }
    val prolog = LabelsUtils.buildProlog(labelToPost, LabelsUtils.PrologTypes.Each)
    logger.log.debug(s"prolog = $prolog")
    evalSubscribeRequestAndSessionPing(SubscribeRequest(window.sessionStorage.getItem(sessionUriName),
      Expression(ApiTypes.INSERT_CONTENT,
        ExpressionContent(cnnxns, prolog, contentToPost, uid))))
  }
}