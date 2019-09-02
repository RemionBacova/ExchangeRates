package models
import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import net.liftweb.json.{DefaultFormats, _}
import play.api.libs.ws.WSClient
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.io.Source.fromURL
import scala.util.Try



trait Behaviour {
  def initiateReading():Future[String]
  def InterpretList() : List[(String, Double)]
  def InterpretListCheck(): String
  def functionToCall : Boolean
}

@Singleton
class  RatesFeatcher  @Inject() (actorSystem: ActorSystem, ws: WSClient,dbConnection: DbConnection)(implicit exec: ExecutionContext) extends  Behaviour {
  var jsonVarible: String = ""

  override def initiateReading(): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(dbConnection.getProviderTimeout.seconds) {
      if (dbConnection.getProviderURL == "") promise.success("No Url for Provider")
      else promise.success(fromURL(dbConnection.getProviderURL).mkString)
    }(actorSystem.dispatcher)
    promise.future
  }

  override def functionToCall :Boolean = {
    Try {
      jsonVarible = Await.result(initiateReading, (dbConnection.getProviderTimeout+1) seconds) //set await +1 from timeout
    }.isSuccess
  }

  override def InterpretList(): List[(String, Double)] = {
    val EmptyList: List[(String, Double)] = List()
    implicit val formats = DefaultFormats
    if (Try(parse(jsonVarible)).isSuccess) {
      var json = parse(jsonVarible)
      (for {JObject(o) <- json; JField((x), JDouble(y)) <- o} yield (x, y))
    }
    else {
      EmptyList
    }
  }

  override def InterpretListCheck(): String = jsonVarible
}

