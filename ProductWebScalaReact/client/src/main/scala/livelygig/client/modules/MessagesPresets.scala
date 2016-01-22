package livelygig.client.modules

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.{CommonStyle, Button}
import livelygig.client.components.Icon
import livelygig.client.css._
import livelygig.client.modals.PostNewMessage


import scalacss.ScalaCssReact._

object MessagesPresets {

  case class Props(ctl: RouterCtl[Loc])

  case class State(showNewMessageForm: Boolean = false)

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }
  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addNewMessageForm() : Callback = {
      t.modState(s => s.copy(showNewMessageForm = true))
    }
    def addMessage(postMessage: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showNewMessageForm}")
      if(!postMessage){
        t.modState(s => s.copy(showNewMessageForm = false))
      } else {
        t.modState(s => s.copy(showNewMessageForm = true))
      }
    }
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Messages")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend

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
//            NewMessage(NewMessage.Props(ctl))
            Button(Button.Props(B.addNewMessageForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"New Message"),
            if (S.showNewMessageForm) PostNewMessage(PostNewMessage.Props(B.addMessage, "New Message"))
            else
              Seq.empty[ReactElement]
            // TermsAndConditions(TermsAndConditions.Props(ctl)),
            // EndUserAgreement(EndUserAgreement.Props(ctl))
          )
          )
        ) }
    )
    .configure(OnUnmount.install)
    .build
  def apply(props: Props) = component(props)

}