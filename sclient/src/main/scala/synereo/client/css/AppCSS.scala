package synereo.client.css

import scalacss.mutable.GlobalRegistry

/**
  * Created by shubham.k on 11/24/2015.
  */
object AppCSS {
  def load() {
    GlobalRegistry.register(
      HeaderCSS.Style,
      /*LftcontainerCSS.Style,*/
      /*FooterCSS.Style,*/
      DashBoardCSS.Style,
      /*CreateAgentCSS.Style,*/
      /*MessagesCSS.Style,*/
      /*ProjectCSS.Style,*/
      /*BiddingScreenCSS.Style,*/
      /*PresetsCSS.Style,*/
      SynereoLoginCSS.Style,
      SynereoDashboardCSS.Style,
      SynereoBlogPostFullCSS.Style,
      SynereoCommanStylesCSS.Style,
      UserProfileViewCSS.Style,
      UserTimelineViewCSS.Style
    )
    //    GlobalRegistry.onRegistration(_.addToDocument()(s))
  }
}
