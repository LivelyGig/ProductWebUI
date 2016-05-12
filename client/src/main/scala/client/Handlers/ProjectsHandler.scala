package client.handlers

import diode.data.PotState.PotPending
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import diode.data.{Empty, Pot, PotAction, Ready}
import shared.models.{ConnectionsModel, JobPost, ProjectsModel}
import shared.RootModels.ProjectsRootModel
import client.services.CoreApi
import shared.dtos._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import upickle._

import scala.scalajs.js.JSON

// Actions
case class RefreshProjects(potResult: Pot[ProjectsRootModel] = Empty) extends PotAction[ProjectsRootModel, RefreshProjects]{
  override def next(value: Pot[ProjectsRootModel]) = RefreshProjects(value)
}


object ProjectsModelHandler{
  def getJobPostsModel(jobPostsResponse: String): ProjectsRootModel = {
    val projectsFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](jobPostsResponse)
    var model = Seq[ProjectsModel]()
    for(projectFromBackend <- projectsFromBackend){
      model:+= ProjectsModel(projectFromBackend.content.sessionURI,
        upickle.default.read[JobPost](projectFromBackend.content.pageOfPosts(0)))
    }
    ProjectsRootModel(model)
  }

}

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case action : RefreshProjects =>
      val updateF = action.effect(CoreApi.getProjects())(jobPostsResponse=>ProjectsModelHandler.getJobPostsModel(jobPostsResponse))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}