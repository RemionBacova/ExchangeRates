package models


import javax.inject.{Inject, Singleton}

import scala.util.Try

trait Operations {
  def getData():Boolean
  def getNotProvidedFromDB:Boolean
  def combine():Boolean
  def publishFetched():List[Rate]
  def publishCombinations():List[Rate]
  def publishNotProvided():List[Rate]
}

@Singleton
class RatesCalculator  @Inject() (ratesFeatcher: RatesFeatcher,dbConnection: DbConnection) extends Operations{


  private var fetchedRates:List[Rate] = List()
  private var notProvided:List[Rate]=List()
  private var combinations :List[Rate] = List()

  override def getData():Boolean ={
    Try(   fetchedRates = ratesFeatcher.InterpretList().map(i => Rate("EUR", i._1, i._2))).isSuccess //should be with provider get base from db
  }
  override def getNotProvidedFromDB:Boolean = {
    Try(   notProvided = dbConnection.getOtherRatesNotProvided).isSuccess
  }

  override def combine():Boolean = {
    val merged = fetchedRates   :::  notProvided
   Try( combinations =   merged.combinations(2).map{ case Seq(x, y) => (x, y) }.toList.map(k=> Rate((k._1).currencyToCode,(k._2).currencyToCode,(k._2).rate/(k._1).rate))).isSuccess
  }

  override def publishNotProvided():List[Rate] = notProvided
  override def publishFetched():List[Rate] = fetchedRates
  override def publishCombinations():List[Rate] = combinations
}
