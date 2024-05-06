package actors

import akka.actor.Actor
import akka.actor.ActorRef


class PictionaryManager extends Actor {
    private var users = List.empty[ActorRef]
    var drawings = List.empty[(String,String,String)]

    import PictionaryManager._
        def receive = {
            case newUser(user) => {
                users ::= user
                println("New User Joined")
                for(user <- users) user ! PictionaryActor.sendPath(drawings.mkString)
            }
            case newPath(pathStr) => {
                //println("User Drew New Line")
                val newPath = pathStr.filterNot(c => c == '(' || c == ')').split(",")
                //printf(s"NewPath ${newPath.mkString}")
                drawings ::= (newPath(0),newPath(1),newPath(2))
                for(user <- users) user ! PictionaryActor.sendPath(drawings.mkString)
            }
        }


}

object PictionaryManager {
    case class newUser(user: ActorRef)
    case class newPath(pathStr: String)
}