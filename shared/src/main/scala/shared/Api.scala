package shared

import scala.concurrent.Future

trait Api {
  /*def createAgent(requestContent: String): Future[String]

  def confirmEmail(requestContent: String): Future[String]

  def agentLogin(requestContent: String): Future[String]

  def sessionPing (requestContent: String) : Future[String]

  def getConnections (requestContent: String) : String

  def getMessages (requestContent: String) : String

  def getProjects(requestContent: String):String

  def subscribeRequest(requestContent: String): Future[String]

  def cancelSubscriptionRequest(requestContent: String): Future[String]

  def postMessage(requestContent: String): Future[String]*/

  def queryApiBackend(requestContent: String): Future[String]

  def getMock(requestContent: String, `type`: String): String
}
