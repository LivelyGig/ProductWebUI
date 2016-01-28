package livelygig.client.Handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Ready, Pot}
import livelygig.client.RootModels.ConnectionsRootModel
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.CoreApi
import livelygig.shared.dtos.{ConnectionProfileResponse, ApiResponse}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
// Actions
case object RefreshConnections

case class UpdateAllConnections( connectionsResponse: Seq[ApiResponse[ConnectionProfileResponse]] )

case class UpdateConnection( connection: ConnectionProfileResponse)
class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case RefreshConnections =>
      effectOnly(Effect(CoreApi.sessionPing().map(UpdateAllConnections)))
    case UpdateAllConnections(connections) =>
      var model = Seq[ConnectionsModel]()
      connections.map(connection=>
        connection.content.name->Option(
          JSON.parse(connection.content.jsonBlob).name)
      )



      connections.foreach {
        connection =>
          val json = JSON.parse(connection.content.jsonBlob)
          val name = json.name.asInstanceOf[String]
          val source = connection.content.connection.source
          val imgSrc = if(connection.content.jsonBlob.contains("imgSrc"))json.imgSrc.asInstanceOf[String] else ""
          model :+= new ConnectionsModel(connection.content.sessionURI,connection.content.connection,
            name, imgSrc)

      }
      model.foreach(temp=>println(temp.name))
      updated(Ready(ConnectionsRootModel(model)))
  }
}