package synereo.client.handlers


import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos._
import synereo.client.rootmodels.IntroRootModel
import synereo.client.logger
import synereo.client.services.{CoreApi, SYNEREOCircuit}
import diode.AnyAction._
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
      ContentHandler.postNewConnection(content)
      noChange

    case PostIntroSuccess(beginIntroductionRes: BeginIntroductionRes) =>
      noChange

    case AcceptNotification(introconfirmSeq: Seq[Introduction]) =>
      val temp = value.introResponse ++ introconfirmSeq
      val newList = temp.groupBy(_.introSessionId).map(_._2.head).toSeq
      updated(IntroRootModel(newList))

    case UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq) =>
      ContentHandler.updateIntroductionsModel(introConfirmReq)
      val newList = value.introResponse.filterNot(
        _.introSessionId.equals(introConfirmReq.introSessionId)
      )
      updated(IntroRootModel(newList))

    case AcceptConnectNotification(connectNotification: ConnectNotification) =>
      updated(IntroRootModel(Nil))

    case AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse) =>
      noChange
  }
}