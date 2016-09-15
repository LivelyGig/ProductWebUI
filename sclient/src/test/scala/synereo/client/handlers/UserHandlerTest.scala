package synereo.client.handlers

import shared.models.UserModel
import diode.ActionResult.ModelUpdate
import diode.RootModelRW
import diode.data.{Pot, Ready}
import synereo.client.UnitTest


/**
  * Created by bhagyashree.b on 2016-09-15.
  */


class UserHandlerTest extends UnitTest("UserHandlerTest") {
  val handler = new UserHandler(new RootModelRW(UserModel()))

val newUserModel =  UserModel("name", "email","password",false,"imgSrc", "confirmPassword",true,"balance","address","sessionUri")

  "LoginUser" should "Login details" in {

    val result = handler.handle(LoginUser(newUserModel))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(true)

    }
  }

}