package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt   

class DatabaseModel(db: Database)(implicit ec: ExecutionContext) {

    def validateUser(username: String, password: String): Future[Option[User]] = ???

    def getUserInfo(userid: String): Future[Option[UserExtras]] = ???

    def createUser(username: String, password: String): Future[Option[User]] = ???

    def getScores(userid: String): Future[Score] = ???

    def updateScore(userid: String, gameName: String): Future[Int] = ???

}