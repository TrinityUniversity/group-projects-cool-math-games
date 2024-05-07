package PictionaryComponents

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom
import play.api.libs.json.Json
import shared.PicitonaryMessage._
import shared._

@react class PictionaryLobby extends Component {

    case class Props(gameID: Int, playerName: String)
    case class State(gameStarted: Boolean, players: List[String])

    //Initialize the websocket
    val socketRoute = dom.document.getElementById("ws-route")
    val socket = new dom.WebSocket(socketRoute.getAttribute("value").replace("http","ws"))

    var playerList = List.empty[String]
    
    socket.onopen = (e: dom.Event) => {
        println(Json.stringify(Json.toJson(JoinLobby(props.playerName, props.gameID))))
        socket.send(Json.stringify(Json.toJson(JoinLobby(props.playerName, props.gameID))))
    }

    socket.onmessage = (e: dom.MessageEvent) => {
        println("Got Here")
        println(e.data)
        playerList = e.data.asInstanceOf[String].filterNot(x => x == '(' || x == ')' || x == 'L' || x == 'i' || x == 's' || x == 't').split(",").toList
        setState(state.copy(players = playerList))
    }

    def initialState: State = State(false, List())

    def startGame() = {
        println("Starting Game")
        setState(state.copy(gameStarted = true))
    }

    def render(): ReactElement = div (
        if(!state.gameStarted){
            div(id := "container")(
            div(id := "lobby-container")(
            h1("Pictionary Lobby"),
            div(id := "lobby-text")(
                p(
                "Share the room code with your friends to invite them to the game!",
                br(),
                "Once everyone has joined, click the \"Start Game\" button to begin."
                )
            ),
            h2("Players in Lobby:"),
            ul(id := "player-list")(
                state.players.zipWithIndex.map(item => (li(key := item._2.toString())(item._1)))
            ),
            h2("Room Code: #" + props.gameID),
            button(id := "start-button", onClick := (e => startGame()))("Start Game")
            )
        )}
        else {
            PictionaryGame(props.gameID, socket, state.players)
        }
    )
}