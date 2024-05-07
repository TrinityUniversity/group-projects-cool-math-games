package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
//import models.DatabaseModel._

@Singleton
class Application @Inject(){
  //val model = new DatabaseModel(db)

  def home = ??? //{
    //Ok(views.html.homepage())
  //}

  def login = ??? //{
    //Ok(views.html.login())
  //}
  
  def signup = ??? //{
    //Ok(views.html.create_account())
  //}

def validateLogin = ??? //Action.async { implicit response =>
//     val postVals = request.body.asFormUrlEncoded
//     postVals.map { args =>
//       val username = args("username").head
//       val password = args("password").head
//       model.validateUser(username, password).map { userCreated =>
//         if(userCreated) {
//           Redirect(routes.Application.home)
//         }

//       }
//     }
//   }
 def createUser = ??? //Action { implicit request =>
//     val postVals = request.body.asFormUrlEncoded
    
//   }

  // Will show all games (gmaes will be stored as an ID, a name, and a description in the database)
  def getGames = ???

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = ???

  def sendTo = ???//(Int: gameID) = {
    // if(gameID == 0) {
    //   Ok(views.html.pictionary_join())
    // } else if (gmaeID == 1) {
    //   Ok(views.html.wordle())
    // }
  //}
}