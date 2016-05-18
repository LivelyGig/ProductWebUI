package synereo.client.components

import shared.RootModels.{SearchesRootModel}
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

/**
  * Created by Mandar on 5/17/2016.
  */
object LabelsSelectize {

  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)

  case class State()

  class Backend(t: BackendScope[Props, _]) {
    def initializeTagsInput(parentIdentifier: String): Unit = {
      val selectState: js.Object = s"#$parentIdentifier > .selectize-control"
      if ($(selectState).length < 1) {
        val selectizeInput: js.Object = "#selectize"
        $(selectizeInput).selectize(SelectizeConfig
          .maxItems(30)
          .plugins("remove_button"))
      }

    }

    def mounted(props: Props): Callback = Callback {
      initializeTagsInput(props.parentIdentifier)
    }

    def render(props: Props) = {

      <.div(^.className := "row")(
        <.h4("Labels Selectize ")
      )
    }
  }

  val componentM = ReactComponentB[Props]("LabelsSelectize")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

}
