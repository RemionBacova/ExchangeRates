package models
import javax.inject.{Inject, Singleton}

import scala.util.Try

case class Rate
                      (
                      val currencyFromCode : String,
                      val currencyToCode : String,
                      val rate : BigDecimal
                      )

trait RatesTrait {
  def generateCombinations:Boolean
  def GetRates : List[Rate]
  def GetRatesOfCurrency (codeFrom: String): List[Rate]
  def GetRatesOfCurrencies(codeFrom: String, codeTo: String) : Option[Rate]
  def AddRate (codeFrom: String, codeTo: String, rate: Double): Boolean

}

@Singleton
class  Rates @Inject()(currencies: Currencies)extends  RatesTrait
{
  var ratesList: List[Rate] = List()

  override def generateCombinations:Boolean = {
    Try(ratesList = currencies.getCurrencies.map(i => i.currencyCode).combinations(2).map{ case Seq(x, y) => (x, y) }.toList.map(k=> Rate(k._1,k._2,0))).isSuccess
 }

  override def GetRates: List[Rate] = ratesList

  override def GetRatesOfCurrency(codeFrom: String): List[Rate] = {
    ratesList.filter(p=>p.currencyFromCode == codeFrom)
  }

  override def GetRatesOfCurrencies(codeFrom: String, codeTo: String): Option[Rate] = {
    ratesList.find(p => p.currencyFromCode==codeFrom && p.currencyToCode==codeTo)
  }

  override def AddRate(codeFrom: String, codeTo: String, rate: Double): Boolean = {
   Try( if(!ratesList.exists(p => p.currencyFromCode ==codeFrom && p.currencyToCode==codeTo)){
      ratesList ::= Rate(codeFrom,codeTo,rate)
    }
    else{
      ratesList = ratesList.map(p => if(p.currencyFromCode ==codeFrom && p.currencyToCode==codeTo) Rate(codeFrom,codeTo,rate) else p)
    }
   ).isSuccess
  }
}