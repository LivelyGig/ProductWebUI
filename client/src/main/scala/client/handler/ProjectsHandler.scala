package client.handler

import client.modules.AppModule
import diode.{ActionHandler, ActionResult, Effect, ModelRW}
import diode.data._
import shared.models.{ProfilesPost, ProjectsPost}
import org.scalajs.dom.window
import client.rootmodel.ProjectsRootModel
import client.services.{CoreApi, LGCircuit}
import diode.util.{Retry, RetryPolicy}
import client.sessionitems.SessionItems
import client.utils.ContentUtils
import org.widok.moment.Moment

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

// Actions
case class RefreshProjects(potResult: Pot[ProjectsRootModel] = Empty, retryPolicy: RetryPolicy = Retry(3))
    extends PotActionRetriable[ProjectsRootModel, RefreshProjects] {
  override def next(value: Pot[ProjectsRootModel], newRetryPolicy: RetryPolicy) = RefreshProjects(value, newRetryPolicy)
}

case class ClearProjects()

class ProjectsHandler[M](modelRW: ModelRW[M, Pot[ProjectsRootModel]]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case action: RefreshProjects =>
//      val labels = window.sessionStorage.getItem(SessionItems.ProjectsViewItems.CURRENT_PROJECTS_LABEL_SEARCH)
      val updateF = action.effectWithRetry(CoreApi.sessionPing(LGCircuit.zoom(_.session.projectSessionUri).value)) { res =>
        LGCircuit.dispatch(RefreshProjects())
        val currentProjects = if (value.nonEmpty) value.get.projectsModelList else Nil
        val proj = currentProjects ++
          ContentUtils
            .processRes(res)
            .filterNot(_.pageOfPosts.isEmpty)
            .flatMap(content => Try(upickle.default.read[ProjectsPost](content.pageOfPosts(0))).toOption)
        ProjectsRootModel(proj.sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created))))
      }
      action.handleWith(this, updateF)(PotActionRetriable.handler())

    case ClearProjects() =>
      updated(Pot.empty)
  }
}