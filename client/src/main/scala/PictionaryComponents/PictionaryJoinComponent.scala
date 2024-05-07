package PictionaryComponents

import org.scalajs.dom
import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.core.SyntheticEvent
import playscala.PostFetch
import play.api.libs.json._
import scala.scalajs.js.Thenable.Implicits._
import scala.scalajs.js.JSON
import scala.concurrent.ExecutionContext.Implicits.global


@react class PictionaryJoinComponent extends Component {
    
    case class CreateLobby(createName: String)
    case class JoinLobby(playerName: String, lobbyCode: Int)
    implicit val createLobbyWrites = Json.writes[CreateLobby]
    implicit val joinLobbyWrites = Json.writes[JoinLobby]

    case class Props(doLogin : () => Unit,csrfToken: String)

    case class State( joinName: String, createName: String, joinCode: String, joiningLobby: Boolean)
    def initialState: State = State("","","",false)

    var postLobbyCode: Int = 0
    var playerName: String = ""
    val createLobbyRoute = dom.document.getElementById("createLobby").asInstanceOf[org.scalajs.dom.raw.HTMLInputElement].value
    val joinLobbyRoute = dom.document.getElementById("joinLobby").asInstanceOf[org.scalajs.dom.raw.HTMLInputElement].value

    println(createLobbyRoute)
    println(props.csrfToken)


    /* 
    THINGS TO ADD HERE:
        -ADD BUTTON FUNCTIONALITY
        -CREATE LOBBY
        -JOIN AN EXISTING LOBBY
    */

    def createLobby() = {
        println("Creating Lobby")
        val createName = state.createName
        PostFetch.fetch(createLobbyRoute,props.csrfToken,Json.toJson[CreateLobby](new CreateLobby(state.createName)), (lobbyCode: Int) => {
            postLobbyCode = lobbyCode
            playerName = state.createName
            setState(state.copy(joiningLobby = true))
        }, (e: JsError) => {
            println("Error")
        })
    }

    def joinLobby() = {
        println("Joining Lobby")
        PostFetch.fetch(joinLobbyRoute,props.csrfToken,Json.toJson[JoinLobby](new JoinLobby(state.joinName, state.joinCode.toInt)), (lobbyCode: Int) => {
            postLobbyCode = lobbyCode
            playerName = state.joinName
            println("Got Here with: " + lobbyCode)
            setState(state.copy(joiningLobby = true))
        }, (e: JsError) => {
            println("Error")
        })
    }

    def render(): ReactElement = div (
        if(!state.joiningLobby){
            div(id := "container")(
            div(id := "lobby-container")(
            h1("Welcome to Pictionary!"),
            div(id := "join-container")(
                h2("Create a New Lobby"),
                div(id := "join-form")(
                    input(`type` := "text", id := "createName", placeholder := "Enter Your Name",
                        onChange := (e => setState(state.copy(createName = e.target.value)))),
                    input(`type` := "submit", 
                        onClick := (e => createLobby()))
                ),
                h2("Join an Existing Lobby"),
                div(id := "join-form")(
                    input(`type` := "text", id := "joinName", placeholder := "Enter Your Name",
                        onChange := (e => setState(state.copy(joinName = e.target.value)))),
                    input(`type` := "text", id := "lobbyCode", placeholder := "Enter Lobby Code",
                        onChange := (e => setState(state.copy(joinCode = e.target.value)))),
                    input(`type` := "submit",
                        onClick := (e => joinLobby())
                )
            )
            )
        )
    )
    } else if(state.joiningLobby){
        PictionaryLobby(PictionaryLobby.Props(postLobbyCode,playerName))
    } else {
        PictionaryMainComponent()
    })
}