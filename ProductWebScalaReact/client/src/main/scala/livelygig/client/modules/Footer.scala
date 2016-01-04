package livelygig.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.{DashboardLoc, Loc}
import livelygig.client.components._
import livelygig.client.css.FooterCSS

import scalacss.ScalaCssReact._
/**
  * Created by bhagyashree.b on 11/30/2015.
  */
object Footer {
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)
  case class FooterItem(idx: Int, label: (Props) => ReactNode, location: Loc)
  private val footerItems = Seq(
    FooterItem(1, _ => "About", DashboardLoc),
    FooterItem(2, _ => "Privacy", DashboardLoc),
    FooterItem(3, _ => "Terms of Use", DashboardLoc ),
    FooterItem(4, _ => "Trademarks", DashboardLoc ),
    FooterItem(5, _ => "LivelyGig", DashboardLoc)
  )
  private val Footer = ReactComponentB[Props]("Footer")
    .stateless
    .render_P((P) => {
      <.ul(bss.navbar,FooterCSS.Style.footRight,^.id:="footMenu")(
        // build a list of menu items
        for (item <- footerItems) yield {
          <.li(^.key := item.idx, (P.currentLoc == item.location) ?= FooterCSS.Style.footerNavLi,
            if(item.idx == 5 ) {
              P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", Icon.copyright, item.label(P))
            }else {
              P.ctl.link(item.location)(FooterCSS.Style.footerNavA, " ", item.label(P))
            }
          )
        }
      )
    })
    .build

  def apply(props: Props) = Footer(props)
}