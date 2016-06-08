package synereo.client.modules

/**
  * Created by Mandar on 4/8/2016.
  */

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain.{SynereoLoc, Loc}
import synereo.client.components.GlobalStyles
import synereo.client.modalpopups.NewConnectionModal
import scalacss.ScalaCssReact._
import synereo.client.components._
import synereo.client.css.DashboardCSS

object Sidebar {

  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props()

  case class FooterItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  private val footerItems = Seq(
    FooterItem(1, _ => "About", SynereoLoc),
    FooterItem(2, _ => "Legal", SynereoLoc),
    FooterItem(3, _ => "LivelyGig", SynereoLoc)
  )
  private val Footer = ReactComponentB[Props]("Footer")
    .stateless
    .render_P((P) => {
      <.div(
        <.ul(^.id := "sidebarNavStyle", ^.className := "nav nav-stacked", DashboardCSS.Style.sidebarNavStyle)(
          <.li()(<.a(^.href := "#")(<.span(Icon.home), "  Stream")),
          <.li()(<.a(^.href := "#")(<.span(/*MIcon.mailOutline*/ Icon.envelope), " Messages")),
          <.li()(<.a(^.href := "#")(<.span(/*MIcon.accountCircle*/ Icon.user), "  Profile")),
          <.li()(<.a(^.href := "/#people")(<.span(Icon.users), "  People")),
          <.li(^.className := "nav-divider")(),
          <.li()(<.a(^.href := "#")(<.span(/*MIcon.accessTime*/), "  Recents")),
          <.li()(<.a(^.href := "#")(<.span(Icon.user), "The DandyMan")),
          <.li()(<.a(^.href := "#")(<.span(Icon.user), "Party crew NYC")),
          <.li()(<.a(^.href := "#")(<.span(Icon.user), "HJ Hubby")),
          <.li()(<.a(^.href := "#")("More ", <.span(Icon.angleRight))),
          <.li(^.className := "nav-divider")(),
          <.li()(<.a(^.href := "#")(<.span(Icon.plus), " Create")),
          <.li()(<.a(^.href := "#")(<.span(Icon.user), "The DandyMan")),
          <.li(^.className := "nav-divider")(),
          <.li()(<.a(^.href := "#")(<.span(Icon.cog), " Settings")),
          <.li()(<.a(^.href := "#")(<.span(Icon.questionCircle), " Help & Feedback"))
        ),
        //            <.button(^.`type`:="button",^.className:="sidebarBtn")(<.span(^.float:="left")(Icon.gift), <.span(^.float:="left", ^.marginLeft:="10px")("Invite Friend"))
        //        NewConnectionModal(NewConnectionModal.Props("Invite Friend",Seq(), Icon.mailForward,))
        NewConnectionModal(NewConnectionModal.Props("", Seq(DashboardCSS.Style.inviteFrndBtn), "", "Invite Friend"))
      )

    })
    .build

  def apply(props: Props) = Footer(props)
}