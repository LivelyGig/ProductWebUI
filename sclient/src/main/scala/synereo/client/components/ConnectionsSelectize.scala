package synereo.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._

import scala.language.existentials
import scala.scalajs.js
import shared.dtos.Connection
import shared.models.{ConnectionsModel, Post}
import synereo.client.services.SYNEREOCircuit


/**
  * Created by mandar.k on 4/6/2016.
  */
//scalastyle:off
object ConnectionsSelectize {

  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
    var selectedConnections = Seq[Connection]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedConnections :+= upickle.default.read[Connection]($(y).attr("data-value").toString))
    //    println("selectedConnections" + selectedConnections)
    selectedConnections
  }

  def getConnectionNames(selectizeInputId: String): Seq[String] = {
    val cnxns = SYNEREOCircuit.zoom(_.connections.connectionsResponse).value
    getConnectionsFromSelectizeInput(selectizeInputId)
      .flatMap(e => cnxns.find(_.connection.target == e.target))
      .map(_.name)
  }


  case class Props(parentIdentifier: String, fromSelecize: () => Callback, option: Option[Int] = None)

  case class State(connections: Seq[ConnectionsModel] = Nil)


  case class Backend(t: BackendScope[Props, State]) {
    def initializeTagsInput(): Unit = {
      val props = t.props.runNow()
      val parentIdentifier = props.parentIdentifier

      val count = props.option match {
        case Some(a) => a
        case None => 30
      }

      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      val selectizeInput: js.Object = s"#${parentIdentifier}-selectize"
      //      $(selectizeInput).selectize()
      $(selectizeInput).selectize(SelectizeConfig
        .maxItems(count)
        .plugins("remove_button")
        .onItemAdd((item: String, value: js.Dynamic) => {
          props.fromSelecize().runNow()
          //          println("")
        })
        .onItemRemove((item: String) => {
          props.fromSelecize().runNow()
          //          println("")
        })

      )

    }

    def mounted(props: Props): Callback = Callback {
      /*if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }
      SYNEREOCircuit.subscribe(SYNEREOCircuit.zoom(_.connections))(_ => attachConnections())*/
      initializeTagsInput()
    }

    def getCnxnModel(): Seq[ConnectionsModel] = {

      try {
        SYNEREOCircuit.zoom(_.connections).value.connectionsResponse
      } catch {
        case e: Exception =>
          Nil
      }
    }

    def willMount(props: Props) = {
      t.modState(s => s.copy(connections = getCnxnModel()))
    }

    /*def attachConnections() = {
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        val value = SYNEREOCircuit.zoom(_.connections).value.get.connectionsResponse
        t.modState(s => s.copy(connections = value)).runNow()
      }
    }




    def componentDidUpdate(props: Props, state: State): Callback = Callback {
//      println("In componentDid  Update")
      if (SYNEREOCircuit.zoom(_.connections).value.isReady) {
        initializeTagsInput(props, state)

        // println(state.connections.foreach(a => println(a.name)))
      }

    }*/

    def render(props: Props, state: State) = {
      <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-selectize", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo" /*, ^.onChange --> getSelectedValues*/)(
        <.option(^.value := "")("Select"),
        for (connection <- state.connections) yield <.option(^.value := upickle.default.write(connection.connection),
          ^.key := connection.connection.target)(
          if (connection.name.startsWith("@")) {
            s"${connection.name}"
          } else {
            s"@${connection.name}"
          })
      )
    }
  }


  val component = ReactComponentB[Props]("SearchesConnectionList")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .componentWillMount(scope => scope.backend.willMount(scope.props))
    /*.componentDidUpdate(scope => {

      scope.$.backend.componentDidUpdate(scope.currentProps, scope.currentState)
    })*/
    //    .componentWillUpdate(scope => scope.)
    .build

  def apply(props: Props) = component(props)
}
