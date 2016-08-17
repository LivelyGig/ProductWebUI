package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.Connection
import synereo.client.handlers.CreateLabels
import synereo.client.services.SYNEREOCircuit
import synereo.client.rootmodels._

import scala.language.existentials
import scala.scalajs.js
import diode.AnyAction._
import diode.react.ModelProxy
import shared.models.Label
import synereo.client.rootmodels.SearchesRootModel


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
    }
    )
    (selectedConnections, selectedLabels)
  }


  def filterLabelStrings(value: Seq[String], character: String): Seq[String] = {
    value
      .filter(e => e.charAt(0) == "#" && e.count(_ == character) == 1)
      .map(_.replace(character, "")).distinct

  }


  case class Props(parentIdentifier: String /*labels: Seq[Label]*/ , proxy: ModelProxy[SearchesRootModel])

  case class State(/*connections: Seq[ConnectionsModel] = Nil,*/ labels: Seq[Label] = Nil)

  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val parentIdentifier = t.props.runNow().parentIdentifier
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      val selectizeInput: js.Object = s"#${parentIdentifier}-selectize"
      //      $(selectizeInput).selectize()
      $(selectizeInput).selectize(SelectizeConfig
        .maxItems(7)
        .plugins("remove_button")
      )
    }

    def mounted(props: Props): Callback = Callback {
      /*if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }

      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.connections))(_ => attachConnections())*/
      //      println("did mount called ")
      //      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom((_.searches)))(_ => attachLabels())
      attachLabels()
      initializeTagsInput()
    }

    //    def willMount(props: Props): Callback = Callback.when(SYNEREOCircuit.zoom(_.searches).value.searchesModel.isEmpty)(Callback{SYNEREOCircuit.dispatch(CreateLabels())})

    def attachLabels() = {
      if (SYNEREOCircuit.zoom(_.searches).value.searchesModel != null) {
        val value = SYNEREOCircuit.zoom(_.searches).value.searchesModel
        // println(s"new Searchesmodel is : $value")
        t.modState(s => s.copy(labels = value))
      }
    }

    //    def clearSelect(props: Props) = {
    //      $(s"${props.parentIdentifier}-selectize".asInstanceOf[js.Object]).find("option").remove()
    //    }

    //    def updateComponent(): Boolean = {
    //      val props = t.props.runNow()
    //            println(s"inside udpateComponent ${props.proxy().searchesModel.isEmpty}")
    //            !props.proxy().searchesModel.isEmpty
    //    }

    /*def attachConnections() = {
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }
    }



    def willMount(props: Props) = Callback {
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

    }*/

    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize",
        ^.className := "demo-default", ^.placeholder := "search e.g. @Synereo or #fun")(
        <.option(^.value := "")("Select"),
        for (connection <- SYNEREOCircuit.zoom(_.connections).value.connectionsResponse) yield <.option(^.value := upickle.default.write(connection.connection),
          ^.key := connection.connection.target)(s"@${connection.name}"),
        //        for (label <- SYNEREOCircuit.zoom(_.searches).value.searchesModel) yield
        for (label <- props.proxy().searchesModel if !props.proxy().searchesModel.isEmpty) yield
          <.option(^.value := label.text, ^.key := label.uid)(s"#${label.text}")
      )
      //        for (label <- state.labels) yield
      //          <.option(^.value := label.text, ^.key := label.uid)(s"#${label.text}"))

    }
  }

  val component = ReactComponentB[Props]("SearchesConnectionList")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    //    .componentDidUpdate(scope => Callback {
    //      println("tags input is did update ")
    //      scope.$.backend.initializeTagsInput
    //    })
    //    .componentWillMount(scope => scope.backend.willMount(scope.props))
    .componentDidUpdate(scope => Callback {
    //    println("ConnectionsLabelsSelectize Component did update ")
  })
    //    .componentWillUnmount(scope =>
    //      Callback {
    //        scope.backend.clearSelect(scope.props)
    //      })
    //    .shouldComponentUpdate(scope => scope.$.backend.updateComponent())
    .componentDidUpdate(scope => Callback {
    SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.searches))(_ => scope.$.backend.attachLabels())
    //    println(s"newLabels $newLabels")
  })
    .build

  def apply(props: Props) = component(props)
}
