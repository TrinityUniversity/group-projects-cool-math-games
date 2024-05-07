package PictionaryComponents

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.{h2,div, input, button, value, `type`, title, style, id, className, form, p}
import slinky.web.svg._
import org.scalajs.dom.Event
import slinky.web.SyntheticMouseEvent
import slinky.web.html.on
import org.scalajs.dom.SVGElementInstanceList
import org.scalajs.dom
import slinky.web.html.placeholder
import scala.scalajs.js
import play.api.libs.json.Json
import slinky.web.html
import shared._
import shared.DrawPath


@react class PictionaryGame extends Component {

    //Define the props and state for the component
    case class Props(gameID: Int, socket: dom.WebSocket, players: List[String])
    case class State(mouseDownPos:(Int, Int) , drawing: Boolean, currentTurn: Boolean , drawnLines: List[(String, String, String)],
                    currentLine : (String, String, List[(Int, Int)]), drawMode: String, colorInput: String,
                    color: String, strokeWidthValue: Int, currentPrompt: String, guess : String, weShowing: Boolean)
    def initialState: State = State((0,0), false, true , List(), ("3px","",List()) ,"line", "", "#000000", 3, "test", "", false)
    
    //Set up the websocket to receive messages
    props.socket.onmessage = (e: dom.MessageEvent) => {

        val rawStr = e.data.asInstanceOf[String]
        //println(rawStr)
        val paths = rawStr.filter(x => x != '(').split("\\)").toList
        print(paths)
        var newDrawnLines: List[(String,String,String)] = List()
            for (path <- paths){
                val Array(strokeWidth,color,d) = path.split(",")
                print((strokeWidth,color,d))
                setState(state => state.copy(drawnLines = state.drawnLines :+ (strokeWidth,color,d)))
                
            }
    }

    def MouseMove(e: SyntheticMouseEvent[svg.tag.type]) = {
         if(state.drawing && state.currentTurn){
            //This is causing the overlap bug in the svg canvas
            val svgElement = e.target.asInstanceOf[dom.svg.SVG]
            val rect = svgElement.getBoundingClientRect()
            val newX = (e.clientX - rect.left).toInt
            val newY = (e.clientY - rect.top).toInt
            //println(newX + ", " + newY)
            setState(state => state.copy(currentLine = (state.currentLine._1,state.currentLine._2,state.currentLine._3 :+ ((newX,newY)))))
        }
    }
    
    def MouseDown(e: SyntheticMouseEvent[svg.tag.type]) = {
        if(state.currentTurn){
            //This one is too
            val svgElement = e.target.asInstanceOf[dom.svg.SVG]
            val rect = svgElement.getBoundingClientRect()
            val startX = (e.clientX - rect.left).toInt
            val startY = (e.clientY - rect.top).toInt
            setState(state => state.copy(mouseDownPos = (startX,startY), drawing = true, currentLine = (state.strokeWidthValue.toString(),state.color,List())))
        }
    }

    def MouseUp(e: SyntheticMouseEvent[svg.tag.type]) = {
        var pathStr: String = "M " + state.mouseDownPos._1 + " " + state.mouseDownPos._2
        val color = state.currentLine._2
        for((x,y) <- state.currentLine._3){
            pathStr = pathStr + " L " + x + " " + y
        }
        props.socket.send(Json.stringify(Json.toJson(DrawPath(state.currentLine._1,color,pathStr))))
        setState(state => state.copy(drawnLines = state.drawnLines :+ (state.currentLine._1,color,pathStr),
                                     drawing = false,mouseDownPos = (0,0), currentLine = ("3","",List())))

    }

    def polyLineString(drawnPoints: List[(Double,Double)]): String = {
        var str = ""
        for ((x:Double, y:Double) <- drawnPoints) {
            str :+ x.toString + " " + y.toString + ","
        }
        str.dropRight(1)
        
    }

    def checkGuess() = {
        if(state.guess == state.currentPrompt){
            println("Good Job!")
        } else {
            println("Fuck you!")
        }
    }

    def render(): ReactElement = div (
    div(id := "container")(
        div(id := "title")("DRAWING AND GUESSING GAME WITH FRIENDS"),
        div(id := "room-number")("Room: #" + props.gameID.toString()),
        div(id := "prompt")("Prompt: Turkey"),
        svg(mods =  width := "600", height := "400", 
            name := "drawing-area",
            onMouseDown := (e => MouseDown(e.asInstanceOf[SyntheticMouseEvent[svg.tag.type]])),
            onMouseUp := (e => MouseUp(e.asInstanceOf[SyntheticMouseEvent[svg.tag.type]])),
            onMouseMove := (e => MouseMove(e.asInstanceOf[SyntheticMouseEvent[svg.tag.type]])),
            state.drawnLines.zipWithIndex.map(lineVal => path(fill := "none",stroke := lineVal._1._2, strokeWidth := lineVal._1._1, d := lineVal._1._3, key := lineVal._2.toString()))
        ),
        div(id := "non-drawing-area-container")(
        div(id := "color-picker-container")(
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "red").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "red")))),
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "green").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "green")))),
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "blue").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "blue")))),
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "yellow").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "yellow")))),
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "orange").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "orange")))),
            div(className := "color-option", style := js.Dictionary("backgroundColor" -> "black").asInstanceOf[scala.scalajs.js.Object], onClick := (e => setState(state.copy(color = "black")))),
        ),
        div(id := "prompt-container" + (if(state.weShowing) ".show" else ""))(
            p("Enter Prompt"),
            div(id := "prompt-form")(
            input(`type` := "text", id := "prompt-input", placeholder := "Enter a prompt"),
            button(`type` := "submit", id := "submit-prompt-button",
                onClick := (e => ()))("Submit Prompt")
            )
        ),
        div(id := "win-container")(
            p("Congrats!! You won XXX Points!")
        ),
        div(id := "game-win-container")(
            p("Greg Won with XXX Points!"),
            button("Quit Game")
        ),
        div(id := "guess-form")(
            input(`type` := "text", id := "guess-input", placeholder := "Enter your guess",
             onChange := (e => setState(state.copy(guess = e.target.value)))),
            button(`type` := "submit", id := "submit-button", onClick := (e => checkGuess()))("Submit Guess")
        ),
        div(id := "guess-display"),
        div(id := "players-section")(
            props.players.zipWithIndex.map(player => div(className := "player-info", html.key := player._2.toString())(player._1 + " - Score: 0")),
            // div(className := "player-info")("Player 1 - Score: 0"),
            // div(className := "player-info")("Player 2 - Score: 0")
        )
        )
    )
    )

}


