package PictionaryComponents

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class PictionaryLobby extends Component {
    case class Props(gameID: Int)
    case class State(gameStarted: Boolean, players: List[String])

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
            ),
            h2("Room Code: #" + props.gameID),
            button(id := "start-button", onClick := (e => startGame()))("Start Game")
            )
        )}
        else {
            PictionaryGame(props.gameID)
        }
    )
}