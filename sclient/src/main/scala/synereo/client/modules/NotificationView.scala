package synereo.client.modules

import japgolly.scalajs.react.{Callback, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.dtos.{Introduction}
import synereo.client.css.NotificationViewCSS
import diode.react.ModelProxy
import synereo.client.rootmodels.IntroRootModel
import synereo.client.components.MIcon
import synereo.client.modalpopups.ConfirmIntroReqModal
import scala.scalajs.js.JSON
import scalacss.ScalaCssReact._
import japgolly.scalajs.react.BackendScope
import org.querki.jquery._
import shared.dtos.{IntroConfirmReq, Introduction}
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import scala.scalajs.js

/**
  * Created by mandar.k on 7/27/2016.
  */
//scalastyle:off

object NotificationView {

  case class Props(proxy: ModelProxy[IntroRootModel])

  case class State()

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props): Callback = Callback {
      //      logger.log.info("notifications view mounted")
    }
  }

  val component = ReactComponentB[Props]("NotificationView")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      <.div()(
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
      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
    })
    .build

  def apply(proxy: ModelProxy[IntroRootModel]) = component(Props(proxy))
}

object NotificationList {

  case class Props(introductions: Seq[Introduction])

  case class State()


  class NotificationsListBackend(t: BackendScope[Props, State]) {

    def deleteIntroduction(introduction: Introduction) = {
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = false)
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    }

    def handleAllIntroduction(areAccepted: Boolean = false) = {
      val props = t.props.runNow()
      val uri = SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value
      props.introductions.foreach {
        introduction =>
          val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = areAccepted)
          SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      }
      $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
    }
  }

  val component = ReactComponentB[Props]("Dashboard")
    .initialState_P(p => State())
    .backend(new NotificationsListBackend(_))
    .renderPS((t, P, S) => {
      def renderIntroductions(introduction: Introduction) = {
        //        println(s"introduction in notification view: $introduction")
        <.li(^.className := "media")(
          <.div(^.className := "card-shadow")(
            <.div(^.className := "row", NotificationViewCSS.Style.notificationCard)(
              <.div(^.className := "col-md-8")(
                <.div(s"You have introduction request for : ${JSON.parse(introduction.introProfile).name.asInstanceOf[String]}", ^.display.`inline-block`, ^.margin := "20.px")
              ),
              <.div(^.className := "col-md-4")(
                <.div(^.display.`inline-block`,
                  ConfirmIntroReqModal(ConfirmIntroReqModal.Props("details", Seq(NotificationViewCSS.Style.acceptBtn), <.span(MIcon.done), "", introduction))),
                <.div(^.display.`inline-block`,
                  <.button(^.className := "btn btn-default", ^.color.red, ^.onClick --> Callback {
                    t.backend.deleteIntroduction(introduction)
                  }, <.span(MIcon.close), "dismiss")
                )
              )
            )
          )
        )
      }
      <.div(^.className := "col-md-12",
        <.h1(s"You have ${P.introductions.length} notifications", NotificationViewCSS.Style.notificationCountHeading),
        <.div(^.id := "acceptRejectAllBtnContainer", NotificationViewCSS.Style.acceptRejectAllBtnContainer)(
          <.button(^.className := "btn btn-default", ^.color.green, MIcon.done, NotificationViewCSS.Style.acceptAllRejectAllBtns, ^.onClick --> Callback {
            t.backend.handleAllIntroduction(true)
          })("Accept all"),
          <.button(^.className := "btn btn-default", ^.color.red, MIcon.close, NotificationViewCSS.Style.acceptAllRejectAllBtns, ^.onClick --> Callback {
            t.backend.handleAllIntroduction(false)
          })("Reject all")
        ),
        <.ul(^.className := "media-list")(
          P.introductions map renderIntroductions
        )
      )
    })
    .componentDidUpdate(scope => Callback {
      if (scope.currentProps.introductions.length == 0) {
        $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
      }
    })
    //    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(introduction: Seq[Introduction]) = component(Props(introduction))

}
