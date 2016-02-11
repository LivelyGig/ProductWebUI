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
      val temp = window.localStorage.getItem("user")
      if (temp!=null) {
        /*userModel->JSON.parse(temp).asInstanceOf[UserModel]*/
        modelFromStore = upickle.default.read[UserModel](temp)
        /*userModel->modelFromStore*/
      }
      //      window.localStorage.setItem("userModel",userModel.toString)
//      println(modelFromStore)
      updated(modelFromStore)
    case LogoutUser() =>
      window.localStorage.setItem("user", null)
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
