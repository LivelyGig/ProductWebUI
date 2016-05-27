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
  val TALENTS_VIEW = "talent"
  val PROJECTS_VIEW = "projects"
  val CONTRACTS_VIEW = "contract"
  val MESSAGES_VIEW = "messages"
  val OFFERINGS_VIEW = "offerings"
  val CONNECTIONS_VIEW = "connections"

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
    } else {
      $(sidebtn).next().removeClass("LftcontainerCSS_Style-sidebarRightContainer")
//      e.stopPropagation()
      $(profileActionButtons).css("pointer-events", "all")
      //  $(profileActionButtons).find("#searchContainer").removeClass("sidebar-left sidebar-animate sidebar-md-show")
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
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          Presets(Presets.Props(p.view))
        ),
        <.div(DashBoardCSS.Style.splitContainer, ^.background := "url(./assets/images/background_texture.jpg)")(
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
                LGCircuit.connect(_.searches)(proxy => Searches(Searches.Props(p.view, proxy)))
              ),
              <.div(^.className := "main col-md-9 col-md-offset-3 LftcontainerCSS_Style-sidebarRightContainer", DashBoardCSS.Style.dashboardResults2)(
                <.div(^.onClick --> showSidebar)(
                  p.view match {
                    case "talent" => TalentResults.component()
                    case "projects" => LGCircuit.connect(_.jobPosts)(ProjectResults(_))
                    case "contract" => ContractResults.component()
                    case "messages" => LGCircuit.connect(_.messages)(MessagesResults(_))
                    case "offerings" => OfferingResults.component()
                    case "connections" => LGCircuit.connect(_.connections)(ConnectionsResults(_))
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

