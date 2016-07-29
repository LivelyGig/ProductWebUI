package synereo.client.handlers

import java.util.UUID

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos.{Expression, ExpressionContent, IntroductionConfirmationResponse, SubscribeRequest, _}
import shared.RootModels.IntroRootModel

import concurrent._
import ExecutionContext.Implicits._
import scala.util.{Failure, Success}

// scalastyle:off

/**
  * Created by mandar.k on 4/6/2016.
  */

case class AcceptNotification(introconfirmSeq: Seq[Introduction])

case class UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq)

case class AcceptConnectNotification(connectNotification: ConnectNotification)

case class AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse)

class IntroductionHandler[M](modelRW: ModelRW[M, IntroRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case AcceptNotification(introconfirmSeq: Seq[Introduction]) =>
      val temp = value.introResponse ++ introconfirmSeq
      val newList = temp.groupBy(_.introSessionId).map(_._2.head).toSeq
      updated(IntroRootModel(newList))

    case UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq) =>
      value.introResponse.foreach(intro => println(s"intro.introSessionId ${intro.introSessionId}"))
      //      println(s"introConfirmReq.introSessionId : ${introConfirmReq.introSessionId}")
      val newList = value.introResponse.filterNot(
        _.introSessionId.equals(introConfirmReq.introSessionId)
      )
      println(s"newList : $newList")
      updated(IntroRootModel(newList))

    case AcceptConnectNotification(connectNotification: ConnectNotification) =>
      updated(IntroRootModel(Nil))

    case AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse) =>
      if (introductionConfirmationResponse.sessionURI.length != 0) {
        updated(IntroRootModel(Nil))
      } else
        noChange
  }
}


//groupby fir map