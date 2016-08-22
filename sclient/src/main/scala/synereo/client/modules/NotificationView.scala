package synereo.client.modules

import japgolly.scalajs.react.{Callback, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom._
import shared.dtos.{IntroConfirmReq, Introduction}
import synereo.client.css.NotificationViewCSS
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import diode.AnyAction._
import diode.react.ModelProxy
import org.querki.jquery._
import synereo.client.rootmodels.IntroRootModel
import synereo.client.components.MIcon
import synereo.client.handlers.UpdateIntroductionsModel
import synereo.client.logger
import synereo.client.modalpopups.ConfirmIntroReqModal

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}
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
      //      logger.log.info("notifications view mounted")
    }
  }

  val component = ReactComponentB[Props]("NotificationView")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS((t, P, S) => {
      <.div(/*^.className := "container-fluid", NotificationViewCSS.Style.notificationViewContainerMain*/)(
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
      //      if (scope.currentProps.proxy().introResponse.length <= 0) {
      //        window.location.href = "/#dashboard"
      //      }
      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $(".modal-backdrop .fade .in".asInstanceOf[js.Object]).removeClass(".modal-backdrop .fade .in")
    })
    .build

  def apply(proxy: ModelProxy[IntroRootModel]) = component(Props(proxy))
}

object NotificationList {

  case class NotificationListProps(introductions: Seq[Introduction])

  case class State()

  class Backend(t: BackendScope[NotificationListProps, State]) {

    def deleteIntroduction(introduction: Introduction) = {
      val uri = SYNEREOCircuit.zoom(_.user.sessionUri).value
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = false)
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    }

    def handleAllIntroduction(areAccepted: Boolean = false) = {
      val props = t.props.runNow()
      val uri = SYNEREOCircuit.zoom(_.user.sessionUri).value
      props.introductions.foreach {
        introduction =>
          val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = areAccepted)
          SYNEREOCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      }
      $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
    }

    def render(s: State, p: NotificationListProps) = {
      def renderIntroductions(introduction: Introduction) = {
        //        println(s"introduction in notification view: $introduction")
        <.li(^.className := "media")(
          <.div(^.className := "card-shadow")(
            <.div(^.className := "row", NotificationViewCSS.Style.notificationCard)(
              <.div(^.className := "col-md-8")(
                <.div(s"you have introduction request for : ${JSON.parse(introduction.introProfile).name.asInstanceOf[String]}", ^.display.`inline-block`, ^.margin := "20.px")
              ),
              <.div(^.className := "col-md-4")(
                <.div(^.display.`inline-block`,
                  ConfirmIntroReqModal(ConfirmIntroReqModal.Props("details", Seq(NotificationViewCSS.Style.acceptBtn), <.span(MIcon.done), "", introduction))),
                <.div(^.display.`inline-block`,
                  <.button(^.className := "btn btn-default", ^.color.red, ^.onClick --> Callback {
                    deleteIntroduction(introduction)
                  }, <.span(MIcon.close), "dismiss")
                )
              )
            )
          )
        )
      }
      <.div(^.className := "col-md-12",
        <.h1(s"you have ${p.introductions.length} notifications", NotificationViewCSS.Style.notificationCountHeading),
        <.div(^.id := "acceptRejectAllBtnContainer", NotificationViewCSS.Style.acceptRejectAllBtnContainer)(
          <.button(^.className := "btn btn-default", ^.color.green, MIcon.done, NotificationViewCSS.Style.acceptAllRejectAllBtns, ^.onClick --> Callback {
            handleAllIntroduction(true)
          })("Accept all"),
          <.button(^.className := "btn btn-default", ^.color.red, MIcon.close, NotificationViewCSS.Style.acceptAllRejectAllBtns, ^.onClick --> Callback {
            handleAllIntroduction(false)
          })("Reject all")
        ),
        <.ul(^.className := "media-list")(
          p.introductions map renderIntroductions
        )
      )
    }
  }

  val component = ReactComponentB[NotificationListProps]("Dashboard")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .componentDidUpdate(scope => Callback {
      if (scope.currentProps.introductions.length == 0) {
        $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
      }
    })
    //    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(introduction: Seq[Introduction]) = component(NotificationListProps(introduction))

}
