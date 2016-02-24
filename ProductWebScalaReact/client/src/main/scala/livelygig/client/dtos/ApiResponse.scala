package livelygig.client.dtos

/**
  * Created by shubham.k on 12/14/2015.
  */
case class ApiResponse[T](msgType: String, content: T )