package synereo.client.handlers

import diode.data.{Empty, Pot, PotAction}
import diode.{ActionHandler, ActionResult, ModelRW}
import shared.RootModels.ConnectionsRootModel
import shared.dtos._
import shared.models.ConnectionsModel
import shared.sessionitems.SessionItems
import synereo.client.services.CoreApi
import synereo.client.utils.ConnectionsUtils

//import rx.ops.Timer
import org.scalajs.dom._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSON

// Actions
//scalastyle:off
case class RefreshConnections(potResult: Pot[ConnectionsRootModel] = Empty) extends PotAction[ConnectionsRootModel, RefreshConnections] {
  // first ping after user login returns the connections for the user.
  // todo replace the session ping with the connections specific calls to api backend server.
  override def next(value: Pot[ConnectionsRootModel]) = RefreshConnections(value)
}

case class AddConnection(connectNotification: ConnectNotification)

object ConnectionModelHandler {
  def getConnectionsModel(response: String): ConnectionsRootModel = {

    val connections = upickle.default.read[Seq[ApiResponse[ConnectionProfileResponse]]](response)
    //    println("connections = "+ connections)
    var model = Seq[ConnectionsModel]()

    connections.foreach {
      connection =>
        //        println(connection.content.jsonBlob)
        val json = JSON.parse(connection.content.jsonBlob)
        val name = json.name.asInstanceOf[String]
        val source = connection.content.connection.source
        val target = connection.content.connection.target
        val label = connection.content.connection.label
        val imgSrc = if (connection.content.jsonBlob.contains("imgSrc")) json.imgSrc.asInstanceOf[String] else ""
        model :+= new ConnectionsModel(connection.content.sessionURI, connection.content.connection,
          name, imgSrc)
    }
    //    model.foreach(temp => println(temp.name))
    ConnectionsRootModel(model.sortBy(_.name))
  }

}

class ConnectionHandler[M](modelRW: ModelRW[M, Pot[ConnectionsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case AddConnection(connectNotification: ConnectNotification) =>
      val connectionSessionUri = window.sessionStorage.getItem(SessionItems.ConnectionViewItems.CONNECTIONS_SESSION_URI)
      val introProfile = JSON.parse(connectNotification.introProfile)
      val tempname = introProfile.name.asInstanceOf[String]
      val img = if (connectNotification.introProfile.contains("imgSrc")) introProfile.imgSrc.asInstanceOf[String] else ""
      val newConnection = ConnectionsModel(connectionSessionUri, connection = connectNotification.connection, name = tempname, img)
      //      val newConneSeq = Pot[Seq[ConnectionsModel]](newConnection)
      //      val newValue = value.++((newConneSeq))
      //      updated(newValue)
      noChange

    case action: RefreshConnections =>
      ConnectionsUtils.checkIntroductionNotification()
      val updateF = action.effect(CoreApi.getConnections())(connections => ConnectionModelHandler.getConnectionsModel(connections))
      action.handleWith(this, updateF)(PotAction.handler())


  }
}