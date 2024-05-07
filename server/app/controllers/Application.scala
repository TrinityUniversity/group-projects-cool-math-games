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
class Application @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(implicit ec: ExecutionContext) 
        extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new DatabaseModel(db)

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
      model.validateUser(username, password).map {opt =>
        if (opt)
          Redirect(routes.Application.home).withSession("username" -> username)
        else
          Redirect(routes.Application.login).flashing("error" -> "User validation failed.")
      }
    }.getOrElse(Future.successful(Redirect(routes.Application.signup).flashing("error" -> "User creation failed.")))

  }

  def createUser = Action {implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head

      model.createUser(username, password).map {opt =>
        if (opt)
          Redirect(routes.Application.home).withSession("username" -> username, "csrf-token" -> play.filters.csrf.CSRF.getToken.get.value)
        else
          Redirect(routes.Application.signup).flashing("error" -> "User creation failed.")
      }
    }.getOrElse(Future.successful(Redirect(routes.Application.signup).flashing("error" -> "User creation failed.")))
  } 

  // if logged in will show personal profile: previously played games, highscores, etc

  def showProfile = Action.async {implicit request => 
    val userIDOption = request.session.get("username")
    userIDOption.map {username => 
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        val username = args("username").head
        val userId = args("userId").head
        model.getScores(userId).map {opt =>
            Ok(views.html.user_profile(opt)).withSession("username" -> username)
        }
      }.getOrElse(Future.successful(Redirect(routes.Application.home)))
    }.getOrElse(Future.successful(Redirect(routes.Application.home)))
  }
}