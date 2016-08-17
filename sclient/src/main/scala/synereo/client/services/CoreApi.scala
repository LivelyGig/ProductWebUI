package synereo.client.services

import shared.dtos._
import org.scalajs.dom._
import upickle.default._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Date
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps
import org.scalajs.dom.ext.Ajax
import shared.models.{EmailValidationModel, SignUpModel, UserModel}
import synereo.client.sessionitems.SessionItems
import shared.sessionitems.SessionItems
import shared.sessionitems.SessionItems.ProfilesViewItems
import synereo.client.logger
import synereo.client.modules.{ConnectionList, Login}
import synereo.client.srp.SRPClient
import synereo.client.utils.{ConnectionsUtils, LabelsUtils}

case class ApiError(response: String) extends Exception

object CoreApi {

  //var BASE_URL = s"https://${window.sessionStorage.getItem(SessionItems.ApiDetails.API_HOST)}:${window.sessionStorage.getItem(SessionItems.ApiDetails.API_PORT)}/api"

  def getBaseUrl() = {
    //s"http://${window.sessionStorage.getItem(SessionItems.ApiDetails.API_HOST)}:${window.sessionStorage.getItem(SessionItems.ApiDetails.API_PORT)}/api"
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
    val requestContent1 = upickle.default.write(ApiRequest(ApiTypes.CREATE_USER_STEP1_REQUEST,
                                                           CreateUserStep1(signUpModel.email)))
    val futureResponse = for {
      requestContent2 <- ajaxPost(requestContent1).map {
        response =>
          Try(upickle.default.read[ApiResponse[CreateUserStep1Response]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(rsp) => upickle.default.write(ApiRequest(ApiTypes.CREATE_USER_STEP2_REQUEST,
                CreateUserStep2(signUpModel.email, Map("name" -> signUpModel.name),
                                true, rsp.content.salt, srpc.getVerifierHex(rsp.content.salt))))
          }
      }
      result <- ajaxPost(requestContent2)
    } yield result

    futureResponse.recover {
      case ae: ApiError => ae.response
      case e: Throwable => upickle.default.write(ApiRequest(ApiTypes.CreateUserError, ErrorResponse(e.getMessage)))
    }
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.CONFIRM_EMAIL, ConfirmEmail(emailValidationModel.token)))
    ajaxPost(requestContent)
  }

  def agentLogin(userModel: UserModel): Future[String] = {
    val srpc = new SRPClient(userModel.email, userModel.password)
    val Aval = srpc.getAHex()
    val requestContent1 = upickle.default.write(ApiRequest(ApiTypes.INITIALIZE_SESSION_STEP1_REQUEST,
      InitializeSession(s"agent://email/${userModel.email}?A=$Aval")))
    val futureResponse = for {
      requestContent2 <- ajaxPost(requestContent1).map {
        response =>
          Try(upickle.default.read[ApiResponse[UserLoginResponse]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(ulr) =>
              val Mval = srpc.getMHex(ulr.content.B, ulr.content.s)
              upickle.default.write(ApiRequest(ApiTypes.INITIALIZE_SESSION_STEP2_REQUEST,
                InitializeSession(s"agent://email/${userModel.email}?M=$Mval")))
          }
      }
      result <- ajaxPost(requestContent2).map {
        response =>
          Try(upickle.default.read[ApiResponse[InitializeSessionResponseCheck]](response)) toOption match {
            case None => throw new ApiError(response)
            case Some(rsp) =>
              if(srpc.matches(rsp.content.M2)) response else throw new Exception("Authentication failed on client")
          }
      }
    } yield result

    futureResponse.recover {
      case ae: ApiError => ae.response
      case e: Throwable => upickle.default.write(ApiRequest(ApiTypes.InitializeSessionError, ErrorResponse(e.getMessage)))
    }
  }

  def sessionPing(uri: String): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.SESSION_PING, SessionPing(uri)))
    ajaxPost(requestContent)
  }

  def evalSubscribeRequest(subscribeRequest: SubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.EVAL_SUBS_REQUEST, subscribeRequest))
    ajaxPost(requestContent)
  }

  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    ajaxPost(requestContent)
  }

  def postIntroduction(introductionModel: Content): Future[String] = {
    val msg = introductionModel match {
      case _: IntroConnections => ApiTypes.BEGIN_INTRODUCTION_REQUEST
      case _: EstablishConnection => ApiTypes.ESTABLISH_CONNECTION_REQ
      case _: IntroConfirmReq => ApiTypes.INTRODUCTION_CONFIRMATION_REQUEST
    }
    ajaxPost(upickle.default.write(ApiRequest(msg, introductionModel)))
  }

  def postLabel(labelPost: LabelPost): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_ALIAS_LABEL_REQ, labelPost))
    ajaxPost(requestContent)
  }

  def updateUserRequest(updateUserRequest: UpdateUserRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(ApiTypes.UPDATE_USER_REQUEST, updateUserRequest))
    ajaxPost(requestContent)
  }
}
