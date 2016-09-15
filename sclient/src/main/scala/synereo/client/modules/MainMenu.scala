package synereo.client.modules


import synereo.client.components._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.modalpopups.{AboutInfoModal, NewMessage, NodeSettingModal, ProfileImageUploaderForm}
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.handlers._
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.css.{DashboardCSS, LoginCSS, SynereoCommanStylesCSS}
import shared.models.UserModel
import synereo.client.services.SYNEREOCircuit

import scalacss.ScalaCssReact._
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import shared.dtos.CloseSessionRequest
import synereo.client.logger
import synereo.client.utils.ContentUtils

import scala.scalajs.js


//scalastyle:off
object MainMenu {

  val introductionConnectProxy = SYNEREOCircuit.connect(_.introduction)
  val userProxy = SYNEREOCircuit.connect(_.user)

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(labelSelectizeId: String = "labelSelectizeInputId", showProfileImageUploadModal: Boolean = false,
                   showNodeSettingModal: Boolean = false, showAboutInfoModal: Boolean = false)

  class MainMenuBackend(t: BackendScope[Props, State]) {

    def toggleTopbar = Callback {
      val topBtn: js.Object = "#TopbarContainer"
      $(topBtn).toggleClass("topbar-left topbar-lg-show")
    }

    def showUploadImageModal(): react.Callback = {
      t.modState(s => s.copy(showProfileImageUploadModal = true))
    }

    def imageUploaded(): Callback = {
      t.modState(s => s.copy(showProfileImageUploadModal = false))
    }

    def toggleShowAboutInfoModal(): react.Callback = {
      t.modState(s => s.copy(showAboutInfoModal = !s.showAboutInfoModal))
    }

    //  def hideAboutInfoModal(): Callback = {
    //    t.modState(s => s.copy(showProfileImageUploadModal = false))
    //  }

    def showNodeSettingModal(): react.Callback = {
      logger.log.debug("showNodeSettingModal")
      t.modState(s => s.copy(showNodeSettingModal = true))
    }

