package client.modules

import client.modules.MainMenu.{Backend, State}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handlers.{LogoutUser, LoginUser}
import client.LGMain._
import client.components.Bootstrap.CommonStyle
import client.modals.{AgentLoginSignUp}
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import shared.models.UserModel
import client.services.{LGCircuit}
import shared.dtos._
import scala.scalajs.js
import scala.util._
import scalacss.ScalaCssReact._
import org.querki.jquery._
import scala.language.reflectiveCalls

object MainMenu {
  val chatIconBlank: js.Object = "#chatIconBlank"
  val chatIconAvailable: js.Object = "#chatIcon"

  def availableForChat = Callback {
    println("In availablefor chat")
    $(chatIconBlank).addClass("hidden")
    $(chatIconAvailable).removeClass("hidden")
  }

  def invisibleForChat = Callback {
    $(chatIconAvailable).addClass("hidden")
    $(chatIconBlank).removeClass("hidden")
  }

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(isLoggedIn: Boolean = false)
  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc, count: ReactElement , locationCount: Loc)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) = {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
    }
  }

  private def buildMenuItem(counter: Int): ReactElement = {
    var retRE = <.span()
    if (counter > 0) {
      retRE = <.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.inputBtnRadius, counter))
    }
    return retRE
  }

  val menuItems = Seq(
    MenuItem(1, _ => "Connections",ConnectionsLoc,buildMenuItem(0), DashboardLoc),
    MenuItem(2, _ => "Messages ",  MessagesLoc, buildMenuItem(6), DashboardLoc),
    MenuItem(3, _ => "Jobs",       JobPostsLoc, buildMenuItem(3), DashboardLoc),
    MenuItem(4, _ => "Offerings",  OfferingsLoc, buildMenuItem(0), DashboardLoc),
    MenuItem(5, _ => "Profiles",   ProfilesLoc, buildMenuItem(0), DashboardLoc),
    MenuItem(6, _ => "Contracts",  ContractsLoc, buildMenuItem(0), DashboardLoc)
    // ToDo: Dashboard menu intentionally hidden for now.  EE  2016-04-12
    // MenuItem(7, _ => "Dashboard",  DashboardLoc , buildMenuItem(0) , DashboardLoc)
  )
   val MainMenu = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)
      println(props.proxy.value.isLoggedIn)*/
      <.div(
        <.ul(^.id := "headerNavUl", ^.className := "nav navbar-nav")(
          // build a list of menu items
          for (item <- menuItems) yield {
            if (Seq(ConnectionsLoc, MessagesLoc, JobPostsLoc, OfferingsLoc,ProfilesLoc, ContractsLoc, ConnectionsLoc, DashboardLoc).contains(item.location)) {
              if (props.proxy.value.isLoggedIn) {
                <.li()(^.key := item.idx, "data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in",
                  props.ctl.link(item.location)((props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA,
                    (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                    " ", item.label(props)),
                  props.ctl.link(item.locationCount)((props.currentLoc != item.locationCount) ?= HeaderCSS.Style.headerNavA,^.className:="countBadge"," ", item.count)
                )
              } else {
                <.li("data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in")
              }
            } else {
              <.li()(^.key := item.idx, "data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in",
                props.ctl.link(item.location)((props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA,
                  (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                  " ", item.label(props)),
                props.ctl.link(item.locationCount)((props.currentLoc != item.locationCount) ?= HeaderCSS.Style.headerNavA,^.className:="countBadge"," ", item.count)
              )
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
  val chatIconBlank: js.Object = "#chatIconBlank"
  val chatIconAvailable: js.Object = "#chatIcon"

  def availableForChat = Callback {
    println("In availablefor chat")
    $(chatIconBlank).addClass("hidden")
    $(chatIconAvailable).removeClass("hidden")
  }

  def invisibleForChat = Callback {
    $(chatIconAvailable).addClass("hidden")
    $(chatIconBlank).removeClass("hidden")
  }

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])
  case class State()
  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) = {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))
    }
  }

  val LoggedUser = ReactComponentB[Props]("MainMenu")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, props, S) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)
      println(props.proxy.value.isLoggedIn)*/

                 <.div(HeaderCSS.Style.LoginInMenuItem)(
                  if (props.proxy.value.isLoggedIn) {
                    var model = props.proxy.value
                    <.div(
                      <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
                      <.div(HeaderCSS.Style.displayInline)(
                        <.div(^.className := "btn-group")(
                          <.span(^.id := "chatIcon", DashBoardCSS.Style.chatIcon, HeaderCSS.Style.displayInline /*, "data-toggle".reactAttr := "dropdown"*/)(Icon.circle),
                          <.span(^.id := "chatIcon", ^.className := "hidden", DashBoardCSS.Style.chatIcon, HeaderCSS.Style.displayInline /*, "data-toggle".reactAttr := "dropdown"*/)(Icon.circle),
                          <.span(^.id := "chatIconBlank", ^.className := "hidden", DashBoardCSS.Style.chatInvisibleIcon, HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(Icon.circleO),
                          <.button(^.className := "btn dropdown-toggle ModalName", HeaderCSS.Style.loginbtn, "data-toggle".reactAttr := "dropdown")(model.name)(
                          ),
                          <.span(HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown")(<.img(HeaderCSS.Style.imgLogo,HeaderCSS.Style.marginTopLoggedIn, ^.src := model.imgSrc)),
                          <.ul(^.id:="navbar",/*HeaderCSS.Style.dropdownMenuWidth,*/ ^.className := "dropdown-menu")(
                            <.li()(<.a(^.className:="ModalNameDropDown")(model.name)),
                            <.li()(<.a(^.onClick --> availableForChat)("Available for Chat")),
                            <.li()(<.a(^.onClick --> invisibleForChat)("Invisible")),
                            <.li(^.className := "divider")(),
                            <.li()(<.a()("Availability Schedule")),
                            <.li(^.className := "divider")(),
                            <.li()(<.a()("Account")),
                            <.li()(<.a()("Profiles")),
                            <.li()(<.a()("Notifications")),
                            <.li()(<.a()("Payments")),
                            <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#myModal", "aria-haspopup".reactAttr := "true")("Preferences"
                            )),
                            <.li(^.className := "divider")(),
                            <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(LogoutUser())))("Sign Out"))
                          )
                        ),
                        <.div(^.className := "modal fade", ^.id := "myModal", ^.role := "dialog", ^.aria.hidden := true, ^.tabIndex := -1)(
                          <.div(DashBoardCSS.Style.verticalAlignmentHelper)(
                            <.div(^.className := "modal-dialog", DashBoardCSS.Style.verticalAlignCenter)(
                              <.div(^.className := "modal-content", DashBoardCSS.Style.modalBorderRadius)(
                                <.div(^.className := "modal-header modalheader", ^.id := "modalheader", DashBoardCSS.Style.modalHeaderPadding)(
                                  <.span(<.button(^.tpe := "button", bss.close, "data-dismiss".reactAttr := "modal", Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)("Preferences"))
                                ),
                                <.div(^.className := "modal-body", DashBoardCSS.Style.modalBodyPadding)(
                                  <.h2("hello"),
                                  <.div(bss.modal.footer,DashBoardCSS.Style.marginTop10px,DashBoardCSS.Style.marginLeftRight)()
                                )
                              )
                            ))
                        )
                      )
                    )
                  } else {
                    <.div(AgentLoginSignUp(AgentLoginSignUp.Props()))
                   // NewMessage(NewMessage.Props("",Seq(HeaderCSS.Style.rsltContainerIconBtn),Icon.mailReply,"Reply" ))
                    /*NewAgentModal(NewAgentModal.Props("Sign Up",Seq(HeaderCSS.Style.rsltContainerIconBtn),"","Sign Up"))*/
                     }
                )
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = LoggedUser(props)
}

