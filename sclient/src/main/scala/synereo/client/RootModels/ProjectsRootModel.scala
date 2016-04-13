package synereo.client.RootModels

import synereo.client.models.ProjectsModel


/**
  * Created by shubham.k on 1/25/2016.
  */
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
