package shared.models

import java.util.Date

import shared.dtos.{Connection, Label}

/**
 * Created by shubham.k on 27-04-2016.
 */

//case class Post (val id: String, postContent: PostContent)

sealed trait Post {
  def uid: String
  def created: String
  def modified: String
  def labels: String
  def connections: Seq[Connection]
  def postType: String
  def versionNumber: Int
}
sealed trait PostContent
case class MessagePost(uid: String = "", created: String = "", modified: String = "",
 labels: String = "", connections: Seq[Connection] = Nil, messagePostContent:MessagePostContent) extends Post {
  override def postType: String = "MessagePost"
  override def versionNumber: Int = 0
}
case class MessagePostContent(text: String = "", subject: String = "") extends PostContent
case class ProjectsPost(uid: String, created: String, modified: String, labels:String = "",
                        connections: Seq[Connection] = Nil, projectPostContent: ProjectPostContent) extends Post  {
  override def postType: String = "VersionedPost"
  override def versionNumber: Int = 0
}
case class ProjectPostContent(name: String, startDate: String, endDate: String, budget: String, contractType: String,
  workLocation: String, description: String, skillNeeded: String, allowFormatting: Boolean,
  versionNumber: Int, message: String) extends PostContent

/*case class TalentPost(versionNumber: Int, talentProfile: Person, employerProfile: EmployerProfile, moderatorProfile: Person) extends VersionedPostContent {
  override def versionedPostType: String = "TalentPost"
}*/

sealed trait Person {
  def name: String
  def capabilities: Seq[String]
  def title: String
}
case class TalentProfile(name: String, title: String, capabilities: Seq[String]) extends Person
case class EmployerProfile(employerName: String, website: String, tagline: String, video: String, twitterUserName: String, Logo: String)
case class ModeratorProfile(name: String, title: String, capabilities: Seq[String], commission: String) extends Person

