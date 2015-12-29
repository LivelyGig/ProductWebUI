package livelygig.shared

import livelygig.shared.dtos._

import scala.concurrent.Future

trait Api {
  def createAgent(userRequest: CreateUserRequest): Future[ApiResponse[CreateUserResponse]]

  def confirmEmail(confirmEmailRequest: ConfirmEmailRequest): Future[ApiResponse[ConfirmEmailResponse]]

  def agentLogin(initializeSessionRequest: InitializeSessionRequest): Future[ApiResponse[InitializeSessionResponse]]

}
