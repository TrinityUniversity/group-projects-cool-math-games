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
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import play.api.db.slick.HasDatabaseConfigProvider
import views.html.defaultpages.error
//protected val dbConfigProvider: DatabaseConfigProvider ,
//(implicit ec: ExecutionContext)
//with HasDatabaseConfigProvider[JdbcProfile] 

@Singleton
class Application @Inject() ( cc: ControllerComponents) 
    extends AbstractController(cc) {
  
  //private val model = new DatabaseModel(db)


  def home = Action { implicit request =>
    Ok(views.html.homepage())
  }

  def login = Action { implicit request =>
    Ok(views.html.login())
  }
  
  def signup = Action { implicit request =>
    Ok(views.html.create_account())
  }

  def validateLogin = Action { implicit request => 
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head 
      // model.validateUser(username, password).map {opt =>
      //   opt match {
      //     case Some(usr) =>
      //       Redirect(routes.Application.home).withSession("username" -> usr.username, "userid" -> usr.userId.toString())
      //     case None =>
      //       Redirect(routes.Application.login).flashing("error" -> "User validation failed.")
      //   }
      // }
      Ok(views.html.login())
    }.getOrElse(Redirect(routes.Application.signup).flashing("error" -> "User creation failed."))
  }

  def createUser = Action {implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      // model.createUser(username, password).map {opt =>
      //   opt match {
      //     case Some(usr) =>
      //       Redirect(routes.Application.home).withSession("username" -> usr.username, "userid" -> usr.userId.toString(), "csrf-token" -> play.filters.csrf.CSRF.getToken.get.value)
      //     case None =>
      //       Redirect(routes.Application.signup).flashing("error" -> "User creation failed.")
      //   }
      // }
      Ok(views.html.create_account())
    }.getOrElse(Redirect(routes.Application.signup).flashing("error" -> "User creation failed."))
  } 

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = Action {implicit request => 
    val userIDOption = request.session.get("userid")
    userIDOption.map {userID => 
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val username = args("username").head
        val userId = args("userId").head
        // model.getUserInfo(userId).map {opt =>
        //   opt match {
        //     case Some(info) =>
        //       Ok(views.html.user_profile(info.scores, info.email)).withSession("username" -> username, "userid" -> userId)
        //     case None =>
        //       Redirect(routes.Application.home)
        //   }
        // }
        Ok(views.html.homepage())
      }.getOrElse(Redirect(routes.Application.home))
    }.getOrElse(Redirect(routes.Application.home))
  }
}