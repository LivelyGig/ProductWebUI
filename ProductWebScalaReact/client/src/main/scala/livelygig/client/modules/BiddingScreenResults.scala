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
        <.div(DashBoardCSS.Style.gigActionsContainer , ^.className:="row")(
          <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(
            <.input(^.`type` := "checkbox",DashBoardCSS.Style.rsltCheckboxStyle),
            //                      <.span(DashBoardCSS.Style.MarginLeftchkproduct, ^.className:="checkbox-lbl"),
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Select Bulk Action ")(
                <.span(^.className:="caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("Hide")),
                <.li()(<.a(^.href:="#")("Favorite")),
                <.li()(<.a(^.href:="#")("Unhide")),
                <.li()(<.a(^.href:="#")("Unfavorite"))
              )
            )//dropdown class
          ),
          <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(
            <.div (DashBoardCSS.Style.rsltCountHolderDiv)("2,352 Results")
          ),
          <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("By Date ")(
                <.span(^.className:="caret", DashBoardCSS.Style.rsltCaretStyle)
              ),
              <.ul(^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("By Date")),
                <.li()(<.a(^.href:="#")("By Experience")),
                <.li()(<.a(^.href:="#")("By Reputation")),
                <.li()(<.a(^.href:="#")("By Rate")),
                <.li()(<.a(^.href:="#")("By Projects Completed"))
              )
            ),
            <.div (DashBoardCSS.Style.rsltGigActionsDropdown, ^.className:="dropdown")(
              <.button(DashBoardCSS.Style.gigMatchButton, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Newest ")(
                <.span(Icon.longArrowDown))
            )


          ),
          <.div(/*DashBoardCSS.Style.listIconPadding ,*/ ^.className:="col-md-3 col-sm-3 col-xs-3")(
            <.div(^.className:="pull-right" )(
              // todo: icon buttons should be different.  Earlier mockup on s3 had <span class="icon-List1">  2  3  ?
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Summery")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Brief")(<.span(Icon.list)),
              <.button(DashBoardCSS.Style.btn,"data-toggle".reactAttr := "tooltip" , "title".reactAttr := "View Full Posts")(<.span(Icon.list))
            )
          )
        ),//col-12
        <.div(^.className:="container-fluid", ^.id:="resultsContainer" )(
           <.div(^.className:="col-md-offset-6 col-sm-offset-6 col-xs-offset-6")(
            <.div(BiddingScreenCSS.Style.biddingheader)("Agreement")

          ),//row

        <.div(BiddingScreenCSS.Style.biddingScreenData)(

          <.div(^.className:="row" , BiddingScreenCSS.Style.borderBottomHeader, BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")("Buyer"),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")("Seller"),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("History")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          ),
          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Original")
          ),

          <.div(^.className:="row",BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
            <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Abed updated 2016-01-07")
          )

        ),//container


          <.div()(
            <.div(^.className:="row" ,BiddingScreenCSS.Style.borderBottomFooter,BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
              <.div(^.className:="col-md-2 col-sm-2 col-xs-2")(<.input(^.`type`:="checkbox")),
              <.div(^.className:="col-md-8 col-sm-8 col-xs-8")("Last action: Rate: Abed updated..")
            )
          ),

          <.div(BiddingScreenCSS.Style.marginLeftRight)(
            <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
              <.div(^.className:="col-md-3 col-sm-3 col-xs-3")(),
              <.div(^.className:="col-md-5 col-sm-5 col-xs-3")(),
              <.div(^.className:="col-md-4 col-sm-4 col-xs-4")(

                <.div(FooterCSS.Style.footGlyphContainer)(
                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
                      <.span()("  Accept"))),
                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
                      <.span(BiddingScreenCSS.Style.footBorder)(" Reject"))),
                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
                      <.span(BiddingScreenCSS.Style.footBorder)(" Counter"))),
                  <.div(BiddingScreenCSS.Style.footDisplayInline)(
                    <.a(BiddingScreenCSS.Style.displayInlineText)( )(
                      <.span(BiddingScreenCSS.Style.footBorder)(" Closed")))
                )

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
