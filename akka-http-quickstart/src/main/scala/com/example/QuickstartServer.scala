package com.example

//#quick-start-server
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import mygatling.monitor.SystemMonitor
import scala.concurrent.duration._

//#main-class
object QuickstartServer extends App with UserRoutes {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec = system.dispatcher
  //#server-bootstrapping

  val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistryActor")

  //#main-class
  // from the UserRoutes trait
  lazy val routes: Route = userRoutes
  //#main-class

  //#http-server
  Http().bindAndHandle(routes, "localhost", 9001)

  println(s"Server online at http://localhost:9001/")

  val systemMonitor = new SystemMonitor(system.settings.config)
  //logger.warn("run:schedule")
  val schedule = system.scheduler.schedule(10 seconds,10 seconds){
    systemMonitor.monitor()
  }


  Await.result(system.whenTerminated, Duration.Inf)
  schedule.cancel()
  //#http-server
  //#main-class
}
//#main-class
//#quick-start-server
