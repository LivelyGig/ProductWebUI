package l.client.handlers

import diode.{ActionHandler, ModelRW}
import l.client.models.UserModel
import l.client.services.LGCircuit
import org.scalajs.dom.window
import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON

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
      window.location.href = "/"
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
