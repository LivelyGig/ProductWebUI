package client.modules

import client.modules.MainMenu.{Backend, State}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handlers.{LoginUser, LogoutUser, ToggleAvailablity}
import client.LGMain._
import client.components.Bootstrap.CommonStyle
import client.modals.{AgentLoginSignUp, ConfirmIntroReq, Legal, NewMessage}
import client.components._
import client.css.{DashBoardCSS, HeaderCSS, WorkContractCSS}
import shared.models.UserModel
import client.services.LGCircuit
import shared.dtos._
import client.components.Bootstrap._
import scala.scalajs.js
import scala.util._
import scalacss.ScalaCssReact._
import org.querki.jquery._

import scala.language.reflectiveCalls

object MainMenu {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(isLoggedIn: Boolean = false, showNewCnxnReq: Boolean = false)

  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc, count: ReactElement, locationCount: Loc)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) = {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
    }

    def newCnxnReq() = {
      $.modState(s => s.copy(showNewCnxnReq = true))
    }

    private def buildMenuItem(counter: Int): ReactElement = {
      var retRE = <.span()
      if (counter > 0 ) {
        retRE = <.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.inputBtnRadius, counter))
      }
      return retRE
    }


    val menuItems = Seq(
      MenuItem(1, _ => AppModule.CONNECTIONS_VIEW.capitalize, ConnectionsLoc, buildMenuItem(5), DashboardLoc),
      MenuItem(2, _ => AppModule.MESSAGES_VIEW.capitalize, MessagesLoc, buildMenuItem(6), DashboardLoc),
      MenuItem(3, _ => AppModule.PROJECTS_VIEW.capitalize, JobPostsLoc, buildMenuItem(3), DashboardLoc),
      MenuItem(4, _ => AppModule.OFFERINGS_VIEW.capitalize, OfferingsLoc, buildMenuItem(0), DashboardLoc),
      MenuItem(5, _ => AppModule.PROFILES_VIEW.capitalize, ProfilesLoc, buildMenuItem(0), DashboardLoc),
      MenuItem(6, _ => AppModule.CONTRACTS_VIEW.capitalize, ContractsLoc, buildMenuItem(0), DashboardLoc)
      // ToDo: Dashboard menu intentionally hidden for now.  EE  2016-04-12
      // MenuItem(7, _ => "Dashboard",  DashboardLoc , buildMenuItem(0) , DashboardLoc)
    )
  }

  val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)
      println(props.proxy.value.isLoggedIn)*/
      <.div(
        //<.ul(^.id := "headerNavUl", ^.className := "nav navbar-nav")(
        <.ul(/*^.id := "headerNavUl",*/ ^.className := "nav", HeaderCSS.Style.navbarNav)(
          // build a list of menu items
          for (item <- $.backend.menuItems) yield {
            if (Seq(ConnectionsLoc, MessagesLoc, JobPostsLoc, OfferingsLoc, ProfilesLoc, ContractsLoc, ConnectionsLoc, DashboardLoc).contains(item.location)) {
              if (props.proxy.value.isLoggedIn) {
                <.li()(^.key := item.idx, "data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in",
                  props.ctl.link(item.location)(
                    (props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA,
                    (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                    " ", item.label(props)
                  ),
                  if (item.location == ConnectionsLoc) {
                    ConfirmIntroReq(ConfirmIntroReq.Props("", Seq(DashBoardCSS.Style.inputBtnRadiusCncx,bss.labelOpt(CommonStyle.danger), bss.labelAsBadge), "3" , "3"))
                  }
                  else
                    props.ctl.link(item.locationCount)((props.currentLoc != item.locationCount) ?= HeaderCSS.Style.headerNavA, ^.className := "countBadge", " ", item.count))
              } else {
                <.li("data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in")
              }
            } else {
              <.li()(^.key := item.idx, "data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in",
                props.ctl.link(item.location)(
                  (props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA,
                  (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                  " ", item.label(props)
                ),
                if (item.location == ConnectionsLoc) {
                  ConfirmIntroReq(ConfirmIntroReq.Props("", Seq(DashBoardCSS.Style.inputBtnRadiusCncx,bss.labelOpt(CommonStyle.danger), bss.labelAsBadge), "3" , "3"))
                }
                else
                  props.ctl.link(item.locationCount)((props.currentLoc != item.locationCount) ?= HeaderCSS.Style.headerNavA, ^.className := "countBadge", " ", item.count))
            }
          }
        )

      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = MainMenu(props)
}

object LoggedInUser {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(userModel: UserModel)

  class Backend(t: BackendScope[Props, State]) {

    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props) = {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
    }
  }

  val LoggedUser = ReactComponentB[Props]("MainMenu")
    .initialState(State(new UserModel("", "", "", false, "", "", true)))
    .backend(new Backend(_))
    .renderPS((t, props, S) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)
      println(props.proxy.value.isLoggedIn)*/
      <.div(HeaderCSS.Style.LoginInMenuItem)(
        if (props.proxy.value.isLoggedIn) {
          val model = props.proxy.value
          <.div(
            <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
            <.div(HeaderCSS.Style.displayInline)(
              <.div(^.className := "btn-group")(
                <.div(HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(
                  if (model.isAvailable) {
                    <.span(DashBoardCSS.Style.chatIcon, HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(Icon.circle)
                  }
                  else {
                    <.span(DashBoardCSS.Style.chatInvisibleIcon, HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(Icon.circleO)
                  },
                  <.ul(^.id := "navbar", /*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                    <.li()(<.a(^.className := "ModalNameDropDown")(model.name)),
                    <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(ToggleAvailablity())))(if (model.isAvailable) {
                      "Invisible"
                    } else {
                      "Available for Chat"
                    })),
                    <.li(^.className := "divider")(),
                    <.li()(<.a()("Availability Schedule")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a()("Account")),
                    <.li()(<.a()("Profiles")),
                    <.li()(<.a()("Notifications")),
                    <.li()(<.a()("Payments")),
                    <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#myModal", "aria-haspopup".reactAttr := "true")("Preferences")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(LogoutUser())))("Log Out")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a()("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#accountModal", "aria-haspopup".reactAttr := "true") ("About"))
                  )
                ),
                <.button(^.className := "btn dropdown-toggle ModalName", HeaderCSS.Style.loginbtn, "data-toggle".reactAttr := "dropdown")(model.name)(),
                <.span(HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(<.img(HeaderCSS.Style.imgLogo, HeaderCSS.Style.logoImage, HeaderCSS.Style.marginTopLoggedIn, ^.src := model.imgSrc)),
                <.ul(^.id := "navbar", /*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                  <.li()(<.a(^.className := "ModalNameDropDown")(model.name)),
                  <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(ToggleAvailablity())))(if (model.isAvailable) {
                    "Invisible"
                  } else {
                    "Available for Chat"
                  })),
                  <.li(^.className := "divider")(),
                  <.li()(<.a()("Availability Schedule")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a()("Account")),
                  <.li()(<.a()("Profiles")),
                  <.li()(<.a()("Notifications")),
                  <.li()(<.a()("Payments")),
                  <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#myModal", "aria-haspopup".reactAttr := "true")("Preferences")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(LogoutUser())))("Log Out")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a()("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#accountModal", "aria-haspopup".reactAttr := "true") ("About"))
                )
              ),
              <.div(^.className := "modal fade", ^.id := "myModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
                <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
                  <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
                    <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
                      <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
                        <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
                      ),
                      <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                        <.h2("hello"),
                        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
                      )
                    )
                  )
                )
              ),
              <.div(^.className := "modal fade", ^.id := "accountModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
                <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
                  <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
                    <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
                      <.div(^.className := "modal-header", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.modalHeader)(
                        <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
                      ),
                      <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                        <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
                          <.div(
                            <.input(^.`type` := "text", ^.className := "form-control", ^.placeholder := "Please Enter USER ID"/*,^.value := s.agentUid, ^.onChange ==> updateAgentUid*/)
                            //                       ,
                            //                <.div(^.id := "agentFieldError", ^.className := "hidden")
                            //                ("User with this uid is already added as your connection")
                          ),

                          <.div()(
                            <.div(DashBoardCSS.Style.modalHeaderPadding, ^.className := "text-right")(
                              <.button(^.tpe := "submit", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, "Send"),
                              <.button(^.tpe := "button", ^.className := "btn", WorkContractCSS.Style.createWorkContractBtn, ^.onClick --> t.backend.hide, "Cancel")
                            )
                          )
                        ),
                        <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
                      )
                    )
                  )
                )
              )
            )
          )
        } else {
          <.div(AgentLoginSignUp(AgentLoginSignUp.Props()))
        }
      )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = LoggedUser(props)
}

