package shared.dtos

/**
  * Represents content within a request/response to API endpoints.
  */

/**
  * Wraps all API requests in a standard format.
  */

case class ApiRequest(msgType: String, content: Content)

case class ApiResponse[T](msgType: String, content: T)

sealed trait Content {

}

case class CreateUser(email: String, password: String, jsonBlob: Map[String, String], createBTCWallet: Boolean)
  extends Content

case class CreateUserResponse()

/*extends Content*/
//case class CreateUserResponse()

case class ConfirmEmail(token: String) extends Content

case class ConfirmEmailResponse(agentURI: String)

case class ErrorResponse(reason: String)

case class InitializeSession(agentURI: String) extends Content

case class InitializeSessionResponse(sessionURI: String, listOfAliases: Seq[String], defaultAlias: String, listOfLabels: Seq[String],
                                     listOfConnections: Seq[Connection], lastActiveLabel: String,
                                     jsonBlob: Map[String, String])

case class InitializeSessionErrorResponse(reason: String)

case class ConnectionProfileResponse(sessionURI: String, connection: Connection, jsonBlob: String)

case class EvalSubscribeResponseContent(sessionURI: String, pageOfPosts: Seq[String] = Nil, connection: Connection = Connection(),
                                        filter: String = "")

case class Connection(source: String = "", label: String = "", target: String = "")

case class SessionPing(sessionURI: String) extends Content

case class SubscribeRequest(sessionURI: String, expression: Expression) extends Content

case class CancelSubscribeRequest(sessionURI: String, connections: Seq[Connection] = Nil, filter: String = "") extends Content

case class Expression(msgType: String, content: ExpressionContent)

case class ExpressionContent(cnxns: Seq[Connection], label: String, value: String = "", uid: String = "")

case class Label(text: String, color: String, imgSrc: String)

case class IntroConnections(sessionURI: String = "", alias: String = "", aConnection: Connection = Connection(),
                            bConnection: Connection = Connection(), aMessage: String = "", bMessage: String = "") extends Content

case class EstablishConnection(sessionURI: String = "", aURI: String = "", bURI: String = "", label: String = "") extends Content


case class LabelPost(sessionURI: String = "", labels: Seq[String] = Nil, alias: String = "") extends Content

case class IntroductionNotification(introSessionId: String, correlationId: String, connection: Connection, message: String, introProfile: String)

case class IntroConfirmReq(sessionURI: String = "", alias: String = "", introSessionId: String = "",
                           correlationId: String = "", accepted: String = "") extends Content

case class SessionPong(sessionURI: String = "")

case class ConnectNotification(connection: Connection, introProfile: String = "")