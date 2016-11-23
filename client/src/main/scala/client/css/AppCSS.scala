package client.css

import scalacss.internal.mutable.GlobalRegistry

object AppCSS {
  def load() {
    GlobalRegistry.register(
      HeaderCSS.Style,
      LftcontainerCSS.Style,
      FooterCSS.Style,
      DashBoardCSS.Style,
      CreateAgentCSS.Style,
      MessagesCSS.Style,
      ProjectCSS.Style,
      WorkContractCSS.Style,
      PresetsCSS.Style
    )
  }
}
