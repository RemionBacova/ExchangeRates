package controllers

import javax.inject.{Inject, Singleton}
import models.{Rate, RatesCalculator}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, ControllerComponents}


  @Singleton
  class RatesCalculatorController  @Inject()(cc: ControllerComponents, ratesCalculator:RatesCalculator) extends AbstractController(cc) {

    implicit val Writes = new Writes[Rate] {
      def writes(rate: Rate) = Json.obj(
        "FromCurrency" -> rate.currencyFromCode,
        "ToCurrency" -> rate.currencyToCode,
        "Rate" -> rate.rate,
      )
    }
    def getNotProvidedFromDB() = Action{
      Ok("Reading from Db with not provided : " + ( if(ratesCalculator.getNotProvidedFromDB) "OK"  else "Not Ok"))
    }

    def getData() = Action{
      Ok("Reading from Provider procedure started : " + ( if(ratesCalculator.getData) "OK"  else "Not Ok"))
    }

    def combine() = Action{
      Ok("Reading from Provider procedure started : " + ( if(ratesCalculator.combine) "OK"  else "Not Ok"))
    }

    def  publishFetched = Action{
      Ok(Json.toJson(ratesCalculator.publishFetched.toList))
    }

    def  publishCombinations = Action{
      Ok(Json.toJson(ratesCalculator.publishCombinations().toList))
    }


    def  publishNotProvided = Action{
      Ok(Json.toJson(ratesCalculator.publishNotProvided().toList))
    }
  }
