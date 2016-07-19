package synereo.client.services

import synereo.client.handlers._
import shared.RootModels._
import diode._
import diode.data._
import diode.react.ReactConnector
import shared.dtos.Introduction
import shared.models.{UserModel}
import synereo.client.handlers.IntroductionHandler

case class RootModel(connections: Pot[ConnectionsRootModel], user: UserModel, messages: Pot[MessagesRootModel],
                     jobPosts: Pot[ProjectsRootModel], searches: SearchesRootModel, profiles: Pot[ProfilesRootModel], introduction: IntroRootModel)

object SYNEREOCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(Empty, UserModel("", "", ""), Empty, Empty, SearchesRootModel(Nil), Empty, IntroRootModel(Nil))

  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ProjectsHandler(zoomRW(_.jobPosts)((m, v) => m.copy(jobPosts = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v))),
    new MessagesHandler(zoomRW(_.messages)((m, v) => m.copy(messages = v))),
    new IntroductionHandler(zoomRW(_.introduction)((m, v) => m.copy(introduction = v)))
  )
}