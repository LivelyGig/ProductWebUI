package synereo.client.modulebackends

import japgolly.scalajs.react._
import synereo.client.logger
import synereo.client.modules.HomeFeedList

/**
  * Created by bhagyashree.b on 2016-09-02.
  */

class HomeFeedListBackend(t: BackendScope[HomeFeedList.Props, HomeFeedList.State]) {

  def mounted() = Callback {

  }

  //    def closeFullViewModalPopUp(): Callback = {
  //      $(dashboardContainerMain).addClass("SynereoCommanStylesCSS_Style-overflowYScroll")
  //      t.modState(s => s.copy(ShowFullPostView = false))
  //    }
  //
  //    def openFullViewModalPopUP(e: ReactEvent): Callback = {
  //      $(dashboardContainerMain).removeClass("SynereoCommanStylesCSS_Style-overflowYScroll")
  //      t.modState(s => s.copy(ShowFullPostView = true, preventFullPostView = true))
  //    }

  //    def preventFullViewModalPopUP(e: ReactEvent): Callback = {
  //      val targetLi = e.target
  //      setTimeout(1500) {
  //        $(targetLi).find(".glance-view-button").addClass(".hide")
  //      }
  //      t.modState(s => s.copy(ShowFullPostView = false))
  //    }

  //    def handleScroll(e: ReactEvent): Callback = {
  //      Callback.empty
  //    }

  //    def mouseEntered(e: ReactEvent): Callback = {
  //      //      println("mouse entered some where ")
  //      val targetLi = e.target
  //      val collapsiblePost: js.Object = $(targetLi).find(".collapse")
  //      setTimeout(FeedTimeOut) {
  //        if (!$(collapsiblePost).hasClass("in")) {
  //          $(collapsiblePost).addClass("in")
  //        }
  //      }
  //      Callback.empty
  //    }


  def filterLabelStrings(value: Seq[String]): Seq[String] = {
    value.filter(
      _.matches("\\S*#(?:\\[[^\\]]+\\]|\\S+)")
    ).map(_.replace("#", "")).toSet.toSeq
  }

  def amplifyPost(e: ReactEventI): Callback = {
    logger.log.debug("amplifyPost called")
    t.modState(state => state.copy(showAmplifyPostForm = true))
  }

  def postAmplified(): Callback = {
    logger.log.debug("postAmplified called ")
    t.modState(s => s.copy(showAmplifyPostForm = false))
  }

  def forwardPost(e: ReactEventI): Callback = {
    logger.log.debug("forwardPost called ")
    t.modState(state => state.copy(showForwardPostForm = true))
  }

  def postForwarded(): Callback = {
    logger.log.debug("postForwarded called")
    t.modState(state => state.copy(showForwardPostForm = false))
  }
}
