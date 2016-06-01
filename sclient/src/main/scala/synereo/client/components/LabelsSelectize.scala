package synereo.client.components

import shared.RootModels.{SearchesRootModel, ConnectionsRootModel}
import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import synereo.client.handlers.CreateLabels
import synereo.client.services.SYNEREOCircuit

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js

/**
  * Created by Mandar on 5/17/2016.
  */
object LabelsSelectize {

  def getLabelsFromSelectizeInput(selectizeInputId: String): Seq[String] = {
    var selectedLabels = Seq[String]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedLabels :+= $(y).attr("data-value").toString)
//    println(selectedLabels)
    selectedLabels
  }

  var getSelectedValue = new ListBuffer[String]()

  /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)

  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String): Unit = {
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      //      println(s"element lenth: ${$(selectState).length}")
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = "#labelsSelectize"
        //        $(selectizeInput).selectize(SelectizeConfig.maxOptions(2)).destroy()
        //        println(s"test : ${$(selectizeInput)}")
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(3)
          .plugins("remove_button"))
//        $(selectizeInput).selectize(SelectizePlugin.plugins.)
      }

    }

    def getSelectedValues = Callback {
      val selectState: js.Object = "#selectize"
      val getSelectedValue = $(selectState).find("option").text()
      //scalastyle:off
      //      println(getSelectedValue)
    }

    def mounted(props: Props): Callback = Callback {
      //      println("searches model is = " + props.proxy().searchesModel)
      initializeTagsInput(props.parentIdentifier)
    }

    def render(props: Props) = {
      val parentDiv: js.Object = s"#${props.parentIdentifier}"
      //      println(s"parent div length ${$(parentDiv).length}")
      if ($(parentDiv).length == 0) {
        <.select(^.className := "select-state", ^.id := "labelsSelectize", ^.className := "demo-default", ^.placeholder := "search for # or @ ", ^.onChange --> getSelectedValues)(
          <.option(^.value := "")("Select"),
          //          props.proxy().render(searchesRootModel => searchesRootModel.se)
          for (label <- props.proxy().searchesModel) yield {
            <.option(^.value := label.text, ^.key := label.uid)(label.text)
          }
        )
      } else {
        <.div()
      }
    }
  }

  val component = ReactComponentB[Props]("LabelsSelectize")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
