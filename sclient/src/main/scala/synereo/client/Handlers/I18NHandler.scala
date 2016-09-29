package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.I18NRootModel

/**
  * Created by mandar.k on 9/27/2016.
  */

case class ChangeLang(lang: scalajs.js.Dynamic)

class I18NHandler[M](modelRW: ModelRW[M, I18NRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case ChangeLang(lang) =>
      updated(value.copy(lang))

  }

}
