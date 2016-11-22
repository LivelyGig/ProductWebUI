package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import synereo.client.rootmodels.ConfigRootModel


case class ChangeConfig (config: scalajs.js.Dynamic)
/**
  * Created by shubham.k on 21-11-2016.
  */
class ConfigHandler[M](modelRW: ModelRW[M, ConfigRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case ChangeConfig(config) =>
      updated(value.copy(config))

  }
}
