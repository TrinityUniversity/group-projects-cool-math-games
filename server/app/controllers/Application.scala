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

@Singleton
class Application @Inject() (protected val dbConfigProvider: DatabaseConfigProvider , cc: ControllerComponents)(implicit ec: ExecutionContext) 
        extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {
  
  private val model = new DatabaseModel(db)


  def home = Action {
    Ok(views.html.homepage())
  }

  def login = Action {
    Ok(views.html.login())
  }
  
  def signup = Action {
    Ok(views.html.create_account())
  }

  def validateLogin = Action.async { implicit request => 
    model.validateUser(username, password).map {opt =>
       opt match {
        case Some(usr) =>
          Ok(views.html.homepage()).withSession("username" -> usr.username, "userid" -> usr.userId.toString())
        case None =>
          Ok(views.html.create_account())
      }
    }

  }

  def createUser(username: String, password: String) = Action.async {implicit request =>
    model.createUser(username, password).map {opt =>
      opt match {
        case Some(usr) =>
          Ok(views.html.homepage()).withSession("username" -> usr.username, "userid" -> usr.userId.toString())
        case None =>
          Ok(views.html.create_account())
      }
    }
  } 

  // if logged in will show personal profile: previously played games, highscores, etc
  def showProfile = Action.async {implicit request => 
    val creds = request.body.asFormUrlEncoded
    creds.map {args => 
      val username = args("username").head
      val userId = args("userId").head
      model.getUserInfo(userId).map {opt =>
        opt match {
          case Some(info) =>
            Ok(views.html.user_profile(info.scores, info.email)).withSession("username" -> username, "userid" -> userId)
           case None =>
            Ok(views.html.homepage())
         }
      }
    }.getOrElse(Future.successful(Ok((views.html.homepage()))))
  }
}
