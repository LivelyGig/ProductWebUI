package services
//import upickle.default._
import livelygig.shared._
import livelygig.shared.dtos._
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import play.libs.Json
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write


class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = "http://54.201.212.193:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  var CONFIRM_EMAIL_MSG = "confirmEmailToken"
  var INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  var SESSION_PING = "sessionPing"
  val wsRequest : WSRequest = WS.url(BASE_URL)


  override def createAgent(userRequest: CreateUserRequest): Future[ApiResponse[CreateUserResponse]] = {
    println(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest))).map(
      response=>parse(response.body.toString).extract[ApiResponse[CreateUserResponse]]
    )
  }

  override def confirmEmail(confirmEmailRequest: ConfirmEmailRequest): Future[ApiResponse[ConfirmEmailResponse]] = {
    WS.url(BASE_URL).post(write(ApiRequest(CONFIRM_EMAIL_MSG,confirmEmailRequest))).map(
      response=>parse(response.body.toString).extract[ApiResponse[ConfirmEmailResponse]])
  }

  override def agentLogin(initializeSessionRequest: InitializeSessionRequest): Future[ApiResponse[InitializeSessionResponse]] = {
    println(write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest))).map(
      response=>parse(response.body.toString).extract[ApiResponse[InitializeSessionResponse]])
  }

  override def sessionPing(sessionPingRequest: SessionPingRequest): Future[Seq[ApiResponse[ConnectionProfileResponse]]] = {
    println(write(ApiRequest(SESSION_PING,sessionPingRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(SESSION_PING,sessionPingRequest))).map(
      response=>parse(response.body.toString).extract[Seq[ApiResponse[ConnectionProfileResponse]]])
  }
}
