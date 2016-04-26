package client.rootmodels

import shared.models.ProjectsModel
import shared.dtos._

case class ProjectsRootModel(projectsModelList: Seq[ProjectsModel]) {
  def updated (newProject: ProjectsModel) = {
//    println(newJobPostsResponse)
    projectsModelList.indexWhere(_.jobPosts.id == newProject.jobPosts.id)
    match {
      case -1 =>
        ProjectsRootModel(projectsModelList:+newProject)
      case target =>
        ProjectsRootModel(projectsModelList.updated(target, newProject))
    }
  }
}
