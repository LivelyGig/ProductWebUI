package livelygig.client.modules

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.{Button, CommonStyle}
import livelygig.client.css.HeaderCSS
import livelygig.client.modals._
import scalacss.ScalaCssReact._

object ProjectPresets {

  case class Props(ctl: RouterCtl[Loc])
  case class State(showBiddingScreen: Boolean = false , showNewProjectForm: Boolean = false ,
                   showUserSkillsForm: Boolean = false , showNewRecommendationForm: Boolean = false)
  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
      t.modState(s => s.copy(showNewProjectForm = true))
      t.modState(s => s.copy(showUserSkillsForm = true))
      t.modState(s => s.copy(showNewRecommendationForm = true))
    }
    def addBiddingScreenForm(): Callback = {
      t.modState(s => s.copy(showBiddingScreen = true))
    }

    def addProjectForm() : Callback = {
      t.modState(s => s.copy(showNewProjectForm = true))
    }

    def addUserSkillsForm() : Callback = {
      t.modState(s => s.copy(showUserSkillsForm = true))
    }

    def addNewRecommendationForm() : Callback = {
      t.modState(s => s.copy(showNewRecommendationForm = true))
    }

    def addBiddingScreen(postBiddingScreen: Boolean = false): Callback = {
      //      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showBiddingScreen}")
      if (postBiddingScreen) {
        t.modState(s => s.copy(showBiddingScreen = true))
      } else {
        t.modState(s => s.copy(showBiddingScreen = false))
      }
    }

    def addNewProject( postProject: Boolean = false): Callback = {
      // log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewProjectForm}")
      if(postProject){
        t.modState(s => s.copy(showNewProjectForm = true))
      } else {
        t.modState(s => s.copy(showNewProjectForm = false))
      }
    }

    def addUserSkills(postUserSkills: Boolean = false): Callback = {
      //  log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showUserSkillsForm}")
      if(postUserSkills){
        t.modState(s => s.copy(showUserSkillsForm = true))
      } else {
        t.modState(s => s.copy(showUserSkillsForm = false))
      }
    }

    def addNewRecommendation(postNewRecommendation: Boolean = false): Callback = {
      // log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewRecommendationForm}")
      if(postNewRecommendation){
        t.modState(s => s.copy(showNewRecommendationForm = true))
      } else {
        t.modState(s => s.copy(showNewRecommendationForm = false))
      }
    }

  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Projects")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      // todo: Need to parameterize.
      // This example is for Talent
      <.div(^.id:="middelNaviContainer",HeaderCSS.Style.middelNaviContainer)(
        <.div(^.className :="row")(
          <.div(^.className:="col-md-11 col-sm-11 col-xs-11")(
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
          //  <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("New Project")(),
            Button(Button.Props(B.addProjectForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"New Project"),
            Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)), "View/Edit Contract"),
            Button(Button.Props(B.addUserSkillsForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"User Skills"),
            Button(Button.Props(B.addNewRecommendationForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"New Recommendation"),
            if (S.showBiddingScreen) BiddingScreenModalForm(BiddingScreenModalForm.Props(B.addBiddingScreen))
            else if (S.showNewProjectForm) PostAProjectForm(PostAProjectForm.Props(B.addNewProject))
              else if (S.showUserSkillsForm) UserSkillsForm(UserSkillsForm.Props(B.addUserSkills))
              else if (S.showNewRecommendationForm) NewRecommendationForm(NewRecommendationForm.Props(B.addNewRecommendation))
             else
              Seq.empty[ReactElement]
            // NewRecommendation(NewRecommendation.Props(ctl))
          )
        )
      )
    }
    )
    .build
}