package mygatling

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

object TrafficDefinitions {

  trait TrafficDefined{

    def minimum:Double = 1
    def maximum:Double
    def timeOfIncrease:FiniteDuration = 30 seconds
    def timeOfMax = 15 seconds

  }

  trait TrafficMax3000 extends TrafficDefined{
    def maximum:Double = 3000
  }

  trait TrafficMax1000 extends TrafficDefined{
    def maximum:Double = 1000
  }

  trait TrafficMax200 extends TrafficDefined{
    def maximum:Double = 200
  }

  trait TrafficMax20 extends TrafficDefined{
    def maximum:Double = 20
  }

}
