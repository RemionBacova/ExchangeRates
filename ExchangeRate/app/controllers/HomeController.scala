package controllers

import javax.inject._
import models.Currencies
import play.api.mvc._


@Singleton
class HomeController @Inject()(cc: ControllerComponents,currencies: Currencies ) extends AbstractController(cc) {

  def index = Action {
    Ok("This is Empty")
  }
}
