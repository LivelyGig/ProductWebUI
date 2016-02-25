package livelygig.client.services

import autowire._
import livelygig.client.Handlers.{SearchesHandler, ProjectsHandler, UserHandler, ConnectionHandler}
import livelygig.client.RootModels._
import upickle.default._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import livelygig.client.models.{ MessagesModel, UserModel}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON


/**
  * Created by shubham.k on 1/11/2016.
  */



case class RootModel(connections: Pot[ConnectionsRootModel], user: UserModel, messages: MessagesModel,
                     jobPosts: Pot[ProjectsRootModel], searches: SearchesRootModel)

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected var model = RootModel(Empty, UserModel("","",""), MessagesModel(4), Empty, SearchesRootModel(Nil))
  // combine all handlers into one
  override protected val actionHandler = combineHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v))),
    new ProjectsHandler(zoomRW(_.jobPosts)((m, v) => m.copy(jobPosts = v))),
    new SearchesHandler(zoomRW(_.searches)((m, v) => m.copy(searches = v)))

  )
}
