package livelygig.client.RootModels

import livelygig.client.models.ProjectsModel
import livelygig.shared.dtos._


/**
  * Created by shubham.k on 1/25/2016.
  */
case class ProjectsRootModel(projectsModelList: Seq[ProjectsModel]) {
  def updated (newProject: ProjectsModel) = {
//    println(newJobPostsResponse)
    projectsModelList.indexWhere(_.connection.target == newProject.connection.target)
    match {
      case -1 =>
        ProjectsRootModel(projectsModelList:+newProject)
      case target =>
        ProjectsRootModel(projectsModelList.updated(target, newProject))
    }
  }
}
