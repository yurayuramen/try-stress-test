package mygatling.akka

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import mygatling.IncreaseGraduallySimulator
import mygatling.TrafficDefinitions._

trait Akka extends IncreaseGraduallySimulator{

  //def name = "nginx"
  val scenarioBuilder = scenario(name)
    .exec(http("/akka")
      .get("/akka"))
    .pause(pauseTime)

}


class Akka200 extends Akka with TrafficMax200{this.run()}


trait AkkaLpush01 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush01").get("/akka/lpush01")).pause(pauseTime)


}

trait AkkaLpush02 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush02").get("/akka/lpush02")).pause(pauseTime)

}

trait AkkaLpush03 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush03").get("/akka/lpush03")).pause(pauseTime)


}
trait AkkaLpush04 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush04").get("/akka/lpush04")).pause(pauseTime)


}

class AkkaLpush01_200 extends AkkaLpush01 with TrafficMax200{this.run()}

class AkkaLpush02_200 extends AkkaLpush02 with TrafficMax200{this.run()}

class AkkaLpush03_200 extends AkkaLpush03 with TrafficMax200{this.run()}

class AkkaLpush04_200 extends AkkaLpush04 with TrafficMax200{this.run()}
