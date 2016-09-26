package client.modules

import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.css.{DashBoardCSS, LftcontainerCSS}
import client.handler.ShowServerError
import client.modals.ServerErrorModal
import org.querki.jquery._

//import client.handlers.{LogoutUser, ShowServerError}
import org.querki.jquery._

import scala.scalajs.js
import scalacss.ScalaCssReact._
import diode.AnyAction._
import diode.react.ModelProxy
import client.rootmodel.AppRootModel

// scalastyle:off
object AppModule {
  val DASHBOARD_VIEW = "dashboard"
  val PROFILES_VIEW = "profiles"
  val PROJECTS_VIEW = "jobs"
  val CONTRACTS_VIEW = "contracts"
  val MESSAGES_VIEW = "messages"
  val OFFERINGS_VIEW = "offerings"
  val CONNECTIONS_VIEW = "connections"
  val NOTIFICATIONS_VIEW = "notifications"

  case class Props(view: String ,proxy: ModelProxy[AppRootModel])

  case class State(showErrorModal: Boolean = false)

  def showSidebar(): Callback = Callback {
    val sidebtn: js.Object = "#searchContainer"
    val overlayContainer: js.Object = "#overlayContainer"
    val sidebarIcon: js.Object = "#sidebarIcon"
    val rsltScrollContainer: js.Object = "#rsltScrollContainer"
    val middelNaviContainer: js.Object = "#middelNaviContainer"
    val profiledescription: js.Object = "DashBoardCSS_Style-profileDescription"
    val profileActionButtons: js.Object = ".profile-action-buttons"
    $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")

    //show
    if (!$(sidebtn).hasClass("sidebar-left sidebar-animate sidebar-md-show")) {
      $(overlayContainer).addClass("overlaySidebar")
      //      $(profiledescription).find(".profile-action-buttons").css("pointer-events", "none")
      //      $(profileActionButtons).css("pointer-events", "none")
    } else
    //hide
    {
      $(overlayContainer).removeClass("overlaySidebar")
      //      $(profileActionButtons).css("pointer-events", "all")
    }
    val t1: js.Object = ".sidebar-left.sidebar-animate.sidebar-md-show > #sidebarbtn > #sidebarIcon"
    val t2: js.Object = ".sidebar > #sidebarbtn > #sidebarIcon"
    $(t2).removeClass("fa fa-chevron-circle-right")
    $(t2).addClass("fa fa-chevron-circle-left")
    $(t1).removeClass("fa fa-chevron-circle-left")
    $(t1).addClass("fa fa-chevron-circle-right")
  }

  def hideSidebar(): Callback = Callback {
    val overlayContainer: js.Object = "#overlayContainer"
    val sidebtn: js.Object = "#searchContainer"

    $(sidebtn).addClass("sidebar-left sidebar-animate sidebar-md-show")
    $(overlayContainer).removeClass("overlaySidebar")
  }


  case class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = {
     showSidebar
      //  LGCircuit.dispatch(ShowServerError(""))
    }

    def serverError(showErrorModal:Boolean=false): Callback = {
      LGCircuit.dispatch(ShowServerError(""))
      if(showErrorModal)
      t.modState(s => s.copy(showErrorModal = false))
      else{
        t.modState(s => s.copy(showErrorModal = false))
      }
    }


    def render(p: Props) = {
      val profilesProxy = LGCircuit.connect(_.profiles)
      val searchesProxy = LGCircuit.connect(_.searches)
      val jobsProxy = LGCircuit.connect(_.jobPosts)
      val messagesProxy = LGCircuit.connect(_.messages)
      val connectionsProxy = LGCircuit.connect(_.connections)
       val appProxy = LGCircuit.connect(_.appRootModel)
      val introProxy =LGCircuit.connect(_.introduction)

      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
         /*^.background := "url(./assets/images/LG_Background3E.svg)", ^.backgroundSize := "101% 101%"*/)(
        <.div()(
          Presets(Presets.Props(p.view))
        ),
        <.div(DashBoardCSS.Style.splitContainer)
          // ^.background := "url(./assets/images/LG_Background3.svg)")
        (
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "split col-lg-10 col-md-12", DashBoardCSS.Style.paddingRight0px)(
            <.div(^.className := "row")(
              //Left Sidebar
              <.div(^.id := "searchContainer", ^.marginBottom := "4px", ^.className := "col-md-3 col-sm-4 sidebar", DashBoardCSS.Style.padding0px)(
                //Adding toggle button for sidebar
                <.button(^.id := "sidebarbtn", ^.`type` := "button", ^.className := "navbar-toggle toggle-left hidden-md hidden-lg", ^.float := "right", "data-toggle".reactAttr := "sidebar", "data-target".reactAttr := ".sidebar-left",
                  ^.onClick --> showSidebar)(
                  <.span(^.id := "sidebarIcon", LftcontainerCSS.Style.toggleBtn)(/*Icon.chevronCircleLeft*/)
                ),
                searchesProxy(searchesProxy => Searches(Searches.Props(p.view, searchesProxy)))
              ),
              if (p.proxy().isServerError){
                ServerErrorModal(ServerErrorModal.Props(serverError, "Server offline"))
              } else {
                <.div()
              },
              <.div(^.id:="overlayContainer", ^.onClick-->hideSidebar()),
              <.div(^.className := "main col-md-9 col-md-offset-3", DashBoardCSS.Style.dashboardResults2)(
                <.div()(
                  p.view match {
                    case PROFILES_VIEW => profilesProxy(ProfilesResults(_))
                    case PROJECTS_VIEW => jobsProxy(ProjectResults(_))
                    case MESSAGES_VIEW => messagesProxy(MessagesResults(_))
                    case CONNECTIONS_VIEW => connectionsProxy(ConnectionsResults(_))
                    case CONTRACTS_VIEW => ContractResults.component()
                    case OFFERINGS_VIEW => OfferingResults.component()
                   // case NOTIFICATIONS_VIEW => connectionsProxy(NotificationResults(_))
                  }
                )
              )
            )
          ),
          <.div(^.className := "col-lg-1")()
        ) //row
      ) // mainContainer
    }
  }

  private val component = ReactComponentB[Props]("AppModule")
    .initialState_P(p => State())
    .renderBackend[Backend]
    //    .componentWillMount(scope => Callback {
    //      val userHasSessionUri = LGCircuit.zoom(_.user.sessionUri).value
    //      if (userHasSessionUri.length < 1)
    //        LGCircuit.dispatch(LogoutUser())
    //    })
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}

