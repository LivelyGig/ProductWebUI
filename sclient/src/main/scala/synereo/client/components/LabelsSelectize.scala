package synereo.client.components

import synereo.client.rootmodels.{ConnectionsRootModel, SearchesRootModel}
import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import shared.models.Label
import synereo.client.handlers.{CreateLabels}
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.LabelsUtils

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js
import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js
import shared.dtos.Connection
import diode.AnyAction._

import scala.scalajs.js.timers._

/**
  * Created by mandar.k on 5/17/2016.
  */
//scalastyle:off
object LabelsSelectize {
  def getLabelsTxtFromSelectize(selectizeInputId: String): Seq[String] = {
    var selectedLabels = Seq[String]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"
    if ($(selector).length > 0) {
      $(selector).each((y: Element) => selectedLabels :+= $(y).attr("data-value").toString)
    } else {
      selectedLabels = Nil
    }

    selectedLabels
  }

  var getSelectedValue = new ListBuffer[String]()

  /*Seq[Label]()*/
  case class Props(parentIdentifier: String)

  case class State(labels: Seq[Label] = Nil)

  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = s"#${t.props.runNow().parentIdentifier}-selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .create(true)
          .maxItems(3)
          .plugins("remove_button"))
      }

    }

    def getSelectedValues = Callback {
      val selectState: js.Object = "#selectize"
      val getSelectedValue = $(selectState).find("option").text()
      //      println(getSelectedValue)
    }

    def mounted(props: Props): Callback = Callback {
      //      println("inside mounted:" + props.proxy().searchesModel)
      //      if (props.proxy().searchesModel.length != 0) {
      initializeTagsInput()
      //      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.connections))(_ => attachLabels())


      //      }
    }

    /*
        def attachLabels() = {
          val value = SYNEREOCircuit.zoom(_.searches.searchesModel).value
          t.modState(s => s.copy(labels = value)).runNow()
        }

        def componentDidUpdate(props: Props): Callback = Callback {
          // println("component did update")
            initializeTagsInput()
        }*/

    //    def willMount(props: Props): Callback = Callback.when(SYNEREOCircuit.zoom(_.searches).value.searchesModel.isEmpty)(Callback{SYNEREOCircuit.dispatch(CreateLabels())})

    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Use # for tag", ^.onChange --> getSelectedValues)(
        <.option(^.value := "")("Select"),
        //          props.proxy().render(searchesRootModel => searchesRootModel.se)
        for (label <- SYNEREOCircuit.zoom(_.searches.searchesModel).value) yield {
          <.option(^.value := label.text, ^.key := label.uid)(s"#${label.text}")
        })

    }
  }

  val component = ReactComponentB[Props]("LabelsSelectize")
    .initialState(State())
    .renderBackend[Backend]
    //    .componentWillMount(scope => scope.backend.willMount(scope.props))
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    //    .componentDidUpdate(scope => scope.$.backend.componentDidUpdate(scope.currentProps))
    .build

  def apply(props: Props) = component(props)
}
