package controllers
import javax.inject.{Inject, Singleton}
import models.{ClientsAPI, Rate}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}



@Singleton
class APIController @Inject()(cc: ControllerComponents,clientsAPI: ClientsAPI) extends AbstractController(cc) {

  implicit val Writes = new Writes[Rate] {
    def writes(rate: Rate) = Json.obj(
      "FromCurrency" -> rate.currencyFromCode,
      "ToCurrency" -> rate.currencyToCode,
      "Rate" -> rate.rate,
    )
  }

  def latestValues = Action {

    Ok(Json.toJson(clientsAPI.latestValues))
  }

  def latestWithBase(Code: String) = Action {
    Ok(Json.toJson(clientsAPI.latestWithBase(Code)))
  }

  def latestForCouple(codeFrom: String, codeTo: String) = Action {
    Ok(Json.toJson(clientsAPI.latestForCouple(codeFrom, codeTo)))
  }

  def SetRatesFromCalculator() = Action{
    Ok("Geting Data from Calculator : " + ( if(clientsAPI.SetRatesFromCalculator) "OK"  else "Not Ok"))
  }
}
