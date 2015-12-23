package services
//import upickle.default._
import livelygig.shared._
import livelygig.shared.dtos._
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import play.libs.Json

import org.json4s.jackson.Serialization.write


class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = "http://54.201.214.48:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  var CONFIRM_EMAIL_MSG = "confirmEmailToken"
  var INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  val wsRequest : WSRequest = WS.url(BASE_URL)


  override def createAgent(userRequest: CreateUserRequest): Future[String] = {
    // println(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest))).map(_.body.toString)
  }

  override def confirmEmail(confirmEmailRequest: ConfirmEmailRequest): Future[String] = {
    println(write(ApiRequest(CONFIRM_EMAIL_MSG,confirmEmailRequest)))
//    println(WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,confirmEmailRequest))).map(_.json.toString()))
    WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,confirmEmailRequest))).map(_.body.toString)
  }

  override def agentLogin(initializeSessionRequest: InitializeSessionRequest): Future[String] = {
    WS.url(BASE_URL).post(write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest))).map(_.body.toString)
  }
}
