package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import com.redis._

import scala.concurrent.{ExecutionContext, Future}
/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class RedisPusherController @Inject()(cc: ControllerComponents)(implicit executionContext:ExecutionContext) extends AbstractController(cc) {

  private val redisClientPool = new RedisClientPool(host = "127.0.0.1" , port = 6379 , maxConnections = 10000)

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def lpush01() = Action { implicit request: Request[AnyContent] =>

    redisClientPool.withClient{client=>
      client.rpush("list01",System.currentTimeMillis())
    }
    Ok(views.html.index())
  }

  def lpush02() = Action { implicit request: Request[AnyContent] =>

    Future{
      redisClientPool.withClient{client=>
        client.rpush("list01",System.currentTimeMillis())
      }
    }
    Ok(views.html.index())
  }
  def lpush03() = Action.async { implicit request: Request[AnyContent] =>

    Future{
      redisClientPool.withClient{client=>
        client.rpush("list01",System.currentTimeMillis())
      }
      Ok(views.html.index())
    }
  }
}
