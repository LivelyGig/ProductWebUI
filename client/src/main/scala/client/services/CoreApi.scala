package client.services

import client.handlers.RefreshConnections
import client.modules.Searches
import client.utils.Utils
import shared.Api
import shared.dtos._
import shared.models._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import upickle.default._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import autowire._
import boopickle.Default._

object CoreApi {
  val CREATE_USER_REQUEST_MSG = "createUserRequest"
  val CONFIRM_EMAIL_MSG = "confirmEmailToken"
  val INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  val SESSION_PING = "sessionPing"
  val PROJECT_MSG = "projectsRequest"
  val EVAL_SUBS_REQUEST = "evalSubscribeRequest"
  val EVAL_SUBS_CANCEL_REQUEST = "evalSubscribeCancelRequest"
  val MESSAGES_SESSION_URI = "messagesSessionUri"
  val CONNECTIONS_SESSION_URI = "connectionsSessionUri"
  val JOBS_SESSION_URI = "jobsSessionUri"
  val INSERT_CONTENT = "insertContent"
  //  var BASE_URL = "http://52.35.10.219:9876/api"
  //  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  //  private def ajaxPost(msgType: String, data: RequestContent): Future[String] = {
  //    Ajax.post(
  //      url = BASE_URL,
  //      data = write(ApiRequest(msgType, data)),
  //      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
  //    ).map(_.responseText)
  //  }

  def getConnections () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING,SessionPing(window.sessionStorage.getItem(CONNECTIONS_SESSION_URI))))
        AjaxClient[Api].queryApiBackend(requestContent).call()
  }
  def getProjects () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(PROJECT_MSG,SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].getMock(requestContent, "jobPostsMock").call()
  }

  def createUser(signUpModel: SignUpModel): Future[/*ApiResponse[CreateUserResponse]*/String] = {
    val requestContent = upickle.default.write(ApiRequest(CREATE_USER_REQUEST_MSG,CreateUser(signUpModel.email, signUpModel.password,
      Map("name" -> signUpModel.name), true)))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(CONFIRM_EMAIL_MSG,ConfirmEmail(emailValidationModel.token)))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def agentLogin(userModel: UserModel): Future[/*ApiResponse[InitializeSessionResponse]*/String] = {
    val requestContent = upickle.default.write(ApiRequest(INITIALIZE_SESSION_MSG,InitializeSession(s"agent://email/${userModel.email}" +
      s"?password=${userModel.password}")))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }


  def sessionPing (uri:String) : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING,SessionPing(window.sessionStorage.getItem(uri))))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def getMessages () : Future[String] = {
    val connectionsList = upickle.default.read[Seq[Connection]](window.sessionStorage.getItem("connectionsList")) ++ Seq(Utils.GetSelfConnnection(MESSAGES_SESSION_URI))
    val currentLabels = window.sessionStorage.getItem("currentSearchLabel")
    val previousLabels = window.sessionStorage.getItem("previousSearchLabel")
    val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem(MESSAGES_SESSION_URI), Expression(msgType = "feedExpr", ExpressionContent(connectionsList, currentLabels)))
    val cancelPreviousRequest = CancelSubscribeRequest(window.sessionStorage.getItem(MESSAGES_SESSION_URI), connectionsList, previousLabels)
    val messageSearchClick = window.sessionStorage.getItem("messageSearchClick")
    if (messageSearchClick != null) {
      window.sessionStorage.setItem("previousSearchLabel",currentLabels)
      for {
        cancel <- cancelSubscriptionRequestAndSessionPing(cancelPreviousRequest)
        messages <- evalSubscribeRequestAndSessionPing(getMessagesSubscription)
      } yield messages
    } else {
      window.sessionStorage.setItem("messageSearchClick", "true")
      window.sessionStorage.setItem("previousSearchLabel",currentLabels)
      evalSubscribeRequestAndSessionPing(getMessagesSubscription)
    }
  }

  def cancelPreviousSubsForLabelSearch() = {
    val selfConnection = Utils.GetSelfConnnection(MESSAGES_SESSION_URI)
    val previousLabels = window.sessionStorage.getItem("previousSearchLabel")
    val cancelPreviousRequest = CancelSubscribeRequest(window.sessionStorage.getItem(MESSAGES_SESSION_URI), Seq(selfConnection), previousLabels)
    cancelSubscriptionRequestAndSessionPing(cancelPreviousRequest)
  }


  def evalSubscribeRequest (subscribeRequest: SubscribeRequest) : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_REQUEST,subscribeRequest))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def evalSubscribeRequestAndSessionPing(subscribeRequest: SubscribeRequest): Future[String] = {
    for {
      subscription <- evalSubscribeRequest(subscribeRequest)
      response <-  sessionPing(subscribeRequest.sessionURI)
    } yield response
  }

  def cancelSubscriptionRequestAndSessionPing(cancelSubscribeRequest: CancelSubscribeRequest) :Future[String] = {
    for {
      cancelRequest <- cancelSubscriptionRequest(cancelSubscribeRequest)
      response <- sessionPing(cancelSubscribeRequest.sessionURI)
    } yield response
  }

  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest) :Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }


}