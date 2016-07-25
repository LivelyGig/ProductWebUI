package controllers

import java.nio.ByteBuffer
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global


class Application extends Controller {
  //  val apiService = new ApiService()

  def index = Action {
                Ok(views.html.index("LivelyGig"))
//    Ok(views.html.index("Welcome to Synereo - the decentralized and distributed social network"))
  }

  def logging = Action(parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach { msg =>
        println(s"Application - CLIENT - $msg")
      }
      Ok("")
  }
}