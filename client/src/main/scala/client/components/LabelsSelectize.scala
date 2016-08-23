package client.components

import client.utils.LabelsUtils
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import client.rootmodel.SearchesRootModel
import shared.models.Label
import client.sessionitems.SessionItems

import scala.collection.mutable.ListBuffer
import scala.language.existentials
import scala.scalajs.js

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

  def getLabelsFromSelectizeInput(selectizeInputId: String): Seq[Label] = {
    var selectedLabels = Seq[Label]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedLabels :+= upickle.default.read[Label]($(y).attr("data-value").toString))
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
          .create(true)
          .maxItems(3)
          .plugins("remove_button"))
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
        <.select(^.className := "select-state", ^.id := "labelsSelectize", ^.className := "demo-default", ^.placeholder := "select #label(s)", ^.onChange --> getSelectedValues)(
          <.option(^.value := "")("Select"),
          //          props.proxy().render(searchesRootModel => searchesRootModel.se)
          for (label <- props.proxy().searchesModel
            .filter(e => e.parentUid == "self")
            .filterNot(e => LabelsUtils.getSystemLabels().contains(e.text))) yield {
            <.option(^.value := upickle.default.write(label), ^.key := label.uid)(label.text)
          }
        )
      } else {
        <.div()
      }
    }
  }

  val component = ReactComponentB[Props]("SearchesConnectionList")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
