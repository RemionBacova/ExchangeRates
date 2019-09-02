package services

import javax.inject._
import models.{ClientsAPI, Currencies, Rate, RatesCalculator, RatesFeatcher}

trait Processes {
  def process1(): Unit

  def process2(): Unit

  def process3(): Unit

  def process4(): Unit
  def process5(): Unit

  def getIntervalProc1: Int

  def getIntervalProc2: Int

  def getIntervalProc3: Int

  def getIntervalProc4: Int
  def getIntervalProc5: Int
  //for test and controll
  def showRun(): String

  def InterpretListCheck(): String

  def CalculatorCheckFetched(): List[Rate]

  def CalculatorCheckCalculated(): List[Rate]

}


@Singleton
class ProcessManager @Inject() (currencies:Currencies, ratesFeatcher: RatesFeatcher, ratesCalculator: RatesCalculator, clientsAPI: ClientsAPI) extends Processes {
  private var execTimesProc1: Int = 0
  private var execTimesProc2: Int = 0
  private var execTimesProc3: Int = 0
  private var execTimesProc4: Int = 0
  private var execTimesProc5: Int = 0
  private var intervalProc1: Int = 10
  private var intervalProc2: Int = 10
  private var intervalProc3: Int = 10
  private var intervalProc4: Int = 10
  private var intervalProc5: Int = 10
  //execution repetition functions session#
  override def process1(): Unit = {
    execTimesProc1 = execTimesProc1 + 1
    currencies.getListFromDB  //get currencies data from the db
  }

  override def process2(): Unit = {
    execTimesProc2 = execTimesProc2 + 1
    ratesFeatcher.functionToCall //fetch data from provider
  }

  override def process3(): Unit = {
    execTimesProc3 = execTimesProc3 + 1
    ratesCalculator.getData //
  }

  override def process4(): Unit = {
    execTimesProc4 = execTimesProc4 + 1
    ratesCalculator.combine
  }

  override def process5(): Unit = {
    execTimesProc5 = execTimesProc5 + 1
    clientsAPI.SetRatesFromCalculator
  }
  //execution repetition functions session#

  override def getIntervalProc1: Int = intervalProc1

  override def getIntervalProc2: Int = intervalProc2

  override def getIntervalProc3: Int = intervalProc3

  override def getIntervalProc4: Int = intervalProc4

  override def getIntervalProc5: Int = intervalProc5

  override def showRun(): String = {
    "Process1 Executed : " + execTimesProc1 + " times and Process2 Executed : " + execTimesProc2 + " times." +
      "Process3 Executed : " + execTimesProc3 + " times and Process4 Executed : " + execTimesProc4 + " times. and Process5 Executed : "  + execTimesProc5 + " times."
  }

  override def InterpretListCheck(): String = ratesFeatcher.InterpretListCheck()//for testing during dev
  override def CalculatorCheckFetched(): List[Rate] = ratesCalculator.publishFetched()//for testing during dev
  override def CalculatorCheckCalculated(): List[Rate] = ratesCalculator.publishCombinations()//for testing during dev
}