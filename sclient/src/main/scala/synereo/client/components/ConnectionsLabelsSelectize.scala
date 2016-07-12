package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.Connection
import shared.models.ConnectionsModel
import synereo.client.handlers.{CreateLabels, RefreshConnections}
import synereo.client.services.SYNEREOCircuit

import scala.language.existentials
import scala.scalajs.js
import diode.AnyAction._


/**
  * Created by shubham.k on 4/6/2016.
  */
//scalastyle:off
object ConnectionsLabelsSelectize {
  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
    var selectedConnections = Seq[Connection]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedConnections :+= upickle.default.read[Connection]($(y).attr("data-value").toString))
    selectedConnections
  }


  case class Props(parentIdentifier: String)

  case class State (connections : Seq[ConnectionsModel] = Nil)

  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      val selectizeInput: js.Object =  s"#${parentIdentifier}-selectize"
//      $(selectizeInput).selectize()
      $(selectizeInput).selectize(SelectizeConfig
        .maxItems(30)
        .plugins("remove_button")
      )
    }

    def mounted(props: Props): Callback = Callback{
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.connections))(_ => attachConnections())
    }

    def attachConnections() = {
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }
    }
    def willMount (props: Props) = Callback {
      if (SYNEREOCircuit.zoom(_.connections).value.isEmpty) {
        SYNEREOCircuit.dispatch(RefreshConnections())
      }
      if (SYNEREOCircuit.zoom(_.searches).value.searchesModel.isEmpty) {
        SYNEREOCircuit.dispatch(CreateLabels())
      }
    }



    def componentDidUpdate(props: Props): Callback = Callback {
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        initializeTagsInput()
      }

    }

    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo"/*, ^.onChange --> getSelectedValues*/)(
        <.option(^.value := "")("Select"),
        for (connection <- state.connections) yield <.option(^.value := upickle.default.write(connection.connection),
          ^.key := connection.connection.target)(s"@${connection.name}"),
      for (label <- SYNEREOCircuit.zoom(_.searches).value.searchesModel) yield
        <.option(^.value := label.text, ^.key := label.uid)(s"#${label.text}"))

    }
  }

  val component = ReactComponentB[Props]("SearchesConnectionList")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentWillMount(scope => scope.backend.willMount(scope.props))
    .componentDidUpdate(scope => scope.$.backend.componentDidUpdate(scope.currentProps))
    //    .componentWillUpdate(scope => scope.)
    .build

  def apply(props: Props) = component(props)
}
