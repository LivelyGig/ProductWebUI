package client.handler

import diode.data.{Empty, Pot, PotActionRetriable}
import client.rootmodel.ProfilesRootModel
import shared.models.{MessagePost, ProfilesPost}
import client.modules.AppModule
import diode.{ActionHandler, ActionResult, ModelRW}
import org.scalajs.dom.window
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.sessionitems.SessionItems
import client.utils.ContentUtils
import org.widok.moment.Moment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
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
        LGCircuit.dispatch(RefreshProfiles())
        val currentProfile = if (value.nonEmpty) value.get.profilesList else Nil
        val updatedProfiles = currentProfile ++ ContentUtils
          .processRes(profilesResponse)
          .filterNot(_.pageOfPosts.isEmpty)
          .flatMap(content => Try(upickle.default.read[ProfilesPost](content.pageOfPosts(0))).toOption)
        ProfilesRootModel(updatedProfiles.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created))))
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearProfiles() =>
      updated(Pot.empty)
  }
}
