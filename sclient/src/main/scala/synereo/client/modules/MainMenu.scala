package synereo.client.modules

import diode.data.Empty
import org.querki.jquery._
import synereo.client.components._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.models.Label
import shared.models.ConnectionsModel
import synereo.client.css.UserProfileViewCSS
import synereo.client.modalpopups.{ConfirmIntroReqModal, NewImage, NewMessage}

import scala.scalajs.js
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.handlers._
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components.MIcon.MIcon
import synereo.client.css.{DashboardCSS, SynereoCommanStylesCSS, LoginCSS}
import shared.models.UserModel
import synereo.client.services.SYNEREOCircuit

import scalacss.ScalaCssReact._
import diode.AnyAction._

//scalastyle:off
object MainMenu {
  // shorthand for styles
  //  val labelSelectizeInputId: String = "labelSelectizeInputId"
  val introductionConnectProxy = SYNEREOCircuit.connect(_.introduction)

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(labelSelectizeId: String = "labelSelectizeInputId")

  //  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = Callback {
      //      println("main menu mounted")
      //      SYNEREOCircuit.dispatch(CreateLabels())
      SYNEREOCircuit.dispatch(LoginUser(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false)))
    }

    def unmounted(props: Props) = Callback {
      //      println("main menu unmounted")
      Empty
    }

    /*def searchWithLabels(e: ReactEventI) = Callback {
      SYNEREOCircuit.dispatch(StoreMessagesLabels(Some(t.state.runNow().labelSelectizeId)))
      SYNEREOCircuit.dispatch(RefreshMessages())
    }*/

    def toggleTopbar = Callback {
      val topBtn: js.Object = "#TopbarContainer"
      $(topBtn).toggleClass("topbar-left topbar-lg-show")
    }
  }

  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      //      println(s"props proxy isLoggedIn : ${props.proxy().isLoggedIn}")
      <.div(^.className := "container-fluid")(
        if (props.proxy.value.isLoggedIn) {
          val model = props.proxy.value
          <.div(^.className := "row")(
            <.div(^.className := "label-selectize-container-main")(
              <.div()(
                if (props.currentLoc == DashboardLoc) {
                  <.div(
                    SearchComponent(SearchComponent.Props())
                  )
                } else {
                  <.span()
                }
                //                <.div(
                //                  <.div(^.className := "pull-right", DashboardCSS.Style.profileActionContainer)(
                //                    <.div(^.id := "TopbarContainer", ^.className := "col-md-2 col-sm-2 topbar topbar-animate")(
                //                      TopMenuBar(TopMenuBar.Props())
                //                        <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", DashboardCSS.Style.ampsDropdownToggleBtn, ^.onClick --> toggleTopbar)(
                //                      <.img(^.src := "./assets/synereo-images/ampsIcon.PNG"), <.span("543")
                //                    )
                //                  )
                //                )
              )
            ),
            <.div(^.className := "nav navbar-nav navbar-right", /* props.proxy().isLoggedIn ?= (^.backgroundColor := "#277490"), */ SynereoCommanStylesCSS.Style.mainMenuNavbar)(
              <.ul(^.className := "nav nav-pills")(
                <.li(
                  introductionConnectProxy(introProxy =>
                    if (introProxy.value.introResponse.length != 0) {
//                      ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(DashboardCSS.Style.confirmIntroReqBtn), MIcon.sms, ""))
                      <.a(^.href := "/#notifications", DashboardCSS.Style.confirmIntroReqBtn)(MIcon.sms)
                    } else {
                      <.span()
                    }
                  )
                )
                ,
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
                  if (model.name.length() < 10) {
                    <.div(model.name)
                  }
                  else {
                    <.span(^.title := model.name, model.name.substring(0, 8) + "...")
                  },
                  <.div(^.className := "text-center")(
                    <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", SynereoCommanStylesCSS.Style.ampsDropdownToggleBtn /*, ^.onClick --> toggleTopbar*/)(
                      /*<.img(^.src := "./assets/synereo-images/ampsIcon.PNG")*/
                      <.span(Icon.cogs),
                      <.span("543")
                    )
                  )
                ),
                <.li(^.className := "")(
                  //                  <.a(^.href := "/#userprofileview", SynereoCommanStylesCSS.Style.userAvatarAnchor)(<.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar))
                  NewImage(NewImage.Props("", Seq(UserProfileViewCSS.Style.newImageBtn), Icon.camera, "","",<.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar)))
                ),
                <.li(
                  NewMessage(NewMessage.Props("Create a post", Seq(SynereoCommanStylesCSS.Style.createPostButton), Icon.envelope, "create-post-button", "create-post-button", (<.span(^.className := "vertical-text-post-btn", "POST"))))
                )
              )
            )
          )
        } else {
          <.ul(^.className := "nav navbar-right nav-pills", SynereoCommanStylesCSS.Style.nonLoggedInMenu)(
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
        //        <.div(^.className := "text-center")(
        //          if (props.proxy().isLoggedIn) {
        //            <.div(^.className := "")(
        //              <.div(SynereoCommanStylesCSS.Style.labelSelectizeContainer)(
        //                <.div(^.id := labelSelectizeInputId, SynereoCommanStylesCSS.Style.labelSelectizeNavbar)(
        //                  SYNEREOCircuit.connect(_.searches)(searchesProxy => LabelsSelectize(LabelsSelectize.Props(searchesProxy, labelSelectizeInputId)))
        //                ),
        //                <.button(^.className := "btn btn-primary", ^.onClick ==> searchWithLabels, SynereoCommanStylesCSS.Style.searchBtn)(MIcon.apply("search", "24")
        //                )
        //              ),
        //              <.div(
        //                <.div(^.className := "pull-right", DashboardCSS.Style.profileActionContainer)(
        //                  <.div(^.id := "TopbarContainer", ^.className := "col-md-2 col-sm-2 topbar topbar-animate")(
        //                    TopMenuBar(TopMenuBar.Props())
        //                    //                      <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", DashboardCSS.Style.ampsDropdownToggleBtn, ^.onClick --> toggleTopbar)(
        //                    //                        <.img(^.src := "./assets/synereo-images/ampsIcon.PNG"), <.span("543")
        //                    //                      )
        //                  )
        //                )
        //              )
        //            )
        //          } else {
        //            <.span()
        //          }
        //        )
        //      )
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentWillUnmount(scope => scope.backend.unmounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}
