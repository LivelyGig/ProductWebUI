package livelygig.shared.dtos

import java.util.Date

import boopickle.Default._
import upickle.Js

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
case class ProjectsResponse(sessionURI: String, pageOfPosts: Seq[String], connection: Connection,
                            filter : String)

//case class JsonBlobModel(name: String, imgSrc: String)

case class Connection (source: String, label: String, target: String)

case class PageOfPosts(id : String, `type` : String,description : String,summary : String,
                       postedDate: String, broadcastDate: String, startDate: String, endDate: String, currency: String,
                       location: String, isPayoutInPieces: String, skills: Seq[Skills], posterId: String, canForward: String,
                       referents: Seq[Referents], contractType: String, budget: Float)
case class Skills (skillId : String, skillName: String)
case class Referents(referentId: String,referentName: String )

case class SessionPing(sessionURI: String) extends Content

object Content {
  implicit val requestContentPickler: Pickler[Content] = generatePickler[Content]
}
//["{\"id\": \"d2b255ca-dde8-4919-9e5d-a0a62d5d7c12\", \"type\": \"PROJECT\", \"summary\": \"This is new project\", \"description\": \"Need real good scala developer to implement a centralized repository for a big financial institution.\", \"postedDate\": \"2002-05-30T09:30:10Z\", \"broadcastDate\": \"2002-05-30T09:30:10Z\", \"startDate\": \"2002-05-30T09:30:10Z\", \"endDate\": \"2002-05-30T09:30:10Z\", \"currency\": \"USD\", \"location\": \"United States\", \"isPayoutInPieces\": \"false\", \"skills\": [{\"skillId\": \"4416192b-9dec-49b0-9d13-fb0815af6c3f\", \"skillName\":\"Java\"}, {\"skillId\": \"3c91c578-2d39-42d4-adb0-9071d9eb116a\", \"skillName\":\"Financial Apps\"}, {\"skillId\": \"b48bfe5a-15fa-4d8e-b253-752b51c2b94b\", \"skillName\":\"cryptography\"}], \"posterId\": \"eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee\", \"canForward\": \"true\", \"referents\": [{\"referentId\":\"40c96981-ca91-4083-9dfc-76826df0f432\", \"referentName\":\"Britta\"},{\"referentId\":\"c6a3c02e-5724-4a35-adc7-ddc37d3c721b\",\"referentName\":\"Jane Best\"}], \"contractType\": \"23940120-4943-4462-9c46-2b23ef94108c\", \"budget\": \"2350.3\"}"],
