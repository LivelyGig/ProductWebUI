package synereo.client.modulebackends

import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import synereo.client.logger
import synereo.client.modules.MainMenu

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-02.
  */

class MainMenuBackend(t: BackendScope[MainMenu.Props, MainMenu.State]) {

  def toggleTopbar = Callback {
    val topBtn: js.Object = "#TopbarContainer"
    $(topBtn).toggleClass("topbar-left topbar-lg-show")
  }

  def showUploadImageModal(): react.Callback = {
    t.modState(s => s.copy(showProfileImageUploadModal = true))
  }

  def imageUploaded(): Callback = {
    t.modState(s => s.copy(showProfileImageUploadModal = false))
  }

  def toggleShowAboutInfoModal(): react.Callback = {
    t.modState(s => s.copy(showAboutInfoModal = !s.showAboutInfoModal))
  }

  //  def hideAboutInfoModal(): Callback = {
  //    t.modState(s => s.copy(showProfileImageUploadModal = false))
  //  }

  def showNodeSettingModal(): react.Callback = {
    logger.log.debug("showNodeSettingModal")
    t.modState(s => s.copy(showNodeSettingModal = true))
  }

  def hideNodeSettingModal(): Callback = {
    logger.log.debug("hideNodeSettingModal")
    t.modState(s => s.copy(showNodeSettingModal = false))
  }
}
