package controllers

import javax.inject._

import shared.SharedMessages
import java.lang.ProcessBuilder.Redirect
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import models.{PictionaryLobbyManager, PictionaryPlayer, CreateLobby,JoinLobby}
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.actor.Props
import actors.{PictionaryActor,PictionaryManager}

@Singleton
class PictionaryController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

    val pictionaryManager = system.actorOf(Props[PictionaryManager])
    implicit val createLobbyReads = Json.reads[CreateLobby]

    def pictionary = Action { implicit request =>
        Ok(views.html.pictionary()(request))
    }

    def createLobby = Action { implicit request =>
        println("Creating Lobby")
        request.body.asJson.map { body =>
            Json.fromJson[CreateLobby](body) match{
                case JsSuccess(lobby, _) => {
                    val lobbyCode:Int = PictionaryLobbyManager.createLobby(new PictionaryPlayer(lobby.playerName,0,false,true))
                    Ok(Json.toJson(lobbyCode)).withSession("csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
                }
                case e @ JsError(_) => BadRequest(Json.obj("error" -> "Invalid JSON"))
            }
        }.getOrElse(Redirect(routes.PictionaryController.pictionary))
    }

    def socket = WebSocket.accept[String, String] { implicit request =>
        println("Getting Socket")
        ActorFlow.actorRef { out =>
            PictionaryActor.props(out,pictionaryManager)    
        }    
    }


}
