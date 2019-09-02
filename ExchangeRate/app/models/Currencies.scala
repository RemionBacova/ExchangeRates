package models

import javax.inject.Inject
import javax.inject.Singleton

import scala.util.Try

case class Currency (
                       currencyCode : String,
                       currencyName : String,
                       currencyDecimal : Int,
                       isBase : Boolean
                    )

trait CurrenciesBehaviour  {
  def getListFromDB :Boolean
  def getCurrencies: List[Currency]
  def getCurrencyByCode(Code: String): Option[Currency]
  def getCurrencyBase: Option[Currency]
  def addCurrency(currencyCode: String, currencyName: String, currencyDecimal:Int, isBase: Boolean): Boolean
  def setCurrencyBaseByCode(Code: String): Boolean
}

@Singleton
class Currencies  @Inject()(dbConnection: DbConnection) extends CurrenciesBehaviour {


  def qualifyForBase(p: Currency)(pred: Currency => Boolean):Currency = if (pred(p)) p.copy(isBase = true) else p.copy(isBase = false)
  var currencies: List[Currency] = List[Currency]()


  override def getCurrencies: List[Currency] = currencies
  override def getCurrencyByCode(Code: String): Option[Currency] = currencies.find(_.currencyCode == Code)
  override def getCurrencyBase: Option[Currency] =  currencies.find(_.isBase == true)

  override def addCurrency(currencyCode: String, currencyName: String, currencyDecimal:Int, isBase: Boolean): Boolean = {
    Try(
      if(!currencies.exists(_.currencyCode == currencyCode)) {
      currencies ::= Currency( currencyCode, currencyName, currencyDecimal, isBase)
      if (isBase) setCurrencyBaseByCode(currencyCode)
      }
    ).isSuccess
  }

  override def setCurrencyBaseByCode(Code: String): Boolean = {
    Try(if(currencies.exists(_.currencyCode==Code)) {
      currencies = currencies.map(qualifyForBase(_)(_.currencyCode == Code))
    }).isSuccess

  }

  override def getListFromDB : Boolean = {
    Try(currencies = dbConnection.getProviderCurrencies()).isSuccess
  }

}