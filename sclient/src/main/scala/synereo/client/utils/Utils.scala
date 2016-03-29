package synereo.client.utils

import org.scalajs.dom._
import shared.dtos.Connection
import synereo.client.models.Label

/**
  * Created by shubham.k on 3/4/2016.
  */
object Utils {
  def GetSelfConnnection(uri: String) : Connection ={
    val sessionUri = window.sessionStorage.getItem(uri)
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://"+sessionUriSplit(2)
    Connection(sourceStr,"alias",sourceStr)
  }

  def GetLabelProlog(labelFamilies: Seq[Seq[Label]]) : String = {
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
    // println(prolog)
    prolog.toString()

  }
}
