package playscala

import play.api.libs.json.JsError
import org.scalajs.dom.experimental.Headers
import org.scalajs.dom.experimental.Fetch
import org.scalajs.dom.experimental.HttpMethod
import org.scalajs.dom.experimental.RequestInit
import play.api.libs.json.Json
import play.api.libs.json.JsSuccess
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import scala.scalajs.js.Thenable.Implicits._
import scala.concurrent.ExecutionContext


object FetchJson {

  implicit val ec = ExecutionContext.global

  def fetchGet[B](url: String, success: B => Unit, error: JsError => Unit)(implicit
      reads: Reads[B], ec: ExecutionContext): Unit = {
    Fetch.fetch(url)
      .flatMap(res => res.text())
      .map { data => 
        Json.fromJson[B](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            success(b)
          case e @ JsError(_) =>
            error(e)
        }
    }
  }
}