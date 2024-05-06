package playscala
import org.scalajs.dom
import scala.concurrent.ExecutionContext
import org.scalajs.dom.experimental._
import play.api.libs.json._
import scala.scalajs.js.Thenable.Implicits._
import scala.scalajs.js.JSON


object PostFetch {
  implicit val ec = ExecutionContext.global

  

  def fetch[A, B](url: String, csrfToken: String, data: A, success: B => Unit, error: JsError => Unit)(implicit writes: Writes[A], reads: Reads[B]): Unit = {
    val hs = new Headers()
    //println(csrfToken)
    hs.set("Content-Type", "application/json")
    hs.set("Csrf-Token", csrfToken)
    Fetch.fetch(
      url,
      new RequestInit {
        method = HttpMethod.POST
        headers = hs
        body = Json.toJson(data).toString
      }
    ).flatMap(_.text()).map { res =>
      println(res)
      Json.fromJson[B](Json.parse(res)) match {
        case JsSuccess(ret, path) => 
          success(ret)
        case e @ JsError(_) => 
          println("Fetch error " + e)
          error(e)
      }
    }
  }
}