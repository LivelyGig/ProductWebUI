package livelygig.client.modules

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.{Button, CommonStyle}
import livelygig.client.css.{MessagesCSS, HeaderCSS, ProjectCSS}
import livelygig.client.modals
import livelygig.client.modals._
import scalacss.ScalaCssReact._


object ProjectPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Projects")
    .render_P(ctl => {
      // todo: Need to parameterize.
      // This example is for Talent
      <.div(^.id := "middelNaviContainer", HeaderCSS.Style.middelNaviContainer)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-11 col-sm-11 col-xs-11")(
            <.div(^.className := "btn-group")(
              <.button(HeaderCSS.Style.projectCreateBtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                <.span(^.className := "caret")
              ),
              <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                <.li()(<.a(^.href := "#")("Recommended Matches")),
                <.li()(<.a(^.href := "#")("Direct from Connection")),
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
              // ToDo: create a NewContest form.  Not needed before ~March 2016. Discuss with Ed or Navneet when ready.
              NewRecommendation(NewRecommendation.Props(ctl, "New Contest")),
              NewProject(NewProject.Props(ctl)),
              BiddingScreenModal(BiddingScreenModal.Props(ctl, "View/Edit Contract")),
              UserSkills(UserSkills.Props(ctl)),
              NewRecommendation(NewRecommendation.Props(ctl, "New Recommendation"))
            )
          )
        )
      )
    }
    )
    .build
}