package models

import javax.inject.Singleton

import scala.util.Try

//dummy class for connection
case class properties  (
                          driver : String,
                          url : String,

                       )

case class   databaseUrl (
                            dataSourceClass :String,
                            dbProperties : properties
                         )

case class ObjectData (
                        providerCode: String,

                        databaseUrlData : databaseUrl
                      )

//still dummy with data generated on object
trait DbConnectionTrait {
  def setDbConString (providerCode:String,datasourceClass:String,dirver:String,url:String):Boolean
  def getProviderBaseCurr():  String
  def getProviderCurrencies() : List[Currency]
  def getProviderURL : String
  def getProviderTimeout : Int
  def getOtherRatesNotProvided:List[Rate]
}

@Singleton
class DbConnection extends  DbConnectionTrait {
  var objectData = new ObjectData("", new databaseUrl("", new properties("", "")))

  override def setDbConString(providerCode: String, datasourceClass: String, driver: String, url: String):Boolean = {
    Try(
    objectData = new ObjectData(providerCode, new databaseUrl(datasourceClass, new properties(driver, url)))
    ).isSuccess
  }

  override def getProviderBaseCurr(): String = {
    "EUR"//dummy data
  }

  override def getProviderCurrencies(): List[Currency] = {
    List(
      Currency("EUR", "Euro", 2, isBase = true),
      Currency("USD", "US Dollar", 2, isBase = false),
      Currency("CAD", "US Dollar", 2, isBase = false),
      Currency("HKD", "US Dollar", 2, isBase = false),
      Currency("ISK", "US Dollar", 2, isBase = false),
      Currency("PHP", "US Dollar", 2, isBase = false),
      Currency("DKK", "US Dollar", 2, isBase = false),
      Currency("HUF", "US Dollar", 2, isBase = false),
      Currency("CZK", "US Dollar", 2, isBase = false),
      Currency("AUD", "US Dollar", 2, isBase = false),
      Currency("RON", "US Dollar", 2, isBase = false),
      Currency("SEK", "US Dollar", 2, isBase = false),
      Currency("IDR", "US Dollar", 2, isBase = false),
      Currency("INR", "US Dollar", 2, isBase = false),
      Currency("BRL", "US Dollar", 2, isBase = false),
      Currency("RUB", "US Dollar", 2, isBase = false),
      Currency("HRK", "US Dollar", 2, isBase = false),
      Currency("JPY", "US Dollar", 2, isBase = false),
      Currency("CHF", "US Dollar", 2, isBase = false),
      Currency("SGD", "US Dollar", 2, isBase = false),
      Currency("PLN", "US Dollar", 2, isBase = false),
      Currency("BGN", "US Dollar", 2, isBase = false),
      Currency("TRY", "US Dollar", 2, isBase = false),
      Currency("CNY", "US Dollar", 2, isBase = false),
      Currency("NOK", "US Dollar", 2, isBase = false),
      Currency("NZD", "US Dollar", 2, isBase = false),
      Currency("ZAR", "US Dollar", 2, isBase = false),
      Currency("USD", "US Dollar", 2, isBase = false),
      Currency("MXN", "US Dollar", 2, isBase = false),
      Currency("ILS", "US Dollar", 2, isBase = false),
      Currency("GBP", "US Dollar", 2, isBase = false),
      Currency("KRW", "US Dollar", 2, isBase = false),
      Currency("MYR", "US Dollar", 2, isBase = false)
    )
  }

  override def getOtherRatesNotProvided: List[Rate] = {
    List(
      Rate("EUR", "LEK", 120.5),
      Rate("EUR", "BTC", 1 / 8895.19)
    ) //dummy data
  }


  override def getProviderURL: String = "https://api.exchangeratesapi.io/latest" //dummy data
  override def getProviderTimeout: Int = 10 //dummy data get timeout from db

}