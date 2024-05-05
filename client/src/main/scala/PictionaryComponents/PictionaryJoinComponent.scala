package PictionaryComponents

import org.scalajs.dom
import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.core.SyntheticEvent
import playscala.FetchJson
//import scala.concurrent.ExecutionContext.Implicits.global


@react class PictionaryJoinComponent extends Component {
    case class Props(doLogin : () => Unit)
    case class State( joinName: String, createName: String, lobbyCode: String)
    def initialState: State = State("","","")

    implicit val ec = scala.concurrent.ExecutionContext.global

    val creatLobbyRoute = dom.document.getElementById("createLobby").asInstanceOf[org.scalajs.dom.raw.HTMLInputElement].value
    println(creatLobbyRoute)


    /* 
    THINGS TO ADD HERE:
        -ADD BUTTON FUNCTIONALITY
        -CREATE LOBBY
        -JOIN AN EXISTING LOBBY
    */

    def createLobby() = {
        // FetchJson.fetchGet[String](creatLobbyRoute + "?name=" + state.createName, 
        //     (data: String) => {
        //         println("Lobby Created")
        //         setState(state.copy(lobbyCode = data))
        //     },
        //     (e) => {
        //         println("Error creating lobby")
        //     }
        // )
    }

    def joinLobby() = ???

    def render(): ReactElement = div (
          div(id := "container")(
            div(id := "lobby-container")(
            h1("Welcome to Pictionary!"),
            div(id := "join-container")(
                h2("Create a New Lobby"),
                form(id := "join-form")(
                    input(`type` := "text", id := "createName", placeholder := "Enter Your Name",
                        onChange := (e => setState(state.copy(createName = e.target.value)))),
                    input(`type` := "submit", 
                        onSubmit := (e => createLobby()))
                ),
                h2("Join an Existing Lobby"),
                form(id := "join-form")(
                    input(`type` := "text", id := "joinName", placeholder := "Enter Your Name",
                        onChange := (e => setState(state.copy(joinName = e.target.value)))),
                    input(`type` := "text", id := "lobbyCode", placeholder := "Enter Lobby Code",
                        onChange := (e => setState(state.copy(lobbyCode = e.target.value)))),
                    input(`type` := "submit")
                )
            )
            )
        )
    )
}