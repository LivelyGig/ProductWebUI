package livelygig.client.services

import livelygig.shared.Api
import livelygig.shared.dtos._
import livelygig.client.models._
import org.scalajs.dom._
import org.scalajs.dom.ext.Ajax
import upickle.default._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import autowire._
import boopickle.Default._

/**
  * Created by shubham.k on 12/14/2015.
  */
object CoreApi {
  //  var BASE_URL = "http://52.35.10.219:9876/api"
  //  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  //  private def ajaxPost(msgType: String, data: RequestContent): Future[String] = {
  //    Ajax.post(
  //      url = BASE_URL,
  //      data = write(ApiRequest(msgType, data)),
  //      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
  //    ).map(_.responseText)
  //  }

  def createUser(userModel: UserModel): Future[ApiResponse[CreateUserResponse]] = {
    AjaxClient[Api].createAgent(CreateUser(userModel.email, userModel.password,
      Map("name" -> userModel.name), true)).call()
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[ApiResponse[ConfirmEmailResponse]] = {
    AjaxClient[Api].confirmEmail(ConfirmEmail(emailValidationModel.token)).call()
  }

  def agentLogin(userModel: UserModel): Future[ApiResponse[InitializeSessionResponse]] = {
    AjaxClient[Api].agentLogin(InitializeSession(s"agent://email/${userModel.email}" +
      s"?password=${userModel.password}")).call()
  }

  def sessionPing () : Future[Seq[ApiResponse[ConnectionProfileResponse]]] = {
    AjaxClient[Api].sessionPing(SessionPing(window.localStorage.getItem("sessionURI"))).call()
  }
  def getJobPosts () : Future[Seq[ApiResponse[JobPostsResponse]]] = {
    AjaxClient[Api].getJobPosts(SessionPing(window.localStorage.getItem("sessionURI"))).call()
  }

}
