package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.RootModels.AppRootModel


/**
  * Created by bhagyashree.b on 2016-07-28.
  */

case class ShowServerError()

class AppHandler[M](modelRW: ModelRW[M, AppRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case ShowServerError() =>
     // println("In show serverError Action and its value is = " + value.isServerError)
      updated(AppRootModel(isServerError = !value.isServerError))
  }
}