package l.client.utils

import l.client.dtos.Connection
import l.client.models.Label
import org.scalajs.dom._

object Utils {
  def GetSelfConnnection() : Connection ={
    val sessionUri = window.sessionStorage.getItem("sessionURI")
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://"+sessionUriSplit(2)
    Connection(sourceStr,"alias",sourceStr)
  }

  def GetLabelProlog(labelFamilies: Seq[Seq[Label]]) : String = {
    // println("labelFamilies = " + labelFamilies)
    var labelsCount =  labelFamilies.length - 1
    val prolog = StringBuilder.newBuilder
    prolog.append("any(")
    val results = for{ a <- labelFamilies } yield {
      prolog.append("[")
      val res = for{b <- a }yield {prolog.append(b.text); if(b.parentUid != "self") prolog.append(",") }
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
