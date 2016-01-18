package livelygig.client.services

import autowire._
import upickle.default._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._
import livelygig.client.models.ConnectionsModel
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/11/2016.
  */

// Actions
case object RefreshConnections

case class UpdateAllConnections( connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]] )

case class UpdateConnection( connection: ConnectionProfileResponse)

case class RootModel(connections: Pot[ConnectionsRootModel])

case class ConnectionsRootModel(connectionsResponse: Seq[ConnectionsModel]) {
  def updated (newConnectionResponse: ConnectionsModel) = {
    println(newConnectionResponse)
    connectionsResponse.indexWhere(_.connection.target == newConnectionResponse.connection.target)
    match {
      case -1 =>
        ConnectionsRootModel(connectionsResponse:+newConnectionResponse)
      case target =>
        ConnectionsRootModel(connectionsResponse.updated(target, newConnectionResponse))
    }
  }
}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case RefreshConnections =>
      effectOnly(Effect(CoreApi.sessionPing().map(UpdateAllConnections)))
    case UpdateAllConnections(connections) =>
      var model = Seq[ConnectionsModel]()
      connections.map(connection=>
        connection.content.name->Option(
          JSON.parse(connection.content.jsonBlob).name))
      /*connections.foreach{connection=>
        val json = JSON.parse(connection.content.jsonBlob)
        println(json.name)
      }*/
//      println(connections)
//      connections.foreach {
//        connection => println {
//          JSON.parse(connection.content.jsonBlob).name
//        }
//      }
      connections.foreach {
        connection =>
          val json = JSON.parse(connection.content.jsonBlob)
          val name = json.name.asInstanceOf[String]
          val imgSrc = if(connection.content.jsonBlob.contains("imgSrc"))json.imgSrc.asInstanceOf[String] else ""
          model :+= new ConnectionsModel(connection.content.sessionURI,connection.content.connection,
            name, imgSrc)

      }
      model.foreach(temp=>println(temp.name))
      updated(Ready(ConnectionsRootModel(model)))
  }
}

object LGCircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected var model = RootModel(Empty)
  // combine all handlers into one
  override protected val actionHandler = combineHandlers(
    new ConnectionHandler(zoomRW(_.connections)((m, v) => m.copy(connections = v)))

  )
}
