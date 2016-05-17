package synereo.client.handlers

import java.util.UUID
import diode.{ ActionHandler, Effect, ModelRW }
import shared.dtos.{ Expression, ExpressionContent, Label, SubscribeRequest, _ }
import shared.models.{ Post, UserModel }
import org.scalajs.dom.window
import shared.sessionitems.SessionItems
import synereo.client.services.{ SYNEREOCircuit, CoreApi }
import synereo.client.utils.Utils

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js
import scala.util.{ Failure, Success }
import org.widok.moment._
import org.querki.jquery._
import japgolly.scalajs.react._

case class LoginUser(userModel: UserModel)
case class LogoutUser()

case class CreateSessions(userModel2: UserModel)
case class PostMessages(content: String, connectionStringSeq: Seq[String], sessionUri: String)
case class TestDispatch()

case class PostContent(value: Post, connectionStringSeq: Seq[String], sessionUri: String)

class UserHandler[M](modelRW: ModelRW[M, UserModel]) extends ActionHandler(modelRW) {
  override def handle = {
    case LoginUser(userModel) =>
      var modelFromStore = userModel
      val temp = window.sessionStorage.getItem("userEmail")
      if (temp != null) {
        modelFromStore = UserModel(
          email = window.sessionStorage.getItem("userEmail"),
          name = window.sessionStorage.getItem("userName"),
          imgSrc = window.sessionStorage.getItem("userImgSrc"), isLoggedIn = true
        )
      }
      updated(modelFromStore)
    /*create sessions functionality is moved to a method in login module this case is sporadically used*/
    case CreateSessions(userModel2) =>
      val sessionURISeq = Seq(SessionItems.MessagesViewItems.MESSAGES_SESSION_URI, CoreApi.JOBS_SESSION_URI)
      val futures = sessionURISeq.map(sessionURI =>
        CoreApi.agentLogin(userModel2).map { responseStr =>
          val response = upickle.default.read[ApiResponse[InitializeSessionResponse]](responseStr)
          window.sessionStorage.setItem(sessionURI, response.content.sessionURI)
        })
      noChange

    case PostMessages(content: String, connectionStringSeq: Seq[String], sessionUri: String) =>
      val createdDateTime = Moment().utc().format("YYYY-MM-DD hh:mm:ss")
      //      println(createdDateTime)
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val connectionsSeq = Seq(Utils.getSelfConnnection(sessionUri)) ++ connectionStringSeq.map(connectionString => upickle.default.read[Connection](connectionString))
      val value = ExpressionContentValue(uid.toString, "TEXT", createdDateTime, createdDateTime, Map[Label, String]().empty, connectionsSeq, content)
      CoreApi.evalSubscribeRequest(SubscribeRequest(window.sessionStorage.getItem(sessionUri), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, "[1111]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => {
          val ContributeThoughtsID: js.Object = "#ContributeThoughtsID"
          $(ContributeThoughtsID).value(" ")
          SYNEREOCircuit.dispatch(RefreshMessages())
        }
        case Failure(response) => println("failure")
      }
      noChange

    case PostContent(value: Post, connectionStringSeq: Seq[String], sessionUri: String) =>
      val uid = UUID.randomUUID().toString.replaceAll("-", "")
      val connectionsSeq = Seq(Utils.getSelfConnnection(sessionUri)) /* ++ connectionStringSeq.map(connectionString=> upickle.default.read[Connection](connectionString))*/
      //      val value =  ExpressionContentValue(uid.toString,"TEXT","2016-04-15 16:31:46","2016-04-15 16:31:46",Map[Label, String]().empty,connectionsSeq,content)
      CoreApi.evalSubscribeRequest(SubscribeRequest(window.sessionStorage.getItem(sessionUri), Expression(CoreApi.INSERT_CONTENT, ExpressionContent(connectionsSeq, "[1111]", upickle.default.write(value), uid)))).onComplete {
        case Success(response) => {
          println("success")
          println("Responce = " + response)
          //          t.modState(s => s.copy(postNewMessage = true))
        }
        case Failure(response) => println("failure")
      }
      noChange

    case LogoutUser() =>
      CoreApi.cancelPreviousSubsForLabelSearch()
      window.sessionStorage.clear()
      window.location.href = "/"
      updated(UserModel(email = "", name = "", imgSrc = "", isLoggedIn = false))

    case TestDispatch() =>
      val momentdate = Moment().format("YYYY-MM-DD HH:MM:SS")
      println("import org.widok.moment._" + momentdate)
      noChange
  }
}
