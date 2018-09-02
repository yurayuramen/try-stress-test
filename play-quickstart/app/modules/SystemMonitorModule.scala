package modules

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import javax.inject.Inject
import play.api.Configuration
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future
import mygatling.monitor.SystemMonitor
import play.api.Logger

class SystemMonitorModule extends AbstractModule{

  override def configure(): Unit = {

    bind(classOf[SystemMonitorStartup]).asEagerSingleton()
  }
}

class SystemMonitorStartup @Inject()(actorSystem: ActorSystem, configuration: Configuration, applicationLifecycle: ApplicationLifecycle){

  val logger = Logger(this.getClass)
  import scala.concurrent.duration._
  implicit val ec = actorSystem.dispatcher


  val systemMonitor = new SystemMonitor(configuration.underlying)
  logger.warn("run:schedule")
  val schedule = actorSystem.scheduler.schedule(10 seconds,10 seconds){
      systemMonitor.monitor()
  }

  applicationLifecycle.addStopHook{()=>

    Future{
      schedule.cancel()

    }
  }

}
