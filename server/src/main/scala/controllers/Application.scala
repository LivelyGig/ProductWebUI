package controllers

import java.nio.ByteBuffer
import boopickle.Default._
import shared.Api
import play.api.mvc._
import services.ApiService
import scala.concurrent.ExecutionContext.Implicits.global

object Router extends autowire.Server[ByteBuffer, Pickler, Pickler] {
  override def read[R: Pickler](p: ByteBuffer) = Unpickle[R].fromBytes(p)

  override def write[R: Pickler](r: R) = Pickle.intoBytes(r)
}

class Application extends Controller {
  val apiService = new ApiService()

  def index = Action {
        Ok(views.html.index("Welcome to LivelyGig - work from anywhere with anyone"))
//    Ok(views.html.index("Welcome to Synereo - the decentralized and distributed social network"))
  }

  def indexl = Action {
    Ok(views.html.index("Welcome to Synereo - the decentralized and distributed social network"))
  }

  def indexs = Action {
    Ok(views.html.index("Welcome to LivelyGig - work from anywhere with anyone"))
  }

  def autowireApi(path: String) = Action.async(parse.raw) {
    implicit request =>
      println(s"Application - Request path: $path")

      // get the request body as Array[Byte]
      val b = request.body.asBytes(parse.UNLIMITED).get

      // call Autowire route
      Router.route[Api](apiService)(
        // autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(ByteBuffer.wrap(b)))
        autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(b.asByteBuffer))
      ).map(buffer => {
          val data = Array.ofDim[Byte](buffer.remaining())
          buffer.get(data)
          Ok(data)
          //        Status(500)
        })
  }

  def logging = Action(parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach { msg =>
        println(s"Application - CLIENT - $msg")
      }
      Ok("")
  }
}