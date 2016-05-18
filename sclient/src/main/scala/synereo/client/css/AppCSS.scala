package synereo.client.css

import scalacss.mutable.GlobalRegistry

/**
  * Created by shubham.k on 11/24/2015.
  */
object AppCSS {
  def load() {
    GlobalRegistry.register(
      SynereoCommanStylesCSS.Style,
      LoginCSS.Style,
      SignupCSS.Style,
      DashboardCSS.Style,
      PostFullViewCSS.Style,
      UserProfileViewCSS.Style,
      UserTimelineViewCSS.Style,
      MarketPlaceFullCSS.Style,
      ConnectionsCSS.Style
    )
    //    GlobalRegistry.onRegistration(_.addToDocument()(s))
  }
}
