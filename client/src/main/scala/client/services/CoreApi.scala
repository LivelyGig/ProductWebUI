package client.services

import client.utils.Utils
import shared.Api
import shared.dtos._
import shared.models._
import org.scalajs.dom._
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import autowire._
import boopickle.Default._
import org.scalajs.dom.ext.Ajax
import shared.sessionitems.SessionItems

object CoreApi {
    var BASE_URL = "http://192.168.99.100:8888/api"
    var CREATE_USER_REQUEST = "createUserRequest"
    private def ajaxPost(requestContent: String): Future[String] = {
      Ajax.post(
        url = BASE_URL,
        data = requestContent,
        headers = Map("Content-Type" -> "application/json;charset=UTF-8")
      ).map(_.responseText)
    }

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

  def getContent(sessionUriName: String): Future[String] = {
    val sessionUri = window.sessionStorage.getItem(sessionUriName)
    val connectionsList = upickle.default.read[Seq[Connection]](
      window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTION_LIST)
    ) ++ Seq(Utils.getSelfConnnection(sessionUri)) // scalastyle:ignore
    val (currentSearchLabels, previousSearchLabels) = Utils.getCurrentPreviousLabel(sessionUriName)
    val getMessagesSubscription = SubscribeRequest(sessionUri, Expression(msgType = "feedExpr", ExpressionContent(connectionsList, currentSearchLabels)))
    val cancelPreviousRequest = CancelSubscribeRequest(sessionUri, connectionsList, previousSearchLabels)
    Option(previousSearchLabels) match {
      case Some(s) =>
        for {
          cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
          messages <- evalSubscribeRequestAndSessionPing(getMessagesSubscription)
        } yield messages
      case None =>
        evalSubscribeRequestAndSessionPing(getMessagesSubscription)

    }
  }

  def getProjects(): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.PROJECT_REQUEST, SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].getMock(requestContent, "jobPostsMock").call()
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

}