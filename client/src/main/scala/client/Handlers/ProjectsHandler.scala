package client.handlers

import diode.data.PotState.PotPending
import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Empty, PotAction, Ready, Pot}
import client.models.{JobPost, ProjectsModel, ConnectionsModel}
import client.rootmodels.ProjectsRootModel
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
  def GetJobPostsModel(jobPostsResponse: String): ProjectsRootModel = {
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
  override def handle = {
    case action : RefreshProjects =>
      val updateF = action.effect(CoreApi.getProjects())(jobPostsResponse=>ProjectsModelHandler.GetJobPostsModel(jobPostsResponse))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}