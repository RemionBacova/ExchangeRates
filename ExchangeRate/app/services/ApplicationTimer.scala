package services

import java.time.{Clock, Instant}
import javax.inject._
import play.api.Logger
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

@Singleton
class ApplicationTimer @Inject() (clock: Clock, appLifecycle: ApplicationLifecycle,actorSystem: ActorSystem,manager:Processes)(implicit ec: ExecutionContext) {

  // This code is called when the application starts.
  private val start: Instant = clock.instant
  Logger(s"ApplicationTimer demo: Starting application at $start.")

  actorSystem.scheduler.schedule(initialDelay = 0.seconds, interval = manager.getIntervalProc1.seconds) {
    manager.process1() // starts currencies initialization and default data from db get
  }
  actorSystem.scheduler.schedule(initialDelay = 5.seconds, interval = manager.getIntervalProc2.seconds) {
    manager.process2() //fetches data from provider
  }
  actorSystem.scheduler.schedule(initialDelay = 10.seconds, interval = manager.getIntervalProc3.seconds) {
    manager.process3() // imports data into the ratescalculator model
  }

  actorSystem.scheduler.schedule(initialDelay = 15.seconds, interval = manager.getIntervalProc4.seconds) {
   manager.process4() // combines and calculates rates
  }
  actorSystem.scheduler.schedule(initialDelay = 20.seconds, interval = manager.getIntervalProc5.seconds) {
    manager.process5() // sets rates from the calculation into memory for the clients api model
  }

  // When the application starts, register a stop hook with the
  // ApplicationLifecycle object. The code inside the stop hook will
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Logger(s"ApplicationTimer demo: Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }
}
