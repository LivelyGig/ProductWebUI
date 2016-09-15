package client.handler

import client.rootmodel.AppRootModel
import client.modules.AppModule
import client.utils.ContentUtils
import diode.{ActionHandler, ActionResult, ModelRW}

/**
  * Created by shubham.k on 05-08-2016.
  */
case class ShowServerError(getError: String)

case class SubscribeForDefaultAndBeginPing()

class ApplicationHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ShowServerError(errorMsg) =>
      updated(value.copy(isServerError = true, serverErrorMsg = errorMsg))

    case SubscribeForDefaultAndBeginPing() =>
      ContentUtils.subsForContentAndBeginSessionPing(AppModule.MESSAGES_VIEW)
      ContentUtils.subsForContentAndBeginSessionPing(AppModule.PROFILES_VIEW)
      ContentUtils.subsForContentAndBeginSessionPing(AppModule.PROJECTS_VIEW)
      noChange
  }
}