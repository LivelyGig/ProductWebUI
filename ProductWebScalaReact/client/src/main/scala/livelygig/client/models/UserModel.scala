package livelygig.client.models

/**
  * Created by shubham.k on 12/17/2015.
  */
case class UserModel (email: String = "", password: String = "", name: String = "", createBTCWallet: Boolean = true,
                      isLoggedIn: Boolean = false, imgSrc: String = "")
