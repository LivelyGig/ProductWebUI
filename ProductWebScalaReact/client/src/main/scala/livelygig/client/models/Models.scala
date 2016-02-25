package livelygig.client.models

import livelygig.client.dtos._

/**
  * Created by shubham.k on 2/11/2016.
  */

object ModelType {
  val connectionModel = "connectionModel"
  val jobPostsModel = "jobPostsModel"
}
case class AppModel(modelType: String, connectionsModel: Seq[ConnectionsModel] = null, jobPostsModel: Seq[ProjectsResponse] = null)
case class MessagesModel (count: Int)
case class ConnectionsModel(sessionURI: String, connection: Connection, name: String, imgSrc: String)
case class ProjectsModel(sessionURI: String, pageOfPosts: PageOfPosts, connection: Connection,
                         filter : String)
case class UserModel (email: String = "", password: String = "", name: String = "", createBTCWallet: Boolean = true,
                      isLoggedIn: Boolean = false, imgSrc: String = "")
case class EmailValidationModel (token: String)
case class SearchesModel (node: Option[Node], leaf: Option[Leaf], uid: String)
case class Node(uid: String, text: String, color: String, imgSrc: String, progeny: Seq[Leaf])
case class Leaf(uid: String, text: String, color: String, imgSrc: String, parentUid: String)
