package PictionaryComponents

import org.scalajs.dom
import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.core.SyntheticEvent
import playscala.FetchJson
import play.api.libs.json._
import scala.scalajs.js.Thenable.Implicits._
import scala.scalajs.js.JSON
import scala.concurrent.ExecutionContext.Implicits.global


@react class PictionaryJoinComponent extends Component {
    case class Props(doLogin : () => Unit,csrfToken: String)
    case class State( joinName: String, createName: String, lobbyCode: String)
    def initialState: State = State("","","")

    val createLobbyRoute = dom.document.getElementById("createLobby").asInstanceOf[org.scalajs.dom.raw.HTMLInputElement].value
    println(createLobbyRoute)


    /* 
    THINGS TO ADD HERE:
        -ADD BUTTON FUNCTIONALITY
        -CREATE LOBBY
        -JOIN AN EXISTING LOBBY
    */

    def createLobby() = {
        println("Creating Lobby")
        FetchJson.fetchGet[Int](createLobbyRoute + "?playerName=" + Json.toJson(state.createName), (lobbyCode: Int) => {
            println("Lobby Code: " + lobbyCode)
        }, (e: JsError) => {
            println("Error")
        })
    }

    def joinLobby() = ???

    def render(): ReactElement = div (
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
                        onChange := (e => setState(state.copy(lobbyCode = e.target.value)))),
                    input(`type` := "submit",
                        onClick := (e => joinLobby())
                )
            )
            )
        )
    ))
}