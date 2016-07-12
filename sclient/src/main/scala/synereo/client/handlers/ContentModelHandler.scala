package synereo.client.handlers

import org.widok.moment.Moment
import shared.dtos.{ApiResponse, EvalSubscribeResponseContent}
import shared.models.{MessagePost, Post, ProjectsPost}
import synereo.client.logger
import synereo.client.modules.AppModule

/**
  * Created by mandar.k on 5/24/2016.
  */
object ContentModelHandler {
  def filterContent(messages: ApiResponse[EvalSubscribeResponseContent], viewName: String): Option[Post] = {
    try {
      viewName match {
        case AppModule.PROJECTS_VIEW =>
          Some(upickle.default.read[ProjectsPost](messages.content.pageOfPosts(0)))
        case AppModule.MESSAGES_VIEW =>
          Some(upickle.default.read[MessagePost](messages.content.pageOfPosts(0)))
      }
    } catch {
      case e: Exception =>
        logger.log.debug(s"Exception in content filtering: ${e.getMessage}")
        None
    }
  }

  def getContentModel(response: String, viewName: String): Seq[Post] = {
    upickle.default.read[Seq[ApiResponse[EvalSubscribeResponseContent]]](response)
      .filterNot(_.content.pageOfPosts.isEmpty)
      .flatMap(content => filterContent(content, viewName))
      .sortWith((x, y) => Moment(x.created).isAfter(Moment(y.created)))
  }
}
