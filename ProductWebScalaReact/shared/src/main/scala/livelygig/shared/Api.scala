package livelygig.shared

import livelygig.shared.dtos._

import scala.concurrent.Future

trait Api {
  def createAgent(userRequest: CreateUserRequest): Future[String]

  def confirmEmail(confirmEmailRequest: ConfirmEmailRequest): Future[String]

  def agentLogin(initializeSessionRequest: InitializeSessionRequest): Future[String]
}
