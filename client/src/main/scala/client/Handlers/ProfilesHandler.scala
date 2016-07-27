package client.handlers

import diode.data.{Empty, Pot, PotActionRetriable}
import shared.RootModels.ProfilesRootModel
import shared.models.{ProfilesPost}
import client.modules.AppModule
import diode.{ ActionHandler, ActionResult, ModelRW }
import org.scalajs.dom.window
import client.services.CoreApi
import diode.util.{ Retry, RetryPolicy }
import shared.sessionitems.SessionItems
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by shubham.k on 26-05-2016.
  */

// Actions
case class RefreshProfiles(potResult: Pot[ProfilesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[ProfilesRootModel, RefreshProfiles] {
  override def next(value: Pot[ProfilesRootModel], newRetryPolicy: RetryPolicy) = RefreshProfiles(value, newRetryPolicy)
}

class ProfilesHandler[M](modelRW: ModelRW[M, Pot[ProfilesRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: RefreshProfiles =>
      val labels = window.sessionStorage.getItem(SessionItems.ProfilesViewItems.CURRENT_PROFILES_LABEL_SEARCH)
      val updateF = action.effectWithRetry(CoreApi.getContent(SessionItems.ProfilesViewItems.PROFILES_SESSION_URI)) { profilesResponse =>
        ProfilesRootModel(ContentModelHandler
          .getContentModel(profilesResponse, AppModule.PROFILES_VIEW)
          .asInstanceOf[Seq[ProfilesPost]])
      }
      Option(labels) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          updated(Empty)
      }
  }
}
