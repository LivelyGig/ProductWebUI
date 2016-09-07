package client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.components.Icon
import client.css.{DashBoardCSS, MessagesCSS, HeaderCSS, PresetsCSS}
import client.modals._
import scalacss.ScalaCssReact._

object Presets {

  case class Props(view: String)

  case class Backend(t: BackendScope[Props, Unit]) {

    def mounted(props: Props): Callback = Callback {

    }

    // scalastyle:off
    def render(p: Props) = {

      // EE: Intend on deleting this.
      <.div(^.display.none)
      // <.div(^.id := "middelNaviContainer", HeaderCSS.Style.profilessmiddelNaviContainer)(
      // )
    }
  }

  private val component = ReactComponentB[Props]("Presets")
    .initialState_P(p => ())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}

