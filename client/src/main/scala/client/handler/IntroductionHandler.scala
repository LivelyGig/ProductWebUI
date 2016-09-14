package client.handler


import java.util.UUID

import diode.{ActionHandler, ActionResult, ModelRW}
import client.rootmodel.IntroRootModel
import client.logger
import client.services.{CoreApi, LGCircuit}
import shared.dtos._

import concurrent._
import ExecutionContext.Implicits._
import diode.AnyAction._

import scala.scalajs.js.JSON
import scala.util.{Failure, Success}

/**
  * Created by bhagyashree.b on 2016-07-20.
  */
case class AddNotification(introconfirmSeq: Seq[Introduction])


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
          logger.log.debug("Connection request sent successfully")
        case Failure(fail) =>
          if (count == 3) {
            //            logger.log.error("Error sending connection request")
            LGCircuit.dispatch(ShowServerError(fail.getMessage))
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
      CoreApi.postIntroduction(introConfirmReq).onComplete {
        case Success(response) => logger.log.debug("Intro confirm request sent successfully")
        case Failure(response) => /*logger.log.error("Error sending intro confirm request")*/ LGCircuit.dispatch(ShowServerError(response.getMessage))
      }
      val newList = value.introResponse.filterNot(
        _.introSessionId.equals(introConfirmReq.introSessionId)
      )
      updated(IntroRootModel(newList))

    case AcceptConnectNotification(connectNotification: ConnectNotification) =>
      updated(IntroRootModel(Nil))

    case AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse) =>
      //      if (introductionConfirmationResponse.sessionURI.length != 0) {
      //        updated(IntroRootModel(Nil))
      //      } else
      noChange
    case AddNotification(introconfirmSeq: Seq[Introduction]) =>
      val modelMod =if (value.introResponse.nonEmpty) {
        value.introResponse ++ introconfirmSeq.filterNot(e=>
          value.introResponse.exists(p=>JSON.parse(p.introProfile).name.asInstanceOf[String] ==  JSON.parse(e.introProfile).name.asInstanceOf[String]))
      } else {
        introconfirmSeq
      }

      updated(IntroRootModel(modelMod))
  }

}