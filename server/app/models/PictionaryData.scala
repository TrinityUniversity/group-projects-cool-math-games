package models

case class CreateLobby(createName: String)

case class JoinLobby(playerName: String, lobbyCode: Int)