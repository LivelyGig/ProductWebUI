package livelygig.client.modules

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.{CommonStyle, Button}
import livelygig.client.css.HeaderCSS
import livelygig.client.css._
import livelygig.client.modals.{UserPreferences}

import scalacss.ScalaCssReact._

object TalentPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("UserPreferences")
       .render_P(ctl => {
      <.div(^.id:="middelNaviContainer",HeaderCSS.Style.middelNaviContainer)(
        <.div(^.className :="row")(
          <.div(^.className:="col-md-10 col-sm-10 col-xs-10")(
            <.div(^.className:="btn-group")(
              <.button(HeaderCSS.Style.projectCreateBtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Recommended Matches ")(
                <.span(^.className:="caret")
              ),
              <.ul(HeaderCSS.Style.dropdownMenuWidth, ^.className:="dropdown-menu")(
                <.li()(<.a(^.href:="#")("Recommended Matches")),
                <.li()(<.a(^.href:="#")("Favorited")),
                <.li()(<.a(^.href:="#")("Available")),
                <.li()(<.a(^.href:="#")("Active Unavailable")),
                <.li()(<.a(^.href:="#")("Inactive")),
                <.li()(<.a(^.href:="#")("Hidden")),
                <.li(^.className:="divider")(),
                <.li()(<.a(^.href:="#")("Videographers w/5+ yrs experience")),
                <.li()(<.a(^.href:="#")("Customize..."))
              )
            ),
            <.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("New Profile")(),
            //Invoice(Invoice.Props(ctl)),
            <.div(MessagesCSS.Style.newProjectbtn)(
              UserPreferences(UserPreferences.Props(ctl))
            )
           // PrivacyPolicyModal(PrivacyPolicyModal.Props(ctl))
          )
        )

    )})

      .build


}