import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class DatabaseModel(db: Database)(implicit ec: ExecutionContext) {
    def validateUser(username: String, password: String): Future[Tuple[Boolean,String,String]] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username && userRow.password === password).result)
        matches.map(userRows => userRows.nonEmpty)
    }

    def getUserInfo(userid: Int): Future[List[String]] = {
        ???
    }

    def createUser(username: String, password: String): Future[Boolean] = {
        ???
    }

    def getScore(userid: String, gameName: String): Future[Int] = {
        ???
    }

    def updateScore(userid: String, gameName: String): Future[Int] = {
        ???
    }

}