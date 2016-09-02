package synereo.client.modules


import java.util.UUID

import japgolly.scalajs.react._
import shared.models.{ConnectionsModel, ServerModel}
import synereo.client.utils.ConnectionsUtils
import synereo.client.css.UserProfileViewCSS
import synereo.client.components.Icon
import org.querki.jquery.$

import scalacss.ScalaCssReact._
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js


/**
  * Created by bhagyashree.b on 2016-07-01.
  */


object AccountInfo {

  val searchContainer: js.Object = "#searchContainer"

  case class Props()

  case class State(DSLCommLinkClient: Seq[ServerModel] = Nil, DSLEvaluator: Seq[ServerModel] = Nil,
                   DSLEvaluatorPreferredSupplier: Seq[ServerModel] = Nil, BFactoryCommLinkServer: Seq[ServerModel] = Nil,
                   BFactoryCommLinkClient: Seq[ServerModel] = Nil, BFactoryEvaluator: Seq[ServerModel] = Nil)

  val agentUID = ConnectionsUtils.getSelfConnnection().source.substring(8).split("\"")
//  val newAgentUID = agentUID.substring(8)
//  val output = newAgentUID.split("\"")


  class Backend(t: BackendScope[Props, State]) {

    def getNewServerModel(): ServerModel = {
      ServerModel(UUID.randomUUID().toString.replaceAll("-", ""), "", true)
    }

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()),
        Seq(getNewServerModel()), Seq(getNewServerModel())))
    }

    def addNewServer(id: String): Callback = {
      println("in ad ")
      id match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient ++ Seq(getNewServerModel())))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator ++ Seq(getNewServerModel())))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier ++ Seq(getNewServerModel()))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer ++ Seq(getNewServerModel())))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient ++ Seq(getNewServerModel())))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator ++ Seq(getNewServerModel())))
      }
    }

    def enableTextboxToggle(sectionId: String, inputId: String): Callback = {
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
      }
    }

    // scalastyle:off
    def updateTextbox(e: ReactEventI): Callback = {
      val currentVal = e.target.value
      val inputId = e.target.id
      val sectionId = $(s"#${e.target.id}".asInstanceOf[js.Object]).parent().parent().parent().attr("id").toString
      printf(sectionId)
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
      }
    }

    def editAll(sectionId: String): Callback = {
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(e => e.copy(isEditable = true))))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(e => e.copy(isEditable = true))))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(e => e.copy(isEditable = true)))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(e => e.copy(isEditable = true))))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(e => e.copy(isEditable = true))))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(e => e.copy(isEditable = true))))
      }
    }

    def deleteAddress(sectionId: String, inputId: String): Callback = {
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.filterNot(p => p.uid == inputId)))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.filterNot(p => p.uid == inputId)))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.filterNot(p => p.uid == inputId))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.filterNot(p => p.uid == inputId)))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.filterNot(p => p.uid == inputId)))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.filterNot(p => p.uid == inputId)))
      }
    }

    def deleteAll(sectionId: String): Callback = {
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = Nil))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = Nil))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = Nil))
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = Nil))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = Nil))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = Nil))
      }
    }

    def saveAll(sectionId: String): Callback = {
      sectionId match {
        case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(e => e.copy(isEditable = false))))
        case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(e => e.copy(isEditable = false))))
        case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(e => e.copy(isEditable = false)))) // scalastyle:ignore
        case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(e => e.copy(isEditable = false))))
        case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(e => e.copy(isEditable = false))))
        case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(e => e.copy(isEditable = false))))
      }
    }

    def render(s: State, p: Props) = {
      <.div()(
        <.div(UserProfileViewCSS.Style.userProfileHeadingContainerDiv)(
          <.div(UserProfileViewCSS.Style.agentUID)(s"Agent UID : ${agentUID.head}"),
          <.div(UserProfileViewCSS.Style.agentUID)("Build Number : "),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "DSLCommLinkClient", ^.id := "DSLCommLinkClient")(<.label(UserProfileViewCSS.Style.label)("DSLCommLinkClient : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("DSLCommLinkClient"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("DSLCommLinkClient"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("DSLCommLinkClient"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("DSLCommLinkClient"))("Delete All")),
            for (dsl <- s.DSLCommLinkClient) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLCommLinkClient", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLCommLinkClient", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("DSLCommLinkClient", dsl.uid))
              )
            }
          ),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "DSLEvaluator")(<.label(UserProfileViewCSS.Style.label)("DSLEvaluator : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("DSLEvaluator"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("DSLEvaluator"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("DSLEvaluator"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("DSLEvaluator"))("Delete All")),
            for (dsl <- s.DSLEvaluator) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLEvaluator", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLEvaluator", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("DSLEvaluator", dsl.uid))
              )
            }
          ),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "DSLEvaluatorPreferredSupplier")(<.label(UserProfileViewCSS.Style.label)("DSLEvaluatorPreferredSupplier : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("DSLEvaluatorPreferredSupplier"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("DSLEvaluatorPreferredSupplier"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("DSLEvaluatorPreferredSupplier"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("DSLEvaluatorPreferredSupplier"))("Delete All")),
            for (dsl <- s.DSLEvaluatorPreferredSupplier) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLEvaluatorPreferredSupplier", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("DSLEvaluatorPreferredSupplier", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("DSLEvaluatorPreferredSupplier", dsl.uid))
              )
            }
          ),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "BFactoryCommLinkServer")(<.label(UserProfileViewCSS.Style.label)("BFactoryCommLinkServer : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("BFactoryCommLinkServer"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("BFactoryCommLinkServer"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("BFactoryCommLinkServer"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("BFactoryCommLinkServer"))("Delete All")),
            for (dsl <- s.BFactoryCommLinkServer) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryCommLinkServer", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryCommLinkServer", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("BFactoryCommLinkServer", dsl.uid))
              )
            }
          ),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "BFactoryCommLinkClient")(<.label(UserProfileViewCSS.Style.label)("BFactoryCommLinkClient : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("BFactoryCommLinkClient"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("BFactoryCommLinkClient"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("BFactoryCommLinkClient"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("BFactoryCommLinkClient"))("Delete All")),
            for (dsl <- s.BFactoryCommLinkClient) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryCommLinkClient", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryCommLinkClient", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("BFactoryCommLinkClient", dsl.uid))
              )
            }
          ),
          <.div(UserProfileViewCSS.Style.agentUID, ^.id := "BFactoryEvaluator")(<.label(UserProfileViewCSS.Style.label)("BFactoryEvaluator : "))
          (<.div(UserProfileViewCSS.Style.buttonsContainerDiv)
          (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> editAll("BFactoryEvaluator"))("Edit All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> saveAll("BFactoryEvaluator"))("Save All"),
            <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> addNewServer("BFactoryEvaluator"))("Add"),
          <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> deleteAll("BFactoryEvaluator"))("Delete All")),
            for (dsl <- s.BFactoryEvaluator) yield {
              <.div(
                <.div(UserProfileViewCSS.Style.inputText, ^.className := "input-group",
                  <.input(^.`type` := "text", ^.onChange ==> updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                  if (dsl.isEditable) {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryEvaluator", dsl.uid))
                    )
                  } else {
                    <.span(^.className := "input-group-btn",
                      <.button(^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> enableTextboxToggle("BFactoryEvaluator", dsl.uid))
                    )
                  }
                ),
                <.button(UserProfileViewCSS.Style.deleteButton)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> deleteAddress("BFactoryEvaluator", dsl.uid))
              )
            }
          )
        ))
    }


  }

  // create the React component for user's connections
  val component = ReactComponentB[Props]("ConnectionsResults")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply() = component(Props())
}


