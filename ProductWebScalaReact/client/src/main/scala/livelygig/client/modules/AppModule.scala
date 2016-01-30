package livelygig.client.modules

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.css.{MessagesCSS, HeaderCSS, DashBoardCSS}
import livelygig.client.modals._
import scalacss.ScalaCssReact._

object AppModule {

  case class Props(ctl: RouterCtl[Loc], view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    def render(p: Props) = {
      <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
        <.div()(
          p.view match {
            case "talent" => Presets(Presets.Props(p.ctl, "talent"))
            case "projects" => Presets(Presets.Props(p.ctl, "projects"))
            case "contract" => Presets(Presets.Props(p.ctl, "contract"))
            case "messages" => Presets(Presets.Props(p.ctl, "messages"))
            case "offerings" => Presets(Presets.Props(p.ctl, "offerings"))
          }
        ),
        <.div(DashBoardCSS.Style.splitContainer)(
          <.div(^.className := "col-lg-1")(),
          <.div(^.className := "split col-lg-10 col-md-12", ^.paddingRight := "0px")(
            //<.div(^.className := "row")(
              <.div(^.className := "col-xs-3", ^.padding := "0px", ^.overflow := "hidden")(
                p.view match {
                  case "talent" => Searches(Searches.Props(p.ctl, "talent"))
                  case "projects" => Searches(Searches.Props(p.ctl, "projects"))
                  case "contract" => Searches(Searches.Props(p.ctl, "contract"))
                  case "messages" => Searches(Searches.Props(p.ctl, "messages"))
                  case "offerings" => Searches(Searches.Props(p.ctl, "offerings"))
                }
              ),
              <.div(^.className := "col-xs-9", ^.id := "dashboardResults2", DashBoardCSS.Style.dashboardResults2)(
                p.view match {
                  case "talent" => TalentResults.component(p.ctl)
                  case "projects" => ProjectResults.component(p.ctl)
                  case "contract" => ContractResults.component(p.ctl)
                  case "messages" => MessagesResults.component(p.ctl)
                  case "offerings" => OfferingResults.component(p.ctl)
                }
              )
            //)
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

