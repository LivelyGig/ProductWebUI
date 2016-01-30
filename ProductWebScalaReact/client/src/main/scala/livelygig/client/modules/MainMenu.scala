package livelygig.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.Handlers.LoginUser
import livelygig.client.LGMain
import livelygig.client.LGMain._
import livelygig.client.components.Bootstrap.CommonStyle
import livelygig.client.modals.{UserPreferences, AddNewAgent}
import livelygig.client.components._
import livelygig.client.css.HeaderCSS
import livelygig.client.models.UserModel
import livelygig.client.services.LGCircuit

import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel])

  case class MenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  private def buildMessagesMenu(mItem: String, counter: Int) : ReactElement = {
    var retRE = <.span(<.span(mItem))
    if (counter > 0) {
    retRE = <.span(
      <.span(mItem),
      <.span(bss.labelOpt(CommonStyle.danger), bss.labelAsBadge, counter)
    ) }
    return retRE
  }

  private val menuItems = Seq(
    MenuItem(1, _ => buildMessagesMenu("Dashboard",0), DashboardLoc),
    MenuItem(2, _ => buildMessagesMenu("Message", 6), MessagesLoc),
    MenuItem(3, _ => buildMessagesMenu("Jobs",3), ProjectsLoc),
    MenuItem(4, _ => buildMessagesMenu("Offerings",0), OfferingsLoc),
    MenuItem(5, _ => buildMessagesMenu("Profiles",0), TalentLoc),
    MenuItem(6, _ => buildMessagesMenu("Contracts",0), ContractsLoc),
    MenuItem(7, _ => buildMessagesMenu("Connections",0), ConnectionsLoc)
    //   MenuItem(10, _ => "Wallets", AddNewAgentLoc)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .stateless
    .render_P((props) => {
      /*var test = LGCircuit.wrap(_.user)(p => Data)

      println(props.proxy.value.isLoggedIn)*/
      <.div(
        <.ul(^.id := "headerNavUl", ^.className := "nav navbar-nav")(
          // build a list of menu items
          for (item <- menuItems) yield {
            <.li(^.key := item.idx,
              props.ctl.link(item.location)((props.currentLoc != item.location) ?= HeaderCSS.Style.headerNavA ,
                (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
                " ", item.label(props))
            )
          }
        ),
        <.div(HeaderCSS.Style.LoginInMenuItem)(
          if (props.proxy.value.isLoggedIn) {
            var model = props.proxy.value
            //          <.ul(bss.navbar)(<.li()(<.span(Icon.bell)))
            <.div(
              <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
              <.div(HeaderCSS.Style.displayInline)(
                <.div(^.className := "btn-group")(
                  <.button(^.className := "btn dropdown-toggle", HeaderCSS.Style.loginbtn, "data-toggle".reactAttr := "dropdown")(model.name)(
                  ),
                  <.span(HeaderCSS.Style.displayInline, "data-toggle".reactAttr := "dropdown" )(HeaderCSS.Style.logoContainer, <.img(HeaderCSS.Style.imgLogo, ^.src := model.imgSrc)),
                  <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className := "dropdown-menu")(
                    <.li()(<.a(^.href := "#")("Available for Chat")),
                    <.li()(<.a(^.href := "#")("Invisible")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a(^.href := "#")("Account")),
                    <.li()(<.a(^.href := "#")("Profiles")),
                    <.li()(<.a(^.href := "#")("Notifications")),
                    <.li()(<.a(^.href := "#")("Payments")),
                    <.li()(<.a(^.href := "#")(/*UserPreferences(UserPreferences.Props(props.ctl))*/ "Preferences")),
                    <.li(^.className := "divider")(),
                    <.li()(<.a(^.href := "#")("Sign Out"))
                  )
                )
              )
            )

          } else {
//            LGCircuit.connect(_.user)(proxy => AddNewAgent(AddNewAgent.Props(proxy)))
            AddNewAgent(AddNewAgent.Props())
            //            <.button(^.className:="btn btn-default",^.tpe := "button", ^.onClick --> props.proxy.dispatch(LoginUser(props.proxy.value)))("test")

          }
        )
      )
    })

    .build

  def apply(props: Props) = MainMenu(props)

  //def apply(ctl: RouterCtl[Loc], currentLoc: Loc, proxy: ModelProxy[UserModel]): ReactElement =
  //  MainMenu(Props(ctl, currentLoc, proxy))
}
