package livelygig.client.utils

import livelygig.client.dtos.Connection
import livelygig.client.models.Label
import org.scalajs.dom._

/**
  * Created by shubham.k on 3/4/2016.
  */
object Utils {
  def GetSelfConnnection() : Connection ={
    val sessionUri = window.sessionStorage.getItem("sessionURI")
    val sessionUriSplit = sessionUri.split('/')
    val sourceStr = "agent://"+sessionUriSplit(2)
    Connection(sourceStr,"alias",sourceStr)
  }
  def GetLabelProlog(labelFamilies: Seq[Seq[Label]]) : String = {
    println(labelFamilies)
    val prolog = StringBuilder.newBuilder
    prolog.append("any(")
    val outerLen = 0
    val len = 0
    labelFamilies.foreach{ e=>prolog.append("[")
      println(outerLen)
      e.foreach{p=>
        println(len)
        if (len == e.length){
          prolog.append(p.text+",")
        } else {
          prolog.append(prolog+p.text+"")
        }
        len+1
      }
      if (outerLen == labelFamilies.length ){
        prolog.append(prolog+"],")
      } else {
        prolog.append(prolog+"]")
      }
      prolog.append(prolog+"]")
      outerLen+1
    }
//    prolog +"["
//    val oho = for (labelFamily <- labelFamilies  ) yield  for(label<-labelFamily) yield prolog+label.text+","
//    prolog+oho+")"
    prolog.toString()
  }
}
