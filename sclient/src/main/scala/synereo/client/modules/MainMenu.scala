package synereo.client.modules

import synereo.client.components._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.modalpopups.NewMessage
import synereo.client.SYNEREOMain
import SYNEREOMain._
import synereo.client.handlers._
import synereo.client.components.Bootstrap.CommonStyle
import synereo.client.css.{DashboardCSS, LoginCSS, SynereoCommanStylesCSS}
import shared.models.UserModel
import synereo.client.services.{CoreApi, RootModel, SYNEREOCircuit}
import scalacss.ScalaCssReact._
import diode.AnyAction._
import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import shared.dtos.CloseSessionRequest
import synereo.client.logger
import synereo.client.utils.{AppUtils, ContentUtils, I18N}
import scala.concurrent.ExecutionContext.Implicits.global
import diode.ModelR
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success, Try}

//scalastyle:off
object MainMenu {

  val introductionConnectProxy = SYNEREOCircuit.connect(_.introduction)
  val userProxy = SYNEREOCircuit.connect(_.user)

  @inline private def bss = GlobalStyles.bootstrapStyles

  class ViewLang

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(labelSelectizeId: String = "labelSelectizeInputId", showProfileImageUploadModal: Boolean = false,
                   showNodeSettingModal: Boolean = false, showAboutInfoModal: Boolean = false, lang: js.Dynamic = SYNEREOCircuit.zoom(_.i18n.language).value)

  class MainMenuBackend(t: BackendScope[Props, State]) {

    def toggleTopbar = Callback {
      val topBtn: js.Object = "#TopbarContainer"
      $(topBtn).toggleClass("topbar-left topbar-lg-show")
    }

    def mounted() = Callback {
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.i18n.language))(e => updateLang(e))
    }

    def updateLang(reader: ModelR[RootModel, js.Dynamic]) = {
      t.modState(s => s.copy(lang = reader.value)).runNow()
    }


    def showImageUploadModal(): react.Callback = Callback {
      logger.log.debug("main menu showImageUploadModal")
      SYNEREOCircuit.dispatch(ToggleImageUploadModal())
    }

    def showAboutInfoModal(): react.Callback = Callback {
      logger.log.debug("main menu showAboutInfoModal")
      SYNEREOCircuit.dispatch(ToggleAboutInfoModal())
    }

    def showNodeSettingModal(): react.Callback = Callback {
      logger.log.debug("main menu showNodeSettingModal")
      SYNEREOCircuit.dispatch(ToggleNodeSettingModal())
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
                  <.div(^.className := "pull-left")(NewMessage(NewMessage.Props("", Seq(SynereoCommanStylesCSS.Style.createPostButton), <.img(^.src := "./assets/synereo-images/CreatePost.gif", SynereoCommanStylesCSS.Style.createPostImg), "", ""))),
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
                      //<.span(model.balance)
                      <.span(Try("%06.4f".format((model.balance).toString.toFloat / synereo.client.utils.AppUtils.BTC_SATOSHI)) match {
                        case Success(a) => a
                        case Failure(b) => "0"
                      })
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
                      <.li(<.a(^.onClick --> scope.backend.showAboutInfoModal())(AppUtils.getFromLang("ABOUT"))),
                      <.li(<.a(^.onClick --> scope.backend.showImageUploadModal())("Change profile picture")),
                      <.li(<.a(^.onClick --> scope.backend.showNodeSettingModal())("Node settings")),
                      <.li(<.a(^.onClick --> Callback(ContentUtils.closeSessionReq(CloseSessionRequest(uri))))("Sign out"))

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
                  )),
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
        <.div(SynereoCommanStylesCSS.Style.changeLanguageDropdownContainer)(
          <.button(^.className := "btn btn-default", ^.`type` := "button", "data-toggle".reactAttr := "dropdown", SynereoCommanStylesCSS.Style.changeLangBtn)(
            state.lang.selectDynamic("LANG_NAME").toString match {
              case "undefined" => "EN_US"
              case _ => state.lang.selectDynamic("LANG_NAME").toString
            }, Icon.caretDown
          ),
          <.ul(^.className := "dropdown-menu", SynereoCommanStylesCSS.Style.langSelectMenu)(
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.en_us))("En-US")),
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.fr))("French")),
            <.li(<.a(^.onClick --> scope.backend.changeLang(I18N.Lang.ch_man))("Chinese"))

          )
        )
      )
    })
    .componentDidMount(scope => scope.backend.mounted())
    .build

  def apply(props: Props) = MainMenu(props)
}
