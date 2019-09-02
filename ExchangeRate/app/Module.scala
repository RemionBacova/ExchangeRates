import java.time.Clock

import com.google.inject.AbstractModule
import models.{ClientsAPI, Currencies, DbConnection, Rates, RatesFeatcher}
import play.libs.akka.AkkaGuiceSupport
import services.{ApplicationTimer, ProcessManager, Processes}
/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule with AkkaGuiceSupport{


  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()

    bind(classOf[RatesFeatcher]).asEagerSingleton()

    bind(classOf[Rates]).asEagerSingleton()
    bind(classOf[Currencies]).asEagerSingleton()
    bind(classOf[DbConnection]).asEagerSingleton()
    bind(classOf[ClientsAPI]).asEagerSingleton()

    // Set Processes as implementation of ProcessManager
    bind(classOf[Processes]).to(classOf[ProcessManager])
  }

}
