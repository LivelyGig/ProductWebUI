package client.modules

import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.css.{ LftcontainerCSS, DashBoardCSS }
import org.querki.jquery._
import scala.scalajs.js
import scalacss.ScalaCssReact._

// scalastyle:off
object AppModule {
  val DASHBOARD_VIEW    = "dashboard"
  val PROFILES_VIEW     = "profiles"
  val PROJECTS_VIEW     = "jobs"
  val CONTRACTS_VIEW    = "contracts"
  val MESSAGES_VIEW     = "messages"
  val OFFERINGS_VIEW    = "offerings"
  val CONNECTIONS_VIEW  = "connections"

  case class Props(view: String)

  def showSidebar(): Callback = Callback {
    val sidebtn: js.Object = "#searchContainer"
    val sidebarIcon: js.Object = "#sidebarIcon"
    val rsltScrollContainer: js.Object = "#rsltScrollContainer"
    val middelNaviContainer: js.Object = "#middelNaviContainer"
    val profiledescription: js.Object = "DashBoardCSS_Style-profileDescription" /*".profile-description"*/
    val profileActionButtons: js.Object = ".profile-action-buttons"
    $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
    if (!$(sidebtn).hasClass("sidebar-left sidebar-animate sidebar-md-show")) {
      $(sidebtn).next().addClass("LftcontainerCSS_Style-sidebarRightContainer")
      $(profiledescription).find(".profile-action-buttons").css("pointer-events", "none")
      $(profileActionButtons).css("pointer-events", "none")
    } else {
      $(sidebtn).next().removeClass("LftcontainerCSS_Style-sidebarRightContainer")
//      e.stopPropagation()
      $(profileActionButtons).css("pointer-events", "all")
    }
    val t1: js.Object = ".sidebar-left.sidebar-animate.sidebar-md-show > #sidebarbtn > #sidebarIcon"
    val t2: js.Object = ".sidebar > #sidebarbtn > #sidebarIcon"
    $(t2).removeClass("fa fa-chevron-circle-right")
    $(t2).addClass("fa fa-chevron-circle-left")
    $(t1).removeClass("fa fa-chevron-circle-left")
    $(t1).addClass("fa fa-chevron-circle-right")
  }

  case class Backend(t: BackendScope[Props, Unit]) {
    def mounted(props: Props) = {
      showSidebar
    }
    def render(p: Props) = {
      val profilesProxy = LGCircuit.connect(_.profiles)
      val searchesProxy = LGCircuit.connect(_.searches)
      val jobsProxy = LGCircuit.connect(_.jobPosts)
      val messagesProxy = LGCircuit.connect(_.messages)
      val connectionsProxy = LGCircuit.connect(_.connections)
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          Presets(Presets.Props(p.view))
        ),
        <.div(DashBoardCSS.Style.splitContainer, ^.background := "url(./assets/images/LG_Background.jpg)")(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "split col-lg-10 col-md-12", DashBoardCSS.Style.paddingRight0px)(
            <.div(^.className := "row")(
              //Left Sidebar
              <.div(^.id := "searchContainer", ^.className := "col-md-3 col-sm-4 sidebar", DashBoardCSS.Style.padding0px)(
                //Adding toggle button for sidebar
                <.button(^.id := "sidebarbtn", ^.`type` := "button", ^.className := "navbar-toggle toggle-left hidden-md hidden-lg", ^.float := "right", "data-toggle".reactAttr := "sidebar", "data-target".reactAttr := ".sidebar-left",
                  ^.onClick --> showSidebar)(
                    <.span(^.id := "sidebarIcon", LftcontainerCSS.Style.toggleBtn)( /*Icon.chevronCircleLeft*/ )
                  ),
                searchesProxy(searchesProxy => Searches(Searches.Props(p.view, searchesProxy)))
              ),
              <.div(^.className := "main col-md-9 col-md-offset-3 LftcontainerCSS_Style-sidebarRightContainer", DashBoardCSS.Style.dashboardResults2)(
                <.div(^.onClick --> showSidebar)(
                  p.view match {
                    case PROFILES_VIEW     => profilesProxy(ProfilesResults(_))
                    case PROJECTS_VIEW     => jobsProxy(ProjectResults(_))
                    case MESSAGES_VIEW     => messagesProxy(MessagesResults(_))
                    case CONNECTIONS_VIEW  => connectionsProxy(ConnectionsResults(_))
                    case CONTRACTS_VIEW    => ContractResults.component()
                    case OFFERINGS_VIEW    => OfferingResults.component()
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
    .initialState_P(p => ())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}

