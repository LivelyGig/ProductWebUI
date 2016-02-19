package services
//import upickle.default._
import livelygig.shared._
import livelygig.shared.dtos._
import mockdata.MockFiles
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import play.libs.Json
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write

class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = "http://54.191.93.74:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  var CONFIRM_EMAIL_MSG = "confirmEmailToken"
  var INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  var SESSION_PING = "sessionPing"
  val wsRequest : WSRequest = WS.url(BASE_URL)


  override def createAgent(userRequest: CreateUser): Future[/*ApiResponse[CreateUserResponse]*/String] = {
    println(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest))).map(
      response=>response.body.toString
    )
  }

  override def confirmEmail(confirmEmailRequest: ConfirmEmail): Future[ApiResponse[ConfirmEmailResponse]] = {
    WS.url(BASE_URL).post(write(ApiRequest(CONFIRM_EMAIL_MSG,confirmEmailRequest))).map(
      response=>parse(response.body.toString).extract[ApiResponse[ConfirmEmailResponse]])
  }

  override def agentLogin(initializeSessionRequest: InitializeSession): Future[/*ApiResponse[InitializeSessionResponse]*/String] = {
    println(write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest))).map{
      response=> response.body.toString()}
  }

  override def sessionPing(sessionPingRequest: SessionPing): Future[Seq[ApiResponse[ConnectionProfileResponse]]] = {
    println(write(ApiRequest(SESSION_PING,sessionPingRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(SESSION_PING,sessionPingRequest))).map(
      response=>parse(response.body.toString).extract[Seq[ApiResponse[ConnectionProfileResponse]]])
  }

  override def getConnections(sessionPingRequest: SessionPing): Future[String] = {
    println(write(ApiRequest(SESSION_PING,sessionPingRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(SESSION_PING,sessionPingRequest))).map{
      response=>
        //println("response.json.toString() = "+response.json.toString())
        response.json.toString()
    }
  }

  override def getProjects(sessionPingRequest: SessionPing): String = {
    val json = scala.io.Source.fromFile(MockFiles.jobsPostJsonLoc).getLines().map(_.trim).mkString
    /*println(json)*/
    json
  }
}
