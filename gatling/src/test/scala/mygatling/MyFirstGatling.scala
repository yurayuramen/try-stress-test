package mygatling
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MyFirstGatling extends Simulation {
  val baseURL = "http://127.0.0.1:8080"
  val users = 10
  val times = 5 seconds
  val pauseTime = 100 milliseconds

  val userAgent = s"baseURL:${baseURL},user:${users},times${times},avg:${users / times},pause:${pauseTime}"




  val httpConf = http
    .baseURL(baseURL) // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("ja,en-US;q=0.7,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader(s"Gatling/${userAgent}")
    .disableFollowRedirect

  val scn = scenario("Sample")
    .exec(http("slash")
      .get("/"))
    .pause(pauseTime)

  setUp(scn.inject(rampUsers(users) over(times)) .protocols(httpConf))

}
