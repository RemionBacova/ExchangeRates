package controllers
import javax.inject.{Inject, Singleton}
import models.{Currencies, Currency}
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents}


@Singleton
class CurrenciesController @Inject()(cc: ControllerComponents,currencies: Currencies) extends AbstractController(cc) {



  implicit val Writes = new Writes[Currency] {
    def writes(currency: Currency) = Json.obj(
"Code" -> currency.currencyCode,
      "Name" -> currency.currencyName,
      "Decimal" -> currency.currencyDecimal,
      "IsBase" -> currency.isBase
    )
  }


  def getListFromDB() = Action{
    Ok("Get Currencies of Provider  Action stared : " + ( if(currencies.getListFromDB) "OK"  else "Not Ok"))
  }


  def getCurrencies   = Action{
    Ok{ Json.toJson(currencies.getCurrencies)}
  }
  def getCurrencyBase  = Action{
    Ok(Json.toJson(currencies.getCurrencyBase.toList))
  }
  def getCurrencyByCode (Code:String) = Action{
    Ok(Json.toJson(currencies.getCurrencyByCode(Code).toList))
  }

  def setCurrencyBaseByCode(Code:String) = Action{
    Ok("Currency: "+ Code + " set as base for API executed : " + ( if(currencies.setCurrencyBaseByCode(Code)) "OK"  else "Not Ok"))
  }

  def addCurrency (currencyCode: String, currencyName: String, currencyDecimal:Int, isBase: Boolean) = Action{
    Ok(
      s"Currency with Data: ($currencyCode,$currencyName,$currencyDecimal) Add Process executed : " +
        ( if(currencies.addCurrency(currencyCode,currencyName,currencyDecimal,isBase)) "OK" else "Not Ok")
    )
  }
}
