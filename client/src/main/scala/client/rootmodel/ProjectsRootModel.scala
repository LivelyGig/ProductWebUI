package client.rootmodel

import shared.models.ProjectsPost
import shared.dtos._

case class ProjectsRootModel(projectsModelList: Seq[ProjectsPost]) {
  def updated(newProject: ProjectsPost): ProjectsRootModel = {
    projectsModelList.indexWhere(_.uid == newProject.uid) match {
      case -1 =>
        ProjectsRootModel(projectsModelList :+ newProject)
      case target =>
        ProjectsRootModel(projectsModelList.updated(target, newProject))
    }
  }
}
