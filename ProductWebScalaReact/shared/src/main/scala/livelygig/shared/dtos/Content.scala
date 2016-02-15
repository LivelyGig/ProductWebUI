package livelygig.shared.dtos

import java.util.Date

import boopickle.Default._

/**
  * Created by shubham.k on 12/14/2015.
  */
/**
  * Represents content within a request/response to API endpoints.
  */
sealed trait Content {

}
case class CreateUser(email: String, password: String, jsonBlob: Map[String, String], createBTCWallet: Boolean)
  extends Content

case class CreateUserResponse() extends Content

case class ConfirmEmail(token: String) extends Content

case class ConfirmEmailResponse(reason: Option[String]) extends Content

case class InitializeSession(agentURI: String) extends Content

case class InitializeSessionResponse(sessionURI: Option[String], defaultAlias: Option[String], jsonBlob: Option[Map[String, String]],
                                     reason : Option[String])  extends Content

case class ConnectionProfileResponse(sessionURI: String, connection: Connection, jsonBlob: String ,
                                     name: Option[String])  extends Content
case class JobPostsResponse(sessionURI: String, pageOfPosts: Seq[String], connection: Connection,
                            filter : String) extends Content

//case class JsonBlobModel(name: String, imgSrc: String)

case class Connection (source: String, label: String, target: String)

case class PageOfPosts(id : String, `type` : String,description : String,summary : String,
                       postedDate: String, broadcastDate: String, startDate: String, endDate: String, currency: String,
                       location: String, skillsId: Seq[String], posterId: String, canForward: Boolean, referralId: Seq[String],
                       contractType: String, budget: Float)
//["{\"id\": \"d2b255ca-dde8-4919-9e5d-a0a62d5d7c13\", \"type\": \"PROJECT\", \"summary\": \"This is really new project\",
// \"description\": \"Need scala developer to implement a centralized repository for a big financial institution. \",
// \"postedDate\": \"2002-05-30T09:30:10Z\", \"broadcastDate\": \"2002-05-30T09:30:10Z\",
// \"startDate\": \"2002-05-30T09:30:10Z\", \"endDate\": \"2002-05-30T09:30:10Z\", \"currency\": \"USD\",
// \"location\": \"United States\", \"isPayoutInPieces\": \"false\",
// \"skillsId\": \"[\\\"4416192b-9dec-49b0-9d13-fb0815af6c3f\\\", \\\"3c91c578-2d39-42d4-adb0-9071d9eb116a\\\",
// \\\"b48bfe5a-15fa-4d8e-b253-752b51c2b94b\\\"]\", \"posterId\": \"eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee\",
// \"canForward\": \" true\",
// \"referralId\": \"[\\\"40c96981-ca91-4083-9dfc-76826df0f432\\\",\\\"c6a3c02e-5724-4a35-adc7-ddc37d3c721b\\\"]\",
// \"contractType\": \"23940120-4943-4462-9c46-2b23ef94108c\", \"budget\": \"2350.3\"}"]

case class SessionPing(sessionURI: String) extends Content

object Content {
  implicit val requestContentPickler: Pickler[Content] = generatePickler[Content]
}

