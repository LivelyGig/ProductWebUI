package services

import shared.Api
import shared.dtos.ServerModel

/**
  * Created by shubham.k on 27-09-2016.
  */
class ApiService extends Api {
  override def postServers(servers: Seq[ServerModel]): String = {
    servers.foreach(println)
    "server post success"
  }
}
