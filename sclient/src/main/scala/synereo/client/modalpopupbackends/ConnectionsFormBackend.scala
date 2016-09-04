package synereo.client.modalpopupbackends

import japgolly.scalajs.react
import japgolly.scalajs.react.{Callback, _}
import org.querki.jquery._
import shared.models.ConnectionsModel
import synereo.client.components.{ConnectionsSelectize, _}
import synereo.client.modalpopups.ConnectionsForm
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.{ConnectionsUtils, ContentUtils}
import synereo.client.components.Bootstrap._

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */

case class ConnectionsFormBackend(t: BackendScope[ConnectionsForm.Props, ConnectionsForm.State]) {
  def hide: Callback = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def fromSelecize(): Callback = {
    val cnxns = ConnectionsSelectize.getConnectionNames(t.state.runNow().selectizeInputId)
    val msg = if (cnxns.length > 1) {
      s"Hi ${cnxns(0)} and ${cnxns(1)}, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${t.state.runNow().userName}"
    } else {
      s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${t.state.runNow().userName}"
    }
    t.modState(s => s.copy(introConnections = s.introConnections.copy(aMessage = msg)))
  }

  def mounted(props: ConnectionsForm.Props): Callback = {
    val usr = SYNEREOCircuit.zoom(_.user).value.name
    val msg = s"Hi <Recipient 1> and <Recipient 2>, \n Here's an introduction for the two of you to connect. \n \n Best regards, \n ${usr}"
    t.modState(s => s.copy(userName = usr, introConnections = s.introConnections.copy(aMessage = msg)))
  }

  def hideModal(): Unit = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def chkCnxnExstUser(e: ReactEventI): react.Callback = {
    val state = t.state.runNow()
    t.modState(s => s.copy(chkCnxnExstUser = true, introduceUsers = false, chkCnxnNewUser = false))
  }

  def chkCnxnNewUser(e: ReactEventI): react.Callback = {
    val state = t.state.runNow()
    t.modState(s => s.copy(chkCnxnNewUser = true, introduceUsers = false, chkCnxnExstUser = false))
  }

  def chkCnxnExstandOther(e: ReactEventI): react.Callback = {
    val state = t.state.runNow()
    t.modState(s => s.copy(introduceUsers = true, chkCnxnNewUser = false, chkCnxnExstUser = false))
  }

  def getCnxn(uri: String): Option[ConnectionsModel] = {
    //      val test = SYNEREOCircuit.zoom(_.connections.get.connectionsResponse)
    //      println(test)
    val connectionsModel = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
    if (!connectionsModel.isEmpty)
      connectionsModel.find(e => e.connection.target.contains(uri))
    else
      None
  }

  def introduceTwoUsers() = {
    $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
    val state = t.state.runNow()
    val msg = state.introConnections.aMessage.replaceAll("/", "//")
    val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
    val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.selectizeInputId)
    if (connections.length == 2) {
      val content = state.introConnections.copy(aConnection = connections(0), bConnection = connections(1),
        sessionURI = uri, alias = "alias", aMessage = msg, bMessage = msg)
      //        SYNEREOCircuit.dispatch(PostNewConnection(content))
      ContentUtils.postNewConnection(content)
      t.modState(s => s.copy(postConnection = true))
    } else {
      $("#cnxnError".asInstanceOf[js.Object]).removeClass("hidden")
      t.modState(s => s.copy(postConnection = false))
    }
  }

  def establishConnection(): react.Callback = {
    val state = t.state.runNow()
    $("#agentFieldError".asInstanceOf[js.Object]).addClass("hidden")
    getCnxn(state.agentUid) match {
      case Some(cnxn) =>
        $("#agentFieldError".asInstanceOf[js.Object]).removeClass("hidden")
        t.modState(s => s.copy(postConnection = false))
      case None =>
        val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
        val content = state.establishConnection.copy(sessionURI = uri,
          aURI = ConnectionsUtils.getSelfConnnection().source,
          bURI = s"agent://${state.agentUid}", label = "869b2062-d97b-42dc-af5d-df28332cdda1")
        //          SYNEREOCircuit.dispatch(PostNewConnection(content))
        ContentUtils.postNewConnection(content)
        t.modState(s => s.copy(postConnection = true))
    }
  }

  def submitForm(e: ReactEventI): react.Callback = {
    e.preventDefault()
    val state = t.state.runNow()
    if (state.introduceUsers) {
      introduceTwoUsers
    }
    else {
      establishConnection
    }
  }

  def formClosed(state: ConnectionsForm.State, props: ConnectionsForm.Props): Callback = {
    // call parent handler with the new item and whether form was OK or cancelled
    //      println(state.postConnection)
    props.submitHandler()
  }

  def updateContent(e: ReactEventI): react.Callback = {
    val value = e.target.value
    t.modState(s => s.copy(introConnections = s.introConnections.copy(bMessage = value, aMessage = value)))
  }

  def updateAgentUid(e: ReactEventI): react.Callback = {
    val value = e.target.value
    t.modState(s => s.copy(agentUid = value))
  }

}


