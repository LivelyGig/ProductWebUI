package livelygig.client.modules

import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{ReactElement, BackendScope, Callback, ReactComponentB}
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap.{CommonStyle, Button}
import livelygig.client.css.HeaderCSS
import livelygig.client.modals.{UserPreferencesForm, UserPreferences}

import scalacss.ScalaCssReact._

object TalentPresets {

  case class Props(ctl: RouterCtl[Loc])

  case class State(showUserPreferencesForm: Boolean = false )

  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
  }

  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
    def mounted(props: Props): Callback =  {
      t.modState(s => s.copy(showUserPreferencesForm = true))
    }
    def addUserPreferencesForm() : Callback = {
      t.modState(s => s.copy(showUserPreferencesForm = true))
    }

    def addUserPreferences(postUserPreferences: Boolean = false): Callback = {
      //log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showUserPreferencesForm}")
      if(postUserPreferences){
        t.modState(s => s.copy(showUserPreferencesForm = true))
      } else {
        t.modState(s => s.copy(showUserPreferencesForm = false))
      }
    }
  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Talent")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      val B = $.backend
      // todo: Need to parameterize.
      // This example is for Talent
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
//            UserPreferences(UserPreferences.Props(ctl))
            Button(Button.Props(B.addUserPreferencesForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)),"User Preferences"),
            if (S.showUserPreferencesForm) UserPreferencesForm(UserPreferencesForm.Props(B.addUserPreferences))
            else
              Seq.empty[ReactElement]
           // PrivacyPolicyModal(PrivacyPolicyModal.Props(ctl))
          )
        )
       )})
      .configure(OnUnmount.install)
      .build
      def apply(props: Props) = component(props)

}