package shared.models

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
  def connections: String
  def text: String
  def postType : String

}
case class MessagePost ( val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String, recipients: String = "", subject : String="", content : String="") extends Post{
  override def postType = "MessagePost"
}
case class VersionedPost (val id: String , createdDate : String,modifiedDate: String, spliciousType: String,labels: String, connections: String, text: String, versionedPostContent: VersionedPostContent)extends Post{
  override def postType = "VersionedPost"
}
sealed trait VersionedPostContent {
  def versionedPostType : String
  def versionNumber: String
}
case class ProjectPost (name : String, startDate: String, endDate: String, budget: String, contractType: String, workLocation: String, description: String, skillNeeded: String, allowFormatting: Boolean, versionNumber: String) extends VersionedPostContent {
  override def versionedPostType = "ProjectPost"
}

case class TalentPost (versionNumber: String, talentProfile: Person, employerProfile: EmployerProfile, moderatorProfile: Person) extends VersionedPostContent{
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

