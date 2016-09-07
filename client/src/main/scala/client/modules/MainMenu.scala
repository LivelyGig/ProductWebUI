package client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handler.{LogoutUser, ToggleAvailablity}
import client.LGMain._
import client.components.Bootstrap.CommonStyle
import client.modals._
import client.components._
import client.css.{DashBoardCSS, HeaderCSS}
import shared.models.UserModel
import client.services.LGCircuit
import client.components.Bootstrap._
import scalacss.ScalaCssReact._
import org.querki.jquery._
import scala.language.reflectiveCalls
import client.modalpopups.{AccountForm, HeaderDropdownDialogs}
import japgolly.scalajs.react
import diode.AnyAction._

object MainMenu {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles


  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class State(isLoggedIn: Boolean = false, showNewCnxnReq: Boolean = false)

  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc, count: ReactElement, locationCount: Loc)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) = Callback {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      /*Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))*/
    }

    def newCnxnReq() = {
      $.modState(s => s.copy(showNewCnxnReq = true))
    }

    private def buildMenuItem(counter: Int): ReactElement = {
      var retRE = <.span()
      if (counter > 0) {
        retRE = <.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.inputBtnRadius, counter))
      }
      return retRE
    }


    val menuItems = Seq(
      MenuItem(1, _ => AppModule.MESSAGES_VIEW.capitalize, MessagesLoc, buildMenuItem(6), DashboardLoc),
      MenuItem(2, _ => AppModule.CONNECTIONS_VIEW.capitalize, ConnectionsLoc, buildMenuItem(0), NotificationsLoc),
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
      val introductionProxy = LGCircuit.connect(_.introduction)
      <.div(
        <.ul(^.className := "nav", HeaderCSS.Style.navbarNav)(
          // build a list of menu items
          for (item <- $.backend.menuItems) yield {
            if (Seq(ConnectionsLoc, MessagesLoc, JobPostsLoc, OfferingsLoc, ProfilesLoc, ContractsLoc, DashboardLoc).contains(item.location)) {
              if (props.proxy.value.isLoggedIn) {
                <.li()(^.key := item.idx, "data-toggle".reactAttr := "collapse", "data-target".reactAttr := ".in",
                  props.ctl.link(item.location)(
                    (props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA,
                    (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                    " ", item.label(props)
                  ),
                  if (item.location == ConnectionsLoc) {
                    introductionProxy(introductionProxy =>
                      if (introductionProxy.value.introResponse.length != 0) {
                        props.ctl.link(item.locationCount)(<.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.notificationsBtn, introductionProxy.value.introResponse.length)))

                      } else {
                        <.span()
                      }
                    )
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
                  props.ctl.link(item.locationCount)(<.span(<.button(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, DashBoardCSS.Style.notificationsBtn, 0)))
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

  case class State(userModel: UserModel, showNewMessageForm: Boolean = false)

  class Backend(t: BackendScope[Props, State]) {

    def hide: Callback = Callback {
      $(t.getDOMNode()).modal("hide")
    }

    def updateModalState: react.Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }

    def addMessage(/*postMessage:PostMessage*/): Callback = {
      t.modState(s => s.copy(showNewMessageForm = false))
    }

    def mounted(props: Props) = Callback {
      //      Callback.ifTrue(props.proxy().isEmpty, props.proxy.dispatch(RefreshConnections()))
      /*Callback(LGCircuit.dispatch(LoginUser(UserModel(email = "", name = "",
        imgSrc = "", isLoggedIn = false))))*/
    }
  }

  val LoggedUser = ReactComponentB[Props]("MainMenu")
    .initialState(State(new UserModel()))
    .backend(new Backend(_))
    .renderPS((t, props, S) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)
      println(props.proxy.value.isLoggedIn)*/
      <.div(HeaderCSS.Style.LoginInMenuItem)(
        if (props.proxy.value.isLoggedIn) {
          val model = props.proxy.value
          //println("model.imgSrc = "+ model.imgSrc)
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
                    <.li()(<.a(/*"data-toggle".reactAttr := "modal", "data-target".reactAttr := "#accountModal", "aria-haspopup".reactAttr := "true"*/ ^.onClick --> t.backend.updateModalState)("Account")),
                    <.li()(<.a()("Profiles")),
                    <.li()(<.a()("Notifications")),
                    <.li()(<.a()("Payments")),
                    <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#myModal", "aria-haspopup".reactAttr := "true")("Preferences")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(LogoutUser())))("Log Out")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#aboutModal", "aria-haspopup".reactAttr := "true")("About"))
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
                  <.li()(<.a(/*"data-toggle".reactAttr := "modal", "data-target".reactAttr := "#accountModal", "aria-haspopup".reactAttr := "true"*/ ^.onClick --> t.backend.updateModalState)("Account")),
                  <.li()(<.a()("Profiles")),
                  <.li()(<.a()("Notifications")),
                  <.li()(<.a()("Payments")),
                  <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#myModal", "aria-haspopup".reactAttr := "true")("Preferences")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a(^.onClick --> Callback(LGCircuit.dispatch(LogoutUser())))("Log Out")),
                  <.li(^.className := "divider")(),
                  <.li()(<.a("data-toggle".reactAttr := "modal", "data-target".reactAttr := "#aboutModal", "aria-haspopup".reactAttr := "true")("About"))
                ),
                HeaderDropdownDialogs(HeaderDropdownDialogs.Props()),
                if (S.showNewMessageForm)
                  AccountForm(AccountForm.Props(t.backend.addMessage, "Account"))
                //                  Account(Account.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Message",Option(true)))
                //                  NewMessageForm(NewMessageForm.Props(t.backend.addMessage, "New Message"))
                //                  NewMessage(NewMessage.Props("", Seq(HeaderCSS.Style.rsltContainerIconBtn), Icon.envelope, "Message",Option(true)))
                //                  Legal(Legal.Props("Legal", Seq(), "", "",Option(true)))

                else <.div()

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

