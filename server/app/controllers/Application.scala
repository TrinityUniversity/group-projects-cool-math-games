package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
//import models.DatabaseModel

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def home = Action {
    Ok(views.html.homepage())
  }

  def login = Action {
    Ok(views.html.login())
  }
  
  def signup = Action {
    Ok(views.html.create_account())
  }

  def validateLogin = TODO
  //   Action.async { implicit response =>
  //   val postVals = response.body.asFormUrlEncoded
  //   postVals.map { args =>
  //     val username = args("username").head
  //     val password = args("password").head
  //     model.validateUser(username, password).map { userCreated =>
  //       if(userCreated) {
  //         Redirect(routes.Application.home)
  //           .withSession("username" -> username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
  //       } else {
  //         Redirect(routes.Application.login).flashing("error" -> "Invalid username/password combination.")
  //       }
  //     }
  //   }
  // }

  def createUser = TODO
    // Action { implicit request =>
    // val postVals = request.body.asFormUrlEncoded

  //}

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = TODO

  // def sendTo(Int: gameID) = {
  //   if(gameID == 0) {
  //     Ok(views.html.pictionary_join())
  //   } else if (gmaeID == 1) {
  //     Ok(views.html.wordle())
  //   }
  // }
}
