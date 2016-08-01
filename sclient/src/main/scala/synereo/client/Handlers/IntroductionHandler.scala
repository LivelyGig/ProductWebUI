package synereo.client.handlers


import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos._
import shared.RootModels.IntroRootModel
import synereo.client.logger
import synereo.client.services.CoreApi

import concurrent._
import ExecutionContext.Implicits._
import scala.util.{Failure, Success}

// scalastyle:off

/**
  * Created by mandar.k on 4/6/2016.
  */

case class PostNewConnection(newCnxn: Content)

case class AcceptNotification(introconfirmSeq: Seq[Introduction])

case class UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq)

case class PostIntroSuccess(beginIntroductionRes: BeginIntroductionRes)

case class AcceptConnectNotification(connectNotification: ConnectNotification)

case class AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse)

class IntroductionHandler[M](modelRW: ModelRW[M, IntroRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {
    case PostNewConnection(content: Content) =>
      var count = 1
      post()
      def post(): Unit = CoreApi.postIntroduction(content).onComplete {
        case Success(res) =>
          logger.log.debug("Cnxn Post success")
        case Failure(fail) =>
          if (count == 3) {
            logger.log.error("Cnxn post failure")
          } else {
            count = count + 1
            post()
          }
      }
      noChange

    case PostIntroSuccess(beginIntroductionRes: BeginIntroductionRes) =>
      noChange

    case AcceptNotification(introconfirmSeq: Seq[Introduction]) =>
      val temp = value.introResponse ++ introconfirmSeq
      val newList = temp.groupBy(_.introSessionId).map(_._2.head).toSeq
      updated(IntroRootModel(newList))

    case UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq) =>
      value.introResponse.foreach(intro => println(s"intro.introSessionId ${intro.introSessionId}"))
      val newList = value.introResponse.filterNot(
        _.introSessionId.equals(introConfirmReq.introSessionId)
      )
      println(s"newList : $newList")
      updated(IntroRootModel(newList))

    case AcceptConnectNotification(connectNotification: ConnectNotification) =>
      updated(IntroRootModel(Nil))

    case AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse) =>
      //      if (introductionConfirmationResponse.sessionURI.length != 0) {
      //        updated(IntroRootModel(Nil))
      //      } else
        noChange
  }
}