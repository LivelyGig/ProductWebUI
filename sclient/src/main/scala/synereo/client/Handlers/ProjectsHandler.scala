package synereo.client.handlers

import diode.data.{ Empty, Pot, PotAction }
import diode.{ ActionHandler, ModelRW }
import shared.RootModels.ProjectsRootModel
import shared.dtos.{ ApiResponse, EvalSubscribeResponseContent }
import shared.models._
import synereo.client.services.CoreApi

//import rx.ops.Timer
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by shubham.k on 1/25/2016.
 */
// Actions
case class RefreshProjects(potResult: Pot[ProjectsRootModel] = Empty) extends PotAction[ProjectsRootModel, RefreshProjects] {
  override def next(value: Pot[ProjectsRootModel]) = RefreshProjects(value)
}

/*case class ApiResponseTest[T](msgType: String, content: T )
case class ProjectsResponseTest(sessionURI: String, pageOfPosts: Seq[String], connection: Connection,
                            filter : String)*/

object ProjectsModelHandler {
  def getJobPostsModel(jobPostsResponse: String): ProjectsRootModel = {
//    val projectsFromBackend = upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](jobPostsResponse)
//    //      println(model(0).content.pageOfPosts(0))
//    //      println(upickle.default.read[PageOfPosts](model(0).content.pageOfPosts(0)))
//    var model = Seq[ProjectsModel]()
//    for (projectFromBackend <- projectsFromBackend) {
//      //      println(upickle.default.read[PageOfPosts](projectFromBackend.content.pageOfPosts(0)))
//      model :+= ProjectsModel(
//        projectFromBackend.content.sessionURI,
//        upickle.default.read[JobPost](projectFromBackend.content.pageOfPosts(0))
//      )
//    }
    //    println(model)
    ProjectsRootModel(Nil)
  }

}

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle = {
    case action: RefreshProjects =>
      println("in refreshProjects")
      val updateF = action.effect(CoreApi.getProjects())(jobPostsResponse => ProjectsModelHandler.getJobPostsModel(jobPostsResponse))
      action.handleWith(this, updateF)(PotAction.handler())
  }
}