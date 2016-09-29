package shared

import shared.dtos.ServerModel

/**
  * Created by shubham.k on 27-09-2016.
  */
trait Api {
  def postServers(servers: Seq[ServerModel]): String
  def getLang(lang: String): String
}
