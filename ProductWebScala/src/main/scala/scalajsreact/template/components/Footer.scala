package scalajsreact.template.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object Footer {

  val component = ReactComponentB.static("Footer",
    <.footer(
    )
  ).buildU

  def apply() = component()
}
