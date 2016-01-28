package livelygig.client.Handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Ready, Pot}
import livelygig.client.Handlers.LoginUser
import livelygig.client.RootModels.{ConnectionsRootModel, UserRootModel}
import livelygig.client.models.{UserModel, ConnectionsModel}
import livelygig.client.services.CoreApi
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
case class LoginUser(userModel: UserModel)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
//      value.isLoggedIn = true
//      value.
      println("In UserHandler")
      updated(UserModel(userModel.email, userModel.password,userModel.name, userModel.createBTCWallet, true))
//      case class UserModel (email: String, password: String, name: String, createBTCWallet: Boolean = true, isLoggedIn: Boolean = false)
  }
}
