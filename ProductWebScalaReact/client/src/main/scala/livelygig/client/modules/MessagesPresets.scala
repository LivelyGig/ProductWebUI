package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Icon
import livelygig.client.css._
import livelygig.client.modules.NewMessage

import scalacss.ScalaCssReact._

object MessagesPresets {
  // create the React component for Dashboard
  val component = ReactComponentB[RouterCtl[Loc]]("Messages")
    .render_P(ctl =>
      // todo: Need to parameterize.
      // This example is for Talent
      <.div(^.id:="middelNaviContainer",HeaderCSS.Style.middelNaviContainer)(
        <.div(^.className :="row")(
          <.div(^.className:="col-md-12 col-sm-12 col-xs-12")(
            <.div(^.className:="btn-group")(
              <.button(HeaderCSS.Style.projectCreateBtn, ^.className:="btn dropdown-toggle","data-toggle".reactAttr := "dropdown")("Unread Messages ")(
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
            /*<.button(HeaderCSS.Style.createNewProjectBtn, ^.className:="btn")("New Message")()*/
              NewMessage(NewMessage.Props(ctl)),
             NewRecommendation(NewRecommendation.Props(ctl))
            // TermsAndConditions(TermsAndConditions.Props(ctl)),
           // EndUserAgreement(EndUserAgreement.Props(ctl))
          )
          )
        )
    )
    .componentDidMount(scope => Callback {
    })
    .build
}