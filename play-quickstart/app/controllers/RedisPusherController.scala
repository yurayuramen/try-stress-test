package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api._
import play.api.mvc._
import com.redis._
import com.twitter.finagle.Redis
import com.twitter.io.Buf
//import com.twitter.finagle.redis.util.StringToChannelBuffer

import scala.concurrent.{ExecutionContext, Future}
/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class RedisPusherController @Inject()(cc: ControllerComponents)(/*implicit executionContext:ExecutionContext*/ actorSystem:ActorSystem) extends AbstractController(cc) {

  implicit val executionContext = actorSystem.dispatchers.lookup("redis-dispatcher")

  private val redisClientPool = new RedisClientPool(host = "127.0.0.1" , port = 6379 , maxConnections = 10000)

  private val redisFinagleClient = Redis.newRichClient("127.0.0.1:6379")

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
        client.rpush("list02",System.currentTimeMillis())
      }
    }
    Ok(views.html.index())
  }
  def lpush03() = Action.async { implicit request: Request[AnyContent] =>

    Future{
      redisClientPool.withClient{client=>
        client.rpush("list03",System.currentTimeMillis())
      }
      Ok(views.html.index())
    }
  }

  def lpush04() = Action { implicit request: Request[AnyContent] =>


    val key = Buf.Utf8("list04")
    val value = Buf.Utf8(s"${System.currentTimeMillis()}")

    redisFinagleClient.lPush(key,List(value))

    Ok(views.html.index())
  }


}
