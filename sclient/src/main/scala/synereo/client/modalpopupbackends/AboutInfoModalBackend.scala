package synereo.client.modalpopupbackends

import japgolly.scalajs.react._
import shared.dtos.{ApiResponse, VersionInfoResponse}
import synereo.client.components._
import synereo.client.components.Bootstrap._
import synereo.client.logger
import synereo.client.modalpopups.AboutInfoModal
import synereo.client.services.CoreApi

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by mandar.k on 9/2/2016.
  */
//scalastyle:off
class AboutInfoModalBackend(t: BackendScope[AboutInfoModal.Props, AboutInfoModal.State]) {
  def hide = Callback {
    jQuery(t.getDOMNode()).modal("hide")
  }

  def mounted(props: AboutInfoModal.Props) = Callback {
    CoreApi.getVersionInfo().onComplete {
      case Success(res) =>
        try {
          val versionInfoFromAjax = upickle.default.read[ApiResponse[VersionInfoResponse]](res).content
          logger.log.debug(s"versionInfoFromAjax:$versionInfoFromAjax")
          t.modState(state => state.copy(versionInfo = versionInfoFromAjax)).runNow()
        } catch {
          case e: Exception => logger.log.error("Exception in read VersionInfoResponse")
        }
      case Failure(res) =>
        logger.log.debug(s"getVersionInfo Failed: $res")
    }
  }

  def formClosed(state: AboutInfoModal.State, props: AboutInfoModal.Props): Callback = {
    props.submitHandler()
  }
}