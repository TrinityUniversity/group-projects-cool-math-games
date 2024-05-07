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
    implicit val joinLobbyReads = Json.reads[JoinLobby]
  

    def pictionary = Action { implicit request =>
        //Ok(views.html.pictionary()(request))
        Ok("Ok that is holding the space of acutally Ok")
    }

    def withJsonBody[A] (f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
        request.body.asJson.map {body =>
            Json.fromJson[A](body) match {
                case JsSuccess(a, path) => f(a)
                case e @ JsError(_) => BadRequest(Json.obj("error" -> "Invalid JSON"))
            }
        }.getOrElse(Redirect(routes.PictionaryController.pictionary))
    }

    def createLobby = Action { implicit request =>
        println("Creating Lobby")
        println(request.body.asJson)
        withJsonBody[CreateLobby] { ud => 
            val lobbyCode:Int = PictionaryLobbyManager.createLobby(new PictionaryPlayer(ud.createName,0,false,true))
            print(Json.toJson(lobbyCode))
            Ok(Json.toJson(lobbyCode))//.withSession("csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)               
        }
    }

    def joinLobby = Action { implicit request =>
        withJsonBody[JoinLobby] { ud =>
            if(PictionaryLobbyManager.validateLobby(ud.lobbyCode)){
                PictionaryLobbyManager.userJoinLobby(ud.lobbyCode,new PictionaryPlayer(ud.playerName))
                Ok(Json.toJson(ud.lobbyCode))
            }else{
                Ok(Json.toJson(0))
            }   
        }

    }

    def socket = WebSocket.accept[String, String] { implicit request =>
        println("Getting Socket")
        ActorFlow.actorRef { out =>
            PictionaryActor.props(out,pictionaryManager)    
        }    
    }


}
