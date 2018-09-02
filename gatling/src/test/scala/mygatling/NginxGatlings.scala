package mygatling.nginx

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import mygatling.IncreaseGraduallySimulator
import mygatling.TrafficDefinitions._

trait Nginx extends IncreaseGraduallySimulator{

  //def name = "nginx"
  val scenarioBuilder = scenario(name)
    .exec(http("slash")
      //.get("/"))
      .get("/"))
    .pause(pauseTime)

}

class Nginx20 extends Nginx with TrafficMax20{this.run()}

class Nginx200 extends Nginx with TrafficMax200{this.run()}

class Nginx1000 extends Nginx with TrafficMax1000{this.run()}

class Nginx3000 extends Nginx with TrafficMax3000{this.run()}
