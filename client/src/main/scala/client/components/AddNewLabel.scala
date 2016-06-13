package client.components

import client.components.Bootstrap.PopoverOptions
import japgolly.scalajs.react.ReactComponentB
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import shared.RootModels.SearchesRootModel
import shared.models.Label
import client.components.Bootstrap._
import client.css.WorkContractCSS
import client.handlers.CreateLabels
import client.logger
import client.services.{ApiTypes, CoreApi, LGCircuit}
import japgolly.scalajs.react

import scala.scalajs.js
import org.querki.jquery._
import org.scalajs.dom
import shared.dtos.{ApiRequest, LabelPost}
import shared.sessionitems.SessionItems

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


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

  case class Props(proxy: ModelProxy[SearchesRootModel])

  case class State(labelModel: Label, postLabel: Boolean = false)

  case class Backend(t: BackendScope[Props, State]) {

    def mounted(props: Props) = {

    }

    def updateLabel(e: ReactEventI): react.Callback = {
      val value = e.currentTarget.value
      t.modState(s => s.copy(labelModel = s.labelModel.copy(text = value)))
    }

    def postLabel(e: ReactEventI) = {
      e.preventDefault()
      var label = t.state.runNow().labelModel
      val labelPost = LabelPost(dom.window.sessionStorage.getItem(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI),leafParser(), "alias")
      println("labelPost = "+labelPost)
      CoreApi.postLabel(labelPost).onComplete{
        case Success(res) =>
          dom.window.sessionStorage.setItem(SessionItems.SearchesView.LIST_OF_LABELS, s"[${leafParser(true).mkString(",")}]")
          LGCircuit.dispatch(CreateLabels())
        case Failure(res) =>
          logger.log.debug("Label Post failure")

      }
      $(addBtn).show()
      t.modState(s => s.copy(postLabel = !s.postLabel))
    }



    def leafParser (requireStore: Boolean = false): Seq[String] = {
      val (props,state) = (t.props.runNow(),t.state.runNow())
      def leaf (text: String, color: String) =  "leaf(text(\""+ s"${ text }"+ "\"),display(color(\""+ s"${color}" + "\"),image(\"\")))"
      def leafMod (text: String, color: String) =  "\"leaf(text(\\\""+ s"${ text }"+ "\\\"),display(color(\\\""+ s"${color}" + "\\\"),image(\\\"\\\")))\""

      if (requireStore)
        props.proxy().searchesModel.map(e => leafMod(e.text, e.color) ):+ leafMod(state.labelModel.text, "#CC5C64")
      else
        props.proxy().searchesModel.map(e => leaf(e.text, e.color) ):+ leaf(state.labelModel.text, "#CC5C64")

    }

    def render(props: Props, state: State) = {
      <.div(
        <.button(^.id:="addBtn",^.`type` := "button", ^.className := "btn btn-default", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.onClick --> Callback{$(addBtn).hide()})("Add Label"),
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
          <.span(^.className:="input-group-addon",^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.className := "btn",^.onClick-->Callback{$(addBtn).show()})(Icon.times),
          <.span(^.className:="input-group-addon",^.`type` := "button", "data-toggle".reactAttr := "collapse", "data-target".reactAttr := "#addLabel",^.className := "btn", ^.onClick ==> postLabel)(Icon.check)
          )
        )
      )
    }
  }

  val component = ReactComponentB[Props]("getAllSearchLabels")
    .initialState(State(Label()))
    .renderBackend[Backend]
   //.componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(props: Props) = component(props)

}
