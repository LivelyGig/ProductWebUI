package synereo.client.modules

import diode.react.ModelProxy
import synereo.client.css.ConnectionsCSS
import synereo.client.services.SYNEREOCircuit
import synereo.client.css.MarketPlaceFullCSS
import org.querki.jquery._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.RootModels.AppRootModel
import synereo.client.handlers.ShowServerError
import diode.AnyAction._
import synereo.client.modalpopups.LoginErrorModal

import scala.scalajs.js
import scalacss.ScalaCssReact._

/**
  * Created by bhagyashree.b on 5/24/2016.
  */


object AppModule {
  val PEOPLE_VIEW = "people"
  val ACCOUNT_VIEW = "account"
  val DASHBOARD_VIEW = "dashboard"
  val POSTFULL_VIEW = "postfullview"
  val USERPROFILE_VIEW = "userprofile"
  val TIMELINE_VIEW = "timeline"
  val NOTIFICATIONS_VIEW = "notifications"
  val MARKETPLACE_VIEW = "marketplace"

  val userProxy = SYNEREOCircuit.connect(_.user)
  val connectionProxy = SYNEREOCircuit.connect(_.connections)
  val messagesProxy = SYNEREOCircuit.connect(_.messages)
  val introductionProxy = SYNEREOCircuit.connect(_.introduction)

  val searchContainer: js.Object = "#searchContainer"

  case class Props(view: String, proxy : ModelProxy[AppRootModel])
  case class State(showErrorModal : Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {

    def serverError(): Callback = {
      SYNEREOCircuit.dispatch(ShowServerError())
      t.modState(s => s.copy(showErrorModal = false))
    }

    def GetErrormodal() : Callback = {
      SYNEREOCircuit.dispatch(ShowServerError())
      val getIsServerError = SYNEREOCircuit.zoom(_.appRootModel).value
      t.modState(s => s.copy(showErrorModal = getIsServerError.isServerError))
    }

    def mounted(props: Props) = Callback {

    }

    def render(p: Props,state: State) = {
      <.div(
        <.div(^.id := "connectionsContainerMain", ConnectionsCSS.Style.connectionsContainerMain)(
          <.div(),
          <.div(^.className := "row")(
            //Left Sidebar
            <.div(^.id := "searchContainer", ^.className := "col-md-2 sidebar sidebar-left sidebar-animate sidebar-lg-show ",
              ^.onMouseEnter --> Callback {
                $(searchContainer).removeClass("sidebar-left sidebar-animate sidebar-lg-show")
              },
              ^.onMouseLeave --> Callback {
                $(searchContainer).addClass("sidebar-left sidebar-animate sidebar-lg-show")
              }
            )(Sidebar(Sidebar.Props()))/*,
            <.div(^.onClick --> GetErrormodal() )("CLick Me"),
            // GetErrormodal(),
            if(state.showErrorModal){
              LoginErrorModal(LoginErrorModal.Props(serverError))
            }
            else
              {<.div()}*/

          ),
          <.div(
            p.view match {
              case PEOPLE_VIEW => connectionProxy(s => ConnectionsResults(s))
              case ACCOUNT_VIEW => userProxy(s => AccountInfo(s))
              case DASHBOARD_VIEW => messagesProxy(s => Dashboard(s))
              case USERPROFILE_VIEW => userProxy(s => UserProfileView(s))
              case POSTFULL_VIEW => PostFullView(PostFullView.Props())
              case TIMELINE_VIEW => TimelineView(TimelineView.Props())
              case NOTIFICATIONS_VIEW => introductionProxy(s => NotificationView(s))
              case MARKETPLACE_VIEW => MarketPlaceFull(MarketPlaceFull.Props())
            }
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("AppModule")
    .initialState_P(p => (State()))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
