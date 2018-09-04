package controllers

import java.nio.charset.Charset

import akka.actor.ActorSystem
import com.redis._
import com.twitter.finagle.Redis
import com.twitter.io.Buf
import javax.inject._
import play.api.mvc._

import scala.concurrent.Promise
//import com.twitter.finagle.redis.util.StringToChannelBuffer

import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class RedisGetController @Inject()(cc: ControllerComponents)(/*implicit executionContext:ExecutionContext*/ actorSystem:ActorSystem) extends AbstractController(cc) {

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
  def lindex01() = Action { implicit request: Request[AnyContent] =>

    redisClientPool.withClient{client=>
      Ok(client.lindex("list01",0).getOrElse("404"))
    }
  }

  def lindex03() = Action.async { implicit request: Request[AnyContent] =>

    Future{
      redisClientPool.withClient{client=>
        Ok(client.lindex("list02",0).getOrElse("404"))
      }
    }
  }

  val charset = Charset.forName("utf-8")
  def lindex04() = Action.async { implicit request: Request[AnyContent] =>


    //変換 promiseを使うしかないらしい（orz...）
    //https://twitter.github.io/util/guide/util-cookbook/futures.html
    val key = Buf.Utf8("list04")
    val twFuture = redisFinagleClient.lIndex(key,0).map{value=>
      Ok(value.map{value=> Buf.decodeString(value,charset)}.getOrElse(""))
    }

    val promise = Promise[Result]()
    twFuture.onSuccess{result=>
      promise.success(result)

    }

    promise.future
  }


}
