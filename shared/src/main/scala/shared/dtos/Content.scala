package shared.dtos

import boopickle.Default._

/**
  * Represents content within a request/response to API endpoints.
  */

/**
  * Wraps all API requests in a standard format.
  */

case class ApiRequest(msgType: String, content: Content)

case class ApiResponse[T](msgType: String, content: T)

sealed trait Content

case class CreateUser(email: String, password: String, jsonBlob: Map[String, String], createBTCWallet: Boolean)
  extends Content

case class CreateUserStep1(email: String) extends Content

case class CreateUserStep2(email: String, jsonBlob: Map[String, String], createBTCWallet: Boolean, salt: String, verifier: String)
  extends Content

case class CreateUserStep1Response(salt: String)

case class CreateUserResponse()

case class CreateUserError(reason: String = "")

case class ConfirmEmail(token: String) extends Content

case class ConfirmEmailResponse(agentURI: String)

case class ErrorResponse(reason: String) extends Content

case class InitializeSession(agentURI: String) extends Content

case class InitializeSessionResponse(sessionURI: String, listOfAliases: Seq[String], defaultAlias: String, listOfLabels: Seq[String],
                                     listOfConnections: Seq[Connection], lastActiveLabel: String,
                                     jsonBlob: Map[String, String], bitcoinNetworkMode: String = "")

case class InitializeSessionResponseCheck(M2: String)

case class InitializeSessionErrorResponse(reason: String)

case class UserLoginResponse(s: String, B: String)

case class ConnectionProfileResponse(sessionURI: String, connection: Connection, jsonBlob: String)

case class ResponseContent(sessionURI: String, pageOfPosts: Seq[String] = Nil, connection: Connection,
                           filter: String)

case class Connection(source: String = "", label: String = "", target: String = "")

case class SessionPing(sessionURI: String) extends Content

case class SubscribeRequest(sessionURI: String, expression: Expression) extends Content

case class CancelSubscribeRequest(sessionURI: String, connections: Seq[Connection] = Nil, filter: String = "") extends Content

case class Expression(msgType: String, content: ExpressionContent)

case class ExpressionContent(cnxns: Seq[Connection], label: String, value: String = "", uid: String = "")

//case class Label(text: String, color: String, imgSrc: String)

case class IntroConnections(sessionURI: String = "", alias: String = "", aConnection: Connection = Connection(),
                            bConnection: Connection = Connection(), aMessage: String = "", bMessage: String = "") extends Content

case class EstablishConnection(sessionURI: String = "", aURI: String = "", bURI: String = "", label: String = "") extends Content


case class LabelPost(sessionURI: String = "", labels: Seq[String] = Nil, alias: String = "") extends Content

case class Introduction(introSessionId: String, correlationId: String, connection: Connection, message: String, introProfile: String)

case class IntroConfirmReq(sessionURI: String = "", alias: String = "", introSessionId: String = "",
                           correlationId: String = "", accepted: Boolean) extends Content

case class IntroductionConfirmationResponse(sessionURI: String = "")

case class SessionPong(sessionURI: String)

case class ConnectNotification(connection: Connection, introProfile: String = "")

case class JsonBlob(name: String = "", imgSrc: String = "")

case class UpdateUserRequest(sessionURI: String = "", jsonBlob: JsonBlob = JsonBlob()) extends Content

case class BeginIntroductionRes(sessionURI: String = "")

case class AddAgentAliasesRequest(sessionURI: String = "", aliases: Seq[String]) extends Content

case class VersionInfoRequest() extends Content

case class VersionInfoResponse(glosevalVersion: String = "", scalaVersion: String = "", mongoDBVersion: String = "", rabbitMQVersion: String = "")

case class CloseSessionRequest(sessionURI: String = "") extends Content

case class OmniBalanceResponse(sessionURI: String , amp: String, btc: String, address: String)

case class SendAmpsRequest(sessionURI: String, amount: String, target: String) extends Content {
  require(amount.nonEmpty, "Amount of AMPs should be non-zero!")
  require(target.nonEmpty, "Unable to amplify the post: receiver's address is missing!")
}

case class SendAmpsResponse(sessionURI: String, transaction: String)

case class ServerModel(uid: String = "", serverAddress : String = "", isEditable : Boolean = true, serverType: String = "")

/*object Content {
  implicit val todoPriorityPickler: Pickler[Content] = generatePickler[Content]
}*/
