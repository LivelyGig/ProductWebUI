package synereo.client.services

import synereo.client.handlers._
import diode._
import diode.data._
import diode.react.ReactConnector
import shared.models.UserModel
import synereo.client.handlers.ConfigHandler
import synereo.client.handlers.I18NHandler
import synereo.client.handlers.AppHandler
import synereo.client.handlers.IntroductionHandler
import synereo.client.rootmodels.{ConfigRootModel, _}
import synereo.client.utils.{Config, I18N}

case class RootModel(connections: ConnectionsRootModel, user: UserModel, messages: Pot[MessagesRootModel],
                     searches: SearchesRootModel, introduction: IntroRootModel, sessionRootModel: SessionRootModel,
                     appRootModel: AppRootModel, i18n: I18NRootModel, configRootModel: ConfigRootModel)

object SYNEREOCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(ConnectionsRootModel(Nil),
    UserModel(), Empty, SearchesRootModel(Nil), IntroRootModel(Nil), SessionRootModel(), AppRootModel(), I18NRootModel(scalajs.js.JSON.parse(I18N.en_us)), ConfigRootModel(scalajs.js.JSON.parse(Config.config)))

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v))),
    new MessagesHandler(zoomRW(_.messages)((m, v) => m.copy(messages = v))),
    new IntroductionHandler(zoomRW(_.introduction)((m, v) => m.copy(introduction = v))),
    new SessionHandler(zoomRW(_.sessionRootModel)((m, v) => m.copy(sessionRootModel = v))),
    new AppHandler(zoomRW(_.appRootModel)((m, v) => m.copy(appRootModel = v))),
    new I18NHandler(zoomRW(_.i18n)((m, v) => m.copy(i18n = v))),
    new ConfigHandler(zoomRW(_.configRootModel)((m, v) => m.copy(configRootModel = v)))
  )
}
