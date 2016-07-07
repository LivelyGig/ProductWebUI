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
import shared.models.Label
import synereo.client.handlers.CreateLabels
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils.LabelsUtils

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js

/**
  * Created by mandar.k on 5/17/2016.
  */
//scalastyle:off
object LabelsSelectize {
  def getLabelsFromSelectizeInput(selectizeInputId: String): Seq[Label] = {
    var selectedLabels = Seq[Label]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedLabels :+= upickle.default.read[Label]($(y).attr("data-value").toString))
    //    println(selectedLabels)
    selectedLabels
  }

  var getSelectedValue = new ListBuffer[String]()

  /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)

  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String): Unit = {
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = s"#${t.props.runNow().parentIdentifier}-selectize"
        $(selectizeInput).selectize(SelectizeConfig
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
      //      println("searches model is = " + props.proxy().searchesModel)
      initializeTagsInput(props.parentIdentifier)
    }

    def render(props: Props) = {
      val parentDiv: js.Object = s"#${props.parentIdentifier}"
      //      println(s"parent div length ${$(parentDiv).length}")
      if ($(parentDiv).length == 0) {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Use # for tag", ^.onChange --> getSelectedValues)(
          <.option(^.value := "")("Select"),
          //          props.proxy().render(searchesRootModel => searchesRootModel.se)
          for (label <- props.proxy().searchesModel.distinct.toSet.toSeq
            .filter(e => e.parentUid == "self")
            .filterNot(e => LabelsUtils.getSystemLabels().contains(e.text))) yield {
            <.option(^.value := upickle.default.write(label), ^.key := label.uid)(s"#${label.text}")
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
