package livelygig.client.models

import livelygig.shared.dtos._

/**
  * Created by shubham.k on 2/11/2016.
  */
case class MessagesModel (count: Int)
case class ConnectionsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)
case class JobPostsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)
case class UserModel (email: String = "", password: String = "", name: String = "", createBTCWallet: Boolean = true,
                      isLoggedIn: Boolean = false, imgSrc: String = "")
case class EmailValidationModel (token: String)