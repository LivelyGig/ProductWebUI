package synereo.client.modules

import japgolly.scalajs.react.{Callback, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import shared.dtos.{IntroConfirmReq, Introduction}
import synereo.client.css.NotificationViewCSS
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import diode.react.ModelProxy
import shared.RootModels.IntroRootModel
import synereo.client.components.MIcon
import synereo.client.logger
import synereo.client.modalpopups.ConfirmIntroReqModal

import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by mandar.k on 7/27/2016.
  */
//scalastyle:off
object NotificationView {

  case class Props(proxy: ModelProxy[IntroRootModel])

  case class State()

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props): Callback = Callback {
      logger.log.info("notifications view mounted")
    }
  }

  val component = ReactComponentB[Props]("NotificationView")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      <.div(^.className := "container-fluid", NotificationViewCSS.Style.notificationViewContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12 col-xs-12 col -sm -12")(
            <.div(^.className := "col-md-6 col-md-offset-3")(
              NotificationList(P.proxy().introResponse)
            )
          )
        )
      )
    })
    .componentDidMount(s => s.backend.mounted(s.props))
    .componentDidUpdate(scope => Callback {
      if (scope.currentProps.proxy().introResponse.length <= 0) {
        window.location.href = "/#dashboard"
      }
    })
    .build

  def apply(proxy: ModelProxy[IntroRootModel]) = component(Props(proxy))
}

object NotificationList {

  case class NotificationListProps(introductions: Seq[Introduction])

  val NotificationList = ReactComponentB[NotificationListProps]("NotificationList")
    .render_P(p => {
      def renderIntroductions(introduction: Introduction) = {
        //        println(s"introduction in notification view: $introduction")
        <.li(^.className := "media")(
          <.div(^.className := "card-shadow")(
            <.div(NotificationViewCSS.Style.notificationCard)(
              <.div("you have unseen notification", ^.display.`inline-block`, ^.margin := "20.px"),
              <.div(^.display.`inline-block`,
                ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(NotificationViewCSS.Style.acceptBtn), <.span(MIcon.sms), "", introduction))),
              <.div(^.display.`inline-block`,
                <.button(^.className := "btn btn-default", ^.color.red, MIcon.close)
              )
            )
          )
        )
      }
      <.div(^.className := "col-md-12",
        <.ul(^.className := "media-list")(p.introductions map renderIntroductions)
      )
    })
    .build

  def apply(introduction: Seq[Introduction]) =
    NotificationList(NotificationListProps(introduction))
}
