package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css.{MessagesCSS, HeaderCSS}
import livelygig.client.modals._
import scalacss.ScalaCssReact._

object Presets {

  case class Props(view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    def render(p: Props) = {
      <.div(^.id := "middelNaviContainer", HeaderCSS.Style.middelNaviContainer)(
        <.div(/*^.className := "row"*/)(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "col-sm-3", ^.paddingLeft := "0px")(
            p.view match {
              case "talent" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer )(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
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
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    UserSkills(UserSkills.Props())
                    // ToDo:  above should be updated to something like the following:
                      // NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.envelope,"New Message" ))
                  )

                )
              } //talent
              case "projects" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer)(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#projects")("Recommended Matches")),
                      <.li()(<.a(^.href := "#projects")("Direct from Connection")),
                      <.li()(<.a(^.href := "#projects")("My Posted Jobs")),
                      <.li()(<.a(^.href := "#projects")("Favorited")),
                      <.li()(<.a(^.href := "#projects")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#projects")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    NewProject(NewProject.Props())
                    // ToDo:  above should be updated to something like the following:
                    // NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.envelope,"New Message" ))
                  )
                )
              } //project
              case "offerings" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer)(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#offerings")("Recommended to Me")),
                      <.li()(<.a(^.href := "#offerings")("My Posted Offerings")),
                      <.li()(<.a(^.href := "#offerings")("Favorited")),
                      <.li()(<.a(^.href := "#offerings")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#offerings")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    // BiddingScreenModal(BiddingScreenModal.Props("New Offering"))
                    Offering(Offering.Props("New Offering"))
                    // ToDo:  above should be updated to something like the following:
                    // NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.envelope,"New Message" ))
                  )
                )
              } //project
              case "contract" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer)(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Active ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#contract")("Active")),
                      <.li()(<.a(^.href := "#contract")("Favorited")),
                      <.li()(<.a(^.href := "#contract")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#contract")("Customize..."))
                    )
                  ),

                    BiddingScreenModal(BiddingScreenModal.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.rocket,"New Contract"))


                )
              }
              case "messages" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer)(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Unread ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#messages")("Inbox")),
                      <.li()(<.a(^.href := "#messages")("Sent")),
                      <.li()(<.a(^.href := "#messages")("Unread")),
                      <.li()(<.a(^.href := "#messages")("Favorited")),
                      <.li()(<.a(^.href := "#messages")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#messages")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    // NewMessage(NewMessage.Props("New Message", Seq(HeaderCSS.Style.createNewProjectBtn),"","Messages" )),
                    NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.envelope,"New Message" ))
                  )
                )
              }
              case "connections" => {
                <.div()(
                  <.div(^.className := "btn-group", HeaderCSS.Style.presetPickContainer)(
                    <.button(HeaderCSS.Style.presetPickBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Favorited ")(
                      <.span(^.className := "caret")
                    ),
                    <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                      <.li()(<.a(^.href := "#connections")("All")),
                      <.li()(<.a(^.href := "#connections")("Available for Chat")),
                      <.li()(<.a(^.href := "#connections")("Favorited")),
                      <.li()(<.a(^.href := "#connections")("Hidden")),
                      <.li(^.className := "divider")(),
                      <.li()(<.a(^.href := "#connections")("Customize..."))
                    )
                  ),
                  <.div(MessagesCSS.Style.newProjectbtn)(
                    // ToDo: need design and implementaiton for New Connection button.
                    NewMessage(NewMessage.Props("New Connection",  Seq(HeaderCSS.Style.createNewProjectBtn),"","Messages"))
                  )
                )
              }
            } //main switch
          ),
          <.div(^.className := "col-sm-7", ^.paddingLeft := "0px")(),
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


