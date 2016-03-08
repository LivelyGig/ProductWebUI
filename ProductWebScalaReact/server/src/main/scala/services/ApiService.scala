package services
//import upickle.default._
import livelygig.shared._
import mockdata.MockFiles
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import play.libs.Json
import org.json4s.jackson.Serialization.write

class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = "http://54.191.41.235:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  var CONFIRM_EMAIL_MSG = "confirmEmailToken"
  var INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  var SESSION_PING = "sessionPing"
  val wsRequest: WSRequest = WS.url(BASE_URL)


  override def createAgent(requestContent: String): Future[ /*ApiResponse[CreateUserResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map(
      response => response.body.toString
    )
  }

  override def confirmEmail(requestContent: String): Future[ /*ApiResponse[ConfirmEmailResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(CONFIRM_EMAIL_MSG,confirmEmailRequest))*/ requestContent).map {
      response =>
        //        println("token receiving at server response.body.tostring : " + response.body.toString)
        response.body.toString
    }
  }

  override def agentLogin(requestContent: String): Future[ /*ApiResponse[InitializeSessionResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest))*/ requestContent).map {
      response =>
        //  println("login receiving at server response.body.tostring : " + response.body.toString)
        response.body.toString()
    }
  }

  override def sessionPing(requestContent: String): Future[ /*Seq[ApiResponse[ConnectionProfileResponse]]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {response =>
      println("response.json.toString() = " + response.json.toString())
      response.json.toString()
    }
  }

  override def getConnections(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {
      response =>
        //        println("response.json.toString() = "+response.json.toString())
        response.json.toString()
    }
  }

  override def getProjects(requestContent: String): String = {
    val json = scala.io.Source.fromFile(MockFiles.jobsPostJsonLoc).getLines().map(_.trim).mkString
    /*println(json)*/
    json
  }
  override def getMessages(requestContent: String): String = {
    val json = scala.io.Source.fromFile(MockFiles.messagesJsonLoc).getLines().map(_.trim).mkString
    /*println(json)*/
    json
  }

  override def subscribeRequest(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {
      response =>
        println("response.json.toString() = "+response.json.toString())
        response.json.toString()
    }
  }
}


