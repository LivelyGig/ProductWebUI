package synereo.client.utils

import shared.dtos.{ApiResponse, InitializeSessionErrorResponse, InitializeSessionResponse}

/**
  * Created by mandar.k on 8/22/2016.
  */
object UserUtils {
  val LOGIN_ERROR = "LOGIN_ERROR"
  val SERVER_ERROR = "SERVER_ERROR"
  val SUCCESS = "SUCCESS"

  def validateInitilizeSessionResponse(response: String): String = {
    try {
      upickle.default.read[ApiResponse[InitializeSessionErrorResponse]](response)
      LOGIN_ERROR
    } catch {
      case e: Exception =>
        try {
          upickle.default.read[ApiResponse[InitializeSessionResponse]](response)
          SUCCESS
        } catch {
          case p: Exception =>
            SERVER_ERROR
        }
    }
  }

}
