  package controllers
  import javax.inject.{Inject, Singleton}
  import models.{ RatesFeatcher}
  import play.api.mvc.{AbstractController, ControllerComponents}

  @Singleton
  class RatesFeatcherController  @Inject()(cc: ControllerComponents, ratesFeatcher:RatesFeatcher) extends AbstractController(cc) {

    def functionToCall() = Action{
      Ok("Reading from Provider procedure started : " + ( if(ratesFeatcher.functionToCall) "OK"  else "Not Ok"))
    }

    def InterpretListCheck() = Action {
      Ok{ ratesFeatcher.InterpretListCheck}
    }
    def InterpretList() = Action {
      Ok{ ratesFeatcher.InterpretList.toString()}
    }
  }