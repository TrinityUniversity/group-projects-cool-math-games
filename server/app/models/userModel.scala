package models

import scala.concurrent.ExecutionContext
import play.api.mvc.ControllerComponents
import play.api.db.slick.DatabaseConfigProvider

class UserModel @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

    private val model = new DatabaseModel(db)

    def getUserInfo(userid: Int): User {
        
    }

    def validateUser(username: String, password: String): User {
        val ret = model.validateLogin(username,password).map {

        }   
    }

    def createUser(username: String, password: String): User {

    }

    def updateScore(userid: Int, game: String, score: Int): Boolean {

    }
}


