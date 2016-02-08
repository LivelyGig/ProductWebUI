package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.models.{UserModel}

/**
  * Created by shubham.k on 1/25/2016.
  */
case class LoginUser(userModel: UserModel)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
//      println(userModel)
      updated(userModel)
  }
}
