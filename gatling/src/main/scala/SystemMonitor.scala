package mygatling.monitor

import java.lang.management.ManagementFactory
import java.net.{HttpURLConnection, URL}
import java.nio.charset.Charset
import java.nio.file.{Files, Path}
import java.util.Date
import java.util.regex.Pattern

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory
import org.slf4j.Logger

import scala.io.Source

object SystemMonitorMain extends App{



    val mon = new SystemMonitor(ConfigFactory.load())

    (0 until 10).foreach{_=>
      mon.monitor()
      Thread.sleep(5000)

    }

}


object SystemMonitor extends App{

  implicit class ConfigExt(underlying:Config){

    def optionalString(path:String)={
      if(!underlying.hasPath(path))
        None
      else
        Some apply underlying.getString(path)
    }

    def getString(path:String,default:String)=optionalString(path).getOrElse(default)

  }


}

class SystemMonitor(config:Config) {
  import SystemMonitor._

  import scala.collection.JavaConverters._

  private val logNginx:org.slf4j.Logger = LoggerFactory.getLogger(s"${this.getClass.getName}.NGINX")
  private val logHeap:org.slf4j.Logger = LoggerFactory.getLogger(s"${this.getClass.getName}.HEAP")
  private val logThread:org.slf4j.Logger = LoggerFactory.getLogger(s"${this.getClass.getName}.THREAD")

  //implicit val actorSystem = ActorSystem("monitor",config)

  val nginxUrl = config.getString("mygatling.systemMonitor.nginx.url","http://127.0.0.1:8080/nginx_status")

  val debug:Boolean = true
  val charset = Charset.forName("utf-8")

  def writeFile(file:Path, messages:String*): Unit =
      Files.write(file,messages.asJava,charset)


  def debug(title:String,messages:Iterable[_]): Unit =
    if(debug)
    messages.foreach{msg=>
      println(s"${"%tY/%<tm/%<td-%<tH:%<tM:%<tS" format new Date()}[$title]$msg")
    }

  def logWrite(logger:org.slf4j.Logger,messages:Iterable[String]): Unit ={
    messages.foreach{msg=>
      logger.info(msg)
    }
  }


  def nginx(): Unit ={

    val connection = new URL(nginxUrl).openConnection().asInstanceOf[HttpURLConnection]

    connection.setRequestMethod("GET")
    val source = Source.fromInputStream(connection.getInputStream,charset.name())

    val lines = source.getLines()

    val activeConnections = lines.next().split("\\s")(2)
    lines.next()
    val array2 = lines.next().split("\\s").filterNot(_.trim == "")

    val accepts = array2(0)
    val handled = array2(1)
    val requests = array2(2)

    val array3 = lines.next().split("\\s").filterNot(_.trim == "")

    val reading = array3(1)
    val writing = array3(3)
    val waiting = array3(5)

    val line = s"active:${activeConnections}\taccepts:${accepts}\thandled:${handled}\trequests:${requests}\treading:${reading}\twriting:${writing}\twaiting:${waiting}"

    //debug("nginx",Seq(line))
    logWrite(logNginx,Seq(line))
    connection.disconnect()



  }

  def os(): Unit ={

    //val osMxBean = ManagementFactory.
    //osMxBean.
  }


  private[this] val pattern = Pattern.compile("^(.+)-[0-9]+$")
  def jvm(): Unit ={

    val threadMxBean = ManagementFactory.getThreadMXBean
    val memoryMxBeans = ManagementFactory.getMemoryManagerMXBeans
    val memoryPoolMxBeans = ManagementFactory.getMemoryPoolMXBeans



    val listThreadInfo = threadMxBean.dumpAllThreads(false,false)


    val listThreadParsed = listThreadInfo.map{threadInfo=>

        val name = threadInfo.getThreadName
        val matcher = pattern.matcher(name)
        val name2=
        if(!matcher.matches())
          name
        else{
          matcher.group(1)


        }


        val threadStates = threadInfo.getThreadState
        (name2 , threadStates)
      }.groupBy{x=>x}.mapValues(_.length).toSeq.sortBy{case((name,state),_)=> (name,state.ordinal())}map{case((name,status),size)=>
        s"name:${name}\tstatus:${status}\tsize:${size}"
      }


    //debug("thread",listThreadParsed)
    logWrite(logger = logThread,listThreadParsed)

    val listHeapParsed=
      memoryPoolMxBeans.asScala.map{aMemoryPoolMxBean=>
      val name = aMemoryPoolMxBean.getName
      val current = aMemoryPoolMxBean.getUsage
      val memoryType = aMemoryPoolMxBean.getType.toString

      val committed = current.getCommitted
      val used  = current.getUsed
      val init = current.getInit
      (name,memoryType,init,used,committed)
    }.sortBy(_._1).map{case(name,memType,init,used,commited)=>
        s"name:${name}\ttype:${memType}\tused:${used}"

      }

    //debug("heap",listHeapParsed)
    logWrite(logHeap,listHeapParsed)

  }


  def monitor(): Unit ={

    jvm()
    nginx()
  }

}

