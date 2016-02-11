package livelygig.client.Handlers

import diode.data.PotState.PotPending
import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Empty, PotAction, Ready, Pot}
import livelygig.client.RootModels.{JobPostsRootModel}
import livelygig.client.models.ConnectionsModel
import livelygig.client.services.CoreApi
import livelygig.shared.dtos.{JobPostsResponse, ConnectionProfileResponse, ApiResponse}
import rx.ops.Timer
import scala.concurrent.ExecutionContext.Implicits.global

import scala.scalajs.js.JSON

/**
  * Created by shubham.k on 1/25/2016.
  */
// Actions
case class RefreshJobPosts(value: Pot[JobPostsRootModel] = Empty) extends PotAction[JobPostsRootModel, RefreshJobPosts]{
  override def next(value: Pot[JobPostsRootModel]) = RefreshJobPosts(value)
}

object JobPostsModelHandler{
  def GetJobPostsModel(jobPosts: Seq[ApiResponse[JobPostsResponse]]): JobPostsRootModel = {
    JobPostsRootModel(jobPosts)
  }

}

class JobPostsHandler[M](modelRW: ModelRW[M, Pot[JobPostsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action : RefreshJobPosts =>
      val updateF = action.effect(CoreApi.getJobPosts())(jobPosts=>JobPostsModelHandler.GetJobPostsModel(jobPosts))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}