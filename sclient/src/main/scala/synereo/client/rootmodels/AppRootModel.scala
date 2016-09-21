package synereo.client.rootmodels

/**
  * Created by bhagyashree.b on 2016-07-28.
  */


case class AppRootModel(isServerError: Boolean = false, serverErrorMsg: String = "", showProfileImageUploadModal: Boolean = false,
                        showNodeSettingModal: Boolean = false, showAboutInfoModal: Boolean = false)
