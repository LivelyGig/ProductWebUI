package livelygig.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.Handlers.LoginUser
import livelygig.client.LGMain
import livelygig.client.LGMain._
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

  private val menuItems = Seq(
    MenuItem(1, _ => "Dashboard", DashboardLoc),
    MenuItem(2, _ => "Messages", MessagesLoc),
    MenuItem(3, _ => "Jobs", ProjectsLoc),
    MenuItem(4, _ => "Offerings", OfferingsLoc),
    MenuItem(5, _ => "Profiles", TalentLoc),
    MenuItem(6, _ => "Contracts", ContractsLoc),
    MenuItem(7, _ => "Connections", ConnectionsLoc)
    //   MenuItem(10, _ => "Wallets", AddNewAgentLoc)
  )
  private val MainMenu = ReactComponentB[Props]("MainMenu")
    .stateless
    .render_P((props) => {
      println(props.proxy.value.isLoggedIn)
      <.div(
        <.ul(^.id := "headerNavUl", ^.className := "nav navbar-nav")(
          // build a list of menu items
          for (item <- menuItems) yield {
            <.li(^.key := item.idx, (props.currentLoc == item.location) ?= (HeaderCSS.Style.headerNavLi),
              props.ctl.link(item.location)(HeaderCSS.Style.headerNavA, " ", item.label(props))
            )
          }
          //        <.div(HeaderCSS.Style.LoginInMenuItem)(
          //          if (props.proxy.value.isLoggedIn){
          //            //          <.ul(bss.navbar)(<.li()(<.span(Icon.bell)))
          //            <.div(
          //            <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
          //            <.div(HeaderCSS.Style.displayInline) ("Dale Steyn"),
          //            <.li(HeaderCSS.Style.displayInline)(HeaderCSS.Style.logoContainer,<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg"))
          //
          //          } else {
          //            AddNewAgent(AddNewAgent.Props())
          //          }
          //        )
          //        <.div(HeaderCSS.Style.LoginInMenuItem)(
          //          AddNewAgent(AddNewAgent.Props())
          //        )
          //     <.div(bss.navbarRight)(<.ul(bss.navbar)(<.li(P.ctl.link(DashboardLoc)(<.span(Icon.bell)))))

          //        <.div(HeaderCSS.Style.LoginInMenuItem)(
          //          <.div(HeaderCSS.Style.displayInline)(<.span(Icon.bell)),
          //          <.div(HeaderCSS.Style.displayInline) ("Dale Steyn"),
          //         // ctl.link(CreateAgentLoc)(HeaderCSS.Style.displayInline)(ctl.link(DashboardLoc)(HeaderCSS.Style.logoContainer,<.img(HeaderCSS.Style.imgLogo, ^.src := "./assets/images/profile.jpg"))),
          //
          //         //    AddNewAgent(AddNewAgent.Props(ctl))
          //          AddNewAgent(AddNewAgent.Props()))


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
            LGCircuit.connect(_.user)(proxy => AddNewAgent(AddNewAgent.Props(proxy)))
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