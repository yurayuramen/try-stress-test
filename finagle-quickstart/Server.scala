//#imports
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}
//#imports

object Server extends App {
//#service
  val Port = ":9002"


  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] =
      Future.value {
        val response = http.Response(req.version, http.Status.Ok)
        response.write("hello")
        response
      }
  }
//#service
//#builder
  val server = Http.serve(Port, service)
  println(s"run port:${Port}")
  Await.ready(server)
//#builder
}
