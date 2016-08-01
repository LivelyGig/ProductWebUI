package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import org.scalajs.dom.window
import shared.dtos.UpdateUserRequest
import shared.models.UserModel
import synereo.client.logger
import synereo.client.services.CoreApi
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

// scalastyle:off
case class LoginUser(userModel: UserModel)

case class LogoutUser()

case class PostUserUpdate(updateUserRequest: UpdateUserRequest)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  //  val messageLoader = "#messageLoader"
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case LoginUser(userModel) =>
      updated(userModel)

    case LogoutUser() =>
      // todo: Cancel all subscribe request for all sessions
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false))

    case PostUserUpdate(req) =>
      var count = 1
      post()
      def post(): Unit = CoreApi.updateUserRequest(req).onComplete {
        case Success(response) =>
          updated(value.copy(imgSrc = req.jsonBlob.imgSrc))
        case Failure(response) => println(s"failure : $response")
          if (count == 3 ) {
            logger.log.debug("user update error")
          } else {
            count = count + 1
            post()
          }
          noChange
      }
      noChange
  }
}
