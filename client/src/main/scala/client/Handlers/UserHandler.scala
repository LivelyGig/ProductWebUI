package client.handlers

import java.util.UUID
import javax.annotation.PostConstruct

import diode.{ActionHandler, Effect, ModelRW}
import shared.dtos.{Expression, ExpressionContent, Label, SubscribeRequest, _}
import shared.models.UserModel
import client.services.{CoreApi, LGCircuit}
import client.utils.Utils
import org.scalajs.dom.window

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

case class LoginUser(userModel: UserModel)
case class LogoutUser()
case class CreateSessions(userModel2: UserModel)
case class PostContent(content: String, connectionStringSeq: Seq[String], sessionUri: String)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp!=null) {
        modelFromStore = UserModel(email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
        imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true)
      }
      updated(modelFromStore)
    case CreateSessions(userModel2) =>
      val sessionURISeq = Seq(CoreApi.MESSAGES_SESSION_URI,CoreApi.JOBS_SESSION_URI)
      for (sessionURI <- sessionURISeq) {
        CoreApi.agentLogin(userModel2).map{responseStr=>
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          window.sessionStorage.setItem(sessionURI,response.content.sessionURI)
        }
      }

        noChange

    case PostContent(content: String, connectionStringSeq: Seq[String], sessionUri: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-","")
      val connectionsSeq = Seq(Utils.GetSelfConnnection(sessionUri)) ++ connectionStringSeq.map(connectionString=> upickle.default.read[Connection](connectionString))
      val value =  ExpressionContentValue(uid.toString,"TEXT","2016-04-15 16:31:46","2016-04-15 16:31:46",Map[Label, String]().empty,connectionsSeq,content)
      CoreApi.evalSubscribeRequest(SubscribeRequest(window.sessionStorage.getItem(sessionUri), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq,"[1111]",upickle.default.write(value),uid)))).onComplete{
        case Success(response) => {println("success")
          println("Responce = "+response)
          //          t.modState(s => s.copy(postNewMessage = true))
        }
        case Failure(response) => println("failure")
      }
      noChange

    case LogoutUser() =>
      CoreApi.cancelPreviousSubsForLabelSearch()
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "",imgSrc = "", isLoggedIn = false))
  }
}
