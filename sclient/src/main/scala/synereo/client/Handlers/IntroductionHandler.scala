package synereo.client.handlers

import java.util.UUID

import diode.{ActionHandler, ActionResult, ModelRW}
import shared.dtos.{Expression, ExpressionContent, SubscribeRequest, _}
import shared.RootModels.IntroRootModel
import concurrent._
import ExecutionContext.Implicits._
import scala.util.{Failure, Success}

// scalastyle:off

/**
  * Created by mandar.k on 4/6/2016.
  */

case class GotNotification(introconfirmSeq: Seq[Introduction])

class IntroductionHandler[M](modelRW: ModelRW[M, IntroRootModel]) extends ActionHandler(modelRW) {
  override def handle: PartialFunction[Any, ActionResult[M]] = {

    case GotNotification(introSeq: Seq[Introduction]) =>
      //      println(s"newIntroConfirmModel: $introSeq")
      updated(IntroRootModel(introSeq))
  }

}

