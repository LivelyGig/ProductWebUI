package shared.models

import java.util.Date

/**
 * Created by shubham.k on 27-04-2016.
 */

//case class Post (val id: String, postContent: PostContent)

sealed trait Post {
  def uid: String
  def created: String
  def modified: String
  def spliciousType: String
  def labels: String
  def connections: Seq[String]
  def postType: String
}
sealed trait VersionedPost {
  def versionedPostType: String
  def versionNumber: Int
}
case class MessagePost(uid: String, spliciousType: String, created: String, modified: String,
    labels: String, connections: Seq[String], message: String, recipients: String = "", subject: String = "", content: String = "") extends Post {
  override def postType: String = "MessagePost"
}
case class ProjectsPost(uid: String, created: String, modified: String, spliciousType: String, labels: String,
    connections: Seq[String], projectPostContent: ProjectPostContent, versionNumber: Int = 0) extends Post with VersionedPost {
  override def postType: String = "VersionedPost"
  override def versionedPostType: String = "ProjectPost"
}
sealed trait VersionedPostContent {
  def versionedPostType: String
  def versionNumber: Int
}
case class ProjectPostContent(name: String, startDate: String, endDate: String, budget: String, contractType: String,
  workLocation: String, description: String, skillNeeded: String, allowFormatting: Boolean,
  versionNumber: Int, message: String)

case class TalentPost(versionNumber: Int, talentProfile: Person, employerProfile: EmployerProfile, moderatorProfile: Person) extends VersionedPostContent {
  override def versionedPostType: String = "TalentPost"
}

sealed trait Person {
  def name: String
  def capabilities: Seq[String]
  def title: String
}
case class TalentProfile(name: String, title: String, capabilities: Seq[String]) extends Person
case class EmployerProfile(employerName: String, website: String, tagline: String, video: String, twitterUserName: String, Logo: String)
case class ModeratorProfile(name: String, title: String, capabilities: Seq[String], commission: String) extends Person

