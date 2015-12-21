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
object RequestContent {
  implicit val requestContentPickler: Pickler[RequestContent] = generatePickler[RequestContent]
}

