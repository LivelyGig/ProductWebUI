package shared.models

import java.util.Date


/**
  * Created by shubham.k on 27-04-2016.
  */

//case class Post (val id: String, postContent: PostContent)

sealed trait Post {
  def id : String
  def createdDate : String
  def modifiedDate: String
  def spliciousType: String
  def labels: String
  def connections: Seq[String]
  def postType : String

}
case class MessagePost (id: String, spliciousType: String, createdDate: String, modifiedDate: String, labels: String, connections: Seq[String], message: String, recipients: String = "", subject : String="", content : String="") extends Post{
  override def postType = "MessagePost"
}
case class VersionedPost (id: String , createdDate : String,modifiedDate: String, spliciousType: String,labels: String, connections: Seq[String], versionedPostContent: VersionedPostContent)extends Post{
  override def postType = "VersionedPost"
}
sealed trait VersionedPostContent {
  def versionedPostType : String
  def versionNumber: Int
}
case class ProjectPost (name : String, startDate: String, endDate: String, budget: String, contractType: String, workLocation: String, description: String, skillNeeded: String, allowFormatting: Boolean, versionNumber: Int, message: String) extends VersionedPostContent {
  override def versionedPostType = "ProjectPost"
}

case class TalentPost (versionNumber: Int, talentProfile: Person, employerProfile: EmployerProfile, moderatorProfile: Person) extends VersionedPostContent{
  override def versionedPostType = "TalentPost"
}

sealed trait Person {
  def name: String
  def capabilities: Seq[String]
  def title: String
}
case class TalentProfile(name: String, title: String, capabilities: Seq[String]) extends Person
case class EmployerProfile(employerName: String, website: String, tagline: String, video: String, twitterUserName: String, Logo: String)
case class ModeratorProfile(name: String, title: String, capabilities: Seq[String], commission: String) extends Person

