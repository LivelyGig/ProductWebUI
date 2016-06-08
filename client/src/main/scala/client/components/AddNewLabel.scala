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
import scala.scalajs.js
import org.querki.jquery._

/**
  * Created by bhagyashree.b on 2016-06-01.
  */
object SearchesLabel {

  case class Props(proxy: ModelProxy[SearchesRootModel], parentIdentifier: String)

  case class Backend(t: BackendScope[Props, _]) {

    def mounted(props: Props): Callback = Callback {
      val labelPopover: js.Object = ".labelPopover"
      $(labelPopover).popover(PopoverOptions.html(true))
    }

    def render(props: Props) = {
      <.a(^.className := "labelPopover", "data-toggle".reactAttr := "popover", "data-content".reactAttr :=   ReactDOMServer.renderToStaticMarkup(getAllSearchLabels.getLabels(getAllSearchLabels.Props(props.proxy)))
      )(<.span(Icon.plus))
    }
  }

  val component = ReactComponentB[Props]("SearchesLabel")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}

object getAllSearchLabels {
  case class Props(proxy: ModelProxy[SearchesRootModel])
  case class State(labelModel: LabelModel)
  case class Backend(t: BackendScope[Props, _]) {
    def updateLabel() = Callback {
      println("In function")
    }

    def render(props: Props) = {
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
            <.input(^.`type` := "text", ^.className := "form-control" /*,^.value := state.postMessage.subject*/)
          ),
          <.button(^.`type` := "submit", ^.className := "btn btn-default", ^.onClick --> updateLabel)("Submit")
        )
      )
    }
  }
  val getLabels = ReactComponentB[Props]("getAllSearchLabels")
    .renderBackend[Backend]
    .build
}
