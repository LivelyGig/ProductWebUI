package client.modules

import client.css.DashBoardCSS
import client.css.DashBoardCSS
import client.modules.Searches
import client.modules.Searches
import client.modules.TalentResults
import client.services.LGCircuit
import client.services.LGCircuit
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.css.{DashBoardCSS}
import client.services.LGCircuit
import scalacss.ScalaCssReact._

object AppModule {

  case class Props(view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

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
              <.div(^.id := "searchContainer", ^.className := "col-md-3 col-sm-4 sidebar ", DashBoardCSS.Style.padding0px)(
                LGCircuit.connect(_.searches)(proxy => Searches(Searches.Props(p.view, proxy)))
              ),
              <.div(^.className := "main col-md-9 col-md-offset-3",DashBoardCSS.Style.dashboardResults2)(
                p.view match {
                  case "talent" => TalentResults.component(Unit)
                  case "projects" => LGCircuit.connect(_.jobPosts)(ProjectResults(_))
                  case "contract" => ContractResults.component(Unit)
                  case "messages" => LGCircuit.connect(_.messages)(MessagesResults(_))
                  case "offerings" => OfferingResults.component(Unit)
                  //case "connections"=> ConnectionList(ConnectionList.ConnectionListProps())
                  case "connections" => LGCircuit.connect(_.connections)(ConnectionsResults(_))
                }
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
    .build

  def apply(props: Props) = component(props)
}

