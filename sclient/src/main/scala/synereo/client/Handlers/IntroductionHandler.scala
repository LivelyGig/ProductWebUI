package synereo.client.handlers

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos._
import synereo.client.rootmodels.IntroRootModel
import synereo.client.utils.ContentUtils

import concurrent._
import ExecutionContext.Implicits._
import scala.scalajs.js.JSON

// scalastyle:off


case class AddNotification(introconfirmSeq: Seq[Introduction])

case class UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq)

case class PostIntroSuccess(beginIntroductionRes: BeginIntroductionRes)

case class AcceptIntroductionConfirmationResponse(introductionConfirmationResponse: IntroductionConfirmationResponse)

class IntroductionHandler[M](modelRW: ModelRW[M, IntroRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case AddNotification(introconfirmSeq: Seq[Introduction]) =>
      println(introconfirmSeq)
      val modelMod =if (value.introResponse.nonEmpty) {
        value.introResponse ++ introconfirmSeq.filterNot(e=>
          value.introResponse.exists(p=>JSON.parse(p.introProfile).name.asInstanceOf[String] ==  JSON.parse(e.introProfile).name.asInstanceOf[String]))
      } else {
        introconfirmSeq
      }

      updated(IntroRootModel(modelMod))

    case UpdateIntroductionsModel(introConfirmReq: IntroConfirmReq) =>
      ContentUtils.updateIntroductionsModel(introConfirmReq)
      val newList = value.introResponse.filterNot( _.introSessionId.equals(introConfirmReq.introSessionId))
      updated(IntroRootModel(newList))
  }
}