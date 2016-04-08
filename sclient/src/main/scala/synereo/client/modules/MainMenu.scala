package synereo.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import shapeless.Tuple
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.Handlers.{LogoutUser, LoginUser}
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.MIcon
import synereo.client.components.MIcon.MIcon
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

  private val menuItems = Seq(
    MenuItem(0, _ => "FullBlogPostView", SynereoBlogPostFullLOC),
    MenuItem(1, _ => "UserProfileView", SynereoUserProfileViewLOC),
    MenuItem(2, _ => "MarketPlaceView", MarketPlaceFullLOC)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      <.div(^.className := "container-fluid")(
        <.ul(^.className := "nav navbar-nav navbar-right")(
          if (props.proxy().isLoggedIn) {
            val model = props.proxy.value
            <.ul(^.className := "nav nav-pills")(
              <.li(SynereoCommanStylesCSS.Style.userNameNavBar)(<.span(model.email)),
              <.li(
                <.div(^.className := "dropdown")(
                  <.button(^.className := "btn btn-default dropdown-toggle",SynereoCommanStylesCSS.Style.userActionButton, ^.`type` := "button", "data-toggle".reactAttr := "dropdown")(MIcon.speakerNotes),
                  <.ul(^.className := "dropdown-menu")(
                    <.li(props.ctl.link(MarketPlaceFullLOC)("Redirect to MarketPlace")),
                    <.li(<.a(^.onClick --> Callback(SYNEREOCircuit.dispatch(LogoutUser())))("Sign Out"))
                  )
                )
              ),
              <.li(
                <.a(^.href := "")(MIcon.chatBubble)
              ),
              <.li(^.className := "")(
                <.a(^.href := "/#userprofileview")(<.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar))
              )
            )
          } else {
            <.ul(^.className := "nav nav-pills")(
              <.li(^.className := "")(
                <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                  <.span(LoginCSS.Style.navLiAIcon)(MIcon.playCircleOutline),
                  <.span("WATCH THE VIDEO")
                )
              ),
              <.li(
                <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                  <.span(LoginCSS.Style.navLiAIcon)(MIcon.helpOutline),
                  "WHAT IS SYNEREO"
                )
              )
            )
          }
        ), <.div(
          if (props.proxy().isLoggedIn) {
            <.form(^.className := "navbar-form", SynereoCommanStylesCSS.Style.searchFormNavbar)(
              <.div(^.className := "form-group")(
                <.input(^.className := "form-control",SynereoCommanStylesCSS.Style.searchFormInputBox)
              )
            )
          }
          else {
            <.span()
          }
        )

      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}
