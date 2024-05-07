package PictionaryComponents

import org.scalajs.dom
import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.div
import PictionaryComponents._

@react class PictionaryMainComponent extends Component {
    
    val csrfToken = dom.document.getElementById("csrfToken").asInstanceOf[org.scalajs.dom.raw.HTMLInputElement].value

    type Props = Unit
    case class State(inGame: Boolean, gameID: Int)

    def initialState: State = State(false, 0)

    def render(): ReactElement = {
        PictionaryJoinComponent(PictionaryJoinComponent.Props(() => setState(state.copy(inGame = true)),csrfToken))
    }
}