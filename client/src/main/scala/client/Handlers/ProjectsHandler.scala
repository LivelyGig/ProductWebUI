package client.handlers

import client.modules.AppModule
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import diode.data._
import shared.models.ProjectsPost
import org.scalajs.dom.window
import client.RootModels.ProjectsRootModel
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.sessionitems.SessionItems

import scala.concurrent.ExecutionContext.Implicits.global

// Actions
case class RefreshProjects(potResult: Pot[ProjectsRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[ProjectsRootModel, RefreshProjects] {
  override def next(value: Pot[ProjectsRootModel], newRetryPolicy: RetryPolicy) = RefreshProjects(value, newRetryPolicy)
}

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: RefreshProjects =>
//      val labels = window.sessionStorage.getItem(SessionItems.ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH)
      val updateF = action.effectWithRetry(CoreApi.sessionPing(LGCircuit.zoom(_.session.projectSessionUri).value)) { res =>
        ProjectsRootModel(ContentModelHandler
          .getContentModel(res, AppModule.PROJECTS_VIEW)
          .asInstanceOf[Seq[ProjectsPost]])
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())
  }
}