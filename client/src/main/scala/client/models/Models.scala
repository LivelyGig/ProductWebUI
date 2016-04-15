package client.models

import shared.dtos._

object ModelType {
  val connectionModel = "connectionModel"
  val jobPostsModel = "jobPostsModel"
}

case class AppModel(modelType: String, connectionsModel: Seq[ConnectionsModel] = null, jobPostsModel: Seq[EvalSubscribeResponseContent] = null)

//case class MessagesModel (count: Int)

//sealed trait SubscribeResponse
case class MessagesModel(uid: String, `type`: String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String, parent: ParentMessageModel = ParentMessageModel("", "", "", "", Nil, Nil, ""))

case class ConnectionsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)

case class Skills(skillId: String, skillName: String)

/*extends SubscribeResponse*/
//case class ParentMessageModel(uid: String, `type`: String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String)

//{"source":"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias", "label":"34dceeb1-65d3-4fe8-98db-114ad16c1b31","target":"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias"}
case class ProjectsModel(sessionURI: String, jobPosts: JobPosts)

//# Post Classes
//  See LivelyGig UI Class diagram https://www.lucidchart.com/publicSegments/view/386d5976-acc9-4539-b7d6-135b204dcc5e/image.png
abstract class Post()

abstract class MimePost()
  extends Post

// ToDo: rename PostMessage to MessagePost to match class diagram and consistent naming?
//case class PostMessage(recipients: String = "", subject: String = "", content: String = "")
 // extends MimePost
// Splicious posts have the following inside the post:
// uid
// type   enum
// created
// modified
// labels    These are just user-created alphanumeric strings, but that won't work for us because of uniqueness issues
// connections
// text
// Ed's suggestions for postTypeSpec
// specVersion
// authorizingAddress
// authorizingSignature
// pomotionTx
// promotedAmount
// promoteStartTimestamp
// promoteDurationInDays
// canForward
// posters


abstract class VersionedPost()
  extends MimePost
// Ed's suggestions for versionedPostTypeSpec
// specVersion
// id
// predecessorID   a GUID that has a common value for all the versioned posts
// publishedDate   a datetime that will be used along with the VersionedPostID to determine the latest version
// isCompleted  a boolean when set to true has the effect of deletion


// ToDo: rename JobPosts to ProjectPost ?
case class JobPosts(id: String, `type`: String, description: String, summary: String,
                    postedDate: String, broadcastDate: String, startDate: String, endDate: String, currency: String,
                    location: String, isPayoutInPieces: String, skills: Seq[Skills], posterId: String, canForward: String,
                    referents: Seq[Referents], contractType: String, budget: Float)
//case class Skills (skillId : String, skillName: String)
case class Referents(referentId: String,referentName: String )
//sealed trait SubscribeResponse
//case class MessagesModel(uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String, parent: ParentMessageModel = ParentMessageModel("","","","",Nil,Nil,"")) /*extends SubscribeResponse*/
case class ParentMessageModel (uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String)
case class UserModel (name: String = "",email: String = "", password: String = "",isLoggedIn: Boolean = false, imgSrc: String = "")
case class SignUpModel (email: String = "", password: String = "", confirmPassword:String="", name: String = "", lastName: String = "", createBTCWallet: Boolean = false, isModerator:Boolean =false,
                        isClient:Boolean =false, isFreelancer:Boolean=false, canReceiveEmailUpdates:Boolean=false, isLoggedIn: Boolean = false, imgSrc: String = "", didAcceptTerms: Boolean = false)
case class PostMessage(recipients: String = "", subject : String="", content : String="")
case class EmailValidationModel (token: String)
case class LabelResponse(text: String, color: String, imgSrc: String)
case class LabelModel(uid: String, text: String, color: String, imgSrc: String, parentUid: String, isChecked: Boolean = false)
//{"source":"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias", "label":"34dceeb1-65d3-4fe8-98db-114ad16c1b31","target":"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias"}
  extends VersionedPost
// Ed's suggestions for projectPostTypeSpec
// specVersion
// startDate
// endDate
// requiredSkills
// desiredSkills
// projectCategories
// projectState
// budget
// budgetCurrency
// workLocation
// contractTemplate

case class ContestPost()
  extends VersionedPost

case class OfferingPost()
  extends VersionedPost

case class BuyerProfilePost()
  extends VersionedPost

case class SellerProfilePost()
  extends VersionedPost
// Ed's thinking
// specVersion
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


case class ModeratorProfilePost()
  extends VersionedPost

case class ContractPost()
  extends VersionedPost
// Ed's thinking.  See also sample-data-demo.xlsx, "contract blob" tab.  It is quite detailed
// specVersion
// state
// originatingPostID
// buyerID
// sellerID
// moderatorID
// feedbackBuyerToSeller
// feedbackSellerToBuyer
// feedbackBuyerToModerator
// feedbackSellerToModerator
// Contract
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
// ContractTerm
// name
// termType
// termValue
// termLinks
// employerAgreement : Boolean
// talentAgreement : Boolean
// history
// Milestone
// plannedFinish
// scheduledFinish
// title
// sellerComplete
// buyerComplete
// ContractNote
//
// Link
// added
// by
// name
// FeedbackItem
// name
// type: enum (...)
// value
// escrowBitcoinTransaction
// transactionId
// transactionType : enum (buyerFund, sellerFund, payout, unknown)
