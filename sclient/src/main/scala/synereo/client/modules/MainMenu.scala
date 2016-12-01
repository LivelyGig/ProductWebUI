package synereo.client.modules

import diode.AnyAction._
import diode.ModelRO
import diode.react.ModelProxy
import japgolly.scalajs.react
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos.CloseSessionRequest
import shared.models.UserModel
import synereo.client.SYNEREOMain._
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.components._
import synereo.client.css.{DashboardCSS, LoginCSS, SynereoCommanStylesCSS}
import synereo.client.handlers._
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import synereo.client.utils.{ContentUtils, I18N}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

//scalastyle:off
object MainMenu {

  val introductionConnectProxy = SYNEREOCircuit.connect(_.introduction)
  //  val userProxy = SYNEREOMain.userProxy

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(showProfileImageUploadModal: Boolean = false,
                   showNodeSettingModal: Boolean = false,
                   showAboutInfoModal: Boolean = false,
                   lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class MainMenuBackend(t: BackendScope[Props, State]) {

    def mounted() = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def updateLang(reader: ModelRO[js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }

    def showImageUploadModal(): react.Callback = Callback {
      //      logger.log.debug("main menu showImageUploadModal")
      SYNEREOCircuit.dispatch(ToggleImageUploadModal())
    }

    def showAboutInfoModal(): react.Callback = Callback {
      //      logger.log.debug("main menu showAboutInfoModal")
      SYNEREOCircuit.dispatch(ToggleAboutInfoModal())
    }

    def showNodeSettingModal(): react.Callback = Callback {
      //      logger.log.debug("main menu showNodeSettingModal")
      SYNEREOCircuit.dispatch(ToggleNodeSettingModal())
    }

    def showNewMessageModal(): react.Callback = Callback {
      //      logger.log.debug("main menu showNewMessageModal")
      SYNEREOCircuit.dispatch(ToggleNewMessageModal())
    }

    def changeLang(lang: String): react.Callback = Callback {
      CoreApi.getLang(lang).onComplete {
        case Success(res) => SYNEREOCircuit.dispatch(ChangeLang(JSON.parse(res)))
        case Failure(_) => logger.log.error(s"failed to load language for ${lang}")
      }
    }

  }

  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new MainMenuBackend(_))
    .renderPS((scope, props, state) => {

      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      <.div(^.className := "container-fluid")(
        if (props.proxy.value.isLoggedIn) {
          val model = props.proxy.value
          <.div(^.className := "row")(
            <.div(^.className := "label-selectize-container-main")(
              if (props.currentLoc == DashboardLoc) {
                <.div(
                  <.div(^.className := "pull-left")(
                    <.button(^.className := "btn", ^.onClick --> scope.backend.showNewMessageModal(), SynereoCommanStylesCSS.Style.createPostBtn,
                        <.span(^.className := "fa fa-plus", SynereoCommanStylesCSS.Style.createPostIcon),
                        <.span(SynereoCommanStylesCSS.Style.createPostIconText,"Create Post")
                      ),
                      <.button(^.className := "btn", ^.onClick --> scope.backend.showNewMessageModal(), SynereoCommanStylesCSS.Style.createPostButton,
                     <.img(^.src := "./assets/synereo-images/CreatePost.gif", SynereoCommanStylesCSS.Style.createPostImg)
                  )),
                  <.div(
                    SearchComponent(SearchComponent.Props())
                  )
                )
              } else {
                <.span()
              }
            ),
            <.div(^.className := "nav navbar-nav  navbar-right", SynereoCommanStylesCSS.Style.mainMenuNavbar)(
              <.ul(^.className := "nav nav-pills")(

                <.li(SynereoCommanStylesCSS.Style.userNameNavBar)(
                  <.span(<.img(^.className := "hidden-xss img-responsive", ^.src := "./assets/synereo-images/bubble.png", SynereoCommanStylesCSS.Style.userNameNavBarBubbleImage)),
                  <.span(SynereoCommanStylesCSS.Style.userNameNavBarText, <.div(SynereoCommanStylesCSS.Style.userNameOverflow)(model.name),
                    <.div(^.className := "text-center")(
                      //  <.span(model.networkMode.toUpperCase),
                      <.button(^.`type` := "button", ^.className := "btn", SynereoCommanStylesCSS.Style.ampsDropdownToggleBtn)(
                        /*<.img(^.src := "./assets/synereo-images/ampsIcon.PNG")*/
                        "data-toggle".reactAttr := "tooltip", "title".reactAttr := "AMP Balance", "data-placement".reactAttr := "right",
                        <.img(^.src := "./assets/synereo-images/amptoken.png", DashboardCSS.Style.ampTokenImg),
                        //                        <.span(Icon.cogs),
                        <.span(DashboardCSS.Style.ampbalancetext)(model.networkMode + " " + model.balanceAmp + " / " + model.balanceBtc)
                      ),
                      <.span(introductionConnectProxy(introProxy =>
                        if (introProxy.value.introResponse.length != 0) {
                          //                      ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(DashboardCSS.Style.confirmIntroReqBtn), MIcon.sms, ""))
                          <.a(^.href := "/#notifications", DashboardCSS.Style.confirmIntroReqBtn,
                            <.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashboardCSS.Style.inputBtnRadius, introProxy.value.introResponse.length))
                          )
                        } else {
                          <.span()
                        }
                      )
                      )
                    )
                  )
                ),
                <.li(SynereoCommanStylesCSS.Style.mainMenuUserActionDropdownLi)(
                  <.div()(
                    <.button(^.className := "btn ", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.mainMenuUserActionDropdownBtn)(
                      <.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar)
                    ),
                    // <.div(^.className := "dropdown-arrow-small"),
                    <.ul(^.className := "dropdown-menu ", SynereoCommanStylesCSS.Style.userActionsMenu)(
                      <.li(<.a(^.onClick --> scope.backend.showAboutInfoModal())(state.lang.selectDynamic("ABOUT").toString)),
                      <.li(<.a(^.onClick --> scope.backend.showImageUploadModal())(state.lang.selectDynamic("CHANGE_PROFILE_PICTURE").toString)),
                      <.li(<.a(^.onClick --> scope.backend.showNodeSettingModal())(state.lang.selectDynamic("NODE_SETTINGS").toString)),
                      <.li()(<.a(^.href := "http://social.synereo.com/jiraissue.html", ^.target := "_blank")(<.span(), "  Report an Issue")),
                      <.li(<.a(^.onClick --> Callback(ContentUtils.closeSessionReq(CloseSessionRequest(uri))))(state.lang.selectDynamic("Log_OUT").toString))

                    )
                    //                  if (state.showProfileImageUploadModal)
                    //                    userProxy(userProxy => ProfileImageUploaderForm(ProfileImageUploaderForm.Props($.backend.showImageUploadModal, "Profile Image Uploader", userProxy)))
                    //                  else if (state.showNodeSettingModal)
                    //                    NodeSettingModal(NodeSettingModal.Props($.backend.showNodeSettingModal))
                    //                  else if (state.showAboutInfoModal)
                    //                    AboutInfoModal(AboutInfoModal.Props($.backend.showAboutInfoModal))
                    //                  else
                    //                    Seq.empty[ReactElement]
                    //NewImage(NewImage.Props("", Seq(UserProfileViewCSS.Style.newImageBtn), Icon.camera, "", "", <.img(^.src := model.imgSrc, SynereoCommanStylesCSS.Style.userAvatar)))
                  )
                ),
                <.li(SynereoCommanStylesCSS.Style.featureHide)(
                  //                  NewMessage(NewMessage.Props("Create a post", Seq(SynereoCommanStylesCSS.Style.createPostButton), /*Icon.envelope*/ "", "create-post-button", "create-post-button", (<.span(^.className := "vertical-text-post-btn", "POST"))))
                )
              )
            )
          )
        } else {
          <.ul(^.className := "nav navbar-right nav-pills", SynereoCommanStylesCSS.Style.nonLoggedInMenu)(
            <.li(
              <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                //                  <.span(LoginCSS.Style.navLiAIcon)(MIcon.helpOutline),
                //                renderLang.asInstanceOf[I18N].
                //                I18N.En.MainMenu.WATCH_THE_VIDEO
                state.lang.selectDynamic("WATCH_THE_VIDEO").toString
              )
            ),
            <.li(^.className := "", LoginCSS.Style.watchVideoBtn)(
              <.a(^.href := "http://www.synereo.com/", LoginCSS.Style.navLiAStyle)(
                state.lang.selectDynamic("WHAT_IS_SYNEREO").toString
                //                AppUtils.getFromLang("WHAT_IS_SYNEREO")
              )
            )
          )
        },
        <.div(SynereoCommanStylesCSS.Style.changeLanguageDropdownContainer, SynereoCommanStylesCSS.Style.featureHide)(
          <.button(^.className := "btn btn-default", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.changeLangBtn)(
            state.lang.selectDynamic("LANG_NAME").toString match {
              case "undefined" => "EN_US"
              case _ => state.lang.selectDynamic("LANG_NAME").toString
            }, Icon.caretDown
          ),
          <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.langSelectMenu)(
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.en_us))("En-US")),
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.ch_man))("Chinese")),
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.fr))("French")),
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.hindi))("Hindi"))
          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = MainMenu(props)
}
