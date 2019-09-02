package controllers
import javax.inject.{Inject, Singleton}
import models.{Rate, Rates}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}


@Singleton
class RatesController  @Inject()(cc: ControllerComponents,rates: Rates) extends AbstractController(cc) {

  implicit val Writes = new Writes[Rate] {
    def writes(rate: Rate) = Json.obj(
      "FromCurrency" -> rate.currencyFromCode,
      "ToCurrency" -> rate.currencyToCode,
      "Rate" -> rate.rate,
    )
  }


  def generateCombinations = Action{
   Ok("Genration of Combinations Operation Executed : " + ( if(rates.generateCombinations) "OK"  else "Not Ok"))
  }


  // def GetRates : List[Rate]
  def  GetRates = Action{
    Ok(Json.toJson(rates.GetRates.toList))
  }

  // def GetRatesOfCurrency (codeFrom: String): List[Rate]
  def  GetRatesOfCurrency(Code : String) = Action {
    Ok(Json.toJson(rates.GetRatesOfCurrency(Code).toList))
  }
  //  def GetRatesOfCurrencies(codeFrom: String, codeTo: String) : Option[Rate]
  def  GetRatesOfCurrencies(codeFrom : String,codeTo:String) = Action {
    Ok(Json.toJson(rates.GetRatesOfCurrencies(codeFrom,codeTo).toList))
  }
  // def AddRate (codeFrom: String, codeTo: String, rate: BigDecimal): List[Rate]
  def  AddRate(codeFrom : String,codeTo:String,rate: Double) = Action {
    Ok("Genration of Combinations Operation Executed : " + ( if(rates.AddRate(codeFrom,codeTo,rate)) "OK"  else "Not Ok"))
  }
}
