package mygatling
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MyFirstGatling extends Simulation {
  //val baseURL = "http://127.0.0.1:8080"
  val baseURL = "http://172.31.0.209:8080"
  //val users = 40000 //redis all ok
  val users = 50000
  val times = 20 seconds
  val pauseTime = 100 milliseconds
  //val uri = "/finagle"
  //val uri = "/play/code01"
  //val uri = "/play/code02"
  //val uri = "/play/lpush01"
  //val uri = "/play/lpush02"
  val uri = "/play/lpush03"
  //val uri = "/play/hello"
  //val uri = "/play"

  val userAgent = s"baseURL:${baseURL},user:${users},times${times},avg:${users / times},pause:${pauseTime}"




  val httpConf = http
    .baseURL(baseURL) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("ja,en-US;q=0.7,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader(s"Gatling/${userAgent}")
    .headers(Map("Host"->"localhost"))
    .disableFollowRedirect

  val scn = scenario("Sample")
    .exec(http("slash")
      //.get("/"))
      .get(uri))
    .pause(pauseTime)

  setUp(scn.inject(rampUsers(users) over(times)) .protocols(httpConf))

}
