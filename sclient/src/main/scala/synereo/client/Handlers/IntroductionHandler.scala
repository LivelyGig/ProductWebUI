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

case class UpdateIntroduction(introConfirmReq: IntroConfirmReq)

case class AcceptConnectNotification(connectNotification: ConnectNotification)

case class AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse)

class IntroductionHandler[M](modelRW: ModelRW[M, IntroRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case AcceptNotification(introSeq: Seq[Introduction]) =>
      //      println(s"newIntroConfirmModel: $introSeq")
      updated(IntroRootModel(introSeq))

    case UpdateIntroduction(introConfirmReq: IntroConfirmReq) =>
      updated(IntroRootModel(Nil))

    case AcceptConnectNotification(connectNotification: ConnectNotification) =>
      updated(IntroRootModel(Nil))

    case AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse) =>
      if (introductionConfirmationResponse.sessionURI.length != 0) {
        updated(IntroRootModel(Nil))
      } else
        noChange
  }
}

