package livelygig.client.services

import org.scalajs.dom.ext.Ajax
import upickle.default._
import livelygig.client.dtos._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import livelygig.client.models._

/**
  * Created by shubham.k on 12/14/2015.
  */
object ApiService {
  var BASE_URL = "http://52.35.10.219:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  private def ajaxPost(msgType: String, data: RequestContent): Future[String] = {
    Ajax.post(
      url = BASE_URL,
      data = write(ApiRequest(msgType, data)),
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
    ).map(_.responseText)
  }

  def createUser(userModel: UserModel): Unit = {
    ajaxPost(CREATE_USER_REQUEST_MSG,CreateUserRequest(userModel.email, userModel.password,
      Map("name" -> userModel.name), userModel.createBTCWallet)).onSuccess {
      case s =>
        println(s)
      // now you need to refresh the UI
    }
  }

}
