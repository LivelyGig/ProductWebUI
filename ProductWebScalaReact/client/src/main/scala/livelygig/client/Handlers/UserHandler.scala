package livelygig.client.Handlers

import diode.{ActionHandler, ModelRW}
import livelygig.client.RootModels.UserRootModel
import livelygig.client.components.PrologParser
import livelygig.client.models.{UserModel}
import org.scalajs.dom.window
import concurrent._
import ExecutionContext.Implicits._

/**
  * Created by shubham.k on 1/25/2016.
  */
case class LoginUser(userModel: UserModel)
case class LogoutUser()

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
      /*var json = Seq[String]( "node(text(\"RocketHub\"), display(color(\"#5C9BCC\"), image(\"\")), progeny(leaf(text(\"Testimonial\"), display(color(\"#CC5C64\"), image(\"\"))), leaf(text(\"Ask\"), display(color(\"#5CCC8C\"), image(\"\"))), leaf(text(\"WhatIsSplicious\"), display(color(\"#5C64CC\"), image(\"\")))))",
        "leaf(text(\"PopularLabels\"), display(color(\"#5C9BCC\"), image(\"\")))",
        "leaf(text(\"Splicious\"), display(color(\"#CC5C64\"), image(\"\")))",
        "leaf(text(\"Synereo\"), display(color(\"#C45CCC\"), image(\"\")))",
        "leaf(text(\"VancouverDecentral\"), display(color(\"#E9CEB9\"), image(\"\")))",
        "leaf(text(\"Crowdsale2Pt0\"), display(color(\"#CC5C64\"), image(\"\")))")


      for(test<-json){
        val test2 = PrologParser.StringToLabel(test)
      }*/
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
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
