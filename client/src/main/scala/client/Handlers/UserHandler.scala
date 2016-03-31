package client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import shared.dtos.{CancelSubscribeRequest, InitializeSessionResponse, ApiResponse}
import client.models.UserModel
import client.services.{CoreApi, LGCircuit}
import org.scalajs.dom.window
import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON

case class LoginUser(userModel: UserModel)
case class LogoutUser()
case class CreateSessionForMessages(userModel2: UserModel)


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
    case CreateSessionForMessages(userModel2) =>
      CoreApi.agentLogin(userModel2).map{responseStr=>
        val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
        window.sessionStorage.setItem(CoreApi.MESSAGES_SESSION_URI,response.content.sessionURI)
      }
        noChange
    case LogoutUser() =>
      CoreApi.cancelAllSubscriptionRequest()
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
