package scalajsreact.template

import japgolly.scalajs.react._
import org.scalajs.dom
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

import scalajsreact.template.routes.AppRouter

object ReactApp extends JSApp {
  @JSExport
  override def main(): Unit = {
    println("LivelyGig")
    AppRouter.router().render(dom.document.body)
  }
}