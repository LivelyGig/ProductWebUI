package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.logger
import synereo.client.rootmodels.AppRootModel


/**
  * Created by bhagyashree.b on 2016-07-28.
  */

case class ShowServerError(getError: String)

case class ToggleImageUploadModal()

case class ToggleAboutInfoModal()

case class ToggleNodeSettingModal()

case class ToggleNewMessageModal()

case class CloseAllPopUp()

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ShowServerError(errorMsg) =>
      updated(value.copy(isServerError = true, serverErrorMsg = errorMsg))

    case ToggleImageUploadModal() =>
      logger.log.debug(s"ToggleImageUploadModal in handler ${value.showProfileImageUploadModal}")
      updated(value.copy(showProfileImageUploadModal = !value.showProfileImageUploadModal))

    case ToggleNewMessageModal() =>
      logger.log.debug(s"ToggleNewMessageModal in handler ${value.showProfileImageUploadModal}")
      updated(value.copy(showNewMessageModal = !value.showNewMessageModal))

    case ToggleAboutInfoModal() =>
      logger.log.debug(s"ToggleAboutInfoModal in handler ${value.showAboutInfoModal}")
      updated(value.copy(showAboutInfoModal = !value.showAboutInfoModal))

    case ToggleNodeSettingModal() =>
      logger.log.debug(s"ToggleNodeSettingModal in handler ${value.showNodeSettingModal}")
      updated(value.copy(showNodeSettingModal = !value.showNodeSettingModal))

    case CloseAllPopUp() =>
      logger.log.debug(s"closing all popup in app module")
      updated(value.copy(showNodeSettingModal = false, showAboutInfoModal = false, showProfileImageUploadModal = false))

  }


}