package shared.models

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, SimpleTimeZone, TimeZone}

import shared.dtos._

object ModelType {
  val connectionModel = "connectionModel"
  val jobPostsModel = "jobPostsModel"
}

case class AppModel(modelType: String, connectionsModel: Seq[ConnectionsModel] = null, jobPostsModel: Seq[EvalSubscribeResponseContent] = null)

//case class MessagesModel (count: Int)

//sealed trait SubscribeResponse
case class MessagesModel(uid: String = "", `type`: String = "", created: String = "", modified: String = "", labels: Seq[LabelResponse] = Nil, connections: Seq[Connection] = Nil, text: String = "", parent: ParentMessageModel = ParentMessageModel("", "", "", "", Nil, Nil, ""))

case class ConnectionsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)

case class Skills(skillId: String, skillName: String)

/*extends SubscribeResponse*/
//case class ParentMessageModel(uid: String, `type`: String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String)

//{"source":"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias", "label":"34dceeb1-65d3-4fe8-98db-114ad16c1b31","target":"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias"}
case class ProjectsModel(sessionURI: String, jobPosts: JobPost)
//case class Skills (skillId : String, skillName: String)
case class Referents(referentId: String,referentName: String )
//sealed trait SubscribeResponse
//case class MessagesModel(uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String, parent: ParentMessageModel = ParentMessageModel("","","","",Nil,Nil,"")) /*extends SubscribeResponse*/
case class ParentMessageModel (uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String)
case class UserModel (name: String = "",email: String = "", password: String = "",isLoggedIn: Boolean = false, imgSrc: String = "",ConfirmPassword: String = "")
case class SignUpModel (email: String = "", password: String = "", confirmPassword:String="", name: String = "", lastName: String = "", createBTCWallet: Boolean = false, isModerator:Boolean =false,
                        isClient:Boolean =false, isFreelancer:Boolean=false, canReceiveEmailUpdates:Boolean=false, isLoggedIn: Boolean = false, imgSrc: String = "", didAcceptTerms: Boolean = false)
case class EmailValidationModel (token: String)
case class LabelResponse(text: String, color: String, imgSrc: String)
case class LabelModel(uid: String, text: String, color: String, imgSrc: String, parentUid: String, isChecked: Boolean = false)
//{"source":"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias", "label":"34dceeb1-65d3-4fe8-98db-114ad16c1b31","target":"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias"}

//# Post Classes follow
//  See LivelyGig UI Class diagram https://www.lucidchart.com/publicSegments/view/386d5976-acc9-4539-b7d6-135b204dcc5e/image.png
abstract class Post (val id: String, val spliciousType: String, val createdDate: String, val modifiedDate: String, val labels: String, val connections: String, val text: String) {
  // The arguments here are to assure compatibility with SpliciousUI, at least for a while.
  def postKindLabel = "undefined"

  // ToDo: Shubham, please check this. Should createdDate and modifiedDate be passed in as var (mutable)?
  // if (createdDate.isEmpty){
    // createdDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).toString
    // modifiedDate = createdDate
  // }

  // Ed's suggestions for postTypeSpec
  // authorizingAddress
  // authorizingSignature
  // pomotionTx
  // promotedAmount
  // promoteStartTimestamp
  // promoteDurationInDays
  // canForward
  // posters
}
abstract class MimePost (override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends Post (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "undefined"
}
case class MessagePost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String, recipients: String = "", subject : String="", content : String="") extends MimePost (id, spliciousType, createdDate, modifiedDate, labels, connections, text)
{
  // ToDo: parameters recipients and connections are probably redundant.  Subject and Content should be blobbed/Mimed inside of Text?
  override def postKindLabel = "11276cf64d6249deaaeeaf156c8dcfda"
}
abstract class VersionedPost (override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends MimePost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "undefined"
  // Ed's suggestions for versionedPostTypeSpec
  // specVersion
  // id
  // predecessorID   a GUID that has a common value for all the versioned posts
  // publishedDate   a datetime that will be used along with the VersionedPostID to determine the latest version
  // isCompleted  a boolean when set to true has the effect of deletion
}
case class JobPost(id: String, `type`: String, description: String, summary: String,
                    postedDate: String, broadcastDate: String, startDate: String, endDate: String, currency: String,
                    location: String, isPayoutInPieces: String, skills: Seq[Skills], posterId: String, canForward: String,
                    referents: Seq[Referents], contractType: String, budget: Float) {
  // ToDo: needs to extends VersionedPost.  However, this would then have a large signature that would macro-expand to exceed the 22 fields limit in case classes. http://www.scala-lang.org/old/node/7910
  //  override def postKindLabel = "cba62260f2ec4bfc86fb49c180c3987d"
  // Ed's notes:
  // requiredSkills
  // desiredSkills
  // projectCategories
  // projectState
  // budget
  // budgetCurrency
  // workLocation
  // contractTemplate
}
case class ContestPost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "bf0571e00e404268b5818c3fc2073010"
}
case class OfferingPost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "8700ce7bcc9b4f3a92be1d0af7c25e0d"
}
case class BuyerProfilePost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "bd6ddc6bdc2e4d9e9d9acb6160d71460"
}
case class SellerProfilePost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "fa44572406ec49f792fe6932dd1dc27e"
  // Ed's notes:
  // numberProjectsCompleted
  // firstName
  // middleName
  // lastName
  // address
  // website
  // phone
  // phoneType
  // xbtWalletAddress
  // dibsAddress
  // skills
  // availableFromDate
  // availableToDate
  // currencyPreference
}
case class ModeratorProfilePost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "4fcd333e0d3345b19ffad536a2b05180"
}
case class ContractPost(override val id: String, override val spliciousType: String, override val createdDate: String, override val modifiedDate: String, override val labels: String, override val connections: String, override val text: String) extends VersionedPost (id, spliciousType, createdDate, modifiedDate, labels, connections, text) {
  override def postKindLabel = "5d11a8528a6b477fb009c0a1028ddc99"
  // Ed's notes.  See also sample-data-demo.xlsx, "contract blob" tab.  It is quite detailed
  // originatingPostID
  // buyerID
  // sellerID
  // moderatorID
  //
  // ## Contract
  // status : enum (Initiating | Escrow | InProgress | Feedback | Closed)
  // escrowStatus: enum (new, waiting on seller, ....)
  // buyerAgreement : Boolean
  // sellerAgreement : Boolean
  // agreementHistory
  // escrowBuyerPublicKey
  // escrowSellerPublicKey
  // escrowModeratorPublicKey
  // escrowTimeout1
  // escrowP2SH
  // sellerProfile
  // buyerProfile
  // moderatorProfile
  // sellableEntity
  //
  // ## ContractTerms
  // name
  // termType
  // termValue
  // termLinks
  // employerAgreement : Boolean
  // talentAgreement : Boolean
  // history
  //
  // ## Milestone
  // plannedFinish
  // scheduledFinish
  // title
  // sellerComplete
  // buyerComplete
  //
  // ## ContractNote
  //
  // ## Links
  // added
  // by
  // name
  //
  // ## FeedbackItems
  // feedbackBuyerToSeller
  // feedbackSellerToBuyer
  // feedbackBuyerToModerator
  // feedbackSellerToModerator
  // feedbackModeratorToBuyer
  // feedbackModeratorToSeller
  //
  // name
  // type: enum (...)
  // value
  // escrowBitcoinTransaction
  // transactionId
  // transactionType : enum (buyerFund, sellerFund, payout, unknown)
}
