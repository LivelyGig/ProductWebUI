package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.css.{MessagesCSS, HeaderCSS, DashBoardCSS}
import livelygig.client.modals._
import scalacss.ScalaCssReact._

object Presets {

  case class Props(ctl: RouterCtl[Loc], view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    def render(p: Props) = {
      <.div(^.id := "middelNaviContainer", HeaderCSS.Style.middelNaviContainer)(
        <.div(/*^.className := "row"*/)(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "col-lg-10 col-sm-12 col-xs-12")(
            p.view match {
              case "talent" => {
                <.div()(
                  <.div(^.className := "btn-group")(
                    <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#")("Recommended Matches")),
                      <.li()(<.a(^.href := "#")("Favorited")),
                      <.li()(<.a(^.href := "#")("Available")),
                      <.li()(<.a(^.href := "#")("Active Unavailable")),
                      <.li()(<.a(^.href := "#")("Inactive")),
                      <.li()(<.a(^.href := "#")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#")("Videographers w/5+ yrs experience")),
                      <.li()(<.a(^.href := "#")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    UserSkills(UserSkills.Props(p.ctl))
                  )
                )
              } //talent
              case "projects" => {
                <.div()(
                  <.div(^.className := "btn-group")(
                    <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#")("Recommended Matches")),
                      <.li()(<.a(^.href := "#")("Direct from Connection")),
                      <.li()(<.a(^.href := "#")("Favorited")),
                      <.li()(<.a(^.href := "#")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    NewProject(NewProject.Props(p.ctl))
                  )
                )
            } //project
              case "offerings" => {
                <.div()(
                  <.div(^.className := "btn-group")(
                    <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#")("Recommended to Me")),
                      <.li()(<.a(^.href := "#")("Favorited")),
                      <.li()(<.a(^.href := "#")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    BiddingScreenModal(BiddingScreenModal.Props(p.ctl, "New Offering"))
                  )
                )
              } //project
              case "contract" => {
                <.div()(
                  <.div(^.className := "btn-group")(
                    <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Active ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#")("Active")),
                      <.li()(<.a(^.href := "#")("Favorited")),
                      <.li()(<.a(^.href := "#")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    BiddingScreenModal(BiddingScreenModal.Props(p.ctl, "New Contract")),
                    Accept(Accept.Props(p.ctl, "Accept")),
                    <.button(HeaderCSS.Style.showDisputeBtn, ^.className := "btn")("Show Dispute")()

                  )
                )
              }
              case "messages" => {
                <.div()(
                  <.div(^.className := "btn-group")(
                    <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Unread ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#")("Inbox")),
                      <.li()(<.a(^.href := "#")("Unread")),
                      <.li()(<.a(^.href := "#")("Favorited")),
                      <.li()(<.a(^.href := "#")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    NewMessage(NewMessage.Props(p.ctl, "New Message"))
                  )
                )
              }
            } //main switch
          ),
          <.div(^.className := "col-lg-1")()
        )
      )

    }
  }

  private val component = ReactComponentB[Props]("Presets")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}


