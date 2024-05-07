package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class PictionaryActor(out: ActorRef, manager: ActorRef) extends Actor{
    println("AAAAAA")
    manager ! PictionaryManager.newUser(self)
    def receive = {
        case s: String => manager ! PictionaryManager.newMsg(s)
        case PictionaryActor.sendPlayers(msg) => out ! msg
        case m => println("Unhandled message in Task7Actor: " + m)
    }

}

object PictionaryActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new PictionaryActor(out,manager))

    case class sendPlayers(players: String)
}