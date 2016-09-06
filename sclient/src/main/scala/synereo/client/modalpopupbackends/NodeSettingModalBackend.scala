package synereo.client.modalpopupbackends

import java.util.UUID

import shared.models.ServerModel
import synereo.client.modalpopups.NodeSettingModal
import synereo.client.modalpopups.NodeSettingModal.{Props, State}
import japgolly.scalajs.react._
import synereo.client.components._
import synereo.client.components.Bootstrap._
import scala.scalajs.js
import org.querki.jquery._

/**
  * Created by mandar.k on 9/6/2016.
  */
//scalastyle:off
class NodeSettingModalBackend(t: BackendScope[NodeSettingModal.Props, NodeSettingModal.State]) {


  def closeForm = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def modalClosed(state: State, props: Props): Callback = {
    props.submitHandler()
  }

  def getNewServerModel(): ServerModel = {
    ServerModel(UUID.randomUUID().toString.replaceAll("-", ""), "", true)
  }

  def mounted(props: Props): Callback = {
    t.modState(s => s.copy(Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()), Seq(getNewServerModel()),
      Seq(getNewServerModel()), Seq(getNewServerModel())))
  }

  def addNewServer(id: String): Callback = {
    println("in ad ")
    id match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient ++ Seq(getNewServerModel())))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator ++ Seq(getNewServerModel())))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier ++ Seq(getNewServerModel()))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer ++ Seq(getNewServerModel())))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient ++ Seq(getNewServerModel())))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator ++ Seq(getNewServerModel())))
    }
  }

  def enableTextboxToggle(sectionId: String, inputId: String): Callback = {
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(s => if (s.uid == inputId) s.copy(isEditable = !s.isEditable) else s)))
    }
  }

  def updateTextbox(e: ReactEventI): Callback = {
    val currentVal = e.target.value
    val inputId = e.target.id
    val sectionId = $(s"#${e.target.id}".asInstanceOf[js.Object]).parent().parent().parent().attr("id").toString
    printf(sectionId)
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(s => if (s.uid == inputId) s.copy(serverAddress = currentVal) else s)))
    }
  }

  def editAll(sectionId: String): Callback = {
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(e => e.copy(isEditable = true))))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(e => e.copy(isEditable = true))))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(e => e.copy(isEditable = true)))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(e => e.copy(isEditable = true))))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(e => e.copy(isEditable = true))))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(e => e.copy(isEditable = true))))
    }
  }

  def deleteAddress(sectionId: String, inputId: String): Callback = {
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.filterNot(p => p.uid == inputId)))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.filterNot(p => p.uid == inputId)))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.filterNot(p => p.uid == inputId))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.filterNot(p => p.uid == inputId)))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.filterNot(p => p.uid == inputId)))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.filterNot(p => p.uid == inputId)))
    }
  }

  def deleteAll(sectionId: String): Callback = {
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = Nil))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = Nil))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = Nil))
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = Nil))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = Nil))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = Nil))
    }
  }

  def saveAll(sectionId: String): Callback = {
    sectionId match {
      case "DSLCommLinkClient" => t.modState(s => s.copy(DSLCommLinkClient = s.DSLCommLinkClient.map(e => e.copy(isEditable = false))))
      case "DSLEvaluator" => t.modState(s => s.copy(DSLEvaluator = s.DSLEvaluator.map(e => e.copy(isEditable = false))))
      case "DSLEvaluatorPreferredSupplier" => t.modState(s => s.copy(DSLEvaluatorPreferredSupplier = s.DSLEvaluatorPreferredSupplier.map(e => e.copy(isEditable = false)))) // scalastyle:ignore
      case "BFactoryCommLinkServer" => t.modState(s => s.copy(BFactoryCommLinkServer = s.BFactoryCommLinkServer.map(e => e.copy(isEditable = false))))
      case "BFactoryCommLinkClient" => t.modState(s => s.copy(BFactoryCommLinkClient = s.BFactoryCommLinkClient.map(e => e.copy(isEditable = false))))
      case "BFactoryEvaluator" => t.modState(s => s.copy(BFactoryEvaluator = s.BFactoryEvaluator.map(e => e.copy(isEditable = false))))
    }
  }

}
