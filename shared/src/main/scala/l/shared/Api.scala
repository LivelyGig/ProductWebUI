package l.shared


import scala.concurrent.Future

trait Api {
  def createAgent(requestContent: String): Future[String]

  def confirmEmail(requestContent: String): Future[String]

  def agentLogin(requestContent: String): Future[String]

  def sessionPing (requestContent: String) : Future[String]

  def getConnections (requestContent: String) : Future[String]

  def getMessages (requestContent: String) : String

  def getProjects(requestContent: String):String

  def subscribeRequest(requestContent: String): Future[String]
}
