package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt   

class DatabaseModel(db: Database)(implicit ec: ExecutionContext) {

    def validateUser(username: String, password: String): Future[Boolean] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.map(userRows => userRows.filter(userRow => BCrypt.checkpw(password, userRow.password)).nonEmpty)
    }

    def createUser(username: String, password: String): Future[Boolean] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.flatMap { userRows =>
            if(!userRows.nonEmpty) {
            db.run(Users += UsersRow(username, BCrypt.hashpw(password, BCrypt.gensalt())))
                .map(addCount => addCount > 0)
            } else Future.successful(false)
        }
    }

    def getScores(userid: String): Future[Seq[(String,String)]] = {
        db.run(
        (for {
            scr <- Scores if scr.username === userid
        } yield {
            (scr.game, scr.score)
        }).result
    )
    }

    def updateScore(userid: String, gameName: String): Future[Int] = ???
}