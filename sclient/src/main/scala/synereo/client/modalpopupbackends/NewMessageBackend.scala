package synereo.client.modalpopupbackends

import japgolly.scalajs.react
import japgolly.scalajs.react._
import org.querki.jquery._
import org.scalajs.dom.raw.{FileReader, UIEvent}
import shared.dtos.LabelPost
import shared.models.Label
import synereo.client.components.{ConnectionsSelectize, LabelsSelectize, _}
import synereo.client.handlers.SearchesModelHandler
import synereo.client.modalpopups.NewMessageForm
import synereo.client.services.SYNEREOCircuit
import synereo.client.utils._
import synereo.client.components.Bootstrap._
import synereo.client.facades.SynereoSelectizeFacade

import scala.scalajs.js

/**
  * Created by bhagyashree.b on 2016-09-01.
  */
//scalastyle:off
case class NewMessageBackend(t: BackendScope[NewMessageForm.Props, NewMessageForm.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def updateSubject(e: ReactEventI) = {
    val value = e.target.value
    t.modState(s => s.copy(postMessage = s.postMessage.copy(subject = value)))
  }

  def updateContent(e: ReactEventI) = {
    val value = e.target.value
    val words: Seq[String] = value.split(" ")
    var tagsCreatedInline: Seq[String] = Seq()
    words.foreach(
      word => if (word.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")) {
        tagsCreatedInline = tagsCreatedInline :+ word
      }
    )
    t.modState(s => s.copy(postMessage = s.postMessage.copy(text = value), tags = tagsCreatedInline))
  }

  def hideModal = {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def mounted(): Callback = Callback {

  }

  def filterLabelStrings(value: Seq[String]): Seq[String] = {
    value.filter(
      _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
    ).map(_.replace("#", "")).distinct
  }

  def labelsTextFromMsg: Seq[String] = {
    filterLabelStrings(t.state.runNow().postMessage.text.split(" +"))
  }

  def labelsToPostMsg: Seq[Label] = {
    val textSeq = labelsTextFromMsg ++ filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(t.state.runNow().labelsSelectizeInputId))

    //      println(s"labelsToPostMsg ${textSeq.distinct}")
    textSeq.distinct.map(LabelsUtils.getLabelModel)
  }

  def getAllLabelsText: Seq[String] = {
    val (props, state) = (t.props.runNow(), t.state.runNow())
    //    println(s"filtered labels from selectize ${filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId))}")
    val allLabels = props.proxy().searchesModel.map(e => e.text) ++ labelsTextFromMsg ++
      filterLabelStrings(LabelsSelectize.getLabelsTxtFromSelectize(state.labelsSelectizeInputId).map(label => s"#$label"))
    allLabels.distinct
  }

  def deleteInlineLabel(e: ReactEventI) = {
    val value = e.target.parentElement.getAttribute("data-count")
    val state = t.state.runNow()
    val newlist = for {
      (x, i) <- state.tags.zipWithIndex
      if i != value.toInt
    } yield x
    t.modState(state => state.copy(tags = newlist))
  }

  def updateImgSrc(e: ReactEventI): react.Callback = Callback {
    val value = e.target.files.item(0)
    //    println(s"value of img = ${value.size}")
    val reader = new FileReader()
    reader.onload = (e: UIEvent) => {
      val contents = reader.result.asInstanceOf[String]
      t.modState(s => s.copy(postMessage = s.postMessage.copy(imgSrc = contents))).runNow()
    }
    reader.readAsDataURL(value)
  }

  def fromSelecize(): Callback = Callback {}

  def submitForm(e: ReactEventI) = {
    e.preventDefault()
    val state = t.state.runNow()
    val props = t.props.runNow()
    val connections = ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId)
    if (connections.length < 1) {
      $("#cnxnError".asInstanceOf[js.Object]).removeClass("hidden")
      t.modState(s => s.copy(postNewMessage = false))
    } else {
      $("#cnxnError".asInstanceOf[js.Object]).addClass("hidden")
      val cnxns = ConnectionsUtils.getCnxnForReq(ConnectionsSelectize.getConnectionsFromSelectizeInput(state.connectionsSelectizeInputId))
      //        val labelsToPost = (props.proxy().searchesModel.map(e => e.text) union allLabelsText distinct) diff props.proxy().searchesModel.map(e => e.text)
      val newLabels = LabelsUtils.getNewLabelsText(getAllLabelsText)
      //      println(s"all label text from content,selectize,circuit : $getAllLabelsText")
      //      println(s"newLabels : $newLabels")
      if (newLabels.nonEmpty) {
        val labelPost = LabelPost(SYNEREOCircuit.zoom(_.sessionRootModel.sessionUri).value, getAllLabelsText.map(SearchesModelHandler.leaf), "alias")
        ContentUtils.postLabelsAndMsg(labelPost, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
        //          newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", UUID.randomUUID().toString.replaceAll("-", "")))
        newLabels.foreach(label => SynereoSelectizeFacade.addOption("SearchComponentCnxnSltz-selectize", s"#$label", label))
      } else {
        ContentUtils.postMessage(MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg))
      }
      //          SYNEREOCircuit.dispatch(PostLabelsAndMsg(allLabelsText, MessagesUtils.getPostData(state.postMessage, cnxns, labelsToPostMsg)))
      t.modState(s => s.copy(postNewMessage = true))
    }
  }

  def formClosed(state: NewMessageForm.State, props: NewMessageForm.Props): Callback = {
    props.submitHandler(/*state.submitForm*/)
  }
}
