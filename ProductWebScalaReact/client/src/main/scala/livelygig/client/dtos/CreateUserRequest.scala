package livelygig.client.dtos

/**
  * Created by shubham.k on 12/14/2015.
  */
/**
  * Transfer Object for createUserRequest
  */
case class CreateUserRequest (
                        email: String,
                        password: String,
                        jsonBlob: Map[String, String],
                        createBTCWallet: Boolean
                        ) extends RequestContent


