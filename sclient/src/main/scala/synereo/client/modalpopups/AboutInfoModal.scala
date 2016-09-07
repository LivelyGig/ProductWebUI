package synereo.client.modalpopups

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos.VersionInfoResponse
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS, UserProfileViewCSS}

import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.modalpopupbackends.{AboutInfoModalBackend => Backend}
import synereo.client.utils.ConnectionsUtils

object AboutInfoModal {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(versionInfo: VersionInfoResponse)


  private val component = ReactComponentB[Props]("AboutInfoModal")
    .initialState_P(p => State(new VersionInfoResponse()))
    .backend(new Backend(_))
    .renderPS((t, props, state) => {
      val agentUID = ConnectionsUtils.getSelfConnnection().source.substring(8).split("\"")
      val headerText = "About"
      val versionInfo = state.versionInfo
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"Agent UID: ${agentUID.head}"),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(""),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)("Build Information"),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"- Gloseval Version: ${versionInfo.glosevalVersion}"),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"- Scala Version: ${versionInfo.scalaVersion}"),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"- MongoDB Version: ${versionInfo.mongoDBVersion}"),
            <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"- RabbitMQ Version: ${versionInfo.rabbitMQVersion} "),
            <.div(^.className := "row pull-right")(
              <.button(^.tpe := "button", SignupCSS.Style.SignUpBtn, ^.width := "110.px", ^.className := "btn", ^.onClick --> t.backend.hide, "Ok")
            ),
            <.div(bss.modal.footer)()
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
