package scalajsreact.template.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._


object Header {

  val component = ReactComponentB.static("Header",
    <.footer(
    )
  ).buildU

  def apply() = component()
}
