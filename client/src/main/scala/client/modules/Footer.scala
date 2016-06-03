package client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.LGMain.{ DashboardLoc, Loc }
import client.components._
import client.css.{ FooterCSS, DashBoardCSS }
import client.modals._
import scalacss.ScalaCssReact._

object Footer {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)
  case class FooterItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  private val footerItems = Seq(
    FooterItem(1, _ => "About", DashboardLoc),
    FooterItem(2, _ => "Legal", DashboardLoc),
    FooterItem(3, _ => "LivelyGig", DashboardLoc)
  )
  private val Footer = ReactComponentB[Props]("Footer")
    .stateless
    .render_P((P) => {
      <.nav(^.className := "footerContainer", FooterCSS.Style.footerContainer)(
        <.div(^.className := "col-lg-1")(),
        <.div(^.className := "col-lg-3 col-md-5 col-sm-5 col-xs-5", FooterCSS.Style.footPaddingLeft)(
          <.div(FooterCSS.Style.footGlyphContainer)(
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://github.com/LivelyGig", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "GitHub")(<.span()(Icon.github))
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://twitter.com/LivelyGig", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Twitter")(
                <.span()(Icon.twitter)
              )
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://www.facebook.com/LivelyGig-835593343168571/", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Facebook")(
                <.span()(Icon.facebook)
              )
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://plus.google.com/+LivelygigCommunity", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Google Plus")(
                <.span()(Icon.googlePlus)
              )
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://www.youtube.com/channel/UCBM73EEC5disDCDnvUXMe4w", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "YouTube Channel")(
                <.span()(Icon.youtube)
              )
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://www.linkedin.com/company/10280853", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "LinkedIn")(
                <.span()(Icon.linkedin)
              )
            ),
            <.div(FooterCSS.Style.displayInline)(
              <.a(FooterCSS.Style.displayInlineGlyph)(^.href := "https://livelygig.slack.com", ^.target := "_blank", "data-toggle".reactAttr := "tooltip", "title".reactAttr := "Slack")(
                <.span()(Icon.slack)
              )
            )
          )
        ),
        <.div(^.className := "col-lg-7 col-md-7 col-sm-7 col-xs-7", DashBoardCSS.Style.paddingRight0px)(
          <.ul(^.className := "nav", FooterCSS.Style.footRight)(
            // build a list of menu items
            for (item <- footerItems) yield {
              <.li(^.className := "pull-left")(^.key := item.idx, (P.currentLoc == item.location) ?= FooterCSS.Style.footerNavLi,
                if (item.idx == 3) {
                  P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", Icon.copyright, item.label(P))
                } else if (item.idx == 2) {
                  Legal(Legal.Props("Legal", Seq(), "", ""))
                } else {
                  P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", item.label(P))
                })
            }
          )
        ),
        <.div(^.className := "col-lg-1")
      )
    })
    .build

  def apply(props: Props) = Footer(props)
}