package shared

import play.api.libs.json.Json

sealed trait PicitonaryMessage 

case class JoinLobby(playerName: String, gameID: Int) extends PicitonaryMessage

case class CorrectGuess(playerName: String) extends PicitonaryMessage

case class DrawPath(color: String, stroke: String, path: String) extends PicitonaryMessage

object PicitonaryMessage {

    implicit val joinLobbyRead = Json.reads[JoinLobby]
    implicit val joinLobbyWrites = Json.writes[JoinLobby]

    implicit val correctGuessRead = Json.reads[CorrectGuess]
    implicit val correctGuessWrite = Json.writes[CorrectGuess]

    implicit val drawPathRead = Json.reads[DrawPath]
    implicit val drawPathWrite = Json.writes[DrawPath]

    implicit val pictionaryMessageRead = Json.reads[PicitonaryMessage]
    implicit val pictionaryMessageWrite = Json.writes[PicitonaryMessage]
}