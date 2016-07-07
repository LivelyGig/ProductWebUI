package synereo.client.components

import shared.RootModels.{ConnectionsRootModel, SearchesRootModel}
import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import shared.models.{ConnectionsModel, Label}
import synereo.client.handlers.CreateLabels
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.LabelsUtils

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js

/**
  * Created by mandar.k on 7/5/2016.
  */
//scalastyle:off
object LabelConnectionSelectize {

  case class Props(lblCnxnSlctzId: String)

  case class State(labelModels: Seq[Label], connectionModels: Seq[ConnectionsModel])

  case class Backend(t: BackendScope[Props, State]) {
    def initializeSelectizeInput(parentIdentifier: String): Unit = {
      val state = t.state.runNow()
      println(s"labelModels: ${state.labelModels}")
      println(s"connectionModels: ${state.connectionModels}")
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = s"#${t.props.runNow().lblCnxnSlctzId}-selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(5))
      }
    }

    def mounted(props: Props): Callback = Callback {
      println("mounted LabelConnectionSelectize")
      val labelsFromCircuit = SYNEREOCircuit.zoom(_.searches.searchesModel).value
      val connectionsFromCircuit = SYNEREOCircuit.zoom(_.connections.get.connectionsResponse).value
      //      t.modState(s => s.copy(connectionModels = connectionsFromCircuit,labelModels = labelsFromCircuit))
      t.setState(s = State(labelsFromCircuit, connectionsFromCircuit))
      initializeSelectizeInput(props.lblCnxnSlctzId)
    }

    def render(props: Props, state: State) = {
      val parentDiv: js.Object = s"#${props.lblCnxnSlctzId}"
      <.div()(
        if ($(parentDiv).length == 0) {
          <.select(^.className := "select-state", ^.id := s"${props.lblCnxnSlctzId}-selectize", ^.className := "demo-default", ^.placeholder := "Use # for tag or @ for connection")(
            <.option(^.value := "")("Select"),
            for (label <- state.labelModels) yield {
              <.option(^.value := upickle.default.write(label), ^.key := label.uid)(s"#${label.text}")
            },
            for (connection <- state.connectionModels) yield {
              <.option(^.value := upickle.default.write(connection.connection), ^.key := connection.connection.target)(s"@${connection.name}")
            }
          )
        } else {
          <.div("nothing to show")
        }
      )
    }
  }

  val component = ReactComponentB[Props]("LabelConnectionSelectize")
    .initialState_P(p => State(labelModels = Seq(), connectionModels = Seq()))
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}

