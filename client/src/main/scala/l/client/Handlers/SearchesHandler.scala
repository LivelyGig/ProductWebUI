package l.client.handlers

import diode.{Effect, ActionHandler, ModelRW}
import diode.data.PotAction
import l.client.dtos._
import l.client.models.Label
import l.client.rootmodels.{MessagesRootModel, SearchesRootModel}
import l.client.services.{LGCircuit, CoreApi}
import l.client.utils.{Utils, PrologParser}
import org.scalajs.dom._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.JSON
import scala.util.Success

object SearchesModelHandler {
  def GetSearchesModel(listOfLabels :Seq[String]): SearchesRootModel ={
    if (listOfLabels != Nil) {
      val labelsArray = PrologParser.StringToLabel(listOfLabels.toJSArray)
      try {
        upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      } catch {
        case e: Exception =>
          SearchesRootModel(Nil)
      }
      val model = upickle.default.read[Seq[Label]](JSON.stringify(labelsArray))
      SearchesRootModel(model)
    }
    else {
      SearchesRootModel(Nil)
    }
  }
  def updateModel (label: Label, labels: Seq[Label]): Seq[Label] ={
    val children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      children.map(e => updateModel(e, labels))
    }
    labels
  }
  var children = Seq[Label]()
  var listE = new ListBuffer[Label]() /*Seq[Label]()*/
  var searchLabels = new ListBuffer[Seq[Label]]()
  def GetChildren(label: Label, labels: Seq[Label]):  Seq[Label]={
    children = labels.filter(p=>p.parentUid==label.uid)
    if (!children.isEmpty){
      listE ++= children
      children.map(e => GetChildren(e, labels))
    }
    listE
  }
  def GetChildrenToParent(label: Label, labels: Seq[Label]):  Seq[Label]={
    children = labels.filter(p=>p.uid==label.parentUid)
    if (!children.isEmpty){
      listE ++= children
      children.map(e => GetChildrenToParent(e, labels))
    }
    listE
  }
}


case class CreateLabels()
case class UpdateLabel(label: Label)
case class SubscribeSearch()
class SearchesHandler[M](modelRW: ModelRW[M, SearchesRootModel]) extends ActionHandler(modelRW){
  override def handle = {
    case CreateLabels() =>
      val listOfLabelFromStore = window.sessionStorage.getItem("listOfLabels")
      //      println("listOfLabelFromStore"+listOfLabelFromStore)
      if (listOfLabelFromStore != null){
        try {
          upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        } catch {
          case e: Exception =>
        }
        val listOfLabels = upickle.default.read[Seq[String]](window.sessionStorage.getItem("listOfLabels"))
        //        println("listOfLabels"+listOfLabels)
        updated(SearchesModelHandler.GetSearchesModel(listOfLabels))
      } else {
        updated(SearchesModelHandler.GetSearchesModel(Nil))
      }
    case UpdateLabel(label) =>
      SearchesModelHandler.listE.clear()
//      value.
//      SearchesModelHandler.searchLabels.clear()
val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
      if (!children.isEmpty){
//        val children = SearchesModelHandler.GetChildren(label,value.searchesModel)
        val test = value.searchesModel.map(e=> if (children.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked) else e)
        updated(SearchesRootModel(test))
      }
      val childrenToParent = SearchesModelHandler.GetChildrenToParent(label,value.searchesModel)
//      val allLeafs = label +: childrenToParent
//      if(label.isChecked == true)
//        SearchesModelHandler.searchLabels.append(allLeafs.seq)
      val modelModified = value.searchesModel.map(e=> if (childrenToParent.exists(p =>p.uid == e.uid)|| e.uid==label.uid) e.copy(isChecked = label.isChecked)
        else e)
//       println(modelModified)
      val modelToUpdate = modelModified.map(e=>if (e.parentUid=="self" && childrenToParent.exists(p=>p.uid == e.uid)){
        SearchesModelHandler.listE.clear()
        val childList = SearchesModelHandler.GetChildren(e,modelModified)
        val selectedChildList = childList.filter(p=>p.isChecked == true)
        if (!selectedChildList.isEmpty){
          e.copy(isChecked = true)
        } else
          e.copy(isChecked = false)
      }else e)
      updated(SearchesRootModel(modelToUpdate))

    case SubscribeSearch() =>
      println(SearchesModelHandler.children)
      println(Utils.GetLabelProlog(SearchesModelHandler.searchLabels.reverse))
      val messageSearchClick = window.sessionStorage.getItem("messageSearchClick")
      if (messageSearchClick == "true") {
        val selfConnection = Utils.GetSelfConnnection()
        val prevLabels = window.sessionStorage.getItem("messageSearchLabel")
        val currentLabels = "any([Splicious])"
        window.sessionStorage.setItem("messageSearchLabel",currentLabels)
        val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem("sessionURI"),Expression(msgType = "feedExpr",ExpressionContent(Seq(selfConnection),currentLabels)))
        val newSubscription = Effect(CoreApi.evalSubscribeRequest(getMessagesSubscription))
        if (prevLabels != null){
          val cancelSubscription = Effect(CoreApi.cancelSubscriptionRequest(CancelSubscribeRequest(prevLabels,Seq(selfConnection),"any([Spilicious])")))
          //        println("in searchWithLabel")

          effectOnly(cancelSubscription >> newSubscription)
        } else {
          effectOnly(newSubscription)
        }
      } else {
        noChange
      }



  }

}
