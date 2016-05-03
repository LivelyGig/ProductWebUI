package client.modules

import client.components.Icon
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.css.{LftcontainerCSS, DashBoardCSS}
import org.querki.jquery._
import scala.scalajs.js
import scalacss.ScalaCssReact._

object AppModule {

  case class Props(view: String)

  def sidebar = Callback {
    val sidebtn: js.Object = "#searchContainer"
    val sidebarIcon: js.Object = "#sidebarIcon"
    val rsltScrollContainer: js.Object = "#rsltScrollContainer"
    val middelNaviContainer: js.Object = "#middelNaviContainer"
    $(sidebtn).toggleClass("sidebar-left sidebar-animate sidebar-md-show")
    if (!$(sidebtn).hasClass("sidebar-left sidebar-animate sidebar-md-show")) {
      $(sidebtn).next().addClass("sidebarRightContainer")
    //  $(rsltScrollContainer).css("pointer-events", "none")
    //  $(middelNaviContainer).css("pointer-events", "none")
    } else {
      $(sidebtn).next().removeClass("sidebarRightContainer")
    // $(rsltScrollContainer).css("pointer-events", "auto")
    // $(middelNaviContainer).css("pointer-events", "auto")
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
      sidebar
    }

    def render(p: Props) = {
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          p.view match {
            case "talent" => Presets(Presets.Props("talent"))
            case "projects" => Presets(Presets.Props("projects"))
            case "contract" => Presets(Presets.Props("contract"))
            case "messages" => Presets(Presets.Props("messages"))
            case "offerings" => Presets(Presets.Props("offerings"))
            case "connections" => Presets(Presets.Props("connections"))
          }
        ),
        <.div(DashBoardCSS.Style.splitContainer, ^.background := "url(./assets/images/background_texture.jpg)")(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "split col-lg-10 col-md-12", DashBoardCSS.Style.paddingRight0px)(
            <.div(^.className := "row")(
              //Left Sidebar
              <.div(^.id := "searchContainer", ^.className := "col-md-3 col-sm-4 sidebar", DashBoardCSS.Style.padding0px)(
                //Adding toggle button for sidebar
                <.button(^.id := "sidebarbtn", ^.`type` := "button", ^.className := "navbar-toggle toggle-left hidden-md hidden-lg", ^.float := "right", "data-toggle".reactAttr := "sidebar", "data-target".reactAttr := ".sidebar-left",
                  ^.onClick --> sidebar)(
                  <.span(^.id := "sidebarIcon", LftcontainerCSS.Style.toggleBtn)(/*Icon.chevronCircleLeft*/)
                ),
                LGCircuit.connect(_.searches)(proxy => Searches(Searches.Props(p.view, proxy)))
              ),
              <.div(^.className := "main col-md-9 col-md-offset-3 sidebarRightContainer", DashBoardCSS.Style.dashboardResults2)(
                <.div(^.onClick --> sidebar)(
                  p.view match {
                    case "talent" => TalentResults.component()
                    case "projects" => LGCircuit.connect(_.jobPosts)(ProjectResults(_))
                    case "contract" => ContractResults.component()
                    case "messages" => LGCircuit.connect(_.messages)(MessagesResults(_))
                    case "offerings" => OfferingResults.component()
                    //case "connections"=> ConnectionList(ConnectionList.ConnectionListProps())
                    case "connections" => LGCircuit.connect(_.connections)(ConnectionsResults(_))
                  }
                )
              )
            )
          ),
          <.div(^.className := "col-lg-1")()
        ) //row
      ) //mainContainer
    }
  }

  private val component = ReactComponentB[Props]("AppModule")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}

