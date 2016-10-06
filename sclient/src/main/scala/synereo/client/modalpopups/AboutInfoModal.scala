package synereo.client.modalpopups

import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS, UserProfileViewCSS}
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.utils.ConnectionsUtils
import japgolly.scalajs.react._
import shared.dtos.{ApiResponse, VersionInfoResponse}
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//scalastyle:off
object AboutInfoModal {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(versionInfo: VersionInfoResponse)

  class AboutInfoModalBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: AboutInfoModal.Props) = Callback {
      logger.log.debug(s"AboutInfoModal mounted")
      CoreApi.getVersionInfo().onComplete {
        case Success(res) =>
          try {
            val versionInfoFromAjax = upickle.default.read[ApiResponse[VersionInfoResponse]](res).content
            t.modState(state => state.copy(versionInfo = versionInfoFromAjax)).runNow()
          } catch {
            case e: Exception => logger.log.error("Exception in read VersionInfoResponse")
          }
        case Failure(res) =>
          logger.log.debug(s"getVersionInfo Failed: $res")
      }
    }

    def formClosed(state: AboutInfoModal.State, props: AboutInfoModal.Props): Callback = {
      props.submitHandler()
    }
  }


  private val component = ReactComponentB[Props]("AboutInfoModal")
    .initialState_P(p => State(new VersionInfoResponse()))
    .backend(new AboutInfoModalBackend(_))
    .renderPS((t, props, state) => {
      val agentUID = ConnectionsUtils.getSelfConnnection().source.substring(8).split("\"")
      val headerText = "About Synereo Social -- Alpha release"
      val versionInfo = state.versionInfo
      val ampAddress = SYNEREOCircuit.zoom(_.user.address).value
      Modal(
        Modal.Props(
          // header contains a cancel button (X)
          header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.span(^.fontWeight.bold)(s"Current User:"),
              //              <.div(^.className := "row", ^.marginLeft := "30px")(
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.fontWeight.bold)(s"Username: "),
                s" n/a"
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.fontWeight.bold)(s"Agent UID: "),
                s" ${agentUID.head}"
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.fontWeight.bold)(s"Wallet Address: "),
                s" $ampAddress"
              ),
              //              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.fontWeight.bold)("Node Operator:"),
                <.div(^.className := "row", ^.marginLeft := "30px")(
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"n/a")
                )
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.fontWeight.bold)("Build Information:"),
                <.div(^.className := "row", ^.marginLeft := "30px")(
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"Web User Interface version: n/a"),
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"GLoSEval version: ${versionInfo.glosevalVersion}"),
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"Scala version: ${versionInfo.scalaVersion}"),
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"MongoDB version: ${versionInfo.mongoDBVersion}"),
                  <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(s"RabbitMQ version: ${versionInfo.rabbitMQVersion} ")
                )
              ),
              <.div(^.className := "row pull-right")(
                <.button(^.tpe := "button", SignupCSS.Style.signUpBtn, ^.width := "110.px", ^.className := "btn", ^.onClick --> t.backend.hide, "OK")
              ),
              <.div(bss.modal.footer)()
            )
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
