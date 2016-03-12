package l.client.css

import scalacss.mutable.GlobalRegistry

object AppCSS {
  def load () {
    GlobalRegistry.register(
      HeaderCSS.Style,
      LftcontainerCSS.Style,
      FooterCSS.Style,
      DashBoardCSS.Style,
      CreateAgentCSS.Style,
      MessagesCSS.Style,
      ProjectCSS.Style,
      BiddingScreenCSS.Style,
      PresetsCSS.Style

      )
//    GlobalRegistry.onRegistration(_.addToDocument()(s))
  }
}
