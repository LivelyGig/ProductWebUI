package client.services

import client.facades.SRPClient
import shared.dtos._
import org.scalajs.dom._
import upickle.default._
import shared.models.{EmailValidationModel, SignUpModel, UserModel}

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Date
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps
import org.scalajs.dom.ext.Ajax
import shared.models.{EmailValidationModel, SignUpModel, UserModel}
import client.sessionitems.SessionItems
import client.modules.ConnectionList
import client.utils.{ConnectionsUtils, LabelsUtils}

case class ApiError(response: String) extends Exception


object CoreApi {

  def getBaseUrl() = {
    s"${window.sessionStorage.getItem(SessionItems.ApiDetails.API_URL)}/api"
  }


  // scalastyle:ignore
  var CREATE_USER_REQUEST = "createUserRequest"


  private def ajaxPost(requestContent: String): Future[String] = {
    Ajax.post(
      url = getBaseUrl,
      data = requestContent,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
    ).map(_.responseText)

  }

  def createUser(signUpModel: SignUpModel): Future[String] = {
    val srpc = new SRPClient(signUpModel.email, signUpModel.password)
    val requestContent1 = upickle.default.write(ApiRequest(ApiTypes.requestTypes.CREATE_USER_STEP1_REQUEST,
      CreateUserStep1(signUpModel.email)))
    val futureResponse = for {
      requestContent2 <- ajaxPost(requestContent1).map {
        response =>
          Try(upickle.default.read[ApiResponse[CreateUserStep1Response]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(rsp) => upickle.default.write(ApiRequest(ApiTypes.requestTypes.CREATE_USER_STEP2_REQUEST,
              CreateUserStep2(signUpModel.email, Map("name" -> signUpModel.name),
                true, rsp.content.salt, srpc.getVerifierHex(rsp.content.salt))))
          }
      }
      result <- ajaxPost(requestContent2)
    } yield result

    futureResponse.recover {
      case ae: ApiError => ae.response
      case e: Throwable => upickle.default.write(ApiRequest(ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR, ErrorResponse(e.getMessage)))
    }
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.CONFIRM_EMAIL, ConfirmEmail(emailValidationModel.token)))
    ajaxPost(requestContent)
  }


  def agentLogin(userModel: UserModel): Future[String] = {
    val srpc = new SRPClient(userModel.email, userModel.password)
    val Aval = srpc.getAHex()
    val requestContent1 = upickle.default.write(ApiRequest(ApiTypes.requestTypes.INITIALIZE_SESSION_STEP1_REQUEST,
      InitializeSession(s"agent://email/${userModel.email}?A=$Aval")))
    val futureResponse = for {
      requestContent2 <- ajaxPost(requestContent1).map {
        response =>
          Try(upickle.default.read[ApiResponse[UserLoginResponse]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(ulr) =>
              val Mval = srpc.getMHex(ulr.content.B, ulr.content.s)
              upickle.default.write(ApiRequest(ApiTypes.requestTypes.INITIALIZE_SESSION_STEP2_REQUEST,
                InitializeSession(s"agent://email/${userModel.email}?M=$Mval")))
          }
      }
      result <- ajaxPost(requestContent2).map {
        response =>
          Try(upickle.default.read[ApiResponse[InitializeSessionResponseCheck]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(rsp) =>
              if (srpc.matches(rsp.content.M2)) response else throw new Exception("Authentication failed on client")
          }
      }
    } yield result

    futureResponse.recover {
      case ae: ApiError => ae.response
      case e: Throwable => upickle.default.write(ApiRequest(ApiTypes.responseTypes.API_HOST_UNREACHABLE_ERROR, ErrorResponse(e.getMessage)))
    }
  }

//  def agentLogin(userModel: UserModel): Future[String] = {
//    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.INITIALIZE_SESSION_REQUEST, InitializeSession(s"agent://email/${userModel.email}" +
//      s"?password=${userModel.password}")))
//    ajaxPost(requestContent)
//  }

  def sessionPing(uri: String): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.SESSION_PING, SessionPing(uri)))
    ajaxPost(requestContent)
  }


  def evalSubscribeRequest(subscribeRequest: SubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.EVAL_SUBS_REQUEST, subscribeRequest))
    ajaxPost(requestContent)
  }


  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    ajaxPost(requestContent)
  }


  def postIntroduction(introductionModel: Content): Future[String] = {
    val msg = introductionModel match {
      case _: IntroConnections => ApiTypes.requestTypes.BEGIN_INTRODUCTION_REQUEST
      case _: EstablishConnection => ApiTypes.requestTypes.ESTABLISH_CONNECTION_REQ
      case _: IntroConfirmReq => ApiTypes.requestTypes.INTRODUCTION_CONFIRMATION_REQUEST
    }
    ajaxPost(upickle.default.write(ApiRequest(msg, introductionModel)))
  }


  def postLabel(labelPost: LabelPost): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.UPDATE_ALIAS_LABEL_REQ, labelPost))
    ajaxPost(requestContent)
  }

  def updateUserRequest(updateUserRequest: UpdateUserRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.requestTypes.UPDATE_USER_REQUEST, updateUserRequest))
    ajaxPost(requestContent)
  }


}