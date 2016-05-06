package client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Icon
import client.css.{DashBoardCSS, MessagesCSS, HeaderCSS, PresetsCSS}
import client.modals._
import scalacss.ScalaCssReact._

object Presets {

  case class Props(view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    def render(p: Props) = {
      //      <.div(^.id := "middelNaviContainer", HeaderCSS.Style.middelNaviContainer)(
      //        <.div(/*^.className := "row"*/)(
      //          <.div(^.className := "col-lg-1")(),
      //          <.div(^.className := "col-md-12 col-lg-10")(
      //            <.div(^.className := "row")(
      //              <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(

      p.view match {
        case "talent" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.profilessmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#talent")("Recommended Matches")),
                          <.li()(<.a(^.href := "#talent")("My Profiles")),
                          <.li()(<.a(^.href := "#talent")("Favorited")),
                          <.li()(<.a(^.href := "#talent")("Available")),
                          <.li()(<.a(^.href := "#talent")("Active Unavailable")),
                          <.li()(<.a(^.href := "#talent")("Inactive")),
                          <.li()(<.a(^.href := "#talent")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#talent")("Videographers w/5+ yrs experience")),
                          <.li()(<.a(^.href := "#talent")("Customize..."))
                        )
                      ),
                      <.div(PresetsCSS.Style.modalBtn)(
                        UserSkills(UserSkills.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.user, "Update Profile"))
                      )

                    )
                  ),
                  <.div(^.className := "col-md-9 col-sm-9", DashBoardCSS.Style.paddingLeft0px)()
                )
              ),
              <.div(^.className := "col-lg-1")()
            )
          )
        } //talent
        case "projects" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.jobsmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#projects")("Recommended Matches")),
                          <.li()(<.a(^.href := "#projects")("Direct from Connection")),
                          <.li()(<.a(^.href := "#projects")("My Posted Jobs")),
                          <.li()(<.a(^.href := "#projects")("Favorited")),
                          <.li()(<.a(^.href := "#projects")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#projects")("Customize..."))
                        )
                      ),
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
          )
        } //project
        case "offerings" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.offeringsmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#offerings")("Recommended to Me")),
                          <.li()(<.a(^.href := "#offerings")("My Posted Offerings")),
                          <.li()(<.a(^.href := "#offerings")("Favorited")),
                          <.li()(<.a(^.href := "#offerings")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#offerings")("Customize..."))
                        )
                      ),
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
          )
        } //project
        case "contract" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.contractssmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Active ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#contract")("Active")),
                          <.li()(<.a(^.href := "#contract")("Favorited")),
                          <.li()(<.a(^.href := "#contract")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#contract")("Customize..."))
                        )
                      ),
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
          )
        }
        case "messages" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.messagesmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Unread ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#messages")("Inbox")),
                          <.li()(<.a(^.href := "#messages")("Sent")),
                          <.li()(<.a(^.href := "#messages")("Unread")),
                          <.li()(<.a(^.href := "#messages")("Favorited")),
                          <.li()(<.a(^.href := "#messages")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#messages")("Customize..."))
                        )
                      ),
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
        case "connections" => {
          <.div(^.id := "middelNaviContainer", HeaderCSS.Style.connectionsmiddelNaviContainer)(
            <.div(/*^.className := "row"*/)(
              <.div(^.className := "col-lg-1")(),
              <.div(^.className := "col-md-12 col-lg-10")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-3 col-sm-3", DashBoardCSS.Style.paddingLeft0px)(
                    <.div()(
                      <.div(^.className := "btn-group")(
                        <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Favorited ")(
                          <.span(^.className := "caret")
                        ),
                        <.ul(/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                          <.li()(<.a(^.href := "#connections")("All")),
                          <.li()(<.a(^.href := "#connections")("Available for Chat")),
                          <.li()(<.a(^.href := "#connections")("Favorited")),
                          <.li()(<.a(^.href := "#connections")("Hidden")),
                          <.li(^.className := "divider")(),
                          <.li()(<.a(^.href := "#connections")("Customize..."))
                        )
                      ),
                      <.div(PresetsCSS.Style.modalBtn)(
                        NewConnectionModal(NewConnectionModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.connectdevelop, "Create New Connection"))
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
      } //main switch


      //              ),
      //              <.div(^.className := "col-md-9 col-sm-9",DashBoardCSS.Style.paddingLeft0px)()
      //            )
      //          ),
      //          <.div(^.className := "col-lg-1")()
      //        )
      //      )


    }
  }

  private val component = ReactComponentB[Props]("Presets")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


