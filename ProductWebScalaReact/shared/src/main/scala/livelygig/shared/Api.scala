package livelygig.shared

import livelygig.shared.dtos.{ApiResponse, CreateUserRequest}

import scala.concurrent.Future

trait Api {
  // message of the day
  def welcome(name: String): String

  // get Todo items
  def getTodos(): Seq[TodoItem]

  // update a Todo
  def updateTodo(item: TodoItem): Seq[TodoItem]

  // delete a Todo
  def deleteTodo(itemId: String): Seq[TodoItem]

  def createAgent(userRequest: CreateUserRequest): Future[String]


}
