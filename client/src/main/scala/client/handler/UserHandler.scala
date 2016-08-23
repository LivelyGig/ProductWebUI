package client.handler

import client.services.{CoreApi, LGCircuit}
import diode.{ActionHandler, ActionResult, ModelRW}
import org.scalajs.dom.window
import shared.dtos.UpdateUserRequest
import shared.models.UserModel
import client.logger
import diode.AnyAction._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

// scalastyle:off
case class LoginUser(userModel: UserModel)

case class LogoutUser()

case class PostUserUpdate(updateUserRequest: UpdateUserRequest)

case class ToggleAvailablity()

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  //  val messageLoader = "#messageLoader"
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case LoginUser(userModel) =>/*println(s"In loginUser Usermodel ${userModel.sessionUri}")*/
      updated(userModel.copy(isAvailable = true, isLoggedIn = true))

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
          logger.log.debug("user update request sent successfully")
        case Failure(response) =>
          if (count == 3) {
            //            logger.log.error("user update error")
            LGCircuit.dispatch(ShowServerError(response.toString))
          } else {
            count = count + 1
            post()
          }
      }
      if (req.jsonBlob.imgSrc != null) {
        updated(value.copy(imgSrc = req.jsonBlob.imgSrc))
      } else noChange

    case ToggleAvailablity() =>
      updated(value.copy(isAvailable = !value.isAvailable))

  }
}
