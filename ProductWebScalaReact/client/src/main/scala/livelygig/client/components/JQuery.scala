package livelygig.client.components

import org.scalajs.dom._

import scala.scalajs.js

/**
 * Minimal facade for JQuery. Use https://github.com/scala-js/scala-js-jquery/blob/master/src/main/scala/org/scalajs/jquery/JQuery.scala
 * for more complete one.
 */
@js.native
trait JQueryEventObject extends Event {
  var data: js.Any = js.native
}

@js.native
trait JQueryStatic extends js.Object {
  def apply(element: Element): JQuery = js.native
}

@js.native
trait JQuery extends js.Object {
  def on(events: String, selector: js.Any, data: js.Any, handler: js.Function1[JQueryEventObject, js.Any]): JQuery = js.native
  def off(events: String): JQuery = js.native
}

@js.native
trait JQueryBtn extends js.Object {
  def on(events: String): JQuery = js.native
  def off(events: String): JQuery = js.native
}
