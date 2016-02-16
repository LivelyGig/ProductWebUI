package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.UserRootModel
import livelygig.client.models.{UserModel}
import org.scalajs.dom.window

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
case class LoginUser(userModel: UserModel)
case class LogoutUser()

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp!=null) {
        modelFromStore = UserModel(email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
        imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true)
      }
      updated(modelFromStore)
    case LogoutUser() =>
      window.sessionStorage.clear()
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
