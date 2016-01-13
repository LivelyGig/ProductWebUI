package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.DashboardLoc
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.modules.DashboardPresets

import scalacss.ScalaCssReact._

object BiddingScreen {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("BiddingScreen")
    .render_P(ctl => {
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          <.div(^.className := "row")(
            <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(
              <.div()("Stage:")
            ),
            <.div(^.className := "col-md-11 col-sm-11 col-xs-11")(
              <.div()(
                <.a()("Initial"), " > ",
                // ToDo: The active stage should be bold.  Putting asterics here temporarily to indicate Negotiating is current stage
                <.a()("*Negotiating*"), " > ",
                <.a()("Funding"), " > ",
                <.a()("In Progress"), " > ",
                <.a()("Acceptance"), " > ",
                <.a()("Review"), " > ",
                <.a()("Completed")
              )
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(
              <.div()("Project:")
            ),
            <.div(^.className := "col-md-11 col-sm-11 col-xs-11")(
              <.div()(<.a()("25688"), ("  Videographer Needed ..."))
            )
          ), <.div(^.className := "row")(
            <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(
              <.div()("Employer:")
            ),
            <.div(^.className := "col-md-11 col-sm-11 col-xs-11")(
              <.div()(<.a()("Pam"))
            )
          ),
          <.div(^.className := "row")(
            <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(
              <.div()("Talent:")
            ),
            <.div(^.className := "col-md-11 col-sm-11 col-xs-11")(
              <.div()(<.a()("Abed"))
            )
          ),
          <.div(^.id := "rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(
            <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomHeader, BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className := "col-md-5 col-sm-3 col-xs-3", DashBoardCSS.Style.slctHeaders)("Term"),
              <.div(^.className := "col-md-1 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Buyer", <.br, "Agreement"),
              <.div(^.className := "col-md-1 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Seller", <.br, "Agreement"),
              <.div(^.className := "col-md-2 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("History")
            ),
            <.div(BiddingScreenCSS.Style.biddingScreenData)(
              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className := "col-md-5 col-sm-5 col-xs-5")(
                  <.div()("Contract Template"),
                  <.div(^.className := "btn-group")(
                    <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Nolo - service ... -23")(
                      <.span(^.className := "caret")(" ")
                    ),
                    <.ul(^.className := "dropdown-menu")(
                      <.li()("Nolo - service contract updated 2011-01-23"),
                      <.li()("Freelancers Union - artwork contract updated 2014-08-23"),
                      <.li()("Rolondulus LLC - Delivered goods updated 2016-03-15")
                    ),
                    <.a(^.href := "#")("view")
                  )
                ),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")("Original")
              ), //row

              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className := "col-md-5 col-sm-5 col-xs-5")(
                  <.div()("Rate"),
                  <.input(^.className := "form-control", DashBoardCSS.Style.inputHeightWidth, ^.placeholder := "25.30 USD")
                ),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")("Original")
              ), // row
              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                <.div(^.className := "col-md-5 col-sm-5 col-xs-5")(
                  <.div()("Statement of Work"),
                  (<.a()("View")),
                  (" "),
                  (<.a()("Modify")),
                  (" "),
                  ("Updated: 2016-01-12"),
                  (" "),
                  ("SHA256: d14a...2f")


                ),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                  ("Last action: Abed updated 2016-01-12 ..."),
                  <.a(^.href := "#")("view")
                )
              ) // row
            )
          ),
          <.div()(
            <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomFooter, BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className := "col-md-5 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("All Terms"),
              <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")("Last action: Statement of Work: Abed updated")
            )
          ),
          <.div(BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
              // ToDo: need to update these style to something in BiddingScreenCSS.Style?
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Close")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Reject")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Counter")(),
              <.button(HeaderCSS.Style.rsltContainerBtn, ^.className := "btn")("Accept")()
            )
          )
        )
      )

    })
    .componentDidMount(scope => Callback {
    })
    .build
}