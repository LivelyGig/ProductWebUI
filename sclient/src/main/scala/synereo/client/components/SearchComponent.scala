package synereo.client.components

import synereo.client.handlers.{DeleteMsgsModel, RefreshMessages}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.css.SynereoCommanStylesCSS
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._

import scalacss.ScalaCssReact._
import synereo.client.utils

import scala.language.reflectiveCalls

//scalastyle:off
object SearchComponent {

  case class Props()

  case class State(labelSelectizeInputId: String = "SearchComponentLblSltz", connectionsSelectizeInputId: String = "SearchComponentCnxnSltz")

  val getSearches =    SYNEREOCircuit.connect(_.searches)

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = Callback {

    }

    def fromSelecize() : Callback = Callback{}

    def searchWithLblAndCnxn(e: ReactEventI) = Callback {
      val cnxnLabels = ConnectionsLabelsSelectize.getCnxnsAndLabelsFromSelectize(t.state.runNow().connectionsSelectizeInputId)
      utils.MessagesUtils.storeCnxnAndLabels(cnxnLabels._1,cnxnLabels._2)
      SYNEREOCircuit.dispatch(DeleteMsgsModel())
      SYNEREOCircuit.dispatch(RefreshMessages())
    }

    def render(s: State, p: Props) = {
      <.div(/*SynereoCommanStylesCSS.Style.searchBoxContainer*/)(
        /*<.div(^.id := s.connectionsSelectizeInputId, SynereoCommanStylesCSS.Style.selectizeSearchComponent)(
          ConnectionsSelectize(ConnectionsSelectize.Props(s.connectionsSelectizeInputId))
        ),
        <.div(^.id := s.labelSelectizeInputId, SynereoCommanStylesCSS.Style.selectizeSearchComponent)(
          LabelsSelectize(LabelsSelectize.Props(s.labelSelectizeInputId))
        ),
        <.button(^.className := "btn btn-primary", ^.onClick ==> searchWithLblAndCnxn, SynereoCommanStylesCSS.Style.searchBtn)(MIcon.apply("search", "24")
        )*/
        <.div(^.id := s.connectionsSelectizeInputId,SynereoCommanStylesCSS.Style.searchBoxContainer)(
          ConnectionsLabelsSelectize(ConnectionsLabelsSelectize.Props(s.connectionsSelectizeInputId))),
        <.div(SynereoCommanStylesCSS.Style.displayInline)(
          <.button(^.className := "btn btn-primary", SynereoCommanStylesCSS.Style.searchBtn, ^.onClick ==> searchWithLblAndCnxn)(MIcon.apply("search", "24")
          ))
      )
    }
  }

  val component = ReactComponentB[Props]("SearchComponent")
    .initialState_P(p => State())
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}