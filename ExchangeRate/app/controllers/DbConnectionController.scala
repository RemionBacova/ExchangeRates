package controllers
import javax.inject.{Inject, Singleton}
import models.{Currency, DbConnection, Rate}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class DbConnectionController  @Inject()(cc: ControllerComponents, dbConnection: DbConnection) extends AbstractController(cc)
{
  def setDbConString(providerCode:String,datasourceClass:String,driver:String,url:String)   = Action{
    Ok{  s"Conection Data of db: ($providerCode,$datasourceClass,$driver,$url) Add Process executed : " +
      (if(dbConnection.setDbConString(providerCode,datasourceClass,driver,url)) "Ok" else "Not Ok")
    }
  }

  def getProviderBaseCurr() = Action{
    Ok(dbConnection.getProviderBaseCurr())
  }

  def getProviderCurrencies() = Action{
    implicit val Writes = new Writes[Currency] {
      def writes(currency: Currency) = Json.obj(
        "Code" -> currency.currencyCode,
        "Name" -> currency.currencyName,
        "Decimal" -> currency.currencyDecimal,
        "IsBase" -> currency.isBase
      )
    }
    Ok(Json.toJson(dbConnection.getProviderCurrencies.toList))
  }

  def getProviderURL () = Action{
    Ok(dbConnection.getProviderURL)
  }

  def getProviderTimeout () = Action{
    Ok(dbConnection.getProviderTimeout.toString)
  }

  def getOtherRatesNotProvided() = Action{
    implicit val Writes = new Writes[Rate] {
      def writes(list: Rate) = Json.obj(
        "CodeFrom" -> list.currencyFromCode,
        "CodeTo" -> list.currencyToCode,
        "Rate" -> list.rate
      )
    }
    Ok(Json.toJson(dbConnection.getOtherRatesNotProvided.toList))
  }
}
