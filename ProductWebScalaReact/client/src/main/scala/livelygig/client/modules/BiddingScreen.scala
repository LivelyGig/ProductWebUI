package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.DashboardLoc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.DashBoardCSS
import livelygig.client.css.HeaderCSS
import livelygig.client.css.LftcontainerCSS
import livelygig.client.css.{HeaderCSS, DashBoardCSS, BiddingScreenCSS}
import livelygig.client.modules.DashboardPresets

import scalacss.ScalaCssReact._

object BiddingScreen {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("BiddingScreen")
    .render_P(ctl => {
          // the contents will vary depending on EntityType, e.g. Messages, Projects, Talent...
      "talent" match {
        case "talent" =>
          <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
            <.div()(
              BiddingScreenPresets.component(ctl)
            ),
            // AddNewAgent(AddNewAgent.Props(ctl)),
            <.div(DashBoardCSS.Style.splitContainer)(
              <.div(^.className := "split")(
                <.div(^.className := "row")(
//                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
//                    // todo: Need to parameterize the Search area depending on EntityType (e.g. Talent, Project) and preset
//                    BiddingScreenSearch.component(ctl)
//                  ),
//                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10", ^.id := "dashboardResults2", BiddingScreenCSS.Style.BiddingScreenResults)(
//                    // todo: Results will be parameterized depending on EntityType, preset
//                    BiddingScreenResults.component(ctl)
//                  )

                  <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                    BiddingScreenResults.component(ctl)
                  )

                )
              )
            )//row
          ) //mainContainer
      }
    })
    .componentDidMount(scope => Callback {

    })
    .build
}