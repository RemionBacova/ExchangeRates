package controllers
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import services.ProcessManager

@Singleton
class ProcessController @Inject() (cc: ControllerComponents, manager: ProcessManager) extends AbstractController(cc) {

  def execStatus = Action { Ok(manager.showRun().toString) }

}
