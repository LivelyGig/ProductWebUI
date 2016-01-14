package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.components.Icon
import livelygig.client.css._

import scalacss.ScalaCssReact._

object BiddingScreenResults {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("BiddingScreen")
    .render_P(ctl =>
      // todo: Need to parameterize.  This example is for Talent
      <.div(^.id:="rsltScrollContainer", DashBoardCSS.Style.rsltContainer)(


        <.div(^.className:="container-fluid", ^.id:="resultsContainer" )(
//           <.div(^.className:="col-md-offset-5 col-sm-offset-5 col-xs-offset-5")(
//            <.div(BiddingScreenCSS.Style.biddingheader)("Agreement")
//
//          ),//row

        <.div()(

          <.div(^.className:="row" , BiddingScreenCSS.Style.borderBottomHeader, BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5",DashBoardCSS.Style.slctHeaders)("Term"),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1",DashBoardCSS.Style.slctHeaders)("Buyer Agreement"),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1",DashBoardCSS.Style.slctHeaders)("Seller Agreement"),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5",DashBoardCSS.Style.slctHeaders)("History")
          ),

          <.div(BiddingScreenCSS.Style.biddingScreenData)(

            <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
              <.div(/*DashBoardCSS.Style.slctHeaders*/)("Contract Template"),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12",BiddingScreenCSS.Style.slctBiddingInputWidth)(


                    <.div(^.className:="btn-group")(
                  <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Nolo Service..-23")(
                    <.span(^.className:="caret")
                  ),
                  <.ul(^.className:="dropdown-menu")(
                    <.li()(<.a(^.href:="#")("Item 1")),
                    <.li()(<.a(^.href:="#")("Item 2")),
                    <.li()(<.a(^.href:="#")("Item 3"))
                  )
                )
                ),
                <.div(BiddingScreenCSS.Style.slctBiddingInputLeftContainerMargin , DashBoardCSS.Style.marginTop10px)(
                  //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                  <.div()(<.a("view"))
                )
              )
              ),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
              <.div(/*DashBoardCSS.Style.slctHeaders*/)("Rate"),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                  <.div()( <.input(^.className:="form-control",^.placeholder:="25.30 USD"))
                )
              )
            ),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Original")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(


            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
              <.div()("Statement of work"),
              <.div(^.className:="row")(
                <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                  <.div()(
                    <.a()("View Modify "),
                    "Updated: 2016-01-12 SHA256:d14a sf"
                  )
                )
              )
            ),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Last action: Abed updated 2016-01-12")
          ),
            <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(

              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Contract Template"),
                <.div(^.className:="row")(
                  <.div(^.className:="col-md-12 col-sm-12 col-xs-12",ProjectCSS.Style.slctProjectInputWidth)(
                    <.div(DashBoardCSS.Style.marginTop20px)("SOW")
                  ),
                  <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin , DashBoardCSS.Style.marginTop10px)(
                    //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                    <.div(^.className:="btn-group")(
                      <.button(ProjectCSS.Style.projectdropdownbtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select One")(
                        <.span(^.className:="caret")
                      ),
                      <.ul(^.className:="dropdown-menu")(
                        <.li()(<.a(^.href:="#")("Item 1")),
                        <.li()(<.a(^.href:="#")("Item 2")),
                        <.li()(<.a(^.href:="#")("Item 3"))
                      )
                    )
                  )
                )
              ),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Original")
            ),

            <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
                <.div(/*DashBoardCSS.Style.slctHeaders*/)("Rate"),
                <.div(^.className:="row")(
                  <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                    <.div()( <.input(^.className:="form-control",^.placeholder:="25.30 USD"))
                  )
                )
              ),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Original")
            ),
            <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(


              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(
                <.div()("Statement of work"),
                <.div(^.className:="row")(
                  <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
                    <.div()(
                      <.a()("View Modify "),
                      "Updated: 2016-01-12 SHA256:d14a sf"
                    )
                  )
                )
              ),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Last action: Abed updated 2016-01-12")
            )
          ),

           <.div()(
          <.div(^.className:="row" ,BiddingScreenCSS.Style.borderBottomFooter,BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5",DashBoardCSS.Style.slctHeaders)("All Terms"),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-1 col-sm-1 col-xs-1")(<.input(^.`type`:="checkbox",DashBoardCSS.Style.rsltCheckboxStyle)),
            <.div(^.className:="col-md-5 col-sm-5 col-xs-5")("Last action:Statement of work: Abed updated..")
          )
        )


        ),//container
          <.div(BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(),
              <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(),
              <.div(^.className:="col-md-5 col-sm-5 col-xs-5")(

//                <.div(FooterCSS.Style.footGlyphContainer)(
//                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
//                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
//                      <.span()("  Accept"))),
//                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
//                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
//                      <.span(BiddingScreenCSS.Style.footBorder)(" Reject"))),
//                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
//                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
//                      <.span(BiddingScreenCSS.Style.footBorder)(" Counter"))),
//                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
//                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
//                      <.span(BiddingScreenCSS.Style.footBorder)(" Closed")))
//                )
                <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("Accept")(),
                <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("Counter")(),
                <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("Reject")(),
                <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("Close")()
              )
           )
          )

        )//gigConversation
      )




    )
    .componentDidMount(scope => Callback {

    })
    .build
}
