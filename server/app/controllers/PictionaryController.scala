package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import play.api.libs.json._
import models._

@Singleton
class PictionaryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    implicit val createLobbyReads = Json.reads[CreateLobby]

    def pictionary = Action { implicit request =>
        Ok(views.html.pictionary())
    }

    def createLobby = Action { implicit request =>
        request.body.asJson.map { body =>
            Json.fromJson[CreateLobby](body) match{
                case JsSuccess(lobby, _) => {
                    val lobbyCode:Int = PictionaryLobbyManager.createLobby(new PictionaryPlayer(lobby.playerName,0,false,true))
                    Ok(Json.toJson(lobbyCode))
                }
                case e @ JsError(_) => Redirect(routes.PictionaryController.pictionary)
            }
        }.getOrElse(Redirect(routes.PictionaryController.pictionary))
    }


}
