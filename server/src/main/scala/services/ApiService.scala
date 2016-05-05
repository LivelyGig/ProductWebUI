package services

import java.io.PrintWriter

import com.typesafe.config.ConfigFactory
import shared._
import mockdata.{ConnectionsMock, JobPostsMock, MessagesMock}
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.json4s.jackson.Serialization.write

class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = ConfigFactory.load().getString("api.baseURL")
  println ("services.ApiService: loading base URL from application.conf " +BASE_URL)

  /*override def createAgent(requestContent: String): Future[ /*ApiResponse[CreateUserResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map(response => response.json.toString)
  }

  override def confirmEmail(requestContent: String): Future[ /*ApiResponse[ConfirmEmailResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map (response => response.body.toString)
  }

  override def agentLogin(requestContent: String): Future[ /*ApiResponse[InitializeSessionResponse]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(INITIALIZE_SESSION_MSG,initializeSessionRequest))*/ requestContent).map {
      response =>
        response.json.toString
    }
  }

  override def sessionPing(requestContent: String): Future[ /*Seq[ApiResponse[ConnectionProfileResponse]]*/ String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {response =>
      //      println("response.json.toString() = " + response.json.toString())
      response.json.toString()
    }
  }

  override def getConnections(requestContent: String): String = {
    println(write(requestContent))
    val json = ConnectionsMock.content
    /*println(json)*/
    json
  }

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
    WS.url(BASE_URL).withRequestTimeout(10000).post(/*write(ApiRequest(SESSION_PING,sessionPingRequest))*/ requestContent).map {
      response =>
        println("response.json.toString() = "+response.body)
        response.body
    }
  }

  override def cancelSubscriptionRequest(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map{
      response =>
        "ok"
    }
  }

  override def postMessage(requestContent: String): Future[String] = {
    println(write(requestContent))
    WS.url(BASE_URL).post(requestContent).map{
      response =>
        println(s"response.json = ${response.json.toString()}")
        "ok"
    }
  }*/

  override def queryApiBackend (requestContent: String) : Future[String] = {
    println(requestContent)
    //    new PrintWriter("chacha.log") { write(requestContent); close }
    WS.url(BASE_URL).post(requestContent).map{
      response =>
        println(s"response.body = ${response.body}")
        response.body
    }
  }
  override def getMock (requestContent: String,  `type`: String): String = {
    println (write(requestContent))
    `type` match {
      case "messagesMock" => MessagesMock.content
      case "jobPostsMock" => JobPostsMock.content
      case "connectionsMock" => ConnectionsMock.content
      case _ => "not found"
    }

  }

}
