package client.modules


import client.rootmodel.{ IntroRootModel}
import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import client.components.Icon
import client.css.{DashBoardCSS}
import client.handler.UpdateIntroductionsModel
import shared.models.ConnectionsModel
import client.services.LGCircuit
import org.querki.jquery._
import shared.dtos.{IntroConfirmReq, Introduction}
import diode.AnyAction._
import scala.scalajs.js
import scala.scalajs.js.JSON
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 2016-08-12.
  */
object NotificationResults {

  case class Props(proxy: ModelProxy[IntroRootModel])

  case class State(selectedItem: Option[ConnectionsModel] = None)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) =Callback{
      //      log.debug("connection view mounted")
      //      Callback.when(props.proxy().isEmpty)(props.proxy.dispatch(RefreshConnections()))
    }


  }

  // create the React component for Dashboard
  val component = ReactComponentB[Props]("Connection")
    .initialState(State())
    .backend(new Backend(_))
    .renderPS(($, P, S) => {
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div(^.id := "resultsConnectionsContainer")(
          NotificationList(P.proxy().introResponse))
      ) //mainContainer
    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentDidUpdate(scope => Callback {
      //      if (scope.currentProps.proxy().introResponse.length <= 0) {
      //        window.location.href = "/#dashboard"
      //      }
      $("body".asInstanceOf[js.Object]).removeClass("modal-open")
      $(".modal-backdrop".asInstanceOf[js.Object]).remove()
      //      $(".modal-backdrop .fade .in".asInstanceOf[js.Object]).removeClass(".modal-backdrop .fade .in")
    })
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(props: Props) = component(props)
}


object NotificationList {

  case class NotificationListProps(introductions: Seq[Introduction])

  case class State()

  class Backend(t: BackendScope[NotificationListProps, State]) {

    def deleteIntroduction(introduction: Introduction) = {
      val uri = LGCircuit.zoom(_.session.messagesSessionUri).value
      val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = false)
      LGCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
    }

    def handleAllIntroduction(areAccepted: Boolean = false) = {
      val props = t.props.runNow()
      val uri = LGCircuit.zoom(_.session.messagesSessionUri).value
      props.introductions.foreach {
        introduction =>
          println(s"areAccepted ${areAccepted}")
          val introConfirmReq = IntroConfirmReq(uri, alias = "alias", introduction.introSessionId, introduction.correlationId, accepted = areAccepted)
          LGCircuit.dispatch(UpdateIntroductionsModel(introConfirmReq))
      }
      $("#acceptRejectAllBtnContainer".asInstanceOf[js.Object]).addClass("hidden")
    }

    def render(s: State, p: NotificationListProps) = {
      def renderIntroductions(introduction: Introduction) = {
        //        println(s"introduction in notification view: $introduction")
        <.li(^.className := "media")(
          <.div(^.className := "card-shadow")(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-8")(
                <.div(s"you have introduction request for : ${JSON.parse(introduction.introProfile).name.asInstanceOf[String]}", ^.display.`inline-block`, ^.margin := "20.px")
              ),
              <.div(^.className := "col-md-4")(
                <.div(^.display.`inline-block`,
                  ConfirmIntroReqModal(ConfirmIntroReqModal.Props("details", Seq(), <.span(Icon.check), "", introduction))),
                <.div(^.display.`inline-block`,
                  <.button(^.className := "btn btn-default", ^.color.red, ^.onClick --> Callback {
                    deleteIntroduction(introduction)
                  }, <.span(Icon.close), "dismiss")
                )
              )
            )
          )
        )
      }
      <.div(^.className := "col-md-12",
        <.div(DashBoardCSS.Style.notificationsText)(s"you have ${p.introductions.length} notifications"),
        <.div(^.id := "acceptRejectAllBtnContainer", DashBoardCSS.Style.notificationsText)(
          <.button(^.className := "btn btn-default", ^.color.green, Icon.check ,^.onClick --> Callback {
            handleAllIntroduction(true)
          })("Accept all"),
          <.button(^.className := "btn btn-default", ^.color.red, Icon.close,  ^.onClick --> Callback {
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
    .build

  def apply(introduction: Seq[Introduction]) = component(NotificationListProps(introduction))

}

