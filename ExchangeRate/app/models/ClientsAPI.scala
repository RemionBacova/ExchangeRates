package models

import javax.inject.{Inject, Singleton}

import scala.util.Try


  //https://api.exchangeratesapi.io/latest return with base of pc
  //https://api.exchangeratesapi.io/latest?base=USD  return withspecific base
  //https://api.exchangeratesapi.io/latest?symbols=USD,GBP return of the specific



trait OperationsOfAPi {
  def latestValues():List[Rate]
  def latestWithBase(currCode: String):List[Rate]
  def latestForCouple(currCodeFrom:String, currCodeTo:String):Option[Rate]
  def SetRatesFromCalculator():Boolean
}

@Singleton
class ClientsAPI  @Inject()(calculator: RatesCalculator,currencies: Currencies) extends OperationsOfAPi {

  var ExchangeRatesInMemory :List[Rate] = List()

  override def latestValues(): List[Rate] = {
    val option = currencies.getCurrencyBase.getOrElse(Currency("EUR","Euro",isBase = true,currencyDecimal = 2))
    ExchangeRatesInMemory.filter(p => p.currencyFromCode == option.currencyCode)
  }

  override def latestWithBase(currCode: String): List[Rate] = {
    ExchangeRatesInMemory.filter(p => p.currencyFromCode == currCode )
  }

  override def latestForCouple(currCodeFrom: String, currCodeTo: String): Option[Rate] = {
    ExchangeRatesInMemory.find(p => p.currencyFromCode == currCodeFrom && p.currencyToCode == currCodeTo)
  }

  override def SetRatesFromCalculator(): Boolean = {
    Try( ExchangeRatesInMemory = calculator.publishCombinations()).isSuccess
  }

}
