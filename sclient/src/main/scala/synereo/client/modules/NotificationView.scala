package synereo.client.modules

import japgolly.scalajs.react.{Callback, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import shared.dtos.IntroConfirmReq
import synereo.client.components.{MIcon}
import synereo.client.css.{NotificationViewCSS}
import synereo.client.handlers.{UpdateIntroduction}
import synereo.client.modalpopups.{ConfirmIntroReqModal}
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 7/27/2016.
  */
//scalastyle:off
object NotificationView {

  case class Props()

  case class State()

  class Backend(t: BackendScope[Props, State]) {

    def rejectNotification(e: ReactEventI): Callback = Callback {
      window.location.href = "/#dashboard"
      if (SYNEREOCircuit.zoom(_.introduction).value.introResponse.length <= 1) {
        SYNEREOCircuit.dispatch(SYNEREOCircuit.dispatch(UpdateIntroduction(IntroConfirmReq(accepted = false))))
      }
    }


    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", NotificationViewCSS.Style.notificationViewContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12")(
            <.div(^.className := "col-md-6 col-md-offset-3")(
              <.div(
                <.ul(^.className := "media-list")(
                  <.li(^.className := "media")(
                    <.div(^.className := "card-shadow")(
                      <.div(NotificationViewCSS.Style.notificationCard)(
                        <.div("you have unseen notification", ^.display.`inline-block`, ^.margin := "20.px"),
                        <.div(^.display.`inline-block`,
                          ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(NotificationViewCSS.Style.acceptBtn), <.span(MIcon.sms), ""))),
                        <.div(^.display.`inline-block`,
                          <.button(^.className := "btn btn-default", ^.color.red, MIcon.close, ^.onClick ==> rejectNotification)
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("NotificationView")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

//object ButtonGroup {
//
//  case class Props()
//
//  private val CheckButtons = ReactComponentB[Props]("CheckButtons")
//    .renderP(Props,) {
//
//    }
//
//}
