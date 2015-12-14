package livelygig.client.services

import org.scalajs.dom
import upickle.default._
import livelygig.client.dtos._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

/**
  * Created by shubham.k on 12/14/2015.
  */
object ApiService {
  var BASE_URL = "http://52.35.10.219:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  def ajaxPost(msgTyoe: String, data: RequestContent): Future[String] = {
    dom.ext.Ajax.post(
      url = BASE_URL,
      data = write(ApiRequest(msgTyoe, data)),
      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
    ).map(_.responseText)
  }

  def createUser(email: String,
                 password: String,
                 jsonBlob: Map[String, String],
                 createBTCWallet: Boolean): Unit = {
    println(ajaxPost(CREATE_USER_REQUEST_MSG,CreateUserRequest(email, password, jsonBlob, createBTCWallet)))
//    ajaxPost(CREATE_USER_REQUEST, CREATE_USER_REQUEST())
  }

}
