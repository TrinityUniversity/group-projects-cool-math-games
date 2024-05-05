package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def home = Action {
    Ok(views.html.pictionary_game())
  }

  def login = Action {
    Ok(views.html.login())
  }



  def validateLogin = ???
  def createUser = ???

  // Will show all games 
  def showGames = ???

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = ???
}
