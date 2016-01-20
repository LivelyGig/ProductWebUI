package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.components.Icon
import livelygig.client.css.FooterCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css._
import livelygig.client.modals.{UserSkills, NewRecommendation, NewProject, BiddingScreenModal}

import scalacss.ScalaCssReact._

object ProjectPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Projects")
    .render_P(ctl =>
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
            NewProject(NewProject.Props(ctl)),
            BiddingScreenModal(BiddingScreenModal.Props(ctl)),
            UserSkills(UserSkills.Props(ctl)),
            NewRecommendation(NewRecommendation.Props(ctl))
          )
        )
      )

    )
    .componentDidMount(scope => Callback {
    })
    .build
}