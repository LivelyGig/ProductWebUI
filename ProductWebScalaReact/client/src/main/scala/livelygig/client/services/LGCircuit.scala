package livelygig.client.services

import autowire._
import livelygig.client.Handlers.{UserHandler, ConnectionHandler}
import livelygig.client.RootModels._
import upickle.default._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import livelygig.client.models.{MessagesModel, UserModel, ConnectionsModel}
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/11/2016.
  */



case class RootModel(connections: Pot[ConnectionsRootModel], user: UserModel, messages: MessagesModel)

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected var model = RootModel(Empty, UserModel("","",""), MessagesModel(4))
  // combine all handlers into one
  override protected val actionHandler = combineHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v))),
    new UserHandler(zoomRW(_.user)((m, v) => m.copy(user = v)))

  )
}
