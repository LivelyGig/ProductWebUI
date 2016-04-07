package client.components

import client.rootmodels.ConnectionsRootModel
import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize._
import org.querki.jquery._
import scala.language.existentials
import scala.collection.mutable.ListBuffer
import scala.scalajs.js

/**
  * Created by Shubham.K on 4/6/2016.
  */
object ConnectionsSelectize {
  var getSelectedValue = new ListBuffer[String]() /*Seq[Label]()*/
  case class Props(proxy: ModelProxy[Pot[ConnectionsRootModel]], parentIdentifier : String)
  case class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String) : Unit = {
      val selectState : js.Object = s"#$parentIdentifier > .selectize-control"
      //    println($(selectState).get())
      if ($(selectState).length < 1){
        val selectizeInput : js.Object = "#selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(10)
          .plugins("remove_button"))
      }

    }
    def getSelectedValues = Callback {
      val selectState : js.Object = "#selectize"
      val getSelectedValue =  $(selectState).find("option").text()
      println(getSelectedValue)

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

    def mounted(props: Props) : Callback = Callback {
      initializeTagsInput(props.parentIdentifier)
    }

    def render (props: Props) = {
      val parentDiv : js.Object = s"#${props.parentIdentifier}"
      if ($(parentDiv).length == 0) {
        <.select(^.className:="select-state",^.id:="selectize", ^.className:="demo-default", ^.placeholder:="e.g. @LivelyGig", ^.onChange --> getSelectedValues)(
          <.option(^.value:="")("Select"),
          props.proxy().render(connectionsRootModel =>
            for (connection<-connectionsRootModel.connectionsResponse) yield <.option(^.value:=upickle.default.write(connection.connection) ,^.key:=connection.connection.target)(connection.name)
          ))
      } else {
        <.div()
      }

    }
  }
  val component = ReactComponentB[Props]("SearchesConnectionList")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply (props: Props) = component(props)
}
