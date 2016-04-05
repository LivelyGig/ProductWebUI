package client.models

import shared.dtos._

object ModelType {
  val connectionModel = "connectionModel"
  val jobPostsModel = "jobPostsModel"
}
case class AppModel(modelType: String, connectionsModel: Seq[ConnectionsModel] = null, jobPostsModel: Seq[EvalSubscribeResponseContent] = null)
//case class MessagesModel (count: Int)
case class ConnectionsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)
case class ProjectsModel(sessionURI: String, jobPosts: JobPosts)
case class JobPosts(id : String, `type` : String, description : String, summary : String,
                    postedDate: String, broadcastDate: String, startDate: String, endDate: String, currency: String,
                    location: String, isPayoutInPieces: String, skills: Seq[Skills], posterId: String, canForward: String,
                    referents: Seq[Referents], contractType: String, budget: Float)
case class Skills (skillId : String, skillName: String)
case class Referents(referentId: String,referentName: String )
//sealed trait SubscribeResponse
case class MessagesModel(uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String, parent: ParentMessageModel = ParentMessageModel("","","","",Nil,Nil,"")) /*extends SubscribeResponse*/
case class ParentMessageModel (uid : String, `type` : String, created: String, modified: String, labels: Seq[LabelResponse], connections: Seq[Connection], text: String)
case class UserModel (email: String = "", password: String = "", name: String = "", createBTCWallet: Boolean = true,
                      isLoggedIn: Boolean = false, imgSrc: String = "")
case class PostMessage(recipients: String = "", subject : String="", content : String="")
case class EmailValidationModel (token: String)
case class LabelResponse(text: String, color: String, imgSrc: String)
case class Label(uid: String, text: String, color: String, imgSrc: String, parentUid: String, isChecked: Boolean = false)
//{"source":"alias://ff5136ad023a66644c4f4a8e2a495bb34689/alias", "label":"34dceeb1-65d3-4fe8-98db-114ad16c1b31","target":"alias://552ef6be6fd2c6d8c3828d9b2f58118a2296/alias"}