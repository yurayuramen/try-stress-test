package controllers

import java.util.UUID

import javax.inject._
import play.api._
import play.api.mvc._

import scala.util.Random

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def hello() = Action{
    Ok("ok")
  }

  def random() = Action{
    val random = Random.nextInt(30)

    Ok(s"$random")
  }

  def uuid() = Action{
    val uuid = UUID.randomUUID().toString

    Ok(s"$uuid")
  }

}
