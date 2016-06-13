package client.services

object ApiTypes {
  val CreateUserError = "createUserError"
  val CreateUserWaiting = "createUserWaiting"
  val InitializeSessionResponse = "initializeSessionResponse"
  val CREATE_USER_REQUEST = "createUserRequest"
  val CONFIRM_EMAIL = "confirmEmailToken"
  val INITIALIZE_SESSION_REQUEST = "initializeSessionRequest"
  val SESSION_PING = "sessionPing"
  val PROJECT_REQUEST = "projectsRequest"
  val EVAL_SUBS_REQUEST = "evalSubscribeRequest"
  val INSERT_CONTENT = "insertContent"
  val EVAL_SUBS_CANCEL_REQUEST = "evalSubscribeCancelRequest"
  val FEED_EXPRESSION = "feedExpr"
  val INTRODUCTION_REQUEST = "beginIntroductionRequest"
}
