package controllers

import javax.inject._
import play.api.mvc._
import org.checkerframework.checker.units.qual.A

@Singleton
class Wordle @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
    def load = Action { implicit request =>
        Ok(views.html.wordle())
    }
}
