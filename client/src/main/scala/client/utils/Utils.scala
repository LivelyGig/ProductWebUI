package client.utils

import shared.dtos.Connection
import shared.models.LabelModel
import org.scalajs.dom._

object Utils {
  def GetSelfConnnection(uri: String) : Connection ={
    val sessionUri = window.sessionStorage.getItem(uri)
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://"+sessionUriSplit(2)
    Connection(sourceStr,"alias",sourceStr)
  }

  /***
    * This function is used to return the prolog used by
    * glosevel to evaluate the subscription requests.
    * @param labelFamilies This is the seq of label families e.g seq of [parent1,child1ToParent1], [parent2,child1ToParent2]
    * @return returns the prolog term e.g any([label1,label2])
    */
  def GetLabelProlog(labelFamilies: Seq[Seq[LabelModel]]) : String = {
    // println("labelFamilies = " + labelFamilies)
    var labelsCount =  labelFamilies.length - 1
    val prolog = StringBuilder.newBuilder
    prolog.append("any(")
    val results = for{ labelFamily <- labelFamilies } yield {
      prolog.append("[")
      val res = for{label <- labelFamily }yield {prolog.append(label.text); if(label.parentUid != "self") prolog.append(",") }
      prolog.append("]")
      if(labelsCount!=0){
        prolog.append(",")
        labelsCount=(labelsCount-1)
      }
    }
    prolog.append(")")
    prolog.toString()
  }
}
