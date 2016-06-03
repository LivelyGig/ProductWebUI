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

    def getAllSearchLabels(props: Props): String = {
      var getlabels = ""
      getlabels += "  <form role=\"form\"> "
      getlabels += " <div class=\"form-group\"> "
      getlabels += " <label for=\"parent\">Parent:</label> "

      getlabels += "  <select>"
      for (label <- props.proxy().searchesModel) yield {
        getlabels += "<option value=" + label.text + ">" + label.text + "</option>"
      }
      getlabels += "</select>"

      getlabels += "   <div class=\"form-group\"> "
      getlabels += "   <label for=\"Name\">Name:</label> "
      getlabels += "    <input type=\"text\" class=\"form-control\" id=\"Name\"> "
      getlabels += "   </div>  "
        getlabels += " <button type=\"submit\" class=\"btn btn-default\">Submit</button> "
      getlabels += "  </form> "
      return getlabels
    }


    def mounted(props: Props): Callback = Callback {
      val labelPopover: js.Object = ".labelPopover"
      $(labelPopover).popover(PopoverOptions.html(true))
    }

    def render(props: Props) = {
      <.a(^.className := "labelPopover", "data-toggle".reactAttr := "popover", "data-content".reactAttr := getAllSearchLabels(props)
      )(<.span(Icon.plus))
    }
  }

  val component = ReactComponentB[Props]("SearchesLabel")
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)
}
