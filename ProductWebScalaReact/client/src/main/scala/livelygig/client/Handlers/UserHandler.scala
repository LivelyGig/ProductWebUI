package livelygig.client.Handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Ready, Pot}
import livelygig.client.RootModels.{ConnectionsRootModel, UserRootModel}
import livelygig.client.models.{UserModel, ConnectionsModel}
import livelygig.client.services.CoreApi
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
case object Login

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case Login =>
      updated(value)
//      case class UserModel (email: String, password: String, name: String, createBTCWallet: Boolean = true, isLoggedIn: Boolean = false)
  }
}
