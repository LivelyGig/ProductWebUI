package shared.dtos

case class ApiResponse[T](msgType: String, content: T )