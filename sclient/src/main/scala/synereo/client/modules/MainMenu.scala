package synereo.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.Handlers.LoginUser
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.{Bootstrap, GlobalStyles, Icon}
import synereo.client.css.{DashBoardCSS, SynereoLoginCSS}
import synereo.client.models.UserModel
import synereo.client.services.LGCircuit

import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(isLoggedIn: Boolean = false)

  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  class Backend($: BackendScope[Props, _]) {
    def mounted(props: Props) =
    //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
  }

  private def buildMenuItem(mItem: String, counter: Int): ReactElement = {
    var retRE = <.span(<.span(mItem))
    if (counter > 0) {
      retRE = <.span(
        <.span(mItem),
        //        <.span(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, counter)
        <.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.inputBtnRadius, counter)
      )
    }
    return retRE
  }

  private val menuItems = Seq(
    MenuItem(1, _ => buildMenuItem("WATCH THE VIDEO", 0), DashboardLoc),
    MenuItem(2, _ => buildMenuItem("WHAT IS SYNEREO", 0), DashboardLoc)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      <.nav(^.className := "nav navbar-nav navbar-right")(
        <.div(^.className := "container-fluid")(
          <.ul(^.className := "nav navbar-nav navbar-right")(
            <.li(^.className := "active")(
              <.a(^.href := "http://www.synereo.com/", SynereoLoginCSS.Style.navLiAStyle)(
                <.span(SynereoLoginCSS.Style.navLiAIcon)(Icon.play),
                "WHAT IS SYNEREO")
            ),
            <.li()(
              <.a(^.href := "http://www.synereo.com/", SynereoLoginCSS.Style.navLiAStyle)(
                <.span(SynereoLoginCSS.Style.navLiAIcon)(Icon.question),
                "WATCH THE VIDEO"
              )
            )
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}
