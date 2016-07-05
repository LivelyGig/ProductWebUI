package synereo.client.services

import shared.dtos._
import shared.models._
import org.scalajs.dom._
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import org.scalajs.dom.ext.Ajax
import shared.sessionitems.SessionItems
import shared.sessionitems.SessionItems.ProfilesViewItems
import synereo.client.modules.{ConnectionList, Login}
import synereo.client.utils.{ConnectionsUtils, LabelsUtils}

object CoreApi {
  val hostDetails = Login.apiDetails
  //  var BASE_URL = "http://localhost:9876/api"
  var BASE_URL = s"http://${Login.hostName}:${Login.portNumber}/api"
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
    val searchConnectionsList = upickle.default.read[Seq[Connection]](
      window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CURRENT_SEARCH_CONNECTION_LIST)
    ) ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri))
    val connectionsList = upickle.default.read[Seq[Connection]](
      window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTION_LIST)
    ) ++ Seq(ConnectionsUtils.getSelfConnnection(sessionUri)) // scalastyle:ignore
    val connectionListTo = if (connectionsList == searchConnectionsList) connectionsList else searchConnectionsList

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
      case _: IntroConnections => ApiTypes.INTRODUCTION_REQUEST
      case _: EstablishConnection => ApiTypes.ESTABLISH_CONNECTION_REQ
    }
    ajaxPost(upickle.default.write(ApiRequest(msg, introductionModel)))
  }

  def postLabel(labelPost: LabelPost): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_ALIAS_LABEL_REQ, labelPost))
    //    println("requestContent = " + requestContent)
    ajaxPost(requestContent)
  }
}