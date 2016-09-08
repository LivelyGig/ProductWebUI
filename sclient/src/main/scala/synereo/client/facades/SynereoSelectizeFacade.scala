package synereo.client.facades

import scala.scalajs.js

/**
  * Created by mandar.k on 8/31/2016.
  */
@js.native
object SynereoSelectizeFacade extends js.Object {
  def addOption(selectizeId: String, text: String, value: String): js.Any = js.native

  def initilizeSelectize(selectizeId: String, maximumItems: Int, allowCreate: Boolean = false): js.native = js.native

}
