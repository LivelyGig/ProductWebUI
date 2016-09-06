package synereo.client.modalpopups

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.ServerModel
import synereo.client.components.Bootstrap._
import synereo.client.components.{GlobalStyles, _}
import synereo.client.css._
import synereo.client.modalpopupbackends.{NodeSettingModalBackend => Backend}
import scala.language.reflectiveCalls
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 9/1/2016.
  */
//scalastyle:off
object NodeSettingModal {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(DSLCommLinkClient: Seq[ServerModel] = Nil, DSLEvaluator: Seq[ServerModel] = Nil,
                   DSLEvaluatorPreferredSupplier: Seq[ServerModel] = Nil, BFactoryCommLinkServer: Seq[ServerModel] = Nil,
                   BFactoryCommLinkClient: Seq[ServerModel] = Nil, BFactoryEvaluator: Seq[ServerModel] = Nil)

  private val component = ReactComponentB[Props]("NodeSettingModal")
    .initialState_P(p => State())
    .backend(new Backend(_))
    .renderPS((t, props, state) => {
      val headerText = "NodeSettings"
      Modal(
        Modal.Props(
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
          closed = () => t.backend.modalClosed(state, props)
        ),
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
            <.div(^.className := "text-right")(
              <.button(^.tpe := "submit", ^.className := "btn btn-default", DashboardCSS.Style.createConnectionBtn, /* ^.onClick --> hide*/ "Submit"),
              <.button(^.tpe := "button", ^.className := "btn btn-default", NewMessageCSS.Style.newMessageCancelBtn, ^.onClick --> t.backend.closeForm, "Cancel")
            )
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
