package services

import java.util.{Date, UUID}
//import upickle.default._
import livelygig.shared._
import livelygig.shared.dtos._
import play.api.libs.ws._
import scala.concurrent.Future
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global
//import play.libs.Json

import org.json4s.jackson.Serialization.write


class ApiService extends Api {
  implicit val formats = org.json4s.DefaultFormats
  var BASE_URL = "http://54.201.214.48:9876/api"
  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  val wsRequest : WSRequest = WS.url(BASE_URL)
  var todos = Seq(
    TodoItem("41424344-4546-4748-494a-4b4c4d4e4f50", 0x61626364, "Wear shirt that says “Life”. Hand out lemons on street corner.", TodoLow, false),
    TodoItem("2", 0x61626364, "Make vanilla pudding. Put in mayo jar. Eat in public.", TodoNormal, false),
    TodoItem("3", 0x61626364, "Walk away slowly from an explosion without looking back.", TodoHigh, false),
    TodoItem("4", 0x61626364, "Sneeze in front of the pope. Get blessed.", TodoNormal, true)
  )

  override def welcome(name: String): String = s"Welcome to SPA, $name! Time is now ${new Date}"

  override def getTodos(): Seq[TodoItem] = {
    // provide some fake Todos
    println(s"Sending ${todos.size} Todo items")
    todos
  }

  // update a Todo
  override def updateTodo(item: TodoItem): Seq[TodoItem] = {
    // TODO, update database etc :)
    if(todos.exists(_.id == item.id)) {
      todos = todos.collect {
        case i if i.id == item.id => item
        case i => i
      }
      println(s"Todo item was updated: $item")
    } else {
      // add a new item
      val newItem = item.copy(id = UUID.randomUUID().toString)
      todos :+= newItem
      println(s"Todo item was added: $newItem")
    }
    todos
  }

  // delete a Todo
  override def deleteTodo(itemId: String): Seq[TodoItem] = {
    println(s"Deleting item with id = $itemId")
    todos = todos.filterNot(_.id == itemId)
    todos
  }

  override def createAgent(userRequest: CreateUserRequest): Future[String] = {
    println(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest)))
    WS.url(BASE_URL).post(write(ApiRequest(CREATE_USER_REQUEST_MSG,userRequest))).map(_.body.toString)
  }
}
