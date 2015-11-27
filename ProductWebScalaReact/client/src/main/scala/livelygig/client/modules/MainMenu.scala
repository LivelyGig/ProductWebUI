package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import livelygig.client.LGMain.{TodoLoc, DashboardLoc, Loc}

import scalacss.ScalaCssReact._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.vdom.prefix_<^._
import rx._
import rx.ops._
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.components.Icon._
import livelygig.client.components._
import livelygig.client.services._
import livelygig.client.css.HeaderCSS

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)

  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)
  private val menuItems = Seq(
    MenuItem(1, _ => "Messages", DashboardLoc),
    MenuItem(2, _ => "Projects", DashboardLoc),
    MenuItem(3, _ => "Talent", DashboardLoc ),
    MenuItem(4, _ => "Offerings", DashboardLoc ),
    MenuItem(5, _ => "Contracts", DashboardLoc ),
    MenuItem(6, _ => "Connections", DashboardLoc ),
    MenuItem(7, _ => "Wallets", DashboardLoc )
  )

  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .stateless
    .render_P((P) => {
    <.ul(bss.navbar, ^.id := "headerNavUl")(
      // build a list of menu items
      for (item <- menuItems) yield {
        <.li(^.key := item.idx, (P.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
          P.ctl.link(item.location)(HeaderCSS.Style.headerNavA ," ", item.label(P))
        )
      },
      <.div(bss.navbarRight)(<.ul(bss.navbar)(<.li(P.ctl.link(DashboardLoc)(<.span(Icon.bell)))))
    )
  })
    .build

  def apply(props: Props) = MainMenu(props)
}
