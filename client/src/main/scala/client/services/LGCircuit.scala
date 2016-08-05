package client.services

//import client.Handlers.IntroductionHandler
import client.handlers._
import shared.RootModels.{AppRootModel, _}
import diode._
import diode.data._
import diode.react.ReactConnector
import shared.models.UserModel

case class RootModel(connections: Pot[ConnectionsRootModel], user: UserModel, messages: Pot[MessagesRootModel],
  jobPosts: Pot[ProjectsRootModel], searches: SearchesRootModel, profiles : Pot[ProfilesRootModel], introduction: IntroRootModel/*,appRootModel : AppRootModel*/)

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(Empty, UserModel("", "", ""), Empty, Empty, SearchesRootModel(Nil), Empty,IntroRootModel(Nil)/*,AppRootModel()*/)
  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ProjectsHandler(zoomRW(_.jobPosts)((m, v) => m.copy(jobPosts = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v))),
    new MessagesHandler(zoomRW(_.messages)((m, v) => m.copy(messages = v))),
    new ProfilesHandler(zoomRW(_.profiles)((m, v) => m.copy(profiles = v))),
    new IntroductionHandler(zoomRW(_.introduction)((m, v) => m.copy(introduction = v)))/*,
    new AppHandler(zoomRW(_.appRootModel)((m,v) => m.copy(appRootModel = v)))*/
  )
}
