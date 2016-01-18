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

case class InitializeSessionResponse(sessionURI: Option[String], defaultAlias: Option[String], jsonBlob: Option[Map[String, String]],
                                     reason : Option[String])
  extends RequestContent

case class ConnectionProfileResponse(sessionURI: String, connection: Connection, jsonBlob: String ,
                                     name: Option[String])
  extends RequestContent

//case class JsonBlobModel(name: String, imgSrc: String)

case class Connection (source: String, label: String, target: String)

case class SessionPingRequest (sessionURI: String) extends RequestContent

object RequestContent {
  implicit val requestContentPickler: Pickler[RequestContent] = generatePickler[RequestContent]
}

