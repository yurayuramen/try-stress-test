package modules

import org.scalatest.{MustMatchers, WordSpec}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.Future
import scala.util.Success

object TryModulesTest{


}

class TryModulesTest extends PlaySpec with MustMatchers with GuiceOneAppPerSuite{

  override lazy val app = {
    GuiceApplicationBuilder().build()
  }




  implicit class FutureExt(underlying:Future[_]){
    import scala.concurrent.ExecutionContext.Implicits.global
    def printIt(): Unit ={

      underlying.onComplete{
        case Success(msg)=>
          println(msg)
        case _=>
      }

    }

  }



  "Named属性による出し分けをクラス分離で対処している" should{
    "テスト" in{
      {
        val useCity = app.injector.instanceOf(classOf[UseCity])

        useCity.tokyo.printIt()
        useCity.osaka.printIt()
        useCity.kyoto.printIt()
      }
      {
        val useCity = app.injector.instanceOf(classOf[UseCity])

        useCity.tokyo.printIt()
        useCity.osaka.printIt()
        useCity.kyoto.printIt()
      }
      Thread.sleep(100)
    }
  }

  "Named属性による出し分けをインスタンス分離で対処している" should{
    "テスト" in{
      {
        val useShogun = app.injector.instanceOf(classOf[UseShogun])
        useShogun.ieyasu.name().printIt()
        useShogun.yoritomo.name().printIt()
        useShogun.hidetada.name().printIt()
        useShogun.yoriie.name().printIt()
      }
      {
        val useShogun = app.injector.instanceOf(classOf[UseShogun])
        useShogun.ieyasu.name().printIt()
        useShogun.yoritomo.name().printIt()
        useShogun.hidetada.name().printIt()
        useShogun.yoriie.name().printIt()
      }
      {
        val useShogun = app.injector.instanceOf(classOf[UseShogun])
        useShogun.ieyasu.name().printIt()
        useShogun.yoritomo.name().printIt()
        useShogun.hidetada.name().printIt()
        useShogun.yoriie.name().printIt()
      }
      Thread.sleep(100)

    }

  }
}
