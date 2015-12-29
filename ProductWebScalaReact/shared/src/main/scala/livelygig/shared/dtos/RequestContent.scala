package livelygig.shared.dtos
import boopickle.Default._

/**
  * Created by shubham.k on 12/14/2015.
  */
/**
  * Represents content within a request to API endpoints.
  */
sealed trait RequestContent {

}
case class CreateUserRequest (email: String, password: String, jsonBlob: Map[String, String], createBTCWallet: Boolean)
  extends RequestContent

case class CreateUserResponse() extends RequestContent

case class ConfirmEmailRequest(token: String) extends RequestContent

case class ConfirmEmailResponse(reason: Option[String]) extends RequestContent

case class InitializeSessionRequest(agentURI: String) extends RequestContent

case class InitializeSessionResponse(sessionURI: String, defaultAlias: String, jsonBlob: Map[String, String])
  extends RequestContent

object RequestContent {
  implicit val requestContentPickler: Pickler[RequestContent] = generatePickler[RequestContent]
}

