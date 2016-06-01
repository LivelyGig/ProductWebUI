package client.components

import client.components.Bootstrap.PopoverOptions
import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, _}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.denigma.selectize.SelectizeConfig
import org.querki.jquery._
import org.scalajs.dom._
import shared.RootModels.SearchesRootModel

import scala.collection.mutable.ListBuffer
import scala.scalajs.js
import client.handlers.RefreshProfiles
import client.components.Icon
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import client.handlers._
import shared.RootModels.SearchesRootModel
import client.css._
import shared.models.{LabelModel, UserModel}
import client.services.{CoreApi, LGCircuit}
import org.scalajs.dom._
import client.components.Bootstrap._
import scalacss.ScalaCssReact._
import org.querki.facades.bootstrap.datepicker._

import scala.scalajs.js
import org.querki.jquery._
import org.denigma.selectize._
import org.scalajs.dom

//import client.components.popoverbootstrap

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
      getlabels += "<div class=\"dropdown\">"
      getlabels += " <button class=\"btn dropdown-toggle\" type=\"button\" data-toggle=\"dropdown\">Select Parent <span class=\"caret\"></span></button>"
      getlabels += "  <ul class=\"dropdown-menu\", style=\"width:123px\">"
      for (label <- props.proxy().searchesModel) yield {
        getlabels += "<li>" + label.text  + "</li>"
      }
      getlabels += "</div>"
      getlabels += "   </div> "
      getlabels += "   <div class=\"form-group\"> "
      getlabels += "   <label for=\"Name\">Name:</label> "
      getlabels += "    <input type=\"text\" class=\"form-control\" id=\"Name\"> "
      getlabels += "   </div>  "
        getlabels += " <button type=\"submit\" class=\"btn btn-default\">Submit</button> "
      getlabels += "  </form> "
      println(" getlabels = " + getlabels)
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
