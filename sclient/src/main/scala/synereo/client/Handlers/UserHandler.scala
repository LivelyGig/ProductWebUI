package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import org.scalajs.dom.window
import shared.dtos.UpdateUserRequest
import shared.models.UserModel
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import diode.AnyAction._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

// scalastyle:off
case class LoginUser(userModel: UserModel)

case class LogoutUser()

case class UpdateUserImage(imgSrc: String)

//case class PostUserUpdate(updateUserRequest: UpdateUserRequest)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  //  val messageLoader = "#messageLoader"
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case LoginUser(userModel) =>
      updated(userModel)

    case LogoutUser() =>
      // todo: Cancel all subscribe request for all sessions
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      CoreApi.closeSessionRequest(uri)
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false))

    case UpdateUserImage(imgSrc) =>
      updated(value.copy(imgSrc = imgSrc))

    //    case PostUserUpdate(req) =>
    //      ContentHandler.postUserUpdate(req)
    //      if (req.jsonBlob.imgSrc != null) {
    //        updated(value.copy(imgSrc = req.jsonBlob.imgSrc))
    //      } else noChange

  }
}
