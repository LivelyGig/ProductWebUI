package livelygig.shared


import scala.concurrent.Future

trait Api {
  def createAgent(requestContent: String): Future[/*ApiResponse[CreateUserResponse]*/String]

  def confirmEmail(requestContent: String): Future[/*ApiResponse[ConfirmEmailResponse]*/String]

  def agentLogin(requestContent: String): Future[/*ApiResponse[InitializeSessionResponse]*/String]

  def sessionPing (requestContent: String) : Future[/*Seq[ApiResponse[ConnectionProfileResponse]]*/String]

  def getConnections (requestContent: String) : Future[String]

  def getProjects(requestContent: String):String
}
