package client.services

object ApiTypes {
  val CreateUserError = "createUserError"
  val CreateUserWaiting = "createUserWaiting"
  val InitializeSessionResponse = "initializeSessionResponse"
  val CREATE_USER_REQUEST = "createUserRequest"
  val UPDATE_USER_REQUEST = "updateUserRequest"
  val CONFIRM_EMAIL = "confirmEmailToken"
  val INITIALIZE_SESSION_REQUEST = "initializeSessionRequest"
  val SESSION_PING = "sessionPing"
  val PROJECT_REQUEST = "projectsRequest"
  val EVAL_SUBS_REQUEST = "evalSubscribeRequest"
  val INSERT_CONTENT = "insertContent"
  val EVAL_SUBS_CANCEL_REQUEST = "evalSubscribeCancelRequest"
  val FEED_EXPRESSION = "feedExpr"
  val BEGIN_INTRODUCTION_REQUEST = "beginIntroductionRequest"
  val UPDATE_ALIAS_LABEL_REQ =  "updateAliasLabelsRequest"
  val ESTABLISH_CONNECTION_REQ = "establishConnectionRequest"
  val INTRODUCTION_CONFIRMATION_REQUEST = "introductionConfirmationRequest"



  val InitializeSessionError = "initializeSessionError"


  val InitializeSessionStep1Response = "initializeSessionStep1Response"
  val InitializeSessionStep2Response = "initializeSessionStep2Response"

  val CREATE_USER_STEP1_REQUEST = "createUserStep1Request"
  val CREATE_USER_STEP2_REQUEST = "createUserStep2Request"

  val INITIALIZE_SESSION_STEP1_REQUEST = "initializeSessionStep1Request"
  val INITIALIZE_SESSION_STEP2_REQUEST = "initializeSessionStep2Request"




}
