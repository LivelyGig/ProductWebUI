package synereo.client.modalpopups

import diode.{ModelR, ModelRO}
import synereo.client.components.{GlobalStyles, Icon}
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.components.Bootstrap.Modal
import synereo.client.css.{SignupCSS, SynereoCommanStylesCSS, UserProfileViewCSS}

import scala.scalajs.js
import scalacss.ScalaCssReact._
import scala.language.reflectiveCalls
import synereo.client.utils.ConnectionsUtils
import japgolly.scalajs.react._
import shared.dtos.{ApiResponse, VersionInfoResponse}
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import synereo.client.services.{CoreApi, RootModel, SYNEREOCircuit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//scalastyle:off
object AboutInfoModal {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(submitHandler: () => Callback)

  case class State(versionInfo: VersionInfoResponse, lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class AboutInfoModalBackend(t: BackendScope[Props, State]) {
    def hide = Callback {
      jQuery(t.getDOMNode()).modal("hide")
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def mounted(props: AboutInfoModal.Props) = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
      logger.log.debug(s"AboutInfoModal mounted")
      CoreApi.getVersionInfo().onComplete {
        case Success(res) =>
          try {
            val versionInfoResponse = upickle.default.read[ApiResponse[VersionInfoResponse]](res).content
            t.modState(state => state.copy(versionInfo = versionInfoResponse)).runNow()
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
          header = hide => <.span(
            <.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close),
            <.div(^.className:="model-title",SynereoCommanStylesCSS.Style.modalHeaderTitleCenter)(headerText)
          ),
          closed = () => t.backend.formClosed(state, props)
        ),
        <.div(^.className := "container-fluid")(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-12 col-sm-12 col-xs-12",UserProfileViewCSS.Style.aboutInfoSectionTitle)(s"Current User:")
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6")(s"Username: "),
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6") (s" n/a")
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6")(s"${state.lang.selectDynamic("AGENT_UID").toString}"),
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6",UserProfileViewCSS.Style.nodeSettingSectionAgentUID)(s" ${agentUID.head}")
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6")(s"${state.lang.selectDynamic("WALLET_ADDRESS").toString}"),
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6")(s"$ampAddress")
              ),
              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6",UserProfileViewCSS.Style.aboutInfoSectionTitle)("Node Operator:"),
                <.span(^.className:="col-md-6 col-sm-6 col-xs-6")(s"n/a")
              ),

              <.div(^.className := "row", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                <.span(^.className:="col-md-12 col-sm-12 col-xs-12",UserProfileViewCSS.Style.aboutInfoSectionTitle)(s"${state.lang.selectDynamic("BUILD_INFORMATION").toString}")
              ),


              <.div( UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer)(
                  <.div(^.className:="row",
                    <.span(^.className := "col-md-6 col-sm-6 col-xs-6", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer,s"Web User Interface version:"),
                       <.span(^.className:="col-md-6 col-sm-6 col-xs-6", s"n/a")),
                <.div(^.className:="row",
                  <.span(^.className := "col-md-6 col-sm-6 col-xs-6", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer,s"${state.lang.selectDynamic("GLOSEVAL_VERSION").toString}"),
                  <.span(^.className:="col-md-6 col-sm-6 col-xs-6", s"${versionInfo.glosevalVersion}")),
                <.div(^.className:="row",
                  <.span(^.className := "col-md-6 col-sm-6 col-xs-6", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer,s"${state.lang.selectDynamic("SCALA_VERSION").toString}"),
                  <.span(^.className:="col-md-6 col-sm-6 col-xs-6", s"${versionInfo.scalaVersion}")),
                <.div(^.className:="row",
                  <.span(^.className := "col-md-6 col-sm-6 col-xs-6", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer,s"${state.lang.selectDynamic("MONGODB_VERSION").toString}"),
                  <.span(^.className:="col-md-6 col-sm-6 col-xs-6",s"${versionInfo.mongoDBVersion}")),
                <.div(^.className:="row",
                  <.span(^.className := "col-md-6 col-sm-6 col-xs-6", UserProfileViewCSS.Style.nodeSettingSection, UserProfileViewCSS.Style.aboutInfoSectionContainer,s"${state.lang.selectDynamic("RABBITMQ_VERSION").toString}"),
                  <.span(^.className:="col-md-6 col-sm-6 col-xs-6",s"${versionInfo.rabbitMQVersion}"))
              ),
              <.div(bss.modal.footer,UserProfileViewCSS.Style.aboutInfoFooterMargin)(
                <.button(^.tpe := "button",  SynereoCommanStylesCSS.Style.modalFooterBtn,^.className := "btn", ^.onClick --> t.backend.hide, "OK")
              )
            )
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
