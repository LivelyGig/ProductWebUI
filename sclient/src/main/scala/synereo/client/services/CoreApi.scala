package synereo.client.services

import shared.dtos._
import org.scalajs.dom._
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import org.scalajs.dom.ext.Ajax
import shared.models.{EmailValidationModel, SignUpModel, UserModel}
import synereo.client.sessionitems.SessionItems

object CoreApi {

  def getBaseUrl() = {
    //s"http://${window.sessionStorage.getItem(SessionItems.ApiDetails.API_HOST)}:${window.sessionStorage.getItem(SessionItems.ApiDetails.API_PORT)}/api"
    s"${window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)}/api"
  }

  // scalastyle:ignore
  var CREATE_USER_REQUEST = "createUserRequest"

  private def ajaxPost(requestContent: String): Future[String] = {
    Ajax.post(
      url = getBaseUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
    ).map(_.responseText)

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

  def evalSubscribeRequest(subscribeRequest: SubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.EVAL_SUBS_REQUEST, subscribeRequest))
    ajaxPost(requestContent)
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
    ajaxPost(upickle.default.write(ApiRequest(msg, introductionModel)))
  }

  def postLabel(labelPost: LabelPost): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_ALIAS_LABEL_REQ, labelPost))
    ajaxPost(requestContent)
  }

  def updateUserRequest(updateUserRequest: UpdateUserRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_USER_REQUEST, updateUserRequest))
    ajaxPost(requestContent)
  }
}