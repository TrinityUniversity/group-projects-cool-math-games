package models
import PictionaryPlayer._
import java.util.Random
import collection.mutable

object PictionaryLobbyManager {
    private var currentLobbies = List.empty[PictionaryLobby]

    //Used To Create New Lobby
    def createLobby(lobbyCreator: PictionaryPlayer): Int = {

        val lobbyCode = new Random().nextInt(1000000)

        currentLobbies ::= new PictionaryLobby(lobby = lobbyCode, pL = List(lobbyCreator))

        lobbyCode
    }

    def deleteLobby(lobbyId: Int): Unit = {
        currentLobbies = currentLobbies.filterNot(x => x.lobbyId == lobbyId)
    }

    def userJoinLobby(lobbyId: Int, newPlayer: PictionaryPlayer): Unit = {
        currentLobbies.find(x => x.lobbyId == lobbyId).get.addUser(newPlayer)
    }
}

class PictionaryLobby(lobby: Int, pL: List[PictionaryPlayer]){
    private var playerList = pL
    val lobbyId = lobby


    //DOES NOT CHECK FOR DUPLICATE NAMES
    def addUser(newPlayer: PictionaryPlayer): Unit = {
        if(!playerList.contains(newPlayer)){
            playerList ::= newPlayer
        }
    }

    def removeUser(playerName: String){
        playerList = playerList.filterNot(x => x.name == playerName)
    }

}

class PictioanryGame(lobby: Int, players: List[PictionaryPlayer]){

    var roundNumber = 0
    //Def not the best way to do this but uhhhhh idk
    var currentDrawer = players(roundNumber)
    var scoreMap = players.map(x => (x, 0)).toMap[PictionaryPlayer, Int]


    def gotoNextRound() = {
        if(!(roundNumber == players.length)){
            //Again, strange way to do this? Could cause issues but I cant test this rn lol
            roundNumber += 1
            currentDrawer = players(roundNumber)
        } else {
            endGame()
        }
    }

    def endGame() = ???


}