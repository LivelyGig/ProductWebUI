package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.Connection
import synereo.client.facades.SynereoSelectizeFacade
import synereo.client.services.SYNEREOCircuit

import scala.language.existentials
import scala.scalajs.js

/**
  * Created by shubham.k on 4/6/2016.
  */
//scalastyle:off
object ConnectionsLabelsSelectize {
  def getCnxnsAndLabelsFromSelectize(selectizeInputId: String): (Seq[Connection], Seq[String]) = {
    var selectedConnections = Seq[Connection]()
    var selectedLabels = Seq[String]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => {
      val dataVal = $(y).attr("data-value").toString
      try {
        val cnxn = upickle.default.read[Connection](dataVal)
        selectedConnections :+= cnxn
      } catch {
        case e: Exception =>
          selectedLabels :+= dataVal
      }
    })
    (selectedConnections, selectedLabels)
  }

  def filterLabelStrings(value: Seq[String], character: String): Seq[String] = {
    value
      .filter(e => e.charAt(0) == "#" && e.count(_ == character) == 1)
      .map(_.replace(character, "")).distinct

  }

  case class Props(parentIdentifier: String)

  case class State(maxItems: Int = 7,
                   maxCharLimit: Int = 16,
                   allowNewItemsCreation: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val state = t.state.runNow()
      val parentIdentifier = t.props.runNow().parentIdentifier
      SynereoSelectizeFacade.initilizeSelectize(s"${parentIdentifier}-selectize", state.maxItems, state.maxCharLimit, state.allowNewItemsCreation)
    }

    def mounted(props: Props): Callback = Callback {
      initializeTagsInput()
    }


    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize",
        ^.className := "demo-default", ^.placeholder := "search e.g. @Synereo or #fun")(
        <.option(^.value := "")("Select"),
        for (connection <- SYNEREOCircuit.zoom(_.connections).value.connectionsResponse) yield <.option(^.value := upickle.default.write(connection.connection),
          ^.key := connection.connection.target)(s"@${connection.name}"),
        for (label <- SYNEREOCircuit.zoom(_.searches).value.searchesModel) yield
          <.option(^.value := label.text, ^.key := label.uid)(s"#${label.text}")
      )

    }
  }

  val component = ReactComponentB[Props]("SearchesConnectionList")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
