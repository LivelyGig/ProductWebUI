package synereo.client.components

import synereo.client.handlers._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import synereo.client.css.SynereoCommanStylesCSS
import synereo.client.services.SYNEREOCircuit
import diode.AnyAction._
import shared.dtos._
import shared.models.Label
import synereo.client.sessionitems.SessionItems
import org.scalajs.dom

import scalacss.ScalaCssReact._
import synereo.client.utils
import synereo.client.utils.{AppUtils, ConnectionsUtils, LabelsUtils}

import scala.language.reflectiveCalls

//scalastyle:off
object SearchComponent {

  case class Props()

  case class State(labelSelectizeInputId: String = "SearchComponentLblSltz", connectionsSelectizeInputId: String = "SearchComponentCnxnSltz")

  val searchesProxy = SYNEREOCircuit.connect(_.searches)

  class Backend(t: BackendScope[Props, State]) {
    def mounted(props: Props) = Callback {

    }

    def fromSelecize(): Callback = Callback {}

    def searchWithLblAndCnxn(e: ReactEventI) = Callback {
      val (cnxns, labels) = ConnectionsLabelsSelectize
        .getCnxnsAndLabelsFromSelectize(t.state.runNow().connectionsSelectizeInputId)
      val cnxnToPost = ConnectionsUtils.getCnxnForReq(cnxns)
      val searchLabels = LabelsUtils.buildProlog(
        Seq(Label(text = AppUtils.MESSAGE_POST_LABEL)) ++ labels.map(currentLabel => Label(text = currentLabel)
        ), LabelsUtils.PrologTypes.Each)
      val expr = Expression("feedExpr", ExpressionContent(cnxnToPost, searchLabels))
      SYNEREOCircuit.dispatch(CancelPreviousAndSubscribeNew(SubscribeRequest(SYNEREOCircuit.zoom(_.user.sessionUri).value, expr)))
    }

    def render(s: State, p: Props) = {
      <.div(
        <.div(^.id := s.connectionsSelectizeInputId, SynereoCommanStylesCSS.Style.searchBoxContainer)(
          searchesProxy(proxy => ConnectionsLabelsSelectize(ConnectionsLabelsSelectize.Props(s.connectionsSelectizeInputId, proxy)))),
        //          ConnectionsLabelsSelectize(ConnectionsLabelsSelectize.Props(s.connectionsSelectizeInputId))),
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