package mygatling.finagle

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import mygatling.IncreaseGraduallySimulator
import mygatling.TrafficDefinitions._

trait Finagle extends IncreaseGraduallySimulator{

  //def name = "nginx"
  val scenarioBuilder = scenario(name)
    .exec(http("slash")
      //.get("/"))
      .get("/finagle"))
    .pause(pauseTime)

}


class Finagle200 extends Finagle with TrafficMax200{this.run()}

