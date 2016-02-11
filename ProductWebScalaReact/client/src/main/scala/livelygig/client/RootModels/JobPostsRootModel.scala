package livelygig.client.RootModels

import livelygig.shared.dtos._


/**
  * Created by shubham.k on 1/25/2016.
  */
case class JobPostsRootModel(jobPostsResponse: Seq[ApiResponse[JobPostsResponse]]) {
  def updated (newJobPostsResponse: ApiResponse[JobPostsResponse]) = {
//    println(newConnectionResponse)
    jobPostsResponse.indexWhere(_.content.connection.target == newJobPostsResponse.content.connection.target)
    match {
      case -1 =>
        JobPostsRootModel(jobPostsResponse:+newJobPostsResponse)
      case target =>
        JobPostsRootModel(jobPostsResponse.updated(target, newJobPostsResponse))
    }
  }
}