    def hideNodeSettingModal(): Callback = {
      logger.log.debug("hideNodeSettingModal")
      t.modState(s => s.copy(showNodeSettingModal = false))
    }
  }


  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new MainMenuBackend(_))
    .renderPS(($, props, state) => {
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      //      println(state"props proxy isLoggedIn : ${props.proxy().isLoggedIn}")
      <.div(^.className := "container-fluid")(
        if (props.proxy.value.isLoggedIn) {
          val model = props.proxy.value
          <.div(^.className := "row")(
            <.div(^.className := "label-selectize-container-main")(
              if (props.currentLoc == DashboardLoc) {
                <.div(
                  <.div(^.className := "pull-left")(NewMessage(NewMessage.Props("", Seq(SynereoCommanStylesCSS.Style.createPostButton), Icon.envelope, "", ""))),
                  <.div(
                    SearchComponent(SearchComponent.Props())
                  )
                )
              } else {
                <.span()
              }
            ),
            <.div(^.className := "nav navbar-nav navbar-right", /* props.proxy().isLoggedIn ?= (^.backgroundColor := "#277490"), */ SynereoCommanStylesCSS.Style.mainMenuNavbar)(
              <.ul(^.className := "nav nav-pills")(
                <.li(
                  introductionConnectProxy(introProxy =>
                    if (introProxy.value.introResponse.length != 0) {
                      //                      ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(DashboardCSS.Style.confirmIntroReqBtn), MIcon.sms, ""))
                      <.a(^.href := "/#notifications", DashboardCSS.Style.confirmIntroReqBtn,
                        <.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashboardCSS.Style.inputBtnRadius, introProxy.value.introResponse.length))
                      )
                    } else {
                      <.span()
                    }
                  )
                ),
                //                <.li(
                //                  <.div(^.className := "dropdown")(
                //                    <.button(^.className := "btn btn-default dropdown-toggle userActionButton", SynereoCommanStylesCSS.Style.userActionButton, ^.`type` := "button", "data-toggle".reactAttr := "dropdown" /*,
                //                    ^.onMouseOver ==>$.props.displayMenu*/)(MIcon.speakerNotes),
                //                    <.div(^.className := "dropdown-arrow"),
                //                    <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.dropdownMenu)(
                //                      <.li(^.className := "hide")(props.ctl.link(MarketPlaceLOC)("Redirect to MarketPlace")),
                //                      <.li(^.className := "hide")(<.a(^.onClick --> Callback(SYNEREOCircuit.dispatch(LogoutUser())))("Sign Out")),
                //                      <.li(<.div("Using", SynereoCommanStylesCSS.Style.dropDownLIHeading), (<.span(^.className := "pull-right")((Icon.connectdevelop))),
                //                        <.ul(^.className := "list-unstyled nav-pills")(
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("LivelyPay"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Yoy @ you"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress")))
                //                        )),
                //                      <.br(),
                //                      <.div(^.className := "col-md-12 text-center", SynereoCommanStylesCSS.Style.dropdownLiMenuSeperator)("My apps 16"),
                //                      <.li(
                //                        <.div("More apps", SynereoCommanStylesCSS.Style.dropDownLIHeading),
                //                        <.ul(^.className := "list-unstyled nav-pills")(
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("LivelyPay"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Yoy @ you"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", SynereoCommanStylesCSS.Style.marginLeftTwentyFive, ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress", SynereoCommanStylesCSS.Style.marginLeftFifteen))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress"))),
                //                          <.li(SynereoCommanStylesCSS.Style.dropdownIcon)(<.img(^.src := "./assets/synereo-images/AppIcon.png", ^.className := "img-responsive inline-block"), <.br(), (<.span("Wordpress")))
                //                        )
                //                      ),
                //                      <.div(^.className := "col-md-12 text-center", SynereoCommanStylesCSS.Style.dropdownLiMenuSeperator)("More From market")
                //                    )
                //                  )
                //                ),
                //                <.li(/*,SynereoCommanStylesCSS.Style.*/)(
                //                  <.div(^.className := "dropdown")(
                //                    <.button(^.className := "btn dropdown-toggle", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.mainMenuUserActionDropdownBtn)((MIcon.chatBubble)),
                //                    <.div(^.className := "dropdown-arrow-small"),
                //                    <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.userActionsMenu)(
                //                      <.li(props.ctl.link(MarketPlaceLOC)("MarketPlace")),
                //                      <.li(<.a(^.onClick --> Callback(SYNEREOCircuit.dispatch(LogoutUser())))("Sign Out"))
                //                    )
                //                  )
                //                ),
                <.li(SynereoCommanStylesCSS.Style.userNameNavBar)(
                  //                  if (model.name.length() < 10) {
                  <.div(SynereoCommanStylesCSS.Style.userNameOverflow)(model.name),
                  //                  }
                  //                  else {
                  //                    <.span(^.title := model.name, model.name.substring(0, 8) + "...")
                  //                  }
                  <.div(^.className := "text-center")(
                    <.button(^.id := "topbarBtn", ^.`type` := "button", ^.className := "btn", SynereoCommanStylesCSS.Style.ampsDropdownToggleBtn /*, ^.onClick --> toggleTopbar*/)(
                      /*<.img(^.src := "./assets/synereo-images/ampsIcon.PNG")*/
                      "data-toggle".reactAttr := "tooltip", "title".reactAttr := "AMP Balance", "data-placement".reactAttr := "right",
                      <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg),
                      //                        <.span(Icon.cogs),
                      <.span(model.balance)
                    )
                  )
                ),
                <.li(/*SynereoCommanStylesCSS.Style.marginRight15px*/)(
                  <.div()(
                    <.button(^.className := "btn ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.mainMenuUserActionDropdownBtn)(
                      <.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar)
                    ),
                    // <.div(^.className := "dropdown-arrow-small"),
                    <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.userActionsMenu)(
                      <.li(<.a(^.onClick --> $.backend.toggleShowAboutInfoModal())("About")),
                      <.li(<.a(^.onClick --> $.backend.showUploadImageModal())(" Change Profile Picture ")),
                      <.li(<.a(^.onClick --> $.backend.showNodeSettingModal(), "Node Settings")),
                      <.li(<.a(^.onClick --> Callback(ContentUtils.closeSessionReq(CloseSessionRequest(uri))))("Sign Out"))
                    )
                  ),
                  if (state.showProfileImageUploadModal)
                    userProxy(userProxy => ProfileImageUploaderForm(ProfileImageUploaderForm.Props($.backend.imageUploaded, "Profile Image Uploader", userProxy)))
                  else if (state.showNodeSettingModal)
                    NodeSettingModal(NodeSettingModal.Props($.backend.hideNodeSettingModal))
                  else if (state.showAboutInfoModal)
                    AboutInfoModal(AboutInfoModal.Props($.backend.toggleShowAboutInfoModal))
                  else
                    Seq.empty[ReactElement]
                  //NewImage(NewImage.Props("", Seq(UserProfileViewCSS.Style.newImageBtn), Icon.camera, "", "", <.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar)))
                ),
                <.li(SynereoCommanStylesCSS.Style.featureHide)(
                  NewMessage(NewMessage.Props("Create a post", Seq(SynereoCommanStylesCSS.Style.createPostButton), /*Icon.envelope*/ "", "create-post-button", "create-post-button", (<.span(^.className := "vertical-text-post-btn", "POST"))))
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
      )
    })
    .build
  def apply(props: Props) = MainMenu(props)
}
