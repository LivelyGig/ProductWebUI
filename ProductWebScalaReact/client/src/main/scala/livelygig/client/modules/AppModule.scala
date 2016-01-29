  package livelygig.client.modules

  import japgolly.scalajs.react.extra.router.RouterCtl
  import japgolly.scalajs.react._
  import japgolly.scalajs.react.vdom.prefix_<^._
  import livelygig.client.LGMain.Loc
  import livelygig.client.css.{MessagesCSS, HeaderCSS, DashBoardCSS}
  import livelygig.client.modals._
  import scalacss.ScalaCssReact._

  object AppModule {
    case class Props(ctl: RouterCtl[Loc], view :String)
    case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }
    def render( p: Props) = {
           <.div(^.id := "mainContainer", DashBoardCSS.Style.mainContainerDiv)(
            <.div()(
              p.view match {
                case "talent"=>  Presets(Presets.Props(p.ctl,"talent"))
                case "projects"=>  Presets(Presets.Props(p.ctl,"projects"))
                case  "contract" =>   Presets(Presets.Props(p.ctl,"contract"))
                case "messages"  =>  Presets(Presets.Props(p.ctl,"messages"))
              }
            ),
             <.div(DashBoardCSS.Style.splitContainer)(
              <.div(^.className := "split")(
                <.div(^.className := "row")(
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                    p.view match {
                      case "talent"=> Searches(Searches.Props(p.ctl,"talent"))
                      case "projects"=> Searches(Searches.Props(p.ctl,"projects"))
                      case  "contract" =>  Searches(Searches.Props(p.ctl,"contract"))
                      case "messages"  => Searches(Searches.Props(p.ctl,"messages"))
                    }
                  ),
                  <.div(^.className := "col-md-10 col-sm-10 col-xs-10", ^.id := "dashboardResults2", DashBoardCSS.Style.dashboardResults2)(
                    p.view match {
                      case "talent"=> TalentResults.component(p.ctl)
                      case "projects"=> ProjectResults.component(p.ctl)
                      case "contract" =>  ContractResults.component(p.ctl)
                      case "messages"  => MessagesResults.component(p.ctl)
                    }
                  )
                )
              )
            )//row
          ) //mainContainer
      }
  }
    private val component = ReactComponentB[Props]("AppModule")
      .initialState_P(p =>())
      .renderBackend[Backend]
      .build
    def apply(props: Props) = component(props)
  }

