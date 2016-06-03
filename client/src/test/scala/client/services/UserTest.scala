package client.services

import shared.models.UserModel

/**
  * Created by bhagyashree.b on 2016-05-18.
  */
class UserTest extends UnitTest("UserModel") {

    "Attributes of Usermodel" should "not be null " in {
      val usermodel = new UserModel(" "," ","",false," ","")
      usermodel.name shouldNot be(null)
      usermodel.password shouldNot  be(null)
      usermodel.confirmPassword shouldNot be(null)
      usermodel.email shouldNot be(null)
      usermodel.imgSrc shouldNot be(null)
  }

}