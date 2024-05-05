package models

case class CreateLobby(playerName: String)

case class JoinLobby(playerName: String, lobbyCode: Int)