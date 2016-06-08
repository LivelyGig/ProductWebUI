package client.components

import client.components.Bootstrap.PopoverOptions
import japgolly.scalajs.react.{ReactComponentB, _}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.RootModels.SearchesRootModel
import shared.models.{LabelModel, UserModel}
import client.services.{CoreApi, LGCircuit}
import client.components.Bootstrap._
import japgolly.scalajs.react

import scala.scalajs.js
import org.querki.jquery._

/**
  * Created by bhagyashree.b on 2016-06-01.
  */
object AddNewLabel {

  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)

  case class Backend(t: BackendScope[Props, _]) {

    def mounted(props: Props): Callback = Callback {
      val labelPopover: js.Object = ".labelPopover"
      $(labelPopover).popover(PopoverOptions.html(true))
    }

    def render(props: Props) = {
      <.a(^.className := "labelPopover", "data-toggle".reactAttr := "popover", "data-content".reactAttr :=   ReactDOMServer.renderToStaticMarkup(LabelsList(LabelsList.Props(props.proxy)))
      )(<.span(Icon.plus))
    }
  }

  val component = ReactComponentB[Props]("SearchesLabel")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}

object LabelsList {
  case class Props(proxy: ModelProxy[SearchesRootModel])
  case class State(labelModel: LabelModel)
  case class Backend(t: BackendScope[Props, State]) {
    def updateLabel(e: ReactEventI): react.Callback =  {
      val value = e.currentTarget.value
      t.modState(s => s.copy(labelModel = s.labelModel.copy(text = value)))

    }
    def postLabel(e: ReactEventI): Callback = Callback {
        e.preventDefault()
        val label = t.state.runNow().labelModel
      println(s"label text: ${label.text}")
    }

    def render(props: Props, state: State) = {
      <.div()(
        <.form(^.role := "form")(
          <.div(^.className := "form-group")(
            <.label(^.`for` := "parent")("Parent"),
            <.select()(
              for (label <- props.proxy().searchesModel) yield {
                <.option(^.value := label.text)(label.text)
              }
            )
          ),
          <.div(^.className := "form-group")(
            <.label(^.`for` := "name")("Name : "),
            <.input(^.`type` := "text", ^.className := "form-control" ,^.value := state.labelModel.text, ^.onChange ==> updateLabel)
          ),
          <.button(^.`type` := "", ^.className := "btn btn-default"/*, ^.onClick ==> postLabel*/)("Submit")
        )
      )
    }
  }
  val component = ReactComponentB[Props]("getAllSearchLabels")
    .initialState(State(LabelModel()))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)

//  def apply: getAllSearchLabels = new getAllSearchLabels()
}
