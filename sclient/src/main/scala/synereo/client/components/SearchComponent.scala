package synereo.client.components

import japgolly.scalajs.react.{ReactElement, _}
import synereo.client.handlers.{RefreshMessages, StoreCnxnAndLabels, StoreMessagesLabels}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import synereo.client.css.SynereoCommanStylesCSS
import synereo.client.services.{CoreApi, SYNEREOCircuit}

import scala.scalajs.js
import scalacss.ScalaCssReact._
import scala.scalajs.js
import org.querki.jquery._
import shared.sessionitems.SessionItems

import scala.language.reflectiveCalls

//scalastyle:off
object SearchComponent {

  case class Props()

  case class State(labelSelectizeInputId: String = "SearchComponentLblSltz", connectionsSelectizeInputId: String = "SearchComponentCnxnSltz")

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = Callback {

    }

    def searchWithLblAndCnxn(e: ReactEventI) = Callback {
      SYNEREOCircuit.dispatch(StoreMessagesLabels(Some(t.state.runNow().labelSelectizeInputId)))
      SYNEREOCircuit.dispatch(StoreCnxnAndLabels(Some(t.state.runNow().labelSelectizeInputId),
        Some(t.state.runNow().connectionsSelectizeInputId), SessionItems.MessagesViewItems.MESSAGES_SESSION_URI))
      SYNEREOCircuit.dispatch(RefreshMessages())
    }

    def render(s: State, p: Props) = {
      <.div(SynereoCommanStylesCSS.Style.searchBoxContainer)(
        <.div(^.id := s.connectionsSelectizeInputId, SynereoCommanStylesCSS.Style.selectizeSearchComponent)(
          SYNEREOCircuit.connect(_.connections)(conProxy => ConnectionsSelectize(ConnectionsSelectize.Props(conProxy, s.connectionsSelectizeInputId)))
        ),
        <.div(^.id := s.labelSelectizeInputId, SynereoCommanStylesCSS.Style.selectizeSearchComponent)(
          SYNEREOCircuit.connect(_.searches)(searchesProxy => LabelsSelectize(LabelsSelectize.Props(searchesProxy, s.labelSelectizeInputId)))
        ),
        <.button(^.className := "btn btn-primary", ^.onClick ==> searchWithLblAndCnxn, SynereoCommanStylesCSS.Style.searchBtn)(MIcon.apply("search", "24")
        )
      )
    }
  }

  val component = ReactComponentB[Props]("SearchComponent")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}