package actors

import akka.actor.Actor
import akka.actor.ActorRef
import models.PictionaryLobbyManager
import collection.mutable
import shared.PicitonaryMessage._
import shared._
import play.api.libs.json._



class PictionaryManager extends Actor {
    private var players = mutable.Map.empty[String, ActorRef]
    var drawings = List.empty[(String,String,String)]

    import PictionaryManager._
        def receive = {
            case newUser(user) => {
                println("New User Joined")
            }
            case newMsg(msg) => {
                val optJoinLobby: Option[JoinLobby] = Json.parse(msg).asOpt[JoinLobby]
                    optJoinLobby match {
                        case Some(JoinLobby(playerName,gameId)) =>{
                            println("Joining Lobby")
                            players += (playerName -> sender())
                            for((name -> user) <- players) user ! PictionaryActor.sendPlayers(players.keys.toList.toString)
                        }
                        case None => 
                            val optCorrectGuess: Option[CorrectGuess] = Json.parse(msg).asOpt[CorrectGuess]
                                optCorrectGuess match {
                                    case Some(CorrectGuess(playerName)) => {
                                        println("User Guessed Correctly")
                                        //ADD FUNCTIONALITY
                                    }
                                    case None =>
                                        val optDrawPath: Option[DrawPath] = Json.parse(msg).asOpt[DrawPath]
                                            optDrawPath match {
                                                case Some(DrawPath(color,stroke,path)) => {
                                                    val drawPath = (color,stroke,path)
                                                    drawings = drawings :+ drawPath
                                                    for((name -> user) <- players) user ! PictionaryActor.sendPlayers(drawings.mkString)
                                                }
                                                case None => println("FUCKY IT DIDNT WORK")
                                            }
                                }
                    }
                    
                // Json.fromJson[shared.PicitonaryMessage](Json.parse(msg))match {
                //     case JsSuccess(JoinLobby(playerName,gameID),_) => {
                //         players += (playerName -> sender())
                //         for((name -> user) <- players) user ! PictionaryActor.sendPath(drawings.mkString)
                //     }
                //     case JsSuccess(CorrectGuess(playerName),_) => {
                //         println("User Guessed Correctly")
                //         //ADD FUNCTIONALITY
                //     }
                //     case JsSuccess(DrawPath(color,stroke,pathStr),_) => {
                //         drawings ::= (color,stroke,pathStr)
                //         for((name -> user) <- players) user ! PictionaryActor.sendPath(drawings.mkString)
                //     }
                //     case JsError(err) => println("Error Parsing JSON: " + err)
                // }
            }
        }


}

object PictionaryManager {
    case class newUser(user: ActorRef)
    case class newMsg(msg: String)
}