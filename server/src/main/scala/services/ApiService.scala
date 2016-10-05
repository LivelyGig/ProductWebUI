package services

import i18n.I18NLoc
import shared.Api
import shared.dtos.ServerModel
import org.json4s.jackson.Serialization.write

/**
  * Created by shubham.k on 27-09-2016.
  */
class ApiService extends Api {
  override def postServers(servers: Seq[ServerModel]): String = {
    servers.foreach(println)
    "server post success"
  }

  override def getLang(lang: String): String = {
    try {
      scala.io.Source.fromFile(s"server/src/main/scala/i18n/${lang}.json", "utf-8").mkString
    } catch {
      case e: Exception =>
        println(e.getMessage)
        ""
    }
  }
}
