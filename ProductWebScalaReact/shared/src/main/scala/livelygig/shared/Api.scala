package livelygig.shared

import livelygig.shared.dtos._

import scala.concurrent.Future

trait Api {
  def createAgent(userRequest: CreateUser): Future[ApiResponse[CreateUserResponse]]

  def confirmEmail(confirmEmailRequest: ConfirmEmail): Future[ApiResponse[ConfirmEmailResponse]]

  def agentLogin(initializeSessionRequest: InitializeSession): Future[ApiResponse[InitializeSessionResponse]]

  def sessionPing (sessionPingRequest: SessionPing) : Future[Seq[ApiResponse[ConnectionProfileResponse]]]

  def getConnections (sessionPingRequest: SessionPing) : Future[String]

  def getProjects(sessionPingRequest: SessionPing):String
}
