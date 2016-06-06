package client.components

import client.utils.LabelsUtils
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import shared.RootModels.SearchesRootModel
import shared.models.LabelModel
import shared.sessionitems.SessionItems

import scala.collection.mutable.ListBuffer
import scala.language.existentials
import scala.scalajs.js

object LabelsSelectize {

  def getLabelsFromSelectizeInput(selectizeInputId: String): Seq[LabelModel] = {
    var selectedLabels = Seq[LabelModel]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedLabels :+= upickle.default.read[LabelModel]($(y).attr("data-value").toString))
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
          for (label <- props.proxy().searchesModel
            .filter(e=>e.parentUid=="self")
            .filterNot(e => e.text == LabelsUtils.getSystemLabels())) yield {
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
