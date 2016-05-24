package client.services

import shared.models.SignUpModel

/**
  * Created by bhagyashree.b on 2016-05-20.
  * */
//
//case class SignUpModel(email: String = "", password: String = "", confirmPassword: String = "", name: String = "", lastName: String = "", createBTCWallet: Boolean = false, isModerator: Boolean = false,
//                       isClient: Boolean = false, isFreelancer: Boolean = false, canReceiveEmailUpdates: Boolean = false, isLoggedIn: Boolean = false, imgSrc: String = "", didAcceptTerms: Boolean = false)
class SignUpModelTest extends UnitTest("SignUpModel") {

  "Attributes of Usermodel" should "not be null " in {
    val signUpModel = new SignUpModel(" "," ","","","",false,false,false,false,false,false,"",false)
    signUpModel.email shouldNot be(null)
    signUpModel.password shouldNot  be(null)
    signUpModel.confirmPassword shouldNot be(null)
    signUpModel.name shouldNot be(null)
    signUpModel.lastName shouldNot be(null)
/*
    signUpModel.createBTCWallet shouldNot be()
    signUpModel.isModerator shouldNot  be()
    signUpModel.isClient shouldNot be()
    signUpModel.isFreelancer shouldNot be()
    signUpModel.canReceiveEmailUpdates shouldNot be()

    signUpModel.isLoggedIn shouldNot be()
    signUpModel.imgSrc shouldNot be()
    signUpModel.didAcceptTerms shouldNot be()*/
  }

}
