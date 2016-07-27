package client.handlers


import java.util.UUID

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.RootModels.IntroRootModel
import shared.dtos.{Expression, ExpressionContent, SubscribeRequest, _}


import concurrent._
import ExecutionContext.Implicits._
import scala.util.{Failure, Success}

/**
  * Created by bhagyashree.b on 2016-07-20.
  */

case class AcceptNotification(introconfirmSeq: Seq[Introduction])

case class GotNotification(introconfirmSeq: Seq[Introduction])

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

