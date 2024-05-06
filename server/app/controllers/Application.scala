package controllers

import javax.inject._

import shared.SharedMessages
import play.api.i18n._
import play.api.mvc._
import models._
import play.api.mvc.ControllerComponents
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def home = Action {
    Ok(views.html.pictionary_game())
  }

  def login = TODO

  def validateLogin = Action.async {  

  }

  def createUser = TODO

  // Will show all games 
  def showGames = TODO

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = TODO
}
