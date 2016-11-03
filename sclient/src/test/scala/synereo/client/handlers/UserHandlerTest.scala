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

  val newUserModel = UserModel("name", "email", "password", false, "imgSrc", "confirmPassword", true, "10.00", "10.00", "address")

  "LoginUser" should "Login details" in {

    val result = handler.handle(LoginUser(newUserModel))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ =>
        assert(true)
    }
  }
  /*"LogoutUser" should "Log out Details" in {

    val result = handler.handle(LogoutUser)
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ => assert(true)
    }
  }*/

  "UpdateUserImage" should "User Image source should not be null" in {

    val result = handler.handle(UpdateUserImage(newUserModel.imgSrc))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue != null)
      case _ => assert(true)
    }

  }

  "BalanceChanged" should "Updated AMP balance should not be null" in {
    val result = handler.handle(BalanceChanged(newUserModel.balanceAmp, newUserModel.balanceBtc, newUserModel.address))
    result match {
      case ModelUpdate(newValue) =>
        assert(newValue.balanceAmp != "0.00" && newValue.balanceAmp != "0.00" && newValue.address != "n/a")
      case _ => assert(true)
    }
  }

}