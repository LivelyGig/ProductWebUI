package synereo.client.modules

import org.querki.jquery._
import synereo.client.components._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.scalajs.js

//import shapeless.Tuple
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.handlers._
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.MIcon.MIcon
import synereo.client.css.{DashboardCSS, SynereoCommanStylesCSS, LoginCSS}
import shared.models.UserModel
import synereo.client.services.SYNEREOCircuit

import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  val labelSelectizeId: String = "labelSelectizeId"

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(isLoggedIn: Boolean = false)

  //  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) =
      Callback(SYNEREOCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
      SYNEREOCircuit.dispatch(CreateLabels())
  }
  def searchWithLabels(e: ReactEventI)= Callback {
    SYNEREOCircuit.dispatch(SearchMessagesOnLabels(Some(labelSelectizeId)))
    SYNEREOCircuit.dispatch(RefreshMessages())
  }

  def toggleTopbar = Callback {
    val topBtn: js.Object = "#TopbarContainer"
    $(topBtn).toggleClass("topbar-left topbar-lg-show")
  }


  //
  //  def displayMenu() = {
  //
  //  }

  //  private val menuItems = Seq(
  //    MenuItem(0, _ => "FullBlogPostView", PostFullViewLOC),
  //    MenuItem(1, _ => "UserProfileView", SynereoUserProfileViewLOC),
  //    MenuItem(2, _ => "MarketPlaceView", MarketPlaceLOC)
  //  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      <.div(^.className := "container-fluid")(
        <.ul(^.className := "nav navbar-nav navbar-right", /* props.proxy().isLoggedIn ?= (^.backgroundColor := "#277490"), */ SynereoCommanStylesCSS.Style.mainMenuNavbar)(
          if (props.proxy().isLoggedIn) {
            val model = props.proxy.value
            <.ul(^.className := "nav nav-pills")(
              <.li(
                <.div(^.className := "dropdown")(
                  <.button(^.className := "btn btn-default dropdown-toggle userActionButton", SynereoCommanStylesCSS.Style.userActionButton, ^.`type` := "button", "data-toggle".reactAttr := "dropdown" /*,
                    ^.onMouseOver ==>$.props.displayMenu*/)(MIcon.speakerNotes),
                  <.div(^.className := "dropdown-arrow"),
                  <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.dropdownMenu)(
                    <.li(^.className := "hide")(props.ctl.link(MarketPlaceLOC)("Redirect to MarketPlace")),
                    <.li(^.className := "hide")(<.a(^.onClick --> Callback(SYNEREOCircuit.dispatch(LogoutUser())))("Sign Out")),
                    <.li(<.div("Using", SynereoCommanStylesCSS.Style.dropDownLIHeading), (<.span(^.className := "pull-right")((Icon.connectdevelop))),
                      <.ul(^.className := "list-unstyled nav-pills")(
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("LivelyPay"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Yoy @ you"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress")))
                      )),
                    <.br(),
                    <.div(^.className := "col-md-12 text-center", SynereoCommanStylesCSS.Style.dropdownLiMenuSeperator)("My apps 16"),
                    <.li(
                      <.div("More apps", SynereoCommanStylesCSS.Style.dropDownLIHeading),
                      <.ul(^.className := "list-unstyled nav-pills")(
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("LivelyPay"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Yoy @ you"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", SynereoCommanStylesCSS.Style.marginLeftTwentyFive, ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress", SynereoCommanStylesCSS.Style.marginLeftFifteen))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress"))),
                        <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress")))
                      )
                    ),
                    <.div(^.className := "col-md-12 text-center", SynereoCommanStylesCSS.Style.dropdownLiMenuSeperator)("More From market")
                  )
                )
              ),
              <.li(/*,SynereoCommanStylesCSS.Style.*/)(
                <.div(^.className := "dropdown")(
                  <.button(^.className := "btn dropdown-toggle", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.mainMenuUserActionDropdownBtn)((MIcon.chatBubble)),
                  <.div(^.className := "dropdown-arrow-small"),
                  <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.userActionsMenu)(
                    <.li(props.ctl.link(MarketPlaceLOC)("MarketPlace")),
                    <.li(<.a(^.onClick --> Callback(SYNEREOCircuit.dispatch(LogoutUser())))("Sign Out"))
                  )
                )
              ),
              <.li(SynereoCommanStylesCSS.Style.userNameNavBar)(
                <.div(model.name),
                <.div(^.className := "text-center")(
                  <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", SynereoCommanStylesCSS.Style.ampsDropdownToggleBtn /*, ^.onClick --> toggleTopbar*/)(
                    /*<.img(^.src := "./assets/synereo-images/ampsIcon.PNG")*/
                    <.span(Icon.cogs),
                    <.span("543")
                  )
                )
              ),
              <.li(^.className := "")(
                <.a(^.href := "/#userprofileview", SynereoCommanStylesCSS.Style.userAvatarAnchor)(<.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar))
              )
            )
          } else {
            <.ul(^.className := "nav nav-pills", SynereoCommanStylesCSS.Style.nonLoggedInMenu)(
              <.li(
                <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                  //                  <.span(LoginCSS.Style.navLiAIcon)(MIcon.helpOutline),
                  "WHAT IS SYNEREO?"
                )
              ),
              <.li(^.className := "", LoginCSS.Style.watchVideoBtn)(
                <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                  //                  <.span(LoginCSS.Style.navLiAIcon)(MIcon.playCircleOutline),
                  <.span("WATCH THE VIDEO")
                )
              )
            )
          }
        ), <.div(
          if (props.proxy().isLoggedIn) {
            <.div(^.className := "text-center")(
              //              <.form(^.className := "navbar-form", SynereoCommanStylesCSS.Style.searchFormNavbar)(
              //                <.div(^.className := "form-group")(
              //                  <.input(^.className := "form-control", SynereoCommanStylesCSS.Style.searchFormInputBox)
              //                ),
              //                <.button(^.className := "btn btn-default", SynereoCommanStylesCSS.Style.searchBtn)(MIcon.apply("search", "24"))
              //              ),
              <.div(SynereoCommanStylesCSS.Style.labelSelectizeContainer)(
                <.div(^.id := labelSelectizeId, SynereoCommanStylesCSS.Style.labelSelectizeNavbar)(
                  SYNEREOCircuit.connect(_.searches)(searchesProxy => LabelsSelectize(LabelsSelectize.Props(searchesProxy, labelSelectizeId)))
                ),
                <.button(^.className := "btn btn-primary", ^.onClick ==> searchWithLabels, SynereoCommanStylesCSS.Style.searchBtn)(MIcon.apply("search", "24")
                ),
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-12 col-xs-12 col-lg-12")(
                    <.div(^.className := "pull-right", DashboardCSS.Style.profileActionContainer)(
                      <.div(^.id := "TopbarContainer", ^.className := "col-md-2 col-sm-2 topbar topbar-animate")(
                        TopMenuBar(TopMenuBar.Props())
                        //                      <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", DashboardCSS.Style.ampsDropdownToggleBtn, ^.onClick --> toggleTopbar)(
                        //                        <.img(^.src := "./assets/synereo-images/ampsIcon.PNG"), <.span("543")
                        //                      )
                      )
                    )
                  )
                )
              )
            )
          } else {
            <.span()
          }
        )

      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}
