package livelygig.client.dtos

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
