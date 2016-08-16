package client.components

import client.services.LGCircuit
import client.rootmodel.ConnectionsRootModel
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import org.scalajs.dom._
import shared.dtos.Connection

import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js

/**
  * Created by Shubham.K on 4/6/2016.
  */
object ConnectionsSelectize {
  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
    var selectedConnections = Seq[Connection]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedConnections :+= upickle.default.read[Connection]($(y).attr("data-value").toString))
    selectedConnections
  }

  def getConnectionNames(selectizeInputId: String): Seq[String] = {
    val cnxns = LGCircuit.zoom(_.connections.connectionsResponse).value
    getConnectionsFromSelectizeInput(selectizeInputId)
      .flatMap(e => cnxns.find(_.connection.target == e.target))
      .map(_.name)
  }

  var getSelectedValue = new ListBuffer[String]()

  /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[ConnectionsRootModel], parentIdentifier: String, fromSelectize: () => Callback)

  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(props: Props, parentIdentifier: String): Unit = {
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      //    println($(selectState).get())
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = s"#${t.props.runNow().parentIdentifier}-sel"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(30)
          .plugins("remove_button")
          .onItemAdd((item: String, value: js.Dynamic) => {
            props.fromSelectize().runNow()
            println("")
          })
          .onItemRemove((item: String)=> {
            props.fromSelectize().runNow()
            println("")
          })
        )
      }

    }

    def getSelectedValues = Callback {
      val selectState: js.Object = s"#${t.props.runNow().parentIdentifier}-sel"
      val getSelectedValue = $(selectState).find("option").text()
//      println(getSelectedValue)

      //       var x = document.getElementById("mySelect").value;
      //       document.getElementById("demo").innerHTML = "You selected: " + x;

      //     var x =  document.getElementById("#selectize").textContent
      //       println(x)
    }

    //     def getSelectedValues(e: ReactEventI) = Callback {
    //       val value = e.target.value
    //       println(value)
    //
    //     }


    def mounted(props: Props): Callback = Callback {
      initializeTagsInput(props, props.parentIdentifier)
    }

    def render(props: Props) = {
      val parentDiv: js.Object = s"#${props.parentIdentifier}"
      if ($(parentDiv).length == 0) {
        <.select(^.className := "select-state", ^.id := s"${props.parentIdentifier}-sel", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @LivelyGig", ^.onChange --> getSelectedValues)(
          <.option(^.value := "")("Select"),
          for (connection <- props.proxy().connectionsResponse) yield <.option(^.value := upickle.default.write(connection.connection), ^.key := connection.connection.target)(connection.name))

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
