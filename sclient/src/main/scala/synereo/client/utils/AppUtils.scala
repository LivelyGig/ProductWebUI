package synereo.client.utils

/**
  * Created by shubham.k on 29-07-2016.
  */
object AppUtils {
  val MESSAGE_POST_LABEL = "MESSAGEPOSTLABEL"
  val BTC_SATOSHI = 100000000
/*
  def handleInitialSessionPingRes(response: String): Unit = {
    var cnxnSeq: Seq[ConnectionsModel] = Nil
    var introSeq: Seq[Introduction] = Nil
    val responseArray = upickle.json.read(response) /*.asInstanceOf[js.Array[ApiResponse[Content]]]*/
    responseArray.arr.foreach {
      obj =>
        try {
          val jsonObj = upickle.json.write(obj)
          if(jsonObj.contains("connectionProfileResponse")) {
            val apiRes = upickle.default.read[ApiResponse[ConnectionProfileResponse]](jsonObj)
            cnxnSeq :+= ConnectionsUtils.getCnxnFromRes(apiRes.content)
          }
          if (jsonObj.contains("introductionNotification")) {
            val apiRes = upickle.default.read[ApiResponse[Introduction]](jsonObj)
            introSeq :+= apiRes.content
          }

        } catch {
          case e: Exception =>
            logger.log.error(e.toString)

        }
    }
    SYNEREOCircuit.dispatch(UpdateConnectionModelSeq(cnxnSeq))
    SYNEREOCircuit.dispatch(AddNotification(introSeq))
  }*/
}

