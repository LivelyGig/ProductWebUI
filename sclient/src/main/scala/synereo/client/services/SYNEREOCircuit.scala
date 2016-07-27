package synereo.client.services

import synereo.client.handlers._
import shared.RootModels._
import diode._
import diode.data._
import diode.react.ReactConnector
import shared.models.{UserModel}
import synereo.client.handlers.IntroductionHandler

case class RootModel(connections: ConnectionsRootModel, user: UserModel, messages: Pot[MessagesRootModel],
                     searches: SearchesRootModel, introduction: IntroRootModel, sessionPing: SessionRootModel)

object SYNEREOCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(ConnectionsRootModel(Nil),
    UserModel("", "", ""), Empty, SearchesRootModel(Nil), IntroRootModel(Nil), SessionRootModel())

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v))),
    new MessagesHandler(zoomRW(_.messages)((m, v) => m.copy(messages = v))),
    new IntroductionHandler(zoomRW(_.introduction)((m, v) => m.copy(introduction = v))),
    new SessionPingHandler(zoomRW(_.sessionPing)((m, v) => m.copy(sessionPing = v)))
  )
}