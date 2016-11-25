package synereo.client

import org.querki.jquery._

import scala.scalajs.js

/**
  * Created by mandar.k on 11/25/2016.
  */
//scalastyle:off
package object modalpopups {

  def hideComponents(jsObjs: String*) = jsObjs.foreach(obj => $(s"#$obj".asInstanceOf[js.Object]).addClass("hidden"))

  def unHideComponents(jsObjs: String*) = jsObjs.foreach(obj => $(s"#$obj".asInstanceOf[js.Object]).removeClass("hidden"))

  def isHidden(jsObj: String) = $(s"#$jsObj".asInstanceOf[js.Object]).hasClass("hidden")

}
