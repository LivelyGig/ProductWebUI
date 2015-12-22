package livelygig.client.css

import scalacss.mutable.GlobalRegistry
/**
  * Created by shubham.k on 11/24/2015.
  */
object AppCSS {
  def load () {
    GlobalRegistry.register(
      HeaderCSS.Style,
      LftcontainerCSS.Style,
      FooterCSS.Style,
      DashBoardCSS.Style,
      CreateAgentCSS.Style

      )
//    GlobalRegistry.onRegistration(_.addToDocument()(s))
  }
}
