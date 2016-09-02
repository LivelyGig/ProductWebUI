package synereo.client.modulebackends

import japgolly.scalajs.react.{Callback, _}
import synereo.client.handlers.{LogoutUser, ShowServerError}
import synereo.client.logger
import synereo.client.modules.AppModule
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._

/**
  * Created by bhagyashree.b on 2016-09-02.
  */

 class AccountModuleBackend(t: BackendScope[AppModule.Props, AppModule.State]) {

  def serverError(): Callback = {
    SYNEREOCircuit.dispatch(ShowServerError(""))
    t.modState(s => s.copy(showErrorModal = false))
  }

  def mounted(props: AppModule.Props) = Callback {
    logger.log.debug("app module mounted")
    val userSessionUri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    if (userSessionUri.length < 1) {
      SYNEREOCircuit.dispatch(LogoutUser())
    }

  }
}