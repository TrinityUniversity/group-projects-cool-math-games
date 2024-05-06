package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef

class PictionaryActor(out: ActorRef, manager: ActorRef) extends Actor{
    manager ! PictionaryManager.newUser(self)
    def receive = {
        case s: String => manager ! PictionaryManager.newPath(s)
        case PictionaryActor.sendPath(msg) => out ! msg
        case m => println("Unhandled message in Task7Actor: " + m)
    }

}

object PictionaryActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new PictionaryActor(out,manager))

    case class sendPath(pathStr: String)
}