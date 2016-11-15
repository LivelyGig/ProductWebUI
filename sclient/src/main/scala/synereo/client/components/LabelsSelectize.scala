package synereo.client.components


import shared.models.Label
import synereo.client.services.SYNEREOCircuit

import scala.language.existentials
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom._
import synereo.client.facades.SynereoSelectizeFacade

import scala.language.existentials
import scala.scalajs.js

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

  case class Props(parentIdentifier: String)

  case class State(labels: Seq[Label] = Nil,
                   maxItems: Int = 7,
                   maxCharLimit: Int = 16,
                   allowNewItemsCreation: Boolean = true)

  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val state = t.state.runNow()
      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        //        val selectizeInput: js.Object = s"#${t.props.runNow().parentIdentifier}-selectize"
        //        $(selectizeInput).selectize(SelectizeConfig
        //          .create(true)
        //          .maxItems(3)
        //          .plugins("remove_button"))
        SynereoSelectizeFacade.initilizeSelectize(s"${parentIdentifier}-selectize", state.maxItems, state.maxCharLimit, state.allowNewItemsCreation)
      }

    }

    //    def getSelectedValues = Callback {
    //      val selectState: js.Object = "#selectize"
    //      val getSelectedValue = $(selectState).find("option").text()
    //      println(getSelectedValue)
    //    }

    def mounted(props: Props): Callback = Callback {
      initializeTagsInput()
    }

    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Use # for tag" /*, ^.onChange --> getSelectedValues*/)(
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
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
