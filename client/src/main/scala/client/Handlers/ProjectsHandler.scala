package client.handlers

import diode.data.PotState.PotPending
import diode.{Effect, ActionHandler, ModelRW}
import diode.data.{Empty, PotAction, Ready, Pot}
import client.models.{JobPosts, ProjectsModel, ConnectionsModel}
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

/*case class ApiResponseTest[T](msgType: String, content: T )
case class ProjectsResponseTest(sessionURI: String, pageOfPosts: Seq[String], connection: Connection,
                            filter : String)*/

object ProjectsModelHandler{
  def GetJobPostsModel(jobPostsResponse: String): ProjectsRootModel = {
    val projectsFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](jobPostsResponse)
//      println(model(0).content.pageOfPosts(0))
//      println(upickle.default.read[PageOfPosts](model(0).content.pageOfPosts(0)))
    var model = Seq[ProjectsModel]()
    for(projectFromBackend <- projectsFromBackend){
//      println(upickle.default.read[PageOfPosts](projectFromBackend.content.pageOfPosts(0)))
      model:+= ProjectsModel(projectFromBackend.content.sessionURI,
        upickle.default.read[JobPosts](projectFromBackend.content.pageOfPosts(0)))
    }
//    println(model)
    ProjectsRootModel(model)
  }

}

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action : RefreshProjects =>
      println("in refreshProjects")
      val updateF = action.effect(CoreApi.getProjects())(jobPostsResponse=>ProjectsModelHandler.GetJobPostsModel(jobPostsResponse))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}