package mygatling.play

import io.gatling.core.Predef.{scenario, _}
import io.gatling.http.Predef._
import mygatling.IncreaseGraduallySimulator
import mygatling.TrafficDefinitions._

trait Play extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder =     scenario(name).exec(http("slash").get("/play")).pause(pauseTime)
  /*scenario(name)
    .exec(http("lpush01").get("/play/lpush01")).pause(pauseTime)
    .exec(http("lpush02").get("/play/lpush02")).pause(pauseTime)
    .exec(http("lpush03").get("/play/lpush03")).pause(pauseTime)
  */

  /*
  override val scenarioBuilders = Seq(
    scenario(name + "/lpush01").exec(http("lpush01").get("/play/lpush01")).pause(pauseTime)
    ,scenario(name + "/lpush02").exec(http("lpush02").get("/play/lpush02")).pause(pauseTime)
    ,scenario(name + "/lpush03").exec(http("lpush03").get("/play/lpush03")).pause(pauseTime)
  )
  */
}

class Play20 extends Play with TrafficMax20{this.run()}

class Play200 extends Play with TrafficMax200{this.run()}

class Play1000 extends Play with TrafficMax1000{this.run()}

class Play3000 extends Play with TrafficMax3000{this.run()}

trait PlayLpush01 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush01").get("/play/lpush01")).pause(pauseTime)


}

trait PlayLpush02 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush02").get("/play/lpush02")).pause(pauseTime)

}

trait PlayLpush03 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush03").get("/play/lpush03")).pause(pauseTime)


}
trait PlayLpush04 extends IncreaseGraduallySimulator{

  //def name = "nginx"
  override val scenarioBuilder = scenario(name)
    .exec(http("lpush04").get("/play/lpush04")).pause(pauseTime)


}

class PlayLpush01_200 extends PlayLpush01 with TrafficMax200{this.run()}

class PlayLpush02_200 extends PlayLpush02 with TrafficMax200{this.run()}

class PlayLpush03_200 extends PlayLpush03 with TrafficMax200{this.run()}

class PlayLpush04_200 extends PlayLpush04 with TrafficMax200{this.run()}
