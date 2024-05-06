package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import models.DatabaseModel

@Singleton
class Application @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  val model = new DatabaseModel(db)

  def home = Action {
    Ok(views.html.homepage())
  }

  def login = Action {
    Ok(views.html.login())
  }
  
  def signup = Action {
    Ok(views.html.create_account())
  }

  def validateLogin = Action.async { implicit response =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      model.validateUser(username, password).map { userCreated =>
        if(userCreated) {
          Redirect(routes.Application.home)
        }

      }
    }
  }
  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    
  }

  // Will show all games (gmaes will be stored as an ID, a name, and a description in the database)
  def getGames = TODO

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = TODO

  def sendTo(Int: gameID) = {
    if(gameID == 0) {
      Ok(views.html.pictionary_join())
    } else if (gmaeID == 1) {
      Ok(views.html.wordle())
    }
  }
}
