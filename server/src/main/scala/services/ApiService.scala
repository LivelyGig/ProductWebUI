package services

import com.typesafe.config.ConfigFactory
import shared._
import mockdata.{MessagesMock, JobPostsMock}
import play.api.Play.current
import play.api.libs.ws._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.json4s.jackson.Serialization.write

class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = ConfigFactory.load().getString("api.baseURL")
  println ("services.ApiService: loading base URL from application.conf " +BASE_URL)

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
        response.body.toString
    }
  }

  override def sessionPing(requestContent: String): Future[ /*Seq[ApiResponse[ConnectionProfileResponse]]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {response =>
//      println("response.json.toString() = " + response.json.toString())
      response.json.toString()
    }
  }

  /*override def getConnections(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {
      response =>
        //        println("response.json.toString() = "+response.json.toString())
        response.json.toString()
    }
  }*/

  override def getProjects(requestContent: String): String = {
//    val json = scala.io.Source.fromFile(MockFiles.jobsPostJsonLoc).getLines().map(_.trim).mkString
    /*println(json)*/
    val json = JobPostsMock.content
    /*println(json)*/
    json
  }
  override def getMessages(requestContent: String): String = {
    /*val json = scala.io.Source.fromFile(MockFiles.messagesJsonLoc).getLines().map(_.trim).mkString*/
    /*println(json)*/
    val json = MessagesMock.content
    json
  }

  override def subscribeRequest(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {
      response =>
//        println("response.json.toString() = "+response.body.toString())
        "ok"
    }
  }

  override def cancelSubscriptionRequest(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map{
      response =>
        "ok"
    }
  }
}
