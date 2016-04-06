package synereo.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.Handlers.LoginUser
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.{MIcon, Bootstrap, GlobalStyles, Icon}
import synereo.client.css.{SynereoCommanStylesCSS, LoginCSS}
import synereo.client.models.UserModel
import synereo.client.services.SYNEREOCircuit

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
      Callback(SYNEREOCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
  }

  private def buildMenuItem(mItem: String): ReactElement = {
    <.span(<.span(mItem))
  }

  private val menuItems = Seq(
    MenuItem(1, _ => buildMenuItem("WHAT IS SYNEREO"), SynereoLoc),
    MenuItem(2, _ => buildMenuItem("WATCH THE VIDEO"), SynereoLoc)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      <.nav(^.className := "nav navbar-nav navbar-right")(
        <.div(^.className := "container-fluid")(
          <.ul(^.className := "nav navbar-nav navbar-right")(
           /* for (item <- menuItems) yield {
              <.li(item.label)
            },*/
            <.li(^.className := "active")(
              <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                <.span(LoginCSS.Style.navLiAIcon)(MIcon.playCircleOutline),
                "WATCH THE VIDEO")
            ),
            <.li()(
              <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                <.span(LoginCSS.Style.navLiAIcon)(MIcon.helpOutline),
                "WHAT IS SYNEREO"
              )
            )/*,
            <.li()(
              <.a(^.href:="#","data-toggle".reactAttr:="popover","title".reactAttr:="Popover Header","data-content".reactAttr:="Some content inside the popover")(
                <.span(MIcon.speakerNotes)
              )
            )*/
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}
