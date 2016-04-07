package client.services

import autowire._
import client.handlers._
import client.rootmodels._
import upickle.default._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import client.models.{ MessagesModel, UserModel}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON

case class RootModel(connections: Pot[ConnectionsRootModel], user: UserModel, messages: Pot[MessagesRootModel],
                     jobPosts: Pot[ProjectsRootModel], searches: SearchesRootModel)

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(Empty, UserModel("","",""), Empty, Empty, SearchesRootModel(Nil))
  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ProjectsHandler(zoomRW(_.jobPosts)((m, v) => m.copy(jobPosts = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v))),
    new MessagesHandler(zoomRW(_.messages)((m, v) => m.copy(messages = v)))

  )
}
