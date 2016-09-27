package synereo.client.services

import boopickle.Pickler
import java.nio.ByteBuffer

import boopickle.Default._
import org.scalajs.dom

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.typedarray._

/**
  * Created by shubham.k on 27-09-2016.
  */

object PlayAjaxClient extends autowire.Client[ByteBuffer, Pickler, Pickler] {
  override def doCall(req: Request): Future[ByteBuffer] = {
    dom.ext.Ajax.post(
      url = "/api/" + req.path.mkString("/"),
      data = Pickle.intoBytes(req.args),
      responseType = "arraybuffer",
      headers = Map("Content-Type" -> "application/octet-stream")
    ).map(r => TypedArrayBuffer.wrap(r.response.asInstanceOf[ArrayBuffer]))
  }

  override def read[Result: Pickler](p: ByteBuffer) = Unpickle[Result].fromBytes(p)
  override def write[Result: Pickler](r: Result) = Pickle.intoBytes(r)
}
