package synereo.client.components

import shared.RootModels.ConnectionsRootModel
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

import scala.scalajs.js.JSON

/**
  * Created by Shubham.K on 4/6/2016.
  */
//scalastyle:off
object ConnectionsSelectize {
  def getConnectionsFromSelectizeInput(selectizeInputId: String): Seq[Connection] = {
    var selectedConnections = Seq[Connection]()
    val selector: js.Object = s"#${selectizeInputId} > .selectize-control> .selectize-input > div"

    $(selector).each((y: Element) => selectedConnections :+= upickle.default.read[Connection]($(y).attr("data-value").toString))
    selectedConnections
  }

  var getSelectedValue = new ListBuffer[String]()

  /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]], parentIdentifier: String)

  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String): Unit = {
      //      var item: String = ""
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = "#selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(30)
          .plugins("remove_button")
          .onItemAdd((item: String, value: js.Dynamic) => {
            //            println(item)
            //            println(value.toString)
            //            val json = JSON.parse(value)
          }))
      }

    }

    def getSelectedValues = Callback {
      val selectState: js.Object = "#selectize"
      val getSelectedValue = $(selectState).find("option").text()
      println(getSelectedValue)
    }

    def mounted(props: Props): Callback = Callback {
      initializeTagsInput(props.parentIdentifier)
    }

    def render(props: Props) = {
      val parentDiv: js.Object = s"#${props.parentIdentifier}"
      if ($(parentDiv).length == 0) {
        <.select(^.className := "select-state", ^.id := "selectize", ^.className := "demo-default", ^.placeholder := "Recipients e.g. @Synereo", ^.onChange --> getSelectedValues)(
          <.option(^.value := "")("Select"),
          props.proxy().render(connectionsRootModel =>
            for (connection <- connectionsRootModel.connectionsResponse) yield <.option(^.value := upickle.default.write(connection.connection),
              ^.key := connection.connection.target)(s"@${connection.name}")
          )
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

