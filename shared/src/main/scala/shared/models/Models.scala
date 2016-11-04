package shared.models

import shared.dtos._

sealed trait Post {
  def uid: String
  def created: String
  def modified: String
  def labels: String
  def connections: Seq[Connection]
  def postType: String
  def versionNumber: Int
  def postContent: PostContent
}

sealed trait PostContent

case class MessagePost(uid: String = "", created: String = "", modified: String = "",
                       labels: String = "", connections: Seq[Connection] = Nil,postContent: MessagePostContent, sender: ConnectionsModel =
                       ConnectionsModel(), receivers: Seq[ConnectionsModel] = Nil) extends Post {
  override def postType: String = "MessagePost"
  override def versionNumber: Int = 0
}

case class MessagePostContent(text: String = "", subject: String = "",imgSrc : String="") extends PostContent

case class ProjectsPost(uid: String, created: String, modified: String, labels: String = "",
                        connections: Seq[Connection] = Nil, postContent: ProjectPostContent) extends Post {
  override def postType: String = "VersionedPost"
  override def versionNumber: Int = 0
}

case class ProjectPostContent(name: String, startDate: String, endDate: String, budget: String, contractType: String,
                              workLocation: String, description: String, skillNeeded: String, allowForwarding: Boolean,
                              versionNumber: Int, message: String) extends PostContent

sealed trait Person {
  def name: String
  def capabilities: String
  def title: String
}

case class ProfilesPost(uid: String, created: String, modified: String, labels: String,
                        connections: Seq[Connection], postContent: ProfilePostContent) extends Post {
  override def postType: String = "ProfilePost"
  override def versionNumber: Int = 0
}

case class ProfilePostContent(talentProfile: TalentProfile = TalentProfile()
                              /*, employerProfile: EmployerProfile = EmployerProfile(),
                              moderatorProfile: ModeratorProfile = ModeratorProfile()*/) extends PostContent

case class TalentProfile(name: String = "", title: String = "", capabilities: String = "", video: String = "") extends Person with PostContent

case class EmployerProfile(name: String = "", website: String = "", tagline: String = "", video: String = "",
                           twitter: String = "", logo: String = "") extends PostContent

case class ModeratorProfile(name: String = "", title: String = "", capabilities: String = "", commission: String = "") extends Person with PostContent

case class ConnectionsModel(sessionURI: String = "", connection: Connection = Connection(), name: String = "", imgSrc: String = "")

case class UserModel(name: String = "",
                     email: String = "",
                     password: String = "",
                     isLoggedIn: Boolean = false,
                     imgSrc: String = "",
                     confirmPassword: String = "",
                     isAvailable:Boolean=true,
                     balanceAmp: String = "0.00",
                     balanceBtc: String = "0.00",
                     address: String = "n/a",
                     networkMode: String = "TestNet")

case class SignUpModel(email: String = "",
                       password: String = "",
                       confirmPassword: String = "",
                       name: String = "",
                       lastName: String = "",
                       createBTCWallet: Boolean = false,
                       isModerator: Boolean = false,
                       isClient: Boolean = false,
                       isFreelancer: Boolean = false,
                       canReceiveEmailUpdates: Boolean = false,
                       isLoggedIn: Boolean = false,
                       imgSrc: String = "",
                       didAcceptTerms: Boolean = false)

case class EmailValidationModel(token: String)

case class Label(uid: String = "", text: String = "", color: String = "", imgSrc: String = "", parentUid: String = "", isChecked: Boolean = false)
