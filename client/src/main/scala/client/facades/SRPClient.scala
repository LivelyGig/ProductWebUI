package client.facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

/**
  * Created by bhagyashree.b on 2016-08-18.
  */

@js.native
@JSName("SRPClient")
class SRPClient(username: String, password: String) extends js.Object {
  def getAHex(): String = js.native
  def getMHex(BHex: String, saltHex: String): String = js.native
  def getVerifierHex(saltHex: String): String = js.native
  def matches(M2Hex: String): Boolean = js.native
}