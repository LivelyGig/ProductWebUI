package livelygig.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain
import livelygig.client.LGMain._
import livelygig.client.components._
import livelygig.client.css.HeaderCSS
import livelygig.client.modules.AddNewAgent

import scalacss.ScalaCssReact._
object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc)
  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)
  private val menuItems = Seq(
    MenuItem(1, _ => "Messages",MessagesLoc),
    MenuItem(2, _ => "Projects", ProjectsLoc),
    MenuItem(3, _ => "Talent", TalentLoc),
   /* MenuItem(4, _ => "Offerings", DashboardLoc ),*/
    MenuItem(4, _ => "Offerings", BiddingScreenLoc ),
    MenuItem(5, _ => "Contracts", DashboardLoc ),
    MenuItem(6, _ => "Connections", ConnectionsLoc ),
    MenuItem(7, _ => "Wallets", DashboardLoc )
 //   MenuItem(8, _ => "Wallets", AddNewAgentLoc)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .stateless
    .render_P((ctl) => {
      <.ul(^.id := "headerNavUl", ^.className:="nav navbar-nav")(
        // build a list of menu items
        for (item <- menuItems) yield {
          <.li(^.key := item.idx, (ctl.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
            ctl.ctl.link(item.location)(HeaderCSS.Style.headerNavA ," ", item.label(ctl))
          )
        }
    //     <.div(bss.navbarRight)(<.ul(bss.navbar)(<.li(P.ctl.link(DashboardLoc)(<.span(Icon.bell)))))


//        <.div(HeaderCSS.Style.LoginInMenuItem)(
//          <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
//          <.div(HeaderCSS.Style.displayInline) ("Dale Steyn"),
//         // ctl.link(CreateAgentLoc)(HeaderCSS.Style.displayInline)(ctl.link(DashboardLoc)(HeaderCSS.Style.logoContainer,<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg"))),
//          // Todo(Todo.Props(r.page,))
//         //    AddNewAgent(AddNewAgent.Props(ctl))
//          AddNewAgent(AddNewAgent.Props()))


      )
    })
    .build
  def apply(props: Props) = MainMenu(props)
}