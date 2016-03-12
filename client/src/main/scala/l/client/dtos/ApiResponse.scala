package l.client.dtos

case class ApiResponse[T](msgType: String, content: T )