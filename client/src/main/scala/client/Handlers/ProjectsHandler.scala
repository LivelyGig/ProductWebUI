package client.handlers

import client.Handlers.ContentModelHandler
import client.modules.AppModule
import diode.{ ActionHandler, ActionResult, Effect, ModelRW }
import diode.data._
import shared.models.ProjectsPost
import org.scalajs.dom.window
import shared.RootModels.ProjectsRootModel
import client.services.{ CoreApi, SessionItems }
import diode.util.{ Retry, RetryPolicy }

import scala.concurrent.ExecutionContext.Implicits.global

// Actions
case class RefreshProjects(potResult: Pot[ProjectsRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[ProjectsRootModel, RefreshProjects] {
  override def next(value: Pot[ProjectsRootModel], newRetryPolicy: RetryPolicy) = RefreshProjects(value, newRetryPolicy)
}

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[AnyRef, ActionResult[M]] = {
    case action: RefreshProjects =>
      val labels = window.sessionStorage.getItem(SessionItems.ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH)
      val updateF = action.effectWithRetry(CoreApi.getProjects()) { jobPostsResponse =>
        ProjectsRootModel(ContentModelHandler
          .getContentModel(jobPostsResponse, AppModule.PROJECTS_VIEW)
          .asInstanceOf[Seq[ProjectsPost]])
      }
      Option(labels) match {
        case Some(s) =>
          action.handleWith(this, updateF)(PotActionRetriable.handler())
        case _ =>
          updated(Empty)
      }
  }
}