package modules


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import javax.inject.{Inject, Named, Provider, Singleton}
import play.api.{Configuration, Environment}

import scala.concurrent.{ExecutionContext, Future}



class TryModules @Inject()(environment: Environment,configuration:Configuration) extends AbstractModule{


  override def configure(): Unit = {

    println("call configure()")

    //Namedラベルごとにクラスを別途定義
    this.bind(classOf[City]).annotatedWith(Names.named("tokyo")).to(classOf[City.Tokyo])
    this.bind(classOf[City]).annotatedWith(Names.named("osaka")).to(classOf[City.Osaka])
    this.bind(classOf[City]).annotatedWith(Names.named("kyoto")).to(classOf[City.Kyoto]).in(classOf[Singleton])


    //Nameラベルごとに同じクラスの別インスタンスを生成するように設定
    configuration.get[Configuration]("shogun").keys.foreach{key=>
      //Providerを介して定義
      //Providerの本来の目的は循環参照回避らしい
      this.bind(classOf[Shogun]).annotatedWith(Names.named(key)).toProvider(new ShogunProvider(key))
    }

    configuration.get[Configuration]("shogun2").keys.foreach{key=>
      //Providerを介さずに定義
      this.bind(classOf[Shogun]).annotatedWith(Names.named(key)).toInstance(new ShogunImpl2(key))
    }



  }
}




trait City{
  println(s"${this.getClass.getName}#ctor is called hashcode:${this.hashCode()}")
  def name():Future[String]
}


object City{
  @Singleton
  class Tokyo @Inject()(configuration: Configuration)(implicit executionContext:ExecutionContext) extends City{


    override def name(): Future[String] = {

      //あえてFuture.successfulはしてない
      Future{
        s"${configuration.get[String]("city.tokyo")}"
      }
    }
  }

  class Osaka @Inject()(configuration: Configuration)(implicit executionContext:ExecutionContext) extends City{

    override def name(): Future[String] = {

      //あえてFuture.successfulはしてない
      Future{
        s"${configuration.get[String]("city.osaka")}"
      }
    }
  }

  class Kyoto @Inject()(configuration: Configuration)(implicit executionContext:ExecutionContext) extends City{

    override def name(): Future[String] = {

      //あえてFuture.successfulはしてない
      Future{
        s"${configuration.get[String]("city.kyoto")}"
      }
    }
  }

}

class UseCity @Inject()(@Named("tokyo") val cityTokyo:City,@Named("osaka") val cityOsaka:City,@Named("kyoto") val cityKyoto:City){

  def tokyo = cityTokyo.name()
  def osaka = cityOsaka.name()
  def kyoto = cityKyoto.name()
}



class ShogunProvider (key:String) extends Provider[Shogun]{

  println(s"${this.getClass.getName}#ctor is called hashcode:${this.hashCode()}")
  @Inject() private implicit var executionContext: ExecutionContext = null
  @Inject() private implicit var configuration: Configuration = null


  /*
 　以下をconstructorで呼び出すのはご法度かもね
  this.shogun.name().onComplete{
    case _=>
      println("shoguin instance init on constructor")
  }
  */

  private lazy val  shogun = {
    new ShogunImpl(key,configuration)(executionContext)
  }


  override def get(): Shogun = {
    shogun
  }

}

trait Shogun{
  println(s"${this.getClass.getName}#ctor is called hashcode:${this.hashCode()}")

  def name():Future[String]
}


class ShogunImpl (key:String,configuration: Configuration)(implicit executionContext: ExecutionContext) extends Shogun{


  override def name() ={

    //あえてFuture.successfulはしてない
    Future{
      s"${configuration.get[String](s"shogun.${key}")}"
    }

  }


}




class ShogunImpl2 (key:String) extends Shogun{

  @Inject() private[this] var configuration: Configuration = _
  @Inject() private[this] implicit var executionContext: ExecutionContext = _

  override def name() ={

    //あえてFuture.successfulはしてない
    Future{
      s"${configuration.get[String](s"shogun2.${key}")}"
    }

  }


}

class UseShogun @Inject()(@Named("ieyasu") val ieyasu:Shogun,@Named("yoritomo") val yoritomo:Shogun ,@Named("hidetada") val hidetada:Shogun,@Named("yoriie") val yoriie:Shogun){


}