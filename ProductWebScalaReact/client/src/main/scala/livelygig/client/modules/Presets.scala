package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.css.{MessagesCSS, HeaderCSS, DashBoardCSS}
import livelygig.client.modals._
import scalacss.ScalaCssReact._

object Presets {
  case class Props(ctl: RouterCtl[Loc], view :String)
  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }
    def render( p: Props) = {
          <.div(^.id:="middelNaviContainer",HeaderCSS.Style.middelNaviContainer)(
            <.div(^.className :="row")(
              <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                <.div(^.className:="btn-group")(
                  <.button(HeaderCSS.Style.projectCreateBtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                    <.span(^.className:="caret")
                  ),
                  <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className:="dropdown-menu")(
                    <.li()(<.a(^.href:="#")("Recommended Matches")),
                    <.li()(<.a(^.href:="#")("Favorited")),
                    <.li()(<.a(^.href:="#")("Available")),
                    <.li()(<.a(^.href:="#")("Active Unavailable")),
                    <.li()(<.a(^.href:="#")("Inactive")),
                    <.li()(<.a(^.href:="#")("Hidden")),
                    <.li(^.className:="divider")(),
                    <.li()(<.a(^.href:="#")("Videographers w/5+ yrs experience")),
                    <.li()(<.a(^.href:="#")("Customize..."))
                  )
                ),

                p.view match {
                  // case "talent" => TalentPresets.component(p.ctl)
                  case "talent" => {
                    <.button(HeaderCSS.Style.createNewProjectBtn, ^.className := "btn")("New Profile")()
                    //Invoice(Invoice.Props(ctl)),
                    <.div(MessagesCSS.Style.newProjectbtn)(
                    //  UserPreferences(UserPreferences.Props(p.ctl))
                      // PrivacyPolicyModal(PrivacyPolicyModal.Props(ctl))
                    )
                  } //talent
                  case "projects" => {
                    <.div(MessagesCSS.Style.newProjectbtn)(
                      // ToDo: create a NewContest form.  Not needed before ~March 2016. Discuss with Ed or Navneet when ready.
                      NewRecommendation(NewRecommendation.Props(p.ctl, "New Contest")),
                      NewProject(NewProject.Props(p.ctl)),
                      BiddingScreenModal(BiddingScreenModal.Props(p.ctl,"View/Edit Contract")),
                      UserSkills(UserSkills.Props(p.ctl)),
                      NewRecommendation(NewRecommendation.Props(p.ctl,"New Recommendation"))
                    )
                  } //project

                  case "contract" =>  {
                    <.div(MessagesCSS.Style.newProjectbtn)(
                      NewProject(NewProject.Props(p.ctl))
                    )
                  }
                  case "messages" => /*MessagesPresets.component(p.ctl)*/ {
                    <.div(MessagesCSS.Style.newProjectbtn)(
                      NewMessage(NewMessage.Props(p.ctl,"New Messages")))
                  }
                }//main switch
              )
            )
          )
    }
  }
  private val component = ReactComponentB[Props]("Presets")
    .initialState_P(p =>())
    .renderBackend[Backend]
    .build
  def apply(props: Props) = component(props)
}


