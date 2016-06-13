package client.components

import client.components.Bootstrap.PopoverOptions
import japgolly.scalajs.react.ReactComponentB
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.RootModels.SearchesRootModel
import shared.models.LabelModel
import client.components.Bootstrap._
import client.css.WorkContractCSS
import japgolly.scalajs.react

import scala.scalajs.js
import org.querki.jquery._

/**
  * Created by bhagyashree.b on 2016-06-01.
  */


//object AddNewLabel {
//
//  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)
//
//  case class Backend(t: BackendScope[Props, _]) {
//
//    def mounted(props: Props): Callback = Callback {
//      val labelPopover: js.Object = ".labelPopover"
//      $(labelPopover).popover(PopoverOptions.html(true))
//    }
//
//    def render(props: Props) = {
//      <.a(^.className := "labelPopover", "data-toggle".reactAttr := "popover", "data-content".reactAttr :=   ReactDOMServer.renderToStaticMarkup(LabelsList(LabelsList.Props(props.proxy)))
//      )(<.span(Icon.plus))
//    }
//  }
//
//  val component = ReactComponentB[Props]("SearchesLabel")
//    .renderBackend[Backend]
//    .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .build
//
//  def apply(props: Props) = component(props)
//}

object LabelsList {
  val addBtn : js.Object = "#addBtn"

  def addCollapsibleHideBtn : Callback = Callback{
    $(addBtn).hide()
  }

  def addCollapsibleShowBtn : Callback = Callback{
    $(addBtn).show()
  }

  case class Props(proxy: ModelProxy[SearchesRootModel])

  case class State(labelModel: LabelModel, postLabel: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {

    def mounted(props: Props) = {

    }

    def updateLabel(e: ReactEventI): react.Callback = {
      val value = e.currentTarget.value
      println("Updated Label " + value)
      t.modState(s => s.copy(labelModel = s.labelModel.copy(text = value)))
    }

    def postLabel(e: ReactEventI) = {
      e.preventDefault()
      var label = t.state.runNow().labelModel
      println(s"On submit label text: ${label.text}")
      $(addBtn).show()
      t.modState(s => s.copy(postLabel = !s.postLabel))
    }

    def render(props: Props, state: State) = {
      <.div(
        <.button(^.id:="addBtn",^.`type` := "button", ^.className := "btn btn-default", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.onClick --> addCollapsibleHideBtn)("Add Label"),
        <.div(^.id := "addLabel", ^.className := "collapse")(
          //          <.div(^.className := "form-group")(
          //            <.label(^.`for` := "parent")("Parent"),
          //            <.select()(
          //              for (label <- props.proxy().searchesModel) yield {
          //                <.option(^.value := label.text)(label.text)
          //              }
          //            )
          //          ),
          <.br,
          <.div(^.className:="input-group")(
          <.input(^.`type` := "text", ^.className := "form-control", ^.value := state.labelModel.text, ^.onChange ==> updateLabel),
          <.span(^.className:="input-group-addon",^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.className := "btn",^.onClick-->addCollapsibleShowBtn)(Icon.times),
          <.span(^.className:="input-group-addon",^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.className := "btn", ^.onClick ==> postLabel)(Icon.check)
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("getAllSearchLabels")
    .initialState(State(LabelModel()))
    .renderBackend[Backend]
   //.componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
