package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.AppRootModel


/**
  * Created by bhagyashree.b on 2016-07-28.
  */

case class ShowServerError(getError : String)

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ShowServerError(errorMsg) =>
      updated(value.copy(isServerError = true, serverErrorMsg = errorMsg))
  }
}