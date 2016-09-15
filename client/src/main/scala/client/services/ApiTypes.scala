package client.services

object ApiTypes {

  object requestTypes {
    val CREATE_USER_REQUEST = "createUserRequest"
    val UPDATE_USER_REQUEST = "updateUserRequest"
    val CREATE_USER_STEP1_REQUEST = "createUserStep1Request"
    val CREATE_USER_STEP2_REQUEST = "createUserStep2Request"
    val CONFIRM_EMAIL = "confirmEmailToken"
    val INITIALIZE_SESSION_REQUEST = "initializeSessionRequest"
    val INITIALIZE_SESSION_STEP1_REQUEST = "initializeSessionStep1Request"
    val INITIALIZE_SESSION_STEP2_REQUEST = "initializeSessionStep2Request"
    val SESSION_PING = "sessionPing"
    val PROJECT_REQUEST = "projectsRequest"
    val EVAL_SUBS_REQUEST = "evalSubscribeRequest"
    val INSERT_CONTENT = "insertContent"
    val EVAL_SUBS_CANCEL_REQUEST = "evalSubscribeCancelRequest"
    val FEED_EXPRESSION = "feedExpr"
    val BEGIN_INTRODUCTION_REQUEST = "beginIntroductionRequest"
    val UPDATE_ALIAS_LABEL_REQ = "updateAliasLabelsRequest"
    val ESTABLISH_CONNECTION_REQ = "establishConnectionRequest"
    val INTRODUCTION_CONFIRMATION_REQUEST = "introductionConfirmationRequest"
    val FEEDEXPR = "feedExpr"
    val VERSION_INFO_REQUEST = "versionInfoRequest"
  }

  object responseTypes {
    val API_HOST_UNREACHABLE_ERROR = "apiHostUnreachableError"
    val CREATE_USER_WAITING = "createUserWaiting"
    val CREATE_USER_ERROR = "createUserError"
    val SESSION_PONG = "sessionPong"
    val INTRODUCTION_NOTIFICATION = "introductionNotification"
    val INTRODUCTION_CONFIRMATIONRESPONSE = "introductionConfirmationResponse"
    val CONNECT_NOTIFICATION = "connectNotification"
    val BEGIN_INTRODUCTION = "beginIntroductionResponse"
    val INITIALIZE_SESSION = "initializeSessionResponse"
    val INITIALIZE_SESSION_STEP1_RESPONSE = "initializeSessionStep1Response"
    val INITIALIZE_SESSION_STEP2_RESPONSE = "initializeSessionStep2Response"
    val EVAL_SUBSCRIBE_RESPONSE = "evalSubscribeResponse"
  }



}