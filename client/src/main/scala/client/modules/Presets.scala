package client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Icon
import client.css.{ DashBoardCSS, MessagesCSS, HeaderCSS, PresetsCSS }
import client.modals._
import scalacss.ScalaCssReact._

object Presets {

  case class Props(view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }
// scalastyle:off
    def render(p: Props) = {
      p.view match {
        case AppModule.PROFILES_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.profilessmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                      <.div(PresetsCSS.Style.modalBtn)(
                        NewProfile(NewProfile.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.user, "Update Profile"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
          )
        } //talent
        case AppModule.PROJECTS_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.jobsmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                      <.div(PresetsCSS.Style.modalBtn)(
                        NewProject(NewProject.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.clipboard, "Create New Job"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
          )
        } //project
        case AppModule.OFFERINGS_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.offeringsmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                      <.div(PresetsCSS.Style.modalBtn)(
                        Offering(Offering.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.briefcase, "Create New Offering"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
          )
        } //project
        case AppModule.CONTRACTS_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.contractssmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                      <.div(PresetsCSS.Style.modalBtn)(
                        WorkContractModal(WorkContractModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.edit, "Create New Contract"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
          )
        }
        case AppModule.MESSAGES_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.messagesmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(PresetsCSS.Style.modalBtn)(
                        NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Create New Message"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
            )
          )
        }
        case AppModule.CONNECTIONS_VIEW => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.connectionsmiddelNaviContainer)(
            <.div( /*^.className := "row"*/ )(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                      <.div(PresetsCSS.Style.modalBtn)(
                        NewConnection(NewConnection.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.connectdevelop, "Create New Connection"))
                      )
                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
          )
        }
      } //main switch
    }
  }

  private val component = ReactComponentB[Props]("Presets")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

