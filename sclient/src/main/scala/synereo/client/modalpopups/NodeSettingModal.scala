package synereo.client.modalpopups

import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css._
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._
import java.util.UUID
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import scala.concurrent.ExecutionContext.Implicits.global
import boopickle.Default._
import scala.scalajs.js
import org.querki.jquery._
import shared.Api
import synereo.client.services.PlayAjaxClient
import autowire._
import shared.dtos.ServerModel


/**
  * Created by mandar.k on 9/1/2016.
  */
//scalastyle:off
object NodeSettingModal {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(DSLCommLinkClient: Seq[ServerModel] = Nil,
                   DSLEvaluator: Seq[ServerModel] = Nil,
                   DSLEvaluatorPreferredSupplier: Seq[ServerModel] = Nil,
                   BFactoryCommLinkServer: Seq[ServerModel] = Nil,
                   BFactoryCommLinkClient: Seq[ServerModel] = Nil,
                   BFactoryEvaluator: Seq[ServerModel] = Nil,
                   formSubmit: Boolean = false)

  class NodeSettingModalBackend(t: BackendScope[Props, State]) {


    def closeForm = {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      val state = t.state.runNow()
      val serversToPost = state.BFactoryCommLinkClient.map(e => e.copy(serverType = "BFactoryCommLinkClient")) ++
        state.BFactoryCommLinkServer.map(e => e.copy(serverType = "BFactoryCommLinkServer")) ++
        state.BFactoryEvaluator.map(e => e.copy(serverType = "BFactoryEvaluator")) ++
        state.DSLCommLinkClient.map(e => e.copy(serverType = "DSLCommLinkClient")) ++
        state.DSLEvaluator.map(e => e.copy(serverType = "DSLEvaluator")) ++
        state.DSLEvaluatorPreferredSupplier.map(e => e.copy(serverType = "DSLEvaluatorPreferredSupplier"))
      PlayAjaxClient[Api].postServers(serversToPost).call()
      t.modState(s => s.copy(formSubmit = true))
    }

    def modalClosed(state: State, props: Props): Callback = {
      props.submitHandler()
    }

    def getNewServerModel(): ServerModel = {
      ServerModel(UUID.randomUUID().toString.replaceAll("-", ""), "", true, "")
    }

    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()),
        Seq(getNewServerModel()), Seq(getNewServerModel())))
    }

    def addNewServer(id: String): Callback = {
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

  }

  private val component = ReactComponentB[Props]("NodeSettingModal")
    .initialState_P(p => State())
    .backend(new NodeSettingModalBackend(_))
    .renderPS((t, props, state) => {
      val headerText = "NodeSettings"
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(^.className:="model-title",SynereoCommanStylesCSS.Style.modalHeaderTitle)(headerText)),
          closed = () => t.backend.modalClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.form(^.onSubmit ==> t.backend.submitForm)(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-12")(
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "DSLCommLinkClient", ^.id := "DSLCommLinkClient",
                  <.label("DSLCommLinkClient : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer,
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("DSLCommLinkClient"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("DSLCommLinkClient"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("DSLCommLinkClient"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("DSLCommLinkClient"))("Delete All")),
                  for (dsl <- state.DSLCommLinkClient) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLCommLinkClient", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLCommLinkClient", dsl.uid)
                          )
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("DSLCommLinkClient", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "DSLEvaluator",
                  <.label("DSLEvaluator : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer)
                  (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("DSLEvaluator"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("DSLEvaluator"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("DSLEvaluator"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("DSLEvaluator"))("Delete All")),
                  for (dsl <- state.DSLEvaluator) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLEvaluator", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLEvaluator", dsl.uid))
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons)(^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("DSLEvaluator", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "DSLEvaluatorPreferredSupplier",
                  <.label("DSLEvaluatorPreferredSupplier : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer)
                  (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("DSLEvaluatorPreferredSupplier"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("DSLEvaluatorPreferredSupplier"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("DSLEvaluatorPreferredSupplier"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("DSLEvaluatorPreferredSupplier"))("Delete All")),
                  for (dsl <- state.DSLEvaluatorPreferredSupplier) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLEvaluatorPreferredSupplier", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("DSLEvaluatorPreferredSupplier", dsl.uid)
                          )
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("DSLEvaluatorPreferredSupplier", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "BFactoryCommLinkServer",
                  <.label("BFactoryCommLinkServer : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer)
                  (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("BFactoryCommLinkServer"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("BFactoryCommLinkServer"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("BFactoryCommLinkServer"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("BFactoryCommLinkServer"))("Delete All")),
                  for (dsl <- state.BFactoryCommLinkServer) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryCommLinkServer", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryCommLinkServer", dsl.uid)
                          )
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("BFactoryCommLinkServer", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "BFactoryCommLinkClient",
                  <.label("BFactoryCommLinkClient : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer)
                  (<.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("BFactoryCommLinkClient"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("BFactoryCommLinkClient"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("BFactoryCommLinkClient"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("BFactoryCommLinkClient"))("Delete All")),
                  for (dsl <- state.BFactoryCommLinkClient) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryCommLinkClient", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryCommLinkClient", dsl.uid)
                          )
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("BFactoryCommLinkClient", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, ^.id := "BFactoryEvaluator",
                  <.label("BFactoryEvaluator : "),
                  <.div(UserProfileViewCSS.Style.sectionButtonsContainer,
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.editAll("BFactoryEvaluator"))("Edit All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.saveAll("BFactoryEvaluator"))("Save All"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.addNewServer("BFactoryEvaluator"))("Add"),
                    <.button(UserProfileViewCSS.Style.sectionButtons)(^.className := "btn btn-default", ^.`type` := "button", ^.onClick --> t.backend.deleteAll("BFactoryEvaluator"))("Delete All")),
                  for (dsl <- state.BFactoryEvaluator) yield {
                    <.div(
                      <.div(UserProfileViewCSS.Style.nodeSettingsFormControlContainer, ^.className := "form-group",
                        <.input(^.`type` := "text", ^.className := "form-control", ^.onChange ==> t.backend.updateTextbox, ^.id := dsl.uid, ^.value := dsl.serverAddress, ^.disabled := !dsl.isEditable),
                        if (dsl.isEditable) {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.check, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryEvaluator", dsl.uid)
                          )
                        } else {
                          <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.pencil, ^.`type` := "button", ^.onClick --> t.backend.enableTextboxToggle("BFactoryEvaluator", dsl.uid)
                          )
                        },
                        <.button(UserProfileViewCSS.Style.nodeSettingsSelectButtons, ^.className := "btn btn-default", Icon.times, ^.`type` := "button", ^.onClick --> t.backend.deleteAddress("BFactoryEvaluator", dsl.uid))
                      )
                    )
                  }
                ),
                <.div(bss.modal.footer,UserProfileViewCSS.Style.nodeSettingFooterMargin)(
                  <.button(^.tpe := "submit", ^.className := "btn ",  SynereoCommanStylesCSS.Style.modalFooterBtn/*,  ^.onClick --> t.backend.submitForm*/ , "Submit"),
                  <.button(^.tpe := "button", ^.className := "btn ", SynereoCommanStylesCSS.Style.modalFooterBtn, ^.onClick --> Callback {
                    t.backend.closeForm
                  }, "Cancel")
                )
              )
            )
          )
        )
      )
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentState.formSubmit) {
        scope.$.backend.closeForm
      }
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
