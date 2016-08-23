package client.handler

import diode.data.{Empty, Pot, PotActionRetriable}
import client.rootmodel.ProfilesRootModel
import shared.models.ProfilesPost
import client.modules.AppModule
import diode.{ActionHandler, ActionResult, ModelRW}
import org.scalajs.dom.window
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.sessionitems.SessionItems

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by shubham.k on 26-05-2016.
  */

// Actions
case class RefreshProfiles(potResult: Pot[ProfilesRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
  extends PotActionRetriable[ProfilesRootModel, RefreshProfiles] {
  override def next(value: Pot[ProfilesRootModel], newRetryPolicy: RetryPolicy) = RefreshProfiles(value, newRetryPolicy)
}

case class ClearProfiles()

class ProfilesHandler[M](modelRW: ModelRW[M, Pot[ProfilesRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: RefreshProfiles =>
      val updateF = action.effectWithRetry(CoreApi.sessionPing(LGCircuit.zoom(_.session.profileSessionUri).value)) { profilesResponse =>
        ProfilesRootModel(ContentModelHandler
          .getContentModel(profilesResponse, AppModule.PROFILES_VIEW)
          .asInstanceOf[Seq[ProfilesPost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearProfiles() =>
      updated(Pot.empty)
  }
}
