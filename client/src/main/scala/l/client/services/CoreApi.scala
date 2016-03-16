package l.client.services

import l.client.utils.Utils
import l.shared.Api
import l.client.dtos._
import l.client.models._
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
  //  var BASE_URL = "http://52.35.10.219:9876/api"
  //  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  //  private def ajaxPost(msgType: String, data: RequestContent): Future[String] = {
  //    Ajax.post(
  //      url = BASE_URL,
  //      data = write(ApiRequest(msgType, data)),
  //      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
  //    ).map(_.responseText)
  //  }

  def createUser(userModel: UserModel): Future[/*ApiResponse[CreateUserResponse]*/String] = {
    val requestContent = upickle.default.write(ApiRequest(CREATE_USER_REQUEST_MSG,CreateUser(userModel.email, userModel.password,
      Map("name" -> userModel.name), true)))
    AjaxClient[Api].createAgent(requestContent).call()
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(CONFIRM_EMAIL_MSG,ConfirmEmail(emailValidationModel.token)))
    println("emailvalidation token : " + requestContent)
    AjaxClient[Api].confirmEmail(requestContent).call()
  }

  def agentLogin(userModel: UserModel): Future[/*ApiResponse[InitializeSessionResponse]*/String] = {
    val requestContent = upickle.default.write(ApiRequest(INITIALIZE_SESSION_MSG,InitializeSession(s"agent://email/${userModel.email}" +
      s"?password=${userModel.password}")))
    println("login data : " + requestContent)
    AjaxClient[Api].agentLogin(requestContent).call()
  }

  def sessionPing () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING,SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].sessionPing(requestContent).call()
  }

  def getConnections () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING,SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].getConnections(requestContent).call()
  }
  def getMessages () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING, SessionPing(window.sessionStorage.getItem("sessionURI"))))
    val currentLabels = "any([Splicious])"
    val selfConnection = Utils.GetSelfConnnection()
    val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem("sessionURI"), Expression(msgType = "feedExpr", ExpressionContent(Seq(selfConnection), currentLabels)))
    val cancelPreviousRequest = CancelSubscribeRequest(window.sessionStorage.getItem("sessionURI"), Seq(selfConnection), "any([Spilicious])")
    //    todo move the logic from button click messages subscription to messages feed
    val messageSearchClick = window.sessionStorage.getItem("messageSearchClick")
    if (messageSearchClick != null) {
      for {
        cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
        newSubscription <- evalSubscribeRequest(getMessagesSubscription)
        messages <-  AjaxClient[Api].sessionPing(requestContent).call()
      } yield messages
    } else {
      window.sessionStorage.setItem("messageSearchClick", "true")
      for {
//        cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
        newSubscription <- evalSubscribeRequest(getMessagesSubscription)
        messages <-  AjaxClient[Api].sessionPing(requestContent).call()
      } yield messages
    }


  }

  def getProjects () : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(PROJECT_MSG,SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].getProjects(requestContent).call()
  }
  def evalSubscribeRequest (subscribeRequest: SubscribeRequest) : Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_REQUEST,subscribeRequest))
    AjaxClient[Api].subscribeRequest(requestContent).call()
  }
  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest) :Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    AjaxClient[Api].cancelSubscriptionRequest(requestContent).call()
  }

}