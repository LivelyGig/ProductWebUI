package livelygig.shared.dtos

/**
  * Created by shubham.k on 12/14/2015.
  */

/**
  * Wraps all API requests in a standard format.
  */

case class ApiRequest(msgType: String, content: Content)
