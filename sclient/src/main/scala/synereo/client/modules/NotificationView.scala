package synereo.client.modules

import diode.react.ModelProxy
import japgolly.scalajs.react.{Callback, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import shared.models.UserModel
import synereo.client.components.{Icon, MIcon}
import synereo.client.css.{DashboardCSS, NotificationViewCSS, UserProfileViewCSS}
import synereo.client.modalpopups.{ConfirmIntroReqModal, NewImage}
import synereo.client.modules.UserProfileView.State

import scalacss.ScalaCssReact._
import scala.scalajs.js

/**
  * Created by mandar.k on 7/27/2016.
  */
//scalastyle:off
object NotificationView {

  case class Props()

  case class State()

  class Backend(t: BackendScope[Props, State]) {

    def mounted(props: Props): Callback = Callback {
      //      t.modState(s => s.copy(showLoginForm = true))
      println("user profile view mounted")
    }

    def render(s: State, p: Props) = {
      <.div(^.className := "container-fluid", NotificationViewCSS.Style.notificationViewContainerMain)(
        <.div(^.className := "row")(
          <.div(^.className := "col-md-12")(
            <.div(^.className := "col-md-6 col-md-offset-3")(
              <.div(
                <.ul(^.id := "homeFeedMediaList", ^.className := "media-list cards-list-home-feed", DashboardCSS.Style.homeFeedContainer)(
                  <.li(^.id := "home-feed-card", ^.className := "media", DashboardCSS.Style.CardHolderLiElement)(
                    <.div(^.className := "card-shadow", DashboardCSS.Style.userPost)(
                      <.div(^.className := "row")(
                        <.div(^.className := "col-md-12")(
                          <.div(DashboardCSS.Style.cardDescriptionContainerDiv)(
                            <.div(DashboardCSS.Style.cardText)(
                              "you Have Unseen Notification",
                              ConfirmIntroReqModal(ConfirmIntroReqModal.Props("", Seq(DashboardCSS.Style.confirmIntroReqBtn), MIcon.sms, ""))
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
