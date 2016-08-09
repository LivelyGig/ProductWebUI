package client.handlers

import client.RootModels.AppRootModel
import diode.{ActionHandler, ActionResult, ModelRW}

/**
  * Created by shubham.k on 05-08-2016.
  */
case class ShowServerError(getError : String)

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ShowServerError(errorMsg) =>
      updated(value.copy(isServerError = true, serverErrorMsg = errorMsg))
  }
}
