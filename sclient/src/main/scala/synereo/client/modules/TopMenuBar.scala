package synereo.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.SYNEREOMain.{SynereoLoc, Loc}
import synereo.client.components.GlobalStyles
import synereo.client.modalpopups.NewConnection
import scalacss.ScalaCssReact._
import synereo.client.components._
import synereo.client.css.{SynereoCommanStylesCSS, DashboardCSS}

/**
  * Created by mandar.k on 4/21/2016.
  */
object TopMenuBar {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props()

  //  case class TopMenuItem(idx: Int, label: (Props) => ReactNode, location: Loc)

  private val DropMenuItem = ReactComponentB[Props]("DropMenuItem")
    .stateless
    .render_P((P) => {
      <.div(
        <.h4("AMPS EARNED", DashboardCSS.Style.ampsEarnedHeading),
        <.ul(^.id := "Topbarid", ^.className := "nav nav-stacked", DashboardCSS.Style.topBarStyle)(
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "Colby Brown"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("+0.3")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "jillianton"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("-0.1")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "Colby Brown"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("-0.5")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "jillianton"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("+0.7")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "Colby Brown"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("-0.")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "jillianton"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("+0.9")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "Colby Brown"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("-0.")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "jillianton"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("+0.9")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "Colby Brown"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("-0.")
          )),
          <.li()(<.a(^.href := "#")(
            <.img(^.src := "./assets/synereo-images/default_avatar.jpg", SynereoCommanStylesCSS.Style.userAvatarTopBar),
            <.div(SynereoCommanStylesCSS.Style.userNameTopMenubar, "jillianton"),
            <.div(^.className := "pull-right", SynereoCommanStylesCSS.Style.ampsCount)("+0.9")
          ))
        )
      )

    })
    .build

  def apply(props: Props) = DropMenuItem(props)

}
